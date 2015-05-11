package com.wzh.lgtrans.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;

import com.wzh.lgtrans.fragment.MainFragment;
import com.wzh.lgtrans.fragment.MyFragment;
import com.wzh.logistics.R;

public class MainActivity extends FragmentActivity {
	private static final String TAG = "MainActivity";
	private ViewPager mPager;
	private ArrayList<Fragment> fragmentList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mian);

//		ActionBar actionBar = getSupportActionBar();
//		actionBar.hide();
		
		mPager = (ViewPager) findViewById(R.id.vpg_main);
		fragmentList = new ArrayList<Fragment>();
		fragmentList.add(new MainFragment());
		fragmentList.add(new MyFragment());
		mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));  
        mPager.setCurrentItem(0);//设置当前显示标签页为第一页  
		
	}

	public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
		ArrayList<Fragment> list;
		public MyFragmentPagerAdapter(FragmentManager fm, ArrayList<Fragment> list) {
			super(fm);
			this.list = list;
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Fragment getItem(int arg0) {
			return list.get(arg0);
		}
	}

}
