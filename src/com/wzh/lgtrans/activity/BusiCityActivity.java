package com.wzh.lgtrans.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.wzh.lgtrans.R;
import com.wzh.lgtrans.adapter.BusiCityAdapter;

public class BusiCityActivity extends ActionBarBaseActivity {
	private ListView listView;
	private BusiCityAdapter busiCityAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_busicity);
		setTitle("业务城市");
		
		listView=(ListView)findViewById(R.id.list_busicity);
		busiCityAdapter=new BusiCityAdapter(this);
		listView.setAdapter(busiCityAdapter);
	}
}
