package com.wzh.lgtrans.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzh.lgtrans.R;
import com.wzh.lgtrans.dialog.CityPickerDialog;
import com.wzh.lgtrans.view.CityInfo;

/**
 * 分类列表的适配器
 * 
 * @author 王植桦 ewangzhihua@yeah.net
 * @version 创建时间：2014年3月18日
 */
public class BusiCityAdapter extends BaseAdapter {
	
	private static final String TAG="BusiCityAdapter";
	private Context ctx;
	/**
	 * 存放内容数据的数组list
	 */
	 public List<CityInfo> cityList = new ArrayList<CityInfo>();
	/**
	 * 实例化布局的对象
	 */
	private final LayoutInflater mInflater;

	public BusiCityAdapter(Context ctx) {
		this.ctx = ctx;
		mInflater = LayoutInflater.from(ctx);
		initData();
	}

	private void initData(){
		cityList.clear();
		for(int i=0;i<10;i++){
			cityList.add(new CityInfo());
		}
	}

	@Override
	public int getCount() {
		return cityList.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView != null) {
			holder = (Holder) convertView.getTag();
		} else {
			holder = new Holder();
			convertView = mInflater.inflate(R.layout.item_list_busicity, null);
			final CityInfo cityInfo=cityList.get(position);
			holder.title = (TextView) convertView.findViewById(R.id.tv_item_busicity_title);
			if(!cityInfo.isNull()){
				holder.title.setText(cityInfo.getName());
			}
			Log.i(TAG, "city title:"+cityInfo.getName());
			holder.title.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					new CityPickerDialog(ctx, cityInfo) {

						@Override
						public void onConfirm(CityInfo city) {
							cityInfo.clone(city);
							Log.i(TAG, "city:"+city.toString());
							Log.i(TAG, "cityInfo:"+cityInfo.toString());
							Log.i(TAG, "cityList["+position+"]:"+cityList.get(position).toString());
							dismiss();
							BusiCityAdapter.this.notifyDataSetChanged();
							BusiCityAdapter.this.notifyDataSetInvalidated();
						}

					}.show();
				}
			});
			convertView.setTag(holder);
		}
		return convertView;
	}

	/**
	 * view的存放类
	 */
	class Holder {
		TextView title;
		ImageView del;
	}
}
