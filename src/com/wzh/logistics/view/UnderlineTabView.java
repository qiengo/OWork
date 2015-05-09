package com.wzh.logistics.view;

import java.util.ArrayList;

import com.wzh.logistics.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.FontMetricsInt;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public class UnderlineTabView extends View implements OnTouchListener{
	private Paint mBorderPaint, mTextPaint,mSelTextPaint,mSelectedPaint;
	private ArrayList<String> titleList;
	private int underLineH=5;
	private int curSelect=1;
	private int viewWidth,unitWidth;
	private OnChangeListener onChangeListener;
	private int lineColor,selectColor;

	public UnderlineTabView(Context context) {
		this(context, null);
	}

	public UnderlineTabView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public UnderlineTabView(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		final Resources res = getResources();
		lineColor=res.getColor(R.color.divider_gray);
		selectColor=res.getColor(R.color.theme_blue);
		titleList = new ArrayList<String>();
		mBorderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mBorderPaint.setColor(lineColor);
		mBorderPaint.setStrokeWidth(2);

		mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mTextPaint.setTextSize(28);
		mTextPaint.setColor(0xff000000);
		mSelTextPaint= new Paint(Paint.ANTI_ALIAS_FLAG);
		mSelTextPaint.setTextSize(28);
		mSelTextPaint.setColor(selectColor);
		
		mSelectedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mSelectedPaint.setColor(selectColor);
		mSelectedPaint.setStyle(Style.FILL);
		setOnTouchListener(this);
		

		addTitle("未确认货单");
		addTitle("进行中货单");
		addTitle("历史货单");
	}

	public void addTitle(String title) {
		titleList.add(title);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int height = getMeasuredHeight();
		float pts[] = { 0, 0, viewWidth, 0, 0, 0, 0, height, viewWidth, 0, viewWidth, height, 0, height, viewWidth, height, unitWidth, 0,
				unitWidth, height, unitWidth * 2, 0, unitWidth * 2, height };
		canvas.drawLines(pts, mBorderPaint);
		for (int i = 0; i < titleList.size(); i++) {
			String title = titleList.get(i);
			float textW = mTextPaint.measureText(title);
			float startX = (unitWidth - textW) * 0.5f;
			int baseline = getTextBaseLine(mTextPaint,0,height);
			if(i==curSelect){
				canvas.drawText(title, unitWidth * i + startX, baseline, mSelTextPaint);
			}else{
				canvas.drawText(title, unitWidth * i + startX, baseline, mTextPaint);
			}
		}
		canvas.drawRect(unitWidth*curSelect, height-underLineH, unitWidth*curSelect+unitWidth, height, mSelectedPaint);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		viewWidth=getMeasuredWidth();
		unitWidth = viewWidth / 3;
	}

	private int getTextBaseLine(Paint paint, int top, int bottom) {
		FontMetricsInt fontMetrics = paint.getFontMetricsInt();
		// 转载请注明出处：http://blog.csdn.net/hursing
		int baseline = top + (bottom - top - fontMetrics.bottom + fontMetrics.top) / 2 - fontMetrics.top;
		return baseline;
	}

	float posTempX,posTempY;
	
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		int action =event.getAction();
		float posX=event.getX();
		float posY=event.getY();
		switch(action){
		case MotionEvent.ACTION_DOWN:
			posTempX=event.getX();
			posTempY=event.getY();
			break;
		case MotionEvent.ACTION_UP:
			if(Math.abs(posX-posTempX)<10&&Math.abs(posY-posTempY)<10){
				curSelect=(int) (posX/unitWidth);
				invalidate();
				if(onChangeListener!=null){
					onChangeListener.onChange(curSelect);
				}
			}
			break;
		}
		return true;
	}

	public interface OnChangeListener {
        void onChange(int pos);
    }
}
