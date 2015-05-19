package com.wzh.lgtrans.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzh.lgtrans.R;
import com.wzh.lgtrans.dialog.CityPickerDialog;
import com.wzh.lgtrans.struct.IdName;

/**
 * 分类列表的适配器
 * 
 * @author 王植桦 ewangzhihua@yeah.net
 * @version 创建时间：2014年3月18日
 */
public class BusiCityAdapter extends BaseAdapter {

	private static final String TAG = "BusiCityAdapter";
	private Context ctx;
	/**
	 * 存放内容数据的数组list
	 */
	private List<IdName> cityList;
	/**
	 * 实例化布局的对象
	 */
	private final LayoutInflater mInflater;

	public BusiCityAdapter(Context ctx) {
		this.ctx = ctx;
		mInflater = LayoutInflater.from(ctx);
	}

	public void setDataList(List<IdName> list) {
		cityList = list;
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
			holder.title = (TextView) convertView.findViewById(R.id.tv_item_busicity_title);
			holder.btn = (ImageView) convertView.findViewById(R.id.iv_item_busicity_rdel);
			convertView.setTag(holder);
		}
		final IdName cityInfo = cityList.get(position);
		OnClickListener clickListener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				new CityPickerDialog(ctx, cityInfo, false) {
					@Override
					public void onConfirm(IdName city) {
						cityInfo.clone(city);
						notifyDataSetChanged();
						dismiss();
					}
				}.show();
			}
		};
		if (!cityInfo.isNull()) {
			holder.title.setText(cityInfo.getName());
			holder.btn.setImageResource(R.drawable.ic_arrow_r);
			holder.btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

				}
			});
		} else {
			holder.btn.setOnClickListener(clickListener);
		}
		holder.title.setOnClickListener(clickListener);
		return convertView;
	}

	/**
	 * view的存放类
	 */
	class Holder {
		TextView title;
		ImageView btn;
	}
}
