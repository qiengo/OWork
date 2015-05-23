package com.wzh.lgtrans.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.wzh.lgtrans.AppConfig;
import com.wzh.lgtrans.MyVolley;
import com.wzh.lgtrans.R;
import com.wzh.lgtrans.adapter.HuodanAdapter;
import com.wzh.lgtrans.struct.HuodanInfo;
import com.wzh.lgtrans.util.IOUtils;

public class MyHuodanActivity extends ActionBarBaseActivity {
	private final String huodanUrl = AppConfig.SERVER_HOST + "/wzh/huodan.txt";
	private ListView listView;
	private HuodanAdapter huodanAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myhuodan);
		setActionBarTitel("我的货单");

		huodanAdapter = new HuodanAdapter(this);
		listView = (ListView) findViewById(R.id.list_myhuodan);
		listView.setAdapter(huodanAdapter);
		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				Intent intent = new Intent(MyHuodanActivity.this, HuodanDetailActivity.class);
				startActivity(intent);
			}
		});
		
		getServerInfo();
	}

	private Response.Listener<JSONObject> successlistener = new Response.Listener<JSONObject>() {
		@Override
		public void onResponse(JSONObject response) {
			try {
				// 解析顶部的广告信息
				JSONArray huodanList = response.getJSONArray("huodan");// 获取JSONArray
				Type listType = new TypeToken<ArrayList<HuodanInfo>>() {}.getType();
				ArrayList<HuodanInfo> huodans = new Gson().fromJson(huodanList.toString(), listType);
				huodanAdapter.setDataList(huodans);
				huodanAdapter.notifyDataSetChanged();
			} catch (JSONException e) {
				throw new RuntimeException(e);
			}
		}
	};

	private void getServerInfo() {
		JsonObjectRequest jsonArrayRequest = new JsonObjectRequest(huodanUrl, null, successlistener,
				new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						System.out.println("---error:" + error);
					}
				});
		MyVolley.getRequestQueue().add(jsonArrayRequest);
	}

	/**
	 * 获取信息
	 */
	private void getLocalInfo() {
		try {
			JSONObject obj = new JSONObject(IOUtils.loadJSONFromAsset(this, "home.txt"));
			successlistener.onResponse(obj);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
}
