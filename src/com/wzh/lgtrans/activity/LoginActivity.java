package com.wzh.lgtrans.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wzh.lgtrans.R;

public class LoginActivity extends ActionBarBaseActivity {
	private Context ctx;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ctx=this;
		findViewById(R.id.tv_login_register).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(ctx, Register1Activity.class);
				ctx.startActivity(intent);
			}
		});
	}
}
