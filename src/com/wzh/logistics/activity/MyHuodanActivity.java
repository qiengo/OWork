package com.wzh.logistics.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.wzh.logistics.R;
import com.wzh.logistics.adapter.HuodanAdapter;

public class MyHuodanActivity extends ActionBarBaseActivity{
	private ListView listView;
	private HuodanAdapter huodanAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myhuodan);
		setActionBarTitel("我的货单");
		
		huodanAdapter=new HuodanAdapter(this);
		listView=(ListView)findViewById(R.id.list_myhuodan);
		listView.setAdapter(huodanAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent=new Intent(MyHuodanActivity.this, HuodanDetailActivity.class);
				startActivity(intent);
			}
		});
	}
}
