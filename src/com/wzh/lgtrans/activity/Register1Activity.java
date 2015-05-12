package com.wzh.lgtrans.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wzh.lgtrans.R;

public class Register1Activity extends ActionBarBaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register1);
		setActionBarTitel("完善信息");
		findViewById(R.id.tv_btn_next).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Register1Activity.this, Register2Activity.class);
				startActivity(intent);
			}
		});
	}
}
