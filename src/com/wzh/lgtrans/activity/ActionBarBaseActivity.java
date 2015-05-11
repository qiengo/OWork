package com.wzh.lgtrans.activity;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wzh.logistics.R;

/**
 * ActionBar基础activity，进行统一的属性设置。<br>
 * 所有顶部带有actionbar的类都继承此类。
 * @author 王植桦 ewangzhihua@yeah.net
 * @version 创建时间：2015年5月6日
 */
public class ActionBarBaseActivity extends ActionBarActivity{

	private TextView titleView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		ActionBar actionBar=getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		 
		LinearLayout actionBarLayout = (LinearLayout)getLayoutInflater().inflate(R.layout.view_actionbar, null);
		titleView = (TextView)actionBarLayout.findViewById(R.id.tv_actionbar_title);
		ImageView backView=(ImageView)actionBarLayout.findViewById(R.id.iv_actionbar_back);
		backView.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ActionBarBaseActivity.this.finish();
			}
		});
		ActionBar.LayoutParams params = new ActionBar.LayoutParams(
		                                            ActionBar.LayoutParams.MATCH_PARENT,
		                                            ActionBar.LayoutParams.MATCH_PARENT,
		                                            Gravity.LEFT);
		actionBar.setCustomView(actionBarLayout, params);
		actionBar.setDisplayHomeAsUpEnabled(false);
	}
	
	protected void setActionBarTitel(String title){
		if(titleView!=null){
			titleView.setText(title);
		}
	}
}
