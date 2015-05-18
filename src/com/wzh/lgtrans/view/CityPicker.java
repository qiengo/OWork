//package com.wzh.lgtrans.view;
//
//import java.util.List;
//
//import android.annotation.SuppressLint;
//import android.content.Context;
//import android.os.Handler;
//import android.os.Message;
//import android.util.AttributeSet;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.widget.LinearLayout;
//
//import com.wzh.lgtrans.R;
//import com.wzh.lgtrans.view.ScrollerPicker.OnSelectListener;
//
//public class CityPicker extends LinearLayout {
//	private static final String TAG = "CityPicker";
//	/** 滑动控件 */
//	private ScrollerPicker provincePicker;
//	private ScrollerPicker cityPicker;
//	private ScrollerPicker counyPicker;
//	private CityAdapter provAdapter;
//	private CityAdapter cityAdapter;
//	private CityAdapter counyAdapter;
//	/** 选择监听 */
//	private OnSelectingListener onSelectingListener;
//	/** 刷新界面 */
//	private static final int REFRESH_VIEW = 0x001;
//	/** 临时日期 */
//	private int tempProvinceIndex = -1;
//	private int temCityIndex = -1;
//	private int tempCounyIndex = -1;
//	private Context context;
//
//	private CityUtil cityUtil = CityUtil.getSingleton();
//
//	public CityPicker(Context context, AttributeSet attrs) {
//		super(context, attrs);
//		this.context = context;
//	}
//
//	public CityPicker(Context context) {
//		super(context);
//		this.context = context;
//	}
//
//	@Override
//	protected void onFinishInflate() {
//		super.onFinishInflate();
//		LayoutInflater.from(getContext()).inflate(R.layout.lay_city_picker, this);
//		provincePicker = (ScrollerPicker) findViewById(R.id.province);
//		cityPicker = (ScrollerPicker) findViewById(R.id.city);
//		counyPicker = (ScrollerPicker) findViewById(R.id.couny);
//		provAdapter = new CityAdapter(true);
//		provAdapter.setData(cityUtil.getProvlist());
//		provincePicker.setAdapter(provAdapter);
//		initSelect("370000");
//		provincePicker.setOnSelectListener(new OnSelectListener() {
//
//			@Override
//			public void endSelect(int id, String text) {
//				System.out.println("id-->" + id + ",text----->" + text);
//				if (text.equals("") || text == null || provAdapter.isAllItem(id)) {
//					cityPicker.setEnable(false);
//					counyPicker.setEnable(false);
//					Message message = new Message();
//					message.what = REFRESH_VIEW;
//					handler.sendMessage(message);
//					return;
//				} else {
//					cityPicker.setEnable(true);
//					counyPicker.setEnable(true);
//				}
//
//				if (tempProvinceIndex != id) {
//					Log.i(TAG, "province endselect");
//					String selectDay = cityPicker.getSelectedText();
//					if (selectDay != null && !selectDay.equals("")) {
//						cityAdapter.setData(cityUtil.getCityList(provAdapter.getCode(id)));
//						cityPicker.setAdapter(cityAdapter);
//						cityPicker.setDefault(1);
//					}
//					String selectMonth = counyPicker.getSelectedText();
//					if (selectMonth != null && !selectMonth.equals("")) {
//						counyAdapter.setData(cityUtil.getCounyList(cityAdapter.getCode(1)));
//						counyPicker.setAdapter(counyAdapter);
//						counyPicker.setDefault(1);
//					}
//					int lastDay = Integer.valueOf(provincePicker.getListSize());
//					if (id > lastDay) {
//						provincePicker.setDefault(lastDay - 1);
//					}
//				}
//				tempProvinceIndex = id;
//				Message message = new Message();
//				message.what = REFRESH_VIEW;
//				handler.sendMessage(message);
//			}
//
//			@Override
//			public void selecting(int id, String text) {
//			}
//		});
//		cityPicker.setOnSelectListener(new OnSelectListener() {
//
//			@Override
//			public void endSelect(int id, String text) {
//				if (text.equals("") || text == null || cityAdapter.isAllItem(id)) {
//					counyPicker.setEnable(false);
//					Message message = new Message();
//					message.what = REFRESH_VIEW;
//					handler.sendMessage(message);
//					return;
//				} else {
//					counyPicker.setEnable(true);
//				}
//
//				if (temCityIndex != id) {
//					Log.i(TAG, "city endselect");
//					String selectProv = provincePicker.getSelectedText();
//					if (selectProv == null || selectProv.equals(""))
//						return;
//					String selectCouny = counyPicker.getSelectedText();
//					if (selectCouny != null && !selectCouny.equals("")) {
//						counyAdapter.setData(cityUtil.getCounyList(cityAdapter.getCode(id)));
//						counyPicker.setAdapter(counyAdapter);
//						counyPicker.setDefault(1);
//					}
//					int lastDay = Integer.valueOf(cityPicker.getListSize());
//					if (id > lastDay) {
//						cityPicker.setDefault(lastDay - 1);
//					}
//				}
//				temCityIndex = id;
//				Message message = new Message();
//				message.what = REFRESH_VIEW;
//				handler.sendMessage(message);
//			}
//
//			@Override
//			public void selecting(int id, String text) {
//			}
//		});
//		counyPicker.setOnSelectListener(new OnSelectListener() {
//
//			@Override
//			public void endSelect(int id, String text) {
//				if (text.equals("") || text == null)
//					return;
//				if (tempCounyIndex != id) {
//					Log.i(TAG, "couny endselect");
//					String selectDay = provincePicker.getSelectedText();
//					if (selectDay == null || selectDay.equals(""))
//						return;
//					String selectMonth = cityPicker.getSelectedText();
//					if (selectMonth == null || selectMonth.equals(""))
//						return;
//					int lastDay = Integer.valueOf(counyPicker.getListSize());
//					if (id > lastDay) {
//						counyPicker.setDefault(lastDay - 1);
//					}
//				}
//				tempCounyIndex = id;
//				Message message = new Message();
//				message.what = REFRESH_VIEW;
//				handler.sendMessage(message);
//			}
//
//			@Override
//			public void selecting(int id, String text) {
//			}
//		});
//	}
//
//	public void initSelect(String code) {
//		int provId = 0;
//		int cityId = 0;
//		int counyId = 0;
//		if (code.length() == 6) {
//			String provCode = code.substring(0, 2) + "0000";
//			System.out.println("======provCode:" + provCode);
//			String locProv = null;
//			String locCity = null;
//			int prov_count = provAdapter.dataList.size();
//			for (int i = 0; i < prov_count; i++) {
//				CityInfo locateInfo = provAdapter.dataList.get(i);
//				if (locateInfo.getId().equals(provCode)) {
//					locProv = locateInfo.getId();
//					System.out.println("======provName:" + locateInfo.getName());
//					provId = i;
//					break;
//				}
//			}
//			provincePicker.setDefault(provId);
//			cityAdapter = new CityAdapter(true);
//			cityAdapter.setData(cityUtil.getCityList(provAdapter.getCode(provId)));
//			cityPicker.setAdapter(cityAdapter);
//			if (locProv != null) {
//				String cityCode = code.substring(0, 4) + "00";
//				System.out.println("======cityCode:" + cityCode);
//				int city_count = cityAdapter.dataList.size();
//				for (int i = 0; i < city_count; i++) {
//					CityInfo locateInfo = cityAdapter.dataList.get(i);
//					if (locateInfo.getId().equals(cityCode)) {
//						locCity = locateInfo.getId();
//						System.out.println("======cityName:" + locateInfo.getName());
//						cityId = i;
//						break;
//					}
//				}
//			}
//			cityPicker.setDefault(cityId);
//			counyAdapter = new CityAdapter(false);
//			if (cityId == 0) {
//				counyAdapter.setData(cityUtil.getCounyList(cityAdapter.getCode(1)));
//				counyPicker.setEnable(false);
//			} else {
//				counyAdapter.setData(cityUtil.getCounyList(cityAdapter.getCode(cityId)));
//				counyPicker.setEnable(true);
//			}
//			counyPicker.setAdapter(counyAdapter);
//			if (locCity != null) {
//				int couny_count = counyAdapter.dataList.size();
//				for (int i = 0; i < couny_count; i++) {
//					CityInfo locateInfo = counyAdapter.dataList.get(i);
//					if (locateInfo.getId().equals(code)) {
//						System.out.println("======counyName:" + locateInfo.getName());
//						counyId = i;
//						break;
//					}
//				}
//			}
//			counyPicker.setDefault(counyId);
//		}
//
//	}
//
//	@SuppressLint("HandlerLeak")
//	Handler handler = new Handler() {
//
//		@Override
//		public void handleMessage(Message msg) {
//			super.handleMessage(msg);
//			switch (msg.what) {
//			case REFRESH_VIEW:
//				if (onSelectingListener != null)
//					onSelectingListener.selected(true);
//				counyPicker.invalidate();
//				cityPicker.invalidate();
//				break;
//			default:
//				break;
//			}
//		}
//	};
//
//	public void setOnSelectingListener(OnSelectingListener onSelectingListener) {
//		this.onSelectingListener = onSelectingListener;
//	}
//
//	public interface OnSelectingListener {
//		public void selected(boolean selected);
//	}
//
//	class CityAdapter extends ScrollerAdapter<CityInfo> {
//
//		public CityAdapter(boolean isAllEnable) {
//			super(isAllEnable);
//		}
//
//		@Override
//		public void setData(List<CityInfo> list) {
//			if (isAllEnable()) {
//				CityInfo allItem = new CityInfo("-1", "不限");
//				setAllItem(allItem);
//			}
//			if (list == null) {
//				setEnabled(false);
//			} else {
//				setEnabled(true);
//			}
//			super.setData(list);
//		}
//
//		@Override
//		String getContent(int i) {
//			return dataList.get(i).getName();
//		}
//
//		String getCode(int i) {
//			if (dataList != null && dataList.size() > i) {
//				return dataList.get(i).getId();
//			} else {
//				return null;
//			}
//		}
//
//	}
//}
