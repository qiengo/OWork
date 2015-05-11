package com.wzh.lgtrans.activity;

import android.os.Bundle;

import com.wzh.lgtrans.R;

public class MyInfoActivity extends ActionBarBaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myinfo);
		setActionBarTitel("个人信息");
	}
}
