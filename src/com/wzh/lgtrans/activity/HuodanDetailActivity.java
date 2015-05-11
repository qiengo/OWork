package com.wzh.lgtrans.activity;

import android.os.Bundle;

import com.wzh.lgtrans.R;

public class HuodanDetailActivity extends ActionBarBaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_huodandetail);
		setActionBarTitel("货单详情");
	}
}
