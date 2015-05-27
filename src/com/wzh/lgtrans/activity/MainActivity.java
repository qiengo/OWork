package com.wzh.lgtrans.activity;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.wzh.lgtrans.R;
import com.wzh.lgtrans.fragment.MainFragment;
import com.wzh.lgtrans.fragment.MoreFragment;
import com.wzh.lgtrans.fragment.MyFragment;

public class MainActivity extends FragmentActivity {
	private ViewPager mPager;
	private ArrayList<Fragment> fragmentList;
	private ImageView ivMore, ivHome, ivMy;
	private TextView tvMore, tvMy;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		ivMore = (ImageView) findViewById(R.id.iv_main_more);
		ivHome = (ImageView) findViewById(R.id.iv_main_home);
		ivMy = (ImageView) findViewById(R.id.iv_main_my);
		tvMore = (TextView) findViewById(R.id.tv_main_more);
		tvMy = (TextView) findViewById(R.id.tv_main_my);

		mPager = (ViewPager) findViewById(R.id.vpg_main);
		fragmentList = new ArrayList<Fragment>();
		fragmentList.add(new MoreFragment());
		fragmentList.add(new MainFragment());
		fragmentList.add(new MyFragment());
		mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));

		findViewById(R.id.lay_lin_main_more).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mPager.setCurrentItem(0);
			}
		});
		ivHome.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mPager.setCurrentItem(1);
			}
		});
		findViewById(R.id.lay_lin_main_my).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				mPager.setCurrentItem(2);
			}
		});
		mPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

			@Override
			public void onPageSelected(int arg0) {
				clearStatus();
				if (arg0 == 0) {
					ivMore.setImageResource(R.drawable.ic_main_box_sel);
					ivMore.setBackgroundResource(R.drawable.bg_main_box_sel);
					tvMore.setVisibility(View.GONE);
				} else if (arg0 == 1) {
					ivHome.setImageResource(R.drawable.ic_main_home_sel);
					ivHome.setBackgroundResource(R.drawable.bg_main_home_sel);
				} else if (arg0 == 2) {
					ivMy.setImageResource(R.drawable.ic_main_my_sel);
					ivMy.setBackgroundResource(R.drawable.bg_main_box_sel);
					tvMy.setVisibility(View.GONE);
				}
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
			}

			@Override
			public void onPageScrollStateChanged(int arg0) {
			}
		});
		mPager.setCurrentItem(1);

	}

	private void clearStatus() {
		ivMore.setImageResource(R.drawable.ic_main_box_nor);
		ivMore.setBackgroundResource(0);
		ivHome.setImageResource(R.drawable.ic_main_home_nor);
		ivHome.setBackgroundResource(R.drawable.bg_main_home_nor);
		ivMy.setImageResource(R.drawable.ic_main_my_nor);
		ivMy.setBackgroundResource(0);
		tvMore.setVisibility(View.VISIBLE);
		tvMy.setVisibility(View.VISIBLE);
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
