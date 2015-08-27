package com.example.androidcustomdial;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Custom check my tension dial, instead of use the images of dial we decided to
 * draw the view and update it dynamically by calling in Take Test Fragment you
 * can find it in Take Test fragment layout file, also calling inside the take
 * test fragment java file.
 * 
 * @author CreoleStudios
 * 
 */
public class AdvanceProgressBar extends View{
	
	private final String TAG = AdvanceProgressBar.class.getCanonicalName();
	
	public final int SIZE = 250;
	public final float RTOP = 0.0f;
	public final float RLEFT = 0.0f;
	public final float RRIGHT = 1.0f;
	public final float RBOTTOM = 1.0f;
	
	public static final int SCALE_DIVISIONS = 5;
	public static final int SCALE_SUBDIVISIONS = 5;
	
	public static final float SCALE_START_VALUE = 0.0f;
    public static final float SCALE_END_VALUE = 100.0f;
    
    public static final float SCALE_START_ANGLE = 30.0f;
    
    //public static final float SCALE_POSITION = 0.025f;
	public static final float SCALE_POSITION = 0.17f;
	
	public static final int SWEEP_ANGLE_DEG = 300;
	public static final int START_SWEEP_ANGLE_DEG = 0;
	public static final int START_ANGLE_DEG = 120;
	
	public static final int START_ABORT_ANGLE_DEG = 60;
	public static final int END_ABORT_ANGLE_DEG = 60;
	
	public static final int PROGRESS_START_VALUE = 0;
    public static final int PROGRESS_END_VALUE = 100;
    
	private Bitmap mBackground;
	
	private RectF mTopCircleRect;
	private RectF mCenterCircleRect;
	private RectF mLineScaleRect;
	
	//private float mCenterCircleWidth;
	private float mScalePosition;
	private int mDivisions;
    private int mSubdivisions;
    
    private float mScaleStartAngle;
    private float mScaleRotation;
    
    private float mSubdivisionAngle;
    
	private Paint mBackgroundPaint;
	private Paint mTopCirclePaint;
	private Paint mCenterCirclePaint;
	
	private double mPointAngel;
	private int mValue;
	private int mPoint;
	private Context mContext;
	
