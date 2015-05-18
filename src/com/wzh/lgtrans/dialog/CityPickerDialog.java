package com.wzh.lgtrans.dialog;

import java.util.List;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.wzh.lgtrans.R;
import com.wzh.lgtrans.view.CityInfo;
import com.wzh.lgtrans.view.CityUtil;
import com.wzh.lgtrans.view.ScrollerAdapter;
import com.wzh.lgtrans.view.ScrollerPicker;
import com.wzh.lgtrans.view.ScrollerPicker.OnSelectListener;

public class CityPickerDialog extends Dialog {
	private static final String TAG = "CityPickerDialog";
	private CityInfo defaultCity;
	private ScrollerPicker provPicker;
	private ScrollerPicker cityPicker;
	private ScrollerPicker counyPicker;
	private CityAdapter provAdapter;
	private CityAdapter cityAdapter;
	private CityAdapter counyAdapter;
	/** 选择监听 */
	private OnSelectingListener onSelectingListener;
	/** 刷新界面 */
	private static final int REFRESH_VIEW = 0x001;
	/** 临时日期 */
	private int tempProvinceIndex = -1;
	private int temCityIndex = -1;
	private int tempCounyIndex = -1;
	private CityUtil cityUtil = CityUtil.getSingleton();

	public CityPickerDialog(final Context context, CityInfo city) {
		super(context);
		setTitle("选择城市");
		setContentView(R.layout.dialog_citypicker);
		setCanceledOnTouchOutside(true);
		defaultCity = city;

		provPicker = (ScrollerPicker) findViewById(R.id.province);
		cityPicker = (ScrollerPicker) findViewById(R.id.city);
		counyPicker = (ScrollerPicker) findViewById(R.id.couny);
		provAdapter = new CityAdapter(true);
		provAdapter.setAllItem(new CityInfo("-1", "全国"));
		cityAdapter = new CityAdapter(true);
		cityAdapter.setAllItem(new CityInfo("-1", "不限"));
		counyAdapter = new CityAdapter(false);
		
		provAdapter.setData(cityUtil.getProvlist());
		provPicker.setAdapter(provAdapter);
		initSelect(defaultCity.getId());
		provPicker.setOnSelectListener(new OnSelectListener() {

			@Override
			public void endSelect(int id, String text) {
				System.out.println("id-->" + id + ",text----->" + text);
				if (text.equals("") || text == null || provAdapter.isAllItem(id)) {
					cityPicker.setEnable(false);
					counyPicker.setEnable(false);
					Message message = new Message();
					message.what = REFRESH_VIEW;
					handler.sendMessage(message);
					return;
				} else {
					cityPicker.setEnable(true);
					counyPicker.setEnable(true);
				}

				if (tempProvinceIndex != id) {
					Log.i(TAG, "province endselect");
					String selectDay = cityPicker.getSelectedText();
					if (selectDay != null && !selectDay.equals("")) {
						cityAdapter.setData(cityUtil.getCityList(provAdapter.getCode(id)));
						cityPicker.setAdapter(cityAdapter);
						cityPicker.setDefault(1);
					}
					String selectMonth = counyPicker.getSelectedText();
					if (selectMonth != null && !selectMonth.equals("")) {
						counyAdapter.setData(cityUtil.getCounyList(cityAdapter.getCode(1)));
						counyPicker.setAdapter(counyAdapter);
						counyPicker.setDefault(1);
					}
					int lastDay = Integer.valueOf(provPicker.getListSize());
					if (id > lastDay) {
						provPicker.setDefault(lastDay - 1);
					}
				}
				tempProvinceIndex = id;
				Message message = new Message();
				message.what = REFRESH_VIEW;
				handler.sendMessage(message);
			}

			@Override
			public void selecting(int id, String text) {
			}
		});
		cityPicker.setOnSelectListener(new OnSelectListener() {

			@Override
			public void endSelect(int id, String text) {
				if (text.equals("") || text == null || cityAdapter.isAllItem(id)) {
					counyPicker.setEnable(false);
					Message message = new Message();
					message.what = REFRESH_VIEW;
					handler.sendMessage(message);
					return;
				} else {
					counyPicker.setEnable(true);
				}

				if (temCityIndex != id) {
					Log.i(TAG, "city endselect");
					String selectProv = provPicker.getSelectedText();
					if (selectProv == null || selectProv.equals(""))
						return;
					String selectCouny = counyPicker.getSelectedText();
					if (selectCouny != null && !selectCouny.equals("")) {
						counyAdapter.setData(cityUtil.getCounyList(cityAdapter.getCode(id)));
						counyPicker.setAdapter(counyAdapter);
						counyPicker.setDefault(1);
					}
					int lastDay = Integer.valueOf(cityPicker.getListSize());
					if (id > lastDay) {
						cityPicker.setDefault(lastDay - 1);
					}
				}
				temCityIndex = id;
				Message message = new Message();
				message.what = REFRESH_VIEW;
				handler.sendMessage(message);
			}

			@Override
			public void selecting(int id, String text) {
			}
		});
		counyPicker.setOnSelectListener(new OnSelectListener() {

			@Override
			public void endSelect(int id, String text) {
				if (text.equals("") || text == null)
					return;
				if (tempCounyIndex != id) {
					Log.i(TAG, "couny endselect");
					String selectDay = provPicker.getSelectedText();
					if (selectDay == null || selectDay.equals(""))
						return;
					String selectMonth = cityPicker.getSelectedText();
					if (selectMonth == null || selectMonth.equals(""))
						return;
					int lastDay = Integer.valueOf(counyPicker.getListSize());
					if (id > lastDay) {
						counyPicker.setDefault(lastDay - 1);
					}
				}
				tempCounyIndex = id;
				Message message = new Message();
				message.what = REFRESH_VIEW;
				handler.sendMessage(message);
			}

			@Override
			public void selecting(int id, String text) {
			}
		});

		findViewById(R.id.btn_citypicker_cancel).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		findViewById(R.id.btn_citypicker_ok).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				CityInfo selCity;
				int counyId = counyPicker.getSelected();
				Log.i(TAG, "counyId:" + counyId);
				if (counyId > 0 && counyPicker.isEnable()) {
					selCity = counyAdapter.getDataList().get(counyId);
				} else {
					int cityId = cityPicker.getSelected();
					Log.i(TAG, "cityId:" + cityId);
					if (cityId > 0 && cityPicker.isEnable()) {
						selCity = cityAdapter.getDataList().get(cityId);
					} else {
						int provId = provPicker.getSelected();
						Log.i(TAG, "provId:" + provId);
						selCity = provAdapter.getDataList().get(provId);
					}
				}
				onConfirm(selCity);
			}
		});
	}

	public void onConfirm(CityInfo city) {
		dismiss();
	}

	public void initSelect(String code) {
		Log.i(TAG, "init code:"+code);
		int provId = 0;
		int cityId = 0;
		int counyId = 0;
		if (code.length() != 6) {
			code="000000";
		}
		String provCode = code.substring(0, 2) + "0000";
		String locProv = null;
		String locCity = null;
		int prov_count = provAdapter.getDataList().size();
		for (int i = 0; i < prov_count; i++) {
			CityInfo locateInfo = provAdapter.getDataList().get(i);
			if (locateInfo.getId().equals(provCode)) {
				locProv = locateInfo.getId();
				provId = i;
				break;
			}
		}
		provPicker.setDefault(provId);
		Log.i(TAG, "init provId:"+provId);
		
		if (provId == 0) {
			cityAdapter.setData(cityUtil.getCityList(provAdapter.getCode(2)));
			cityPicker.setEnable(false);
		} else {
			cityAdapter.setData(cityUtil.getCityList(provAdapter.getCode(provId)));
			cityPicker.setEnable(true);
		}
		cityPicker.setAdapter(cityAdapter);
		if (locProv != null) {
			String cityCode = code.substring(0, 4) + "00";
			int city_count = cityAdapter.getDataList().size();
			for (int i = 0; i < city_count; i++) {
				CityInfo locateInfo = cityAdapter.getDataList().get(i);
				if (locateInfo.getId().equals(cityCode)) {
					locCity = locateInfo.getId();
					cityId = i;
					break;
				}
			}
		}
		cityPicker.setDefault(cityId);
		Log.i(TAG, "init cityId:"+cityId);
		if (cityId == 0) {
			counyAdapter.setData(cityUtil.getCounyList(cityAdapter.getCode(1)));
			counyPicker.setEnable(false);
		} else {
			counyAdapter.setData(cityUtil.getCounyList(cityAdapter.getCode(cityId)));
			counyPicker.setEnable(true);
		}
		counyPicker.setAdapter(counyAdapter);
		if (locCity != null) {
			int couny_count = counyAdapter.getDataList().size();
			for (int i = 0; i < couny_count; i++) {
				CityInfo locateInfo = counyAdapter.getDataList().get(i);
				if (locateInfo.getId().equals(code)) {
					counyId = i;
					break;
				}
			}
		}
		counyPicker.setDefault(counyId);
		Log.i(TAG, "init counyId:"+counyId);

	}

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case REFRESH_VIEW:
				if (onSelectingListener != null)
					onSelectingListener.selected(true);
				counyPicker.invalidate();
				cityPicker.invalidate();
				break;
			default:
				break;
			}
		}
	};

	public void setOnSelectingListener(OnSelectingListener onSelectingListener) {
		this.onSelectingListener = onSelectingListener;
	}

	public interface OnSelectingListener {
		public void selected(boolean selected);
	}

	class CityAdapter extends ScrollerAdapter<CityInfo> {

		public CityAdapter(boolean isAllEnable) {
			super(isAllEnable);
		}

		@Override
		public String getContent(int i) {
			return dataList.get(i).getName();
		}

		String getCode(int i) {
			if (dataList != null && dataList.size() > i) {
				return dataList.get(i).getId();
			} else {
				return null;
			}
		}

	}

}
