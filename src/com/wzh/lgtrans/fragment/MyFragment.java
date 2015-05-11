package com.wzh.lgtrans.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wzh.logistics.R;

/**
 * 购物车界面的fragment
 * 
 * @author 王植桦 ewangzhihua@yeah.net
 * @version 创建时间：2014年3月19日
 *
 */
public class MyFragment extends Fragment {
	public static final String TAG = "MainFragment";
	/**
	 * 实例化布局的对象
	 */
	private LayoutInflater mInflater;
	private Context ctx;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		ctx = (Context) activity;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mInflater = LayoutInflater.from(ctx);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View mainView = mInflater.inflate(R.layout.fragment_my, container, false);
		return mainView;
	}

}