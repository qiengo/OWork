package com.wzh.lgtrans.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.wzh.lgtrans.R;
import com.wzh.lgtrans.dialog.CityPickerDialog;

public class TestActivity extends ActionBarBaseActivity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
//		findViewById(R.id.btn_test_pop).setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				new CityPickerDialog(TestActivity.this){
//
//					@Override
//					public void onConfirm(String ret) {
//						dismiss();
//						Log.i("tt", ret);
//					}
//					
//				}.show();
//			}
//		});
	}
}
