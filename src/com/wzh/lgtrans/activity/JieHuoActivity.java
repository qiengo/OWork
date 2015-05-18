package com.wzh.lgtrans.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.wzh.lgtrans.R;
import com.wzh.lgtrans.adapter.JieHuoAdapter;
import com.wzh.lgtrans.dialog.CityPickerDialog;
import com.wzh.lgtrans.view.CityInfo;

public class JieHuoActivity extends ActionBarBaseActivity {
	private ListView listView;
	private JieHuoAdapter jiehuoAdapter;
	private TextView viewPoss,viewPose;
	private Context ctx;
	private CityInfo startCity, endCity;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ctx = this;
		setContentView(R.layout.activity_jiehuo);
		setActionBarTitel("我要接活");

		startCity = new CityInfo("370783", "寿光市");
		endCity = new CityInfo("370783", "寿光市");
		
		viewPoss=(TextView)findViewById(R.id.tv_jiehuo_poss);
		viewPose=(TextView)findViewById(R.id.tv_jiehuo_pose);
		viewPoss.setText(startCity.getName());
		viewPose.setText(endCity.getName());
		findViewById(R.id.lay_lin_jiehuo_poss).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new CityPickerDialog(ctx, startCity) {

					@Override
					public void onConfirm(CityInfo city) {
						startCity=city;
						viewPoss.setText(startCity.getName());
						dismiss();
					}

				}.show();
			}
		});
		findViewById(R.id.lay_lin_jiehuo_pose).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new CityPickerDialog(ctx, endCity) {

					@Override
					public void onConfirm(CityInfo city) {
						endCity=city;
						viewPose.setText(endCity.getName());
						dismiss();
					}

				}.show();
			}
		});
		jiehuoAdapter = new JieHuoAdapter(this);
		listView = (ListView) findViewById(R.id.list_myhuodan);
		listView.setAdapter(jiehuoAdapter);

	}
}
