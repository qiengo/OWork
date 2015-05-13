package com.wzh.lgtrans.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzh.lgtrans.R;

/**
 * 分类列表的适配器
 * @author 王植桦 ewangzhihua@yeah.net
 * @version 创建时间：2014年3月18日
 */
public class JieHuoAdapter extends BaseAdapter{
	/**
	 * 存放内容数据的数组list
	 */
//	public ArrayList<ItemCatg> mList = new ArrayList<ItemCatg>();
	/**
	 * 实例化布局的对象
	 */
	private final LayoutInflater mInflater;
	public JieHuoAdapter(Context ctx){
		mInflater= LayoutInflater.from(ctx);
	}
	
//	public void setContentList(ArrayList<ItemCatg> list){
//		mList=list;
//	}
	
	@Override
	public int getCount() {
		return 10;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder=null;
		if(convertView!=null){
			holder=(Holder)convertView.getTag();
		}else{
			holder=new Holder();
			convertView=mInflater.inflate(R.layout.item_list_jiehuo, null);
//			holder.title=(TextView)convertView.findViewById(R.id.tv_list_catg_title);
//			holder.subtitle=(TextView)convertView.findViewById(R.id.tv_list_catg_subtitle);
//			holder.img=(ImageView)convertView.findViewById(R.id.iv_list_catg_cover);
			convertView.setTag(holder);
		}
//		ItemCatg item=mList.get(position);
//		holder.title.setText(item.title);
//		holder.subtitle.setText(item.subtitle);
		return convertView;
	}
	/**
	 * view的存放类
	 */
	class Holder{
		TextView title;
		TextView subtitle;
		ImageView img;
	}
}
