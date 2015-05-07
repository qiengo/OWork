package com.wzh.logistics.activity;

import android.os.Bundle;

import com.wzh.logistics.R;

public class MyHuodanActivity extends ActionBarBaseActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myhuodan);
		setActionBarTitel("我的货单");
	}
}
