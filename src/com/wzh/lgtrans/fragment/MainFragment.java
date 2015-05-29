package com.wzh.lgtrans.fragment;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.wzh.lgtrans.MyVolley;
import com.wzh.lgtrans.R;
import com.wzh.lgtrans.activity.InviteActivity;
import com.wzh.lgtrans.activity.JieHuoActivity;
import com.wzh.lgtrans.activity.LoginActivity;
import com.wzh.lgtrans.activity.MyHuodanActivity;
import com.wzh.lgtrans.activity.PicUploadActivity;
import com.wzh.lib.AppConfig;
import com.wzh.lib.adapter.ImgPagerAdapter;
import com.wzh.lib.struct.RemoteImg;
import com.wzh.lib.util.IOUtils;
import com.wzh.lib.view.InfiniteCircleIndicator;
import com.wzh.lib.view.SmoothViewPager;

/**
 * 购物车界面的fragment
 * 
 * @author 王植桦 ewangzhihua@yeah.net
 * @version 创建时间：2014年3月19日
 *
 */
public class MainFragment extends Fragment {
	public static final String TAG = "MainFragment";

	private LayoutInflater mInflater;
	/**
	 * 顶部广告栏
	 */
	private SmoothViewPager smoothViewPager;
	/**
	 * 广告栏适配器
	 */
	private ImgPagerAdapter bannerAdapter;
	private Context ctx;
	private final String homeUrl=AppConfig.SERVER_HOST+"/wzh/home.txt";

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		ctx = (Context) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInflater = LayoutInflater.from(ctx);
		 bannerAdapter=new ImgPagerAdapter(ctx);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View mainView = mInflater.inflate(R.layout.fragment_main, container, false);
		smoothViewPager = (SmoothViewPager) mainView.findViewById(R.id.svp_ads);
		InfiniteCircleIndicator pageIndicator = (InfiniteCircleIndicator) mainView.findViewById(R.id.svp_indicator);

		int screenW = AppConfig.getScreenWidth();
		smoothViewPager.setLayoutParams(new FrameLayout.LayoutParams(screenW, (int) (screenW * 0.5)));
		smoothViewPager.setIndicator(pageIndicator);

		mainView.findViewById(R.id.btn_main_login).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ctx, LoginActivity.class);
				ctx.startActivity(intent);
			}
		});
		mainView.findViewById(R.id.lay_lin_huodan).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ctx, MyHuodanActivity.class);
				ctx.startActivity(intent);
			}
		});
		mainView.findViewById(R.id.btn_main_jiehuo).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ctx, JieHuoActivity.class);
				ctx.startActivity(intent);
			}
		});
		mainView.findViewById(R.id.lay_lin_invite).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ctx, InviteActivity.class);
				ctx.startActivity(intent);
			}
		});
		mainView.findViewById(R.id.lay_lin_chexian).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ctx, PicUploadActivity.class);
				ctx.startActivity(intent);
			}
		});
		return mainView;
	}
	
	 @Override  
	    public void onActivityCreated(Bundle savedInstanceState) {  
	        super.onActivityCreated(savedInstanceState);  
//			if (TbConfig.USE_LOCAL_JSON) {
//				getLocalInfo();
//			} else {
				getServerInfo();
//			}
	    }  
	
	private Response.Listener<JSONObject> successlistener=new Response.Listener<JSONObject>() {
		@Override
		public void onResponse(JSONObject response) {
			try { 
				//解析顶部的广告信息
				JSONArray bannerImgs=response.getJSONArray("banner_img");//获取JSONArray 
				int bannercount = bannerImgs.length();  
				final ArrayList<RemoteImg> adsList=new ArrayList<RemoteImg>();
	            for(int i = 0; i < bannercount; i++){
	                JSONObject imgObject = bannerImgs.getJSONObject(i);  
	                Log.i(TAG, "imgObject:"+imgObject);
	                adsList.add(new RemoteImg(imgObject));
	            }  
	            bannerAdapter.setContentList(adsList);
				smoothViewPager.setAdapter(bannerAdapter);
				//解析3栏图片信息
//				JSONArray btnImgs=response.getJSONArray("btn3_img");
//				if (btnImgs.length() >= 3) {
//					for (int i = 0; i < 3; i++) {
//						JSONObject imgObject = btnImgs.getJSONObject(i);
//						RemoteImg remoteImg = new RemoteImg();
//						remoteImg.fromJsonObject(imgObject);
//						imgBtn[i].setRatio(remoteImg.getRatio());
//						imageLoader.displayImage(remoteImg.getUrl(), imgBtn[i]);
//					}
//				}		
			}catch(JSONException e){
				throw new RuntimeException(e);
			}
		}
	};

	private void getServerInfo(){
		JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(
				homeUrl, null,
				successlistener, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						System.out.println("---error:"+error);
					}
				});
		MyVolley.getRequestQueue().add(jsonArrayRequest);
	}
	/**
	 * 获取信息
	 */
	private void getLocalInfo(){
		try {
			JSONObject obj = new JSONObject(IOUtils.loadJSONFromAsset(ctx, "home.txt"));
			successlistener.onResponse(obj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}