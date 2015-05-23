package com.wzh.lgtrans.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.wzh.lgtrans.R;
import com.wzh.lgtrans.struct.HuodanInfo;

/**
 * 货单列表的适配器
 * 
 * @author 王植桦 ewangzhihua@yeah.net
 * @version 创建时间：2014年3月18日
 */
public class HuodanAdapter extends BaseAdapter {
	/**
	 * 存放内容数据的数组list
	 */
	private ArrayList<HuodanInfo> huodans;
	/**
	 * 实例化布局的对象
	 */
	private final LayoutInflater mInflater;

	public HuodanAdapter(Context ctx) {
		mInflater = LayoutInflater.from(ctx);
	}

	public void setDataList(ArrayList<HuodanInfo> list) {
		huodans = list;
	}

	@Override
	public int getCount() {
		if (huodans != null) {
			return huodans.size();
		}
		return 0;
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder = null;
		if (convertView != null) {
			holder = (Holder) convertView.getTag();
		} else {
			holder = new Holder();
			convertView = mInflater.inflate(R.layout.item_list_myhuodan_not, null);
			holder.statusView = (TextView) convertView.findViewById(R.id.tv_item_huodan_status);
			holder.timeView = (TextView) convertView.findViewById(R.id.tv_item_huodan_time);
			holder.longView = (TextView) convertView.findViewById(R.id.tv_item_huodan_carlong);
			holder.typeView = (TextView) convertView.findViewById(R.id.tv_item_huodan_cartype);
			holder.possView = (TextView) convertView.findViewById(R.id.tv_item_huodan_poss);
			holder.poseView = (TextView) convertView.findViewById(R.id.tv_item_huodan_pose);
			holder.ownerView = (TextView) convertView.findViewById(R.id.tv_item_huodan_owner);
			holder.msgView = (TextView) convertView.findViewById(R.id.tv_item_huodan_msg);
			convertView.setTag(holder);
		}
		HuodanInfo huodanInfo = huodans.get(position);
		holder.statusView.setText(huodanInfo.status);
		holder.timeView.setText(huodanInfo.time);
		holder.longView.setText(huodanInfo.carlong);
		holder.typeView.setText(huodanInfo.cartype);
		holder.possView.setText(huodanInfo.start);
		holder.poseView.setText(huodanInfo.end);
		holder.ownerView.setText(huodanInfo.owner+":");
		holder.msgView.setText(huodanInfo.msg);;
		return convertView;
	}

	/**
	 * view的存放类
	 */
	class Holder {
		TextView statusView;
		TextView timeView;
		TextView typeView;
		TextView longView;
		TextView possView;
		TextView poseView;
		TextView phoneView;
		TextView ownerView;
		TextView msgView;

	}
}
