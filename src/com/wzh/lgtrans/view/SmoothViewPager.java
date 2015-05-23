package com.wzh.lgtrans.view;

import java.util.Timer;
import java.util.TimerTask;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.animation.Interpolator;
import android.widget.Scroller;

import com.wzh.lgtrans.R;
import com.wzh.lgtrans.adapter.ImgPagerAdapter;

/**
 * 广告栏控件，自动切换
 * @author 王植桦 ewangzhihua@yeah.net
 * @version 创建时间：2014年3月10日
 *
 */
public class SmoothViewPager extends ViewPager {
	
	private InfiniteCircleIndicator circlePageIndicator;
	private ViewAspectRatioMeasurer varm;
	/**
	 * 切换时间
	 */
	private final long switchPeriod =4000;
	private final int MSG_ADS_SWITCH = 1;
	private boolean EnableAutoSwitch=false;
	/**
	 * 切换Timer
	 */
	private Timer switchTimer;
	
	/**
	 * 1-<strong>dragging</strong><br>
	 * 2-<strong>automatically settling to the current page</strong><br>
	 * 0-<strong>fully stopped/idle</strong><br>
	 */
	private int scrollState=0;
	
	private final Handler _handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case MSG_ADS_SWITCH:
				if(0==scrollState){
					MoveNext();
				}
				break;
			default:
				break;
			}
			super.handleMessage(msg);
		}

	};
	
	public SmoothViewPager(Context context) {
		this(context, null);
	}
	public SmoothViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
		
		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.SmoothViewPager);
		EnableAutoSwitch=a.getBoolean(R.styleable.SmoothViewPager_autoSwitch, false);
		a.recycle();
		varm = new ViewAspectRatioMeasurer(2);
	       
//		 /*主要代码段*/  
//        try {               
//            Field mField = ViewPager.class.getDeclaredField("mScroller");               
//            mField.setAccessible(true);     
//             //设置加速度 ，通过改变FixedSpeedScroller这个类中的mDuration来改变动画时间（如mScroller.setmDuration(mMyDuration);）    
//            mScroller = new FixedSpeedScroller(mViewPager.getContext(), new AccelerateInterpolator());           
//            mField.set(mViewPager, mScroller);           
//            } catch (Exception e) {           
//                e.printStackTrace();  
//            }  
        
		setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				if(circlePageIndicator!=null){
					circlePageIndicator.setCurIndex(arg0);
				}
			}
			
			/**
			 * Called when the scroll state changes. Useful for discovering 
			 * when the user begins <strong>dragging</strong>, 
			 * when the pager is <strong>automatically 
			 * settling to the current page</strong>, or when it is 
			 * <strong>fully stopped/idle</strong>.<br>
			 * 1-<strong>dragging</strong><br>
			 * 2-<strong>automatically settling to the current page</strong><br>
			 * 0-<strong>fully stopped/idle</strong><br>
			 */
			@Override
			public void onPageScrollStateChanged(int arg0) {
				scrollState=arg0;
			}
		});
	}
	
	/**
	 * 设置索引指示器
	 * @param indicator
	 */
	public void setIndicator(InfiniteCircleIndicator indicator){
		circlePageIndicator=indicator;
		invalidate();
	}
	
	
	@Override
	public void setAdapter(PagerAdapter arg0) {
		super.setAdapter(arg0);
		if(circlePageIndicator!=null){
			int actualCount=((ImgPagerAdapter)getAdapter()).getActualNum();
			circlePageIndicator.setCount(actualCount);
		}
		setCurrentItem((int) (ImgPagerAdapter.LOOPS_COUNT*0.5));
	}
	
	@Override
	protected void onAttachedToWindow() {
		super.onAttachedToWindow();
		if(EnableAutoSwitch){
			startSwitch();
		}
	}
	@Override
	protected void onDetachedFromWindow() {
		if(switchTimer!=null){
			switchTimer.cancel();
		}
		_handler.removeMessages(MSG_ADS_SWITCH);
		super.onDetachedFromWindow();
	}
	
	

//	@Override
//	protected void onMeasure(int arg0, int arg1) {
//		if(varm.getRatio()>0){
//			varm.measure(arg0, arg1);
//			setMeasuredDimension(varm.getMeasuredWidth(), varm.getMeasuredHeight());
//		}else{
//			super.onMeasure(arg0, arg1);
//		}
//	}
	
	/**
	 * 切换到下一页
	 */
	public void MoveNext() {
		setCurrentItem(getCurrentItem() + 1);
	}
	/**
	 * 开始自动切换
	 */
	private void startSwitch(){
		if(switchTimer!=null){
			switchTimer.cancel();
		}
		switchTimer = new Timer();
		switchTimer.schedule(new TimerTask() {

			@Override
			public void run() {
				_handler.sendEmptyMessage(MSG_ADS_SWITCH);
			}
		},switchPeriod, switchPeriod);
	}
	
	public class ScrollerCustomDuration extends Scroller {
	    private double mScrollFactor = 2;

	    public ScrollerCustomDuration(Context context) {
	        super(context);
	    }

	    public ScrollerCustomDuration(Context context, Interpolator interpolator) {
	        super(context, interpolator);
	    }

	    /**
	     * Set the factor by which the duration will change
	     */
	    public void setScrollDurationFactor(double scrollFactor) {
	        mScrollFactor = scrollFactor;
	    }

	    @Override
	    public void startScroll(int startX, int startY, int dx, int dy,
	            int duration) {
	        super.startScroll(startX, startY, dx, dy,
	                (int) (duration * mScrollFactor));
	    }

	}
}
