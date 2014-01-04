package atlega.babyline.pottyminder;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

public class BubbleTextView extends View {
	private Context mContext;
	private int mHours;
	private int mMinutes;
	private int mSeconds;
	private Bitmap mBubbleBitMap;
	private Bitmap mHourBubble; 
	private Bitmap mMinuteBubble; 
	private Bitmap mSecondsBubble; 
	private Paint mHoursPaint;
	private Paint mMinutesPaint;
	private Paint mSecondsPaint;
	private int paddingLeft = 0;
	private int paddingRight = 0;
	private int paddingTop = 0;
	private int paddingBottom = 0;
	private int fontSize = 40;
 
	
	public void setTimer (int hours, int minutes, int seconds) {
		mHours = hours;
		mMinutes = minutes;
		mSeconds = seconds;
		this.invalidate();
	}

	public BubbleTextView(Context context) {
		super(context);
		if (!isInEditMode())
			initialize();
	}

	public BubbleTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		if (!isInEditMode())
			initialize();
	}

	public void setPaddingLeft(int padding) {
		paddingLeft = padding;
	}

	public void setPaddingRight(int padding) {
		paddingRight = padding;
	}

	public void setPaddingBottom(int padding) {
		paddingBottom = padding;
	}

	public void setPaddingTop(int padding) {
		paddingTop = padding;
	}

	public void setFontSize(int size) {
		fontSize = size;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		Rect rect = new Rect();
		
		int width = Math.max(rect.width(), getSuggestedMinimumWidth())
				+ paddingLeft + paddingRight;
		int height = Math.max(rect.height(), getSuggestedMinimumHeight())
				+ paddingTop + paddingBottom;

		setMeasuredDimension(225,225);
	}

	/**
	 * Initialize the view
	 */
	private void initialize() {
		mContext = getContext();
		mHoursPaint = createPaint(Color.GREEN, 40, -1);
		mMinutesPaint = createPaint(Color.BLUE, 50, -1);
		mSecondsPaint = createPaint(Color.RED, 30, -1);

		mBubbleBitMap = BitmapFactory.decodeResource(getResources(), R.drawable.blue);	
		mHourBubble = Bitmap.createScaledBitmap(mBubbleBitMap, 100, 100, false); 
		mMinuteBubble = Bitmap.createScaledBitmap(mBubbleBitMap, 130, 130, false); 
		mSecondsBubble = Bitmap.createScaledBitmap(mBubbleBitMap, 80, 80, false); 
	}
	
	/**
	 * Helper methods for creating Paint object using parameters supplied
	 * @param color
	 * @param fontSize
	 * @param alpha number between 0 and 255 that set transparency/opacity level of text
	 * @return a reference to a Paint object
	 */
	private Paint createPaint (int color, int fontSize, int alpha){
		Paint paint = new Paint();
		paint.setColor(color);
		paint.setTextSize(fontSize);
		paint.setTextAlign(Align.CENTER);
		paint.setFakeBoldText(true);
		if (alpha > -1)
			paint.setAlpha(alpha);
			
		return paint;
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int x = paddingLeft;
		int y = paddingTop;
		
		if (mSeconds > 0) {
			canvas.drawBitmap(mHourBubble, x, y+25, mHoursPaint);
			canvas.drawText(String.format("%02d", mHours), x + (100/5) + (40/2) , y + 25 + (100/2) + (40/8), mHoursPaint);
			
			canvas.drawBitmap(mMinuteBubble, x+(80-10), y, mMinutesPaint);			
			canvas.drawText(String.format("%02d", mMinutes), x + 70 + (130/5) + (50/2) , y + (130/2) + (50/8), mMinutesPaint);
			
			canvas.drawBitmap(mSecondsBubble, x+ (80-10-15) + (130-10-15), y+15, mSecondsPaint);
			canvas.drawText(String.format("%02d", mSeconds), x + 155 + (80/5) + (30/2) , y + 15 + (80/2) + (30/8), mSecondsPaint);
		}
	}
}