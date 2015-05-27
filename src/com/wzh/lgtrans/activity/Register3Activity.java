package com.wzh.lgtrans.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wzh.lgtrans.R;

public class Register3Activity extends ActionBarBaseActivity{
	private Context ctx;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register3);
		ctx=this;
		setActionBarTitel("完善信息");
		
		findViewById(R.id.iv_reg3_shenfen).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(ctx, PicUploadActivity.class);
				ctx.startActivity(intent);
			}
		});
	}
}
