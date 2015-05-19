package com.wzh.lgtrans.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.wzh.lgtrans.R;
import com.wzh.lgtrans.adapter.BusiCityAdapter;

public class BusiCityActivity extends ActionBarBaseActivity {
	private ListView listView;
	private BusiCityAdapter busiCityAdapter;
	private int resultCode=1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_busicity);
		setActionBarTitel("业务城市");

		listView = (ListView) findViewById(R.id.list_busicity);
		busiCityAdapter = new BusiCityAdapter(this);
		busiCityAdapter.setDataList(Register2Activity.cityList);
		listView.setAdapter(busiCityAdapter);

		findViewById(R.id.btn_busicity_ok).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				BusiCityActivity.this.setResult(resultCode);
				BusiCityActivity.this.finish();
			}
		});
	}

}
