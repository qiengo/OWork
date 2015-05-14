package com.wzh.lgtrans.activity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.wzh.lgtrans.R;
import com.wzh.lgtrans.adapter.JieHuoAdapter;
import com.wzh.lgtrans.dialog.CityPickerDialog;

public class JieHuoActivity extends ActionBarBaseActivity {
	private ListView listView;
	private JieHuoAdapter jiehuoAdapter;
	private Context ctx;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctx = this;
		setContentView(R.layout.activity_jiehuo);
		setActionBarTitel("我要接活");
		findViewById(R.id.lay_lin_jiehuo_poss).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new CityPickerDialog(ctx) {

					@Override
					public void onConfirm(String ret) {
						dismiss();
						Log.i("tt", ret);
					}

				}.show();
			}
		});
		findViewById(R.id.lay_lin_jiehuo_pose).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new CityPickerDialog(ctx) {

					@Override
					public void onConfirm(String ret) {
						dismiss();
						Log.i("tt", ret);
					}

				}.show();
			}
		});
		jiehuoAdapter = new JieHuoAdapter(this);
		listView = (ListView) findViewById(R.id.list_myhuodan);
		listView.setAdapter(jiehuoAdapter);

	}
}
