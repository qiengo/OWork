package com.wzh.lgtrans.adapter;

import java.util.List;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.wzh.lgtrans.struct.RemoteImg;
import com.wzh.lgtrans.view.ClickMaskRatioImageView;

/**
 * 滑动切换广告栏的适配器
 * @author Administrator
 *
 */
public class ImgPagerAdapter extends PagerAdapter{
	
	/**
	 * 最多循环数，用于无限翻页
	 */
	public static int LOOPS_COUNT = 500;
	
	private Context _ctx;
	/**
	 * 图片list
	 */
	private List<RemoteImg> _imgList;
	/**
	 * 加载图片的全局变量
	 */
	private ImageLoader imageLoader = ImageLoader.getInstance();
	
	public ImgPagerAdapter(Context ctx) {
		this(ctx, null);
	}

	public ImgPagerAdapter(Context ctx,List<RemoteImg> mListViews) {  
    	_ctx=ctx;
        _imgList = mListViews;
        
    }  

	/**
	 * 设置显示内容
	 * @param mListViews
	 */
	public void setContentList(List<RemoteImg> mListViews){
		_imgList=mListViews;
		System.out.println("_imgList:"+_imgList.size());
		this.notifyDataSetChanged();
	}
	
    /**
     * Remove a page for the given position. The adapter is responsible for
     * removing the view from its container, although it only must ensure
     * this is done by the time it returns from {@link #finishUpdate()}.
     * 
     * @param container
     *            The containing View from which the page will be removed.
     * @param position
     *            The page position to be removed.
     * @param object
     *            The same object that was returned by
     *            {@link #instantiateItem(View, int)}.
     */
    @Override  
    public void destroyItem(ViewGroup container, int position, Object object)   {  
        container.removeView((ClickMaskRatioImageView)object);
    }  


	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		if (null == _imgList) {
			return null;
		}
		int pos = position % _imgList.size();
		RemoteImg remoteImg=_imgList.get(pos);
		ClickMaskRatioImageView view = new ClickMaskRatioImageView(_ctx);
		
		if(remoteImg!=null){
			view.setRatio(remoteImg.getRatio());
			imageLoader.displayImage(remoteImg.getUrl(), view);
		}

		container.addView(view);
		return view;
	}

    @Override  
    public int getCount() {    
        return  LOOPS_COUNT;
    }  

    public int getActualNum(){
    	if(null==_imgList){
    		return 0;
    	}else{
    		return _imgList.size();
    	}
    }
    
    /**
     * to judge whether the view and object are the same one.
     */
	@Override  
    public boolean isViewFromObject(View arg0, Object arg1) { 
        return arg0==arg1;  
    }  
	
}
