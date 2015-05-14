package com.wzh.lgtrans.activity;

import android.content.Context;
import android.os.Bundle;

import com.wzh.lgtrans.R;

public class QiangSucActivity extends ActionBarBaseActivity {
	private Context ctx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctx = this;
		setContentView(R.layout.activity_qiangsuc);
		setActionBarTitel("抢单成功");

	}
}
