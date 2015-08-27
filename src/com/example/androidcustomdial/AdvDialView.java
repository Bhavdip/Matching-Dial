package com.example.androidcustomdial;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

public class AdvDialView extends View {

	int resource[] = { R.color.color_global_dial_gray,
			R.color.color_global_low, R.color.color_global_mild,
			R.color.color_global_moderate, R.color.color_global_high,
			R.color.color_global_severe };

	Paint[] mPaints = new Paint[10];
	
	int mRadius = 200;
	Context mContext;

	public static final int SIZE = 250;

	int deviceWidth = 0;
	int deviceHeight = 0;
	
	public static final float SCALE_POSITION = 0.17f;
	public static final float SCALE_START_ANGLE = 30.0f;
	public static final int SCALE_DIVISIONS = 5;
	public static final int SCALE_SUBDIVISIONS = 5;
	
	private float mScalePosition;
	private int mSubdivisions;
	private int mDivisions;
	private float mScaleStartAngle;
	private float mScaleRotation;
	private float mSubdivisionAngle;
	
	private final RectF rect = new RectF();
	private final RectF innerRect = new RectF();
	private RectF mLineScaleRect;
	
	public AdvDialView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mContext = context;
		setUp();
	}

	public AdvDialView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		setUp();
	}

	public AdvDialView(Context context) {
		super(context);
		mContext = context;
		setUp();
	}
	
	public int getStrokeWidth(){
		return (int)(mRadius/1.40);
	}
	
	private void initScale() {
		mScaleRotation = (SCALE_START_ANGLE + 180) % 360;
		//mDivisionValue = (mScaleEndValue - mScaleStartValue) / mDivisions;
	    //mSubdivisionValue = mDivisionValue / mSubdivisions;
		mSubdivisionAngle = (360 - 2 * mScaleStartAngle) / (mDivisions * mSubdivisions);
	}
	
	
	private void readAttrs() {
		mScalePosition = SCALE_POSITION;
		mSubdivisions = SCALE_SUBDIVISIONS;
		mDivisions = SCALE_DIVISIONS;
		mScaleStartAngle = SCALE_START_ANGLE;
	}
	
	private void setUp() {
		readAttrs();
		initScale();
		
		mPaints[0] = new Paint();
		mPaints[0].setColor(Color.parseColor("#0D0D0D"));
		mPaints[0].setFlags(Paint.ANTI_ALIAS_FLAG);
		mPaints[0].setStrokeWidth(getStrokeWidth());
		mPaints[0].setAntiAlias(true);
		mPaints[0].setStrokeCap(Paint.Cap.BUTT);
		mPaints[0].setStyle(Paint.Style.STROKE);

		mPaints[1] = new Paint();
		mPaints[1].setColor(Color.parseColor("#0072CF"));
		mPaints[1].setFlags(Paint.ANTI_ALIAS_FLAG);
		mPaints[1].setStrokeWidth(getStrokeWidth());
		mPaints[1].setAntiAlias(true);
		mPaints[1].setStrokeCap(Paint.Cap.BUTT);
		mPaints[1].setStyle(Paint.Style.STROKE);

		mPaints[2] = new Paint();
		mPaints[2].setColor(Color.parseColor("#02CE24"));
		mPaints[2].setFlags(Paint.ANTI_ALIAS_FLAG);
		mPaints[2].setStrokeWidth(getStrokeWidth());
		mPaints[2].setAntiAlias(true);
		mPaints[2].setStrokeCap(Paint.Cap.BUTT);
		mPaints[2].setStyle(Paint.Style.STROKE);

		mPaints[3] = new Paint();
		mPaints[3].setColor(Color.parseColor("#FFDE00"));
		mPaints[3].setFlags(Paint.ANTI_ALIAS_FLAG);
		mPaints[3].setStrokeWidth(getStrokeWidth());
		mPaints[3].setAntiAlias(true);
		mPaints[3].setStrokeCap(Paint.Cap.BUTT);
		mPaints[3].setStyle(Paint.Style.STROKE);

		mPaints[4] = new Paint();
		mPaints[4].setColor(Color.parseColor("#F89200"));
		mPaints[4].setFlags(Paint.ANTI_ALIAS_FLAG);
		mPaints[4].setStrokeWidth(getStrokeWidth());
		mPaints[4].setAntiAlias(true);
		mPaints[4].setStrokeCap(Paint.Cap.BUTT);
		mPaints[4].setStyle(Paint.Style.STROKE);

		mPaints[5] = new Paint();
		mPaints[5].setColor(Color.parseColor("#F73D04"));
		mPaints[5].setFlags(Paint.ANTI_ALIAS_FLAG);
		mPaints[5].setStrokeWidth(getStrokeWidth());
		mPaints[5].setAntiAlias(true);
		mPaints[5].setStrokeCap(Paint.Cap.BUTT);
		mPaints[5].setStyle(Paint.Style.STROKE);

		mPaints[6] = new Paint();
		mPaints[6].setColor(Color.parseColor("#320F30"));
		mPaints[6].setFlags(Paint.ANTI_ALIAS_FLAG);
		//mPaints[6].setStrokeWidth(mRadius / 2);
		mPaints[6].setStrokeCap(Paint.Cap.BUTT);
		mPaints[6].setStyle(Paint.Style.FILL);
		
		mPaints[7] = new Paint();
		mPaints[7].setColor(Color.WHITE);
		mPaints[7].setStrokeWidth(5);
		mPaints[7].setAntiAlias(true);
		mPaints[7].setStrokeCap(Paint.Cap.BUTT);
		mPaints[7].setStyle(Paint.Style.STROKE);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		//rect.set(innerRect.left, innerRect.top,innerRect.right,innerRect.bottom);
		rect.set(getWidth() / 1.5f - (mRadius/2), getHeight() / 1.5f - (mRadius/2),
				getWidth() / 1.5f + (mRadius/2), getHeight() / 1.5f + (mRadius/2));
		canvas.drawArc(rect, 0, 60, false, mPaints[5]);
		canvas.drawArc(rect, 60, 60, false, mPaints[0]);
		canvas.drawArc(rect, 120, 60, false, mPaints[1]);
		canvas.drawArc(rect, 180, 60, false, mPaints[2]);
		canvas.drawArc(rect, 240, 60, false, mPaints[3]);
		canvas.drawArc(rect, 300, 60, false, mPaints[4]);
		
		innerRect.set(getWidth() / 2 - (mRadius/2), getHeight() / 2 - (mRadius/2),
				getWidth() / 2 + (mRadius/2), getHeight() / 2 + (mRadius/2));
		canvas.drawOval(innerRect, mPaints[6]);
		
	}
	
	public void initSize(int width,int height)
	{
	    deviceWidth = width;
	    deviceHeight = height;
	    mRadius = deviceWidth > deviceHeight ? deviceHeight /2 : deviceWidth /2;
	    setUp();
	    this.invalidate();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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
	protected void onSizeChanged(final int w, final int h, final int oldw,
			final int oldh) {
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
	
	private Paint getDefaultScalePaint() {
		final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		int myColor = mContext.getResources().getColor(android.R.color.white);
		paint.setColor(myColor);
        paint.setStyle(Paint.Style.STROKE);
        //paint.setStrokeWidth(0.001f);
        paint.setStrokeWidth(0.014f);
        return paint;
	}
}
