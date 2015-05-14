package com.wzh.lgtrans.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzh.lgtrans.R;
import com.wzh.lgtrans.activity.QiangSucActivity;

/**
 * 分类列表的适配器
 * @author 王植桦 ewangzhihua@yeah.net
 * @version 创建时间：2014年3月18日
 */
public class JieHuoAdapter extends BaseAdapter{
	private Context ctx;
	/**
	 * 存放内容数据的数组list
	 */
//	public ArrayList<ItemCatg> mList = new ArrayList<ItemCatg>();
	/**
	 * 实例化布局的对象
	 */
	private final LayoutInflater mInflater;
	public JieHuoAdapter(Context ctx){
		this.ctx=ctx;
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
			holder.qiang=(TextView)convertView.findViewById(R.id.tv_item_jiehuo_qiang);
			holder.qiang.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent=new Intent(ctx, QiangSucActivity.class);
					ctx.startActivity(intent);
				}
			});
			convertView.setTag(holder);
		}
		return convertView;
	}
	/**
	 * view的存放类
	 */
	class Holder{
		TextView title;
		TextView qiang;
		ImageView img;
	}
}