	public AdvanceProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;
		readAttrs();
		init();
	}

	public AdvanceProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		readAttrs();
		init();
	}

	public AdvanceProgressBar(Context context) {
		super(context);
		mContext = context;
		readAttrs();
		init();
	}

	@Override
	protected void onMeasure(final int widthMeasureSpec,
			final int heightMeasureSpec) {
		final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		final int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		final int chosenWidth = chooseDimension(widthMode, widthSize);
		final int chosenHeight = chooseDimension(heightMode, heightSize);
		setMeasuredDimension(chosenWidth, chosenHeight);
	}

	private int chooseDimension(final int mode, final int size) {
		switch (mode) {
		case View.MeasureSpec.AT_MOST:
		case View.MeasureSpec.EXACTLY:
			return size;
		case View.MeasureSpec.UNSPECIFIED:
			return getDefaultDimension();
		default:
			return getDefaultDimension();
		}
	}

	private int getDefaultDimension() {
		return SIZE;
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		//drawBackground(canvas);
		//final float scale = Math.min(getWidth(), getHeight());
        //canvas.scale(scale, scale);
     /*   canvas.translate((scale == getHeight()) ? ((getWidth() - scale) / 2) / scale : 0
                , (scale == getWidth()) ? ((getHeight() - scale) / 2) / scale : 0);*/
        if(!isInEditMode()){
        	drawGauge(canvas);
            //drawScale(canvas);	
        }
	}
	
	private void drawBackground(final Canvas canvas) {
		if (mBackground != null) {
			canvas.drawBitmap(mBackground, 0, 0, mBackgroundPaint);
		}
	}
	
	@Override
	protected void onSizeChanged(final int w, final int h, final int oldw,
			final int oldh) {
		if(!isInEditMode()){
				
		}
		
	}
	
	private void readAttrs() {
		//mCenterCircleWidth = CENTER_CIRCLE_WIDTH;
		mDivisions = SCALE_DIVISIONS;
		mSubdivisions = SCALE_SUBDIVISIONS;
		mScalePosition = SCALE_POSITION;
		mScaleStartAngle = SCALE_START_ANGLE;
		
	}
	private void init() {
		// TODO Why isn't this working with HA layer?
		// The needle is not displayed although the onDraw() is being triggered
		// by invalidate()
		// calls.
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			setLayerType(View.LAYER_TYPE_SOFTWARE, null);
		}
		
		mPointAngel = ((double) Math.abs(SWEEP_ANGLE_DEG) / (PROGRESS_END_VALUE - PROGRESS_START_VALUE));
		mValue = PROGRESS_START_VALUE;
	    mPoint = START_ANGLE_DEG;
		
	    if(!isInEditMode()){
	    	initDrawingRects();
			initDrawingTools();
			initScale();	
	    }
		
	}

	public void initDrawingRects() {
		mTopCircleRect = new RectF(RLEFT, RTOP, RRIGHT, RBOTTOM);
		mCenterCircleRect = new RectF(mTopCircleRect.left - mScalePosition,
				mTopCircleRect.top + mScalePosition, mTopCircleRect.right - mScalePosition,
				mTopCircleRect.bottom + mScalePosition);
		mLineScaleRect = new RectF(mTopCircleRect.left + mScalePosition, mTopCircleRect.top + mScalePosition, mTopCircleRect.right - mScalePosition,
				mTopCircleRect.bottom - mScalePosition);
	}

	private void initDrawingTools() {
		mBackgroundPaint = new Paint();
        mBackgroundPaint.setFilterBitmap(true);
		mTopCirclePaint = getDefaultTopPaint();
		mCenterCirclePaint = getDefaultInnerCirclePaint();
	}

	private void drawGauge(Canvas canvas) {
		/*if (null != mBackground) {
			// Let go of the old background
			mBackground.recycle();
		}
		// Create a new background according to the new width and height
		mBackground = Bitmap.createBitmap(getWidth(), getHeight(),Bitmap.Config.ARGB_8888);
		final Canvas canvas = new Canvas(mBackground);*/
		final float scale = Math.min(getWidth(), getHeight());
		canvas.scale(scale, scale);
		canvas.translate((scale == getHeight()) ? ((getWidth() - scale) / 2)/ scale : 0,(scale == getWidth()) ? ((getHeight() - scale) / 2) / scale : 0);

	    if(!isInEditMode()){
	    	drawRim(canvas);	
	    }
		
	}
	
	private void drawRim(final Canvas canvas) {
		//canvas.drawOval(mTopCircleRect, mTopCirclePaint);
		//canvas.drawOval(mCenterCircleRect, mCenterCirclePaint);
		
		canvas.drawArc(mCenterCircleRect, 0, 60, false, getHeighStress());
		canvas.drawArc(mCenterCircleRect, 60, 60, false, getDeepRelaxation());
		canvas.drawArc(mCenterCircleRect, 120, 60, false, getRelaxation());
		canvas.drawArc(mCenterCircleRect, 180, 60, false, getInBalance());
		canvas.drawArc(mCenterCircleRect, 240, 60, false, getInStress());
		canvas.drawArc(mCenterCircleRect, 300, 60, false, getHeighStress());
	}
	
	private void drawScale(final Canvas canvas) {
		canvas.save(Canvas.MATRIX_SAVE_FLAG);
		// On canvas, North is 0 degrees, East is 90 degrees, South is 180 etc.
		// We start the scale somewhere South-West so we need to first rotate
		// the canvas.
		canvas.rotate(mScaleRotation, 0.5f, 0.5f);

		final int totalTicks = mDivisions * mSubdivisions + 1;
		for (int i = 0; i < totalTicks; i++) {
			final float y1 = mLineScaleRect.top;
			final float y2 = y1 - 0.010f; // height of division
			final float y3 = y1 + 0.030f; // height of subdivision
			final Paint paint = getDefaultScalePaint();
			canvas.drawLine(0.5f, y2, 0.5f, y3, paint);
			canvas.rotate(mSubdivisionAngle, 0.5f, 0.5f);
		}
		canvas.restore();
	}
	 
	 
	private void initScale() {
		mScaleRotation = (SCALE_START_ANGLE + 180) % 360;
		mSubdivisionAngle = (360 - 2 * mScaleStartAngle) / (mDivisions * mSubdivisions);
	}
	public Paint getDefaultTopPaint() {
		final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		int myColor = mContext.getResources().getColor(R.color.color_global_low);
		paint.setColor(myColor);
		return paint;
	}
	
	private Paint getDefaultInnerCirclePaint() {
		final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setStyle(Paint.Style.FILL_AND_STROKE);
		int myColor = mContext.getResources().getColor(R.color.color_global_purpal);
		paint.setColor(myColor);
        return paint;
	}
	
	private Paint getDefaultScalePaint() {
		final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		int myColor = mContext.getResources().getColor(R.color.color_global_white);
		paint.setColor(myColor);
        paint.setStyle(Paint.Style.STROKE);
        //paint.setStrokeWidth(0.001f);
        paint.setStrokeWidth(0.014f);
        return paint;
	}
	
	public void setValue(int value) {
		mValue = value;
		mPoint = (int) (START_ANGLE_DEG + ((mValue - PROGRESS_START_VALUE)* mPointAngel));
		Log.d(TAG, String.format("InputValue : %d \t Points : %d",value,mPoint));
		invalidate();
	}
	
	public int getValue() {
		return mValue;
	}
	
	public float getLevelWidth(){
		return 0.19f;
	}
	
	private Paint getDefaultProgressPaint() {
		final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		int myColor = mContext.getResources().getColor(R.color.color_global_lightPurple);
		paint.setColor(myColor);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeCap(Paint.Cap.BUTT);
        paint.setStrokeWidth(0.19f);
        return paint;
	}
	
	private Paint getAbortTestPaint() {
		final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		int myColor = mContext.getResources().getColor(R.color.color_global_black);
		paint.setColor(myColor);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeCap(Paint.Cap.BUTT);
        paint.setStrokeWidth(0.19f);
        return paint;
	}
	
	private Paint getDeepRelaxation() {
		final Paint mPaints = new Paint();
		mPaints.setColor(Color.parseColor("#0D0D0D"));
		mPaints.setFlags(Paint.ANTI_ALIAS_FLAG);
		mPaints.setStrokeWidth(getLevelWidth());
		mPaints.setAntiAlias(true);
		mPaints.setStrokeCap(Paint.Cap.BUTT);
		mPaints.setStyle(Paint.Style.STROKE);
		return mPaints;
	}
	
	private Paint getRelaxation() {
		final Paint mPaints = new Paint();
		mPaints.setColor(Color.parseColor("#0072CF"));
		mPaints.setFlags(Paint.ANTI_ALIAS_FLAG);
		mPaints.setStrokeWidth(getLevelWidth());
		mPaints.setAntiAlias(true);
		mPaints.setStrokeCap(Paint.Cap.BUTT);
		mPaints.setStyle(Paint.Style.STROKE);
		return mPaints;
	}
	
	private Paint getInBalance() {
		final Paint mPaints = new Paint();
		mPaints.setColor(Color.parseColor("#02CE24"));
		mPaints.setFlags(Paint.ANTI_ALIAS_FLAG);
		mPaints.setStrokeWidth(getLevelWidth());
		mPaints.setAntiAlias(true);
		mPaints.setStrokeCap(Paint.Cap.BUTT);
		mPaints.setStyle(Paint.Style.STROKE);
		return mPaints;
	}
	
	private Paint getInStress() {
		final Paint mPaints = new Paint();
		mPaints.setColor(Color.parseColor("#FFDE00"));
		mPaints.setFlags(Paint.ANTI_ALIAS_FLAG);
		mPaints.setStrokeWidth(getLevelWidth());
		mPaints.setAntiAlias(true);
		mPaints.setStrokeCap(Paint.Cap.BUTT);
		mPaints.setStyle(Paint.Style.STROKE);
		return mPaints;
	}
	
	private Paint getHeighStress() {
		final Paint mPaints = new Paint();
		mPaints.setColor(Color.parseColor("#F73D04"));
		mPaints.setFlags(Paint.ANTI_ALIAS_FLAG);
		mPaints.setStrokeWidth(getLevelWidth());
		mPaints.setAntiAlias(true);
		mPaints.setStrokeCap(Paint.Cap.BUTT);
		mPaints.setStyle(Paint.Style.STROKE);
		return mPaints;
	}
}

