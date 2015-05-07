package com.wzh.logistics.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

public class UnderlineTabView extends View {
	private Paint mLinePaint;

	public UnderlineTabView(Context context) {
		this(context, null);
	}

	public UnderlineTabView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public UnderlineTabView(Context context, AttributeSet attrs,
			int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mLinePaint.setColor(0xffff0ff0);
		mLinePaint.setStrokeWidth(2);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int width=getMeasuredWidth();
		int height=getMeasuredHeight();
		int unit=width/3;
		float pts[]={0,0,width,0,
				0,0,0,height,
				width,0,width,height,
				0,height,width,height,
				unit,0,unit,height,
				unit*2,0,unit*2,height};
		canvas.drawLines(pts, mLinePaint);
	}
}
