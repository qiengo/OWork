package com.wzh.lgtrans.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.wzh.lgtrans.R;
import com.wzh.lgtrans.adapter.HuodanAdapter;
import com.wzh.lgtrans.adapter.JieHuoAdapter;

public class JieHuoActivity extends ActionBarBaseActivity{
	private ListView listView;
	private JieHuoAdapter jiehuoAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_jiehuo);
		setActionBarTitel("我要接活");
		
		jiehuoAdapter=new JieHuoAdapter(this);
		listView=(ListView)findViewById(R.id.list_myhuodan);
		listView.setAdapter(jiehuoAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent=new Intent(JieHuoActivity.this, HuodanDetailActivity.class);
				startActivity(intent);
			}
		});
	}
}
