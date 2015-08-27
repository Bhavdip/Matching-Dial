package com.example.androidcustomdial;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

public class AdvProgressBar extends View {

	public static final int SIZE = 300;
	public static final float RTOP = 0.0f;
	public static final float RLEFT = 0.0f;
	
	public static final float RRIGHT = 1.0f;
	public static final float RBOTTOM = 1.0f;
	
	public static final float CENTER = 0.5f;
	public static final float CENTER_CIRCLE_WIDTH = 0.2f;
	public static final float PROGRESS_CIRCLE_WIDTH = 0.188f;

	public static final float SCALE_START_VALUE = 0.0f;
	public static final float SCALE_END_VALUE = 100.0f;

 	public static final float SCALE_POSITION = 0.17f;

	public static final int SWEEP_ANGLE_DEG = 300;
	public static final int START_ANGLE_DEG = 120;
	
	public static final int PROGRESS_START_VALUE = 0;
	public static final int PROGRESS_END_VALUE = 100;

	private Bitmap mBackground;

	private RectF mTopCircleRect;
	private RectF mProgrssFillRect;
	
	private float mCenterCircleWidth;
	private float mProgressCircleWidth;
	private float mScalePosition;
	
	private Paint mBackgroundPaint;
	
	private double mPointAngel;
	private int mValue;
	private int mPoint;
	private Context mContext;

	public AdvProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;
		readAttrs();
		init();
	}

	public AdvProgressBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		readAttrs();
		init();
	}

	public AdvProgressBar(Context context) {
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
		default:
			return getDefaultDimension();
		}
	}

	private int getDefaultDimension() {
		return SIZE;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		drawBackground(canvas);
		final float scale = Math.min(getWidth(), getHeight());
		canvas.scale(scale, scale);
		canvas.translate((scale == getHeight()) ? ((getWidth() - scale) / 2)
				/ scale : 0,
				(scale == getWidth()) ? ((getHeight() - scale) / 2) / scale : 0);

		if (mValue != PROGRESS_START_VALUE) {
			canvas.drawArc(mProgrssFillRect, START_ANGLE_DEG, mPoint- START_ANGLE_DEG, false, getDefaultProgressPaint());
		} 
	}

	private void drawBackground(final Canvas canvas) {
		if (null != mBackground) {
			canvas.drawBitmap(mBackground, 0, 0, mBackgroundPaint);
		}
	}

	@Override
	protected void onSizeChanged(final int w, final int h, final int oldw,final int oldh) {
		drawGauge();
	}

	private void readAttrs() {
		mProgressCircleWidth = PROGRESS_CIRCLE_WIDTH;
		mCenterCircleWidth = CENTER_CIRCLE_WIDTH;
		mScalePosition = SCALE_POSITION;
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

		initDrawingRects();
		
		initDrawingTools();
		
	}

	public void initDrawingRects() {
		// The drawing area is a rectangle of width 1 and height 1,
		// where (0,0) is the top left corner of the canvas.
		// Note that on Canvas X axis points to right, while the Y axis points
		// downwards.
		mTopCircleRect = new RectF(RLEFT, RTOP,RRIGHT, RBOTTOM);
		mProgrssFillRect = new RectF(mTopCircleRect.left + mProgressCircleWidth, mTopCircleRect.top+ mProgressCircleWidth, mTopCircleRect.right- mProgressCircleWidth, mTopCircleRect.bottom- mProgressCircleWidth);
	}

	private void initDrawingTools() {
		mBackgroundPaint = new Paint();
		mBackgroundPaint.setFilterBitmap(true);
	}

	private void drawGauge() {
		if (null != mBackground) {
			// Let go of the old background
			mBackground.recycle();
		}
		// Create a new background according to the new width and height
		mBackground = Bitmap.createBitmap(getWidth(), getHeight(),Bitmap.Config.ARGB_8888);
		final Canvas canvas = new Canvas(mBackground);
		final float scale = Math.min(getWidth(), getHeight());
		canvas.scale(scale, scale);
		canvas.translate((scale == getHeight()) ? ((getWidth() - scale) / 2)/ scale : 0,(scale == getWidth()) ? ((getHeight() - scale) / 2) / scale : 0);
	}

	private Paint getDefaultProgressPaint() {
		final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		int myColor = mContext.getResources().getColor(R.color.color_global_green);
		paint.setColor(myColor);
		paint.setStyle(Paint.Style.STROKE);
		paint.setStrokeCap(Paint.Cap.BUTT);
		paint.setStrokeWidth(0.24f);
		return paint;
	}

	public void setValue(int value) {
		mValue = value;
		mPoint = (int) (START_ANGLE_DEG + (mValue - PROGRESS_START_VALUE)* mPointAngel);
		invalidate();
	}

	public int getValue() {
		return mValue;
	}
}