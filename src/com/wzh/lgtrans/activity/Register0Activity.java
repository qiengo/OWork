package com.wzh.lgtrans.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wzh.lgtrans.R;

public class Register0Activity extends ActionBarBaseActivity{
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register0);
		setActionBarTitel("注册");
		findViewById(R.id.tv_btn_next).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(Register0Activity.this, Register1Activity.class);
				startActivity(intent);
			}
		});
	}
}
