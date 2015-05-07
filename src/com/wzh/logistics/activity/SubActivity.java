package com.wzh.logistics.activity;

import android.os.Bundle;

import com.wzh.logistics.R;

public class SubActivity extends ActionBarBaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub);
		setActionBarTitel("SubActivity");
	}
}
