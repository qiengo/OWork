package com.wzh.lgtrans.activity;

import android.os.Bundle;

import com.wzh.lgtrans.R;

public class SubActivity extends ActionBarBaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sub);
		setActionBarTitel("SubActivity");
	}
}
