package com.wzh.lgtrans.view;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.wzh.lgtrans.R;
import com.wzh.lgtrans.view.ScrollerPicker.OnSelectListener;

/**
 * 城市Picker
 * 
 * @author zd
 * 
 */
public class LocatePicker extends LinearLayout {
	/** 滑动控件 */
	private ScrollerPicker provincePicker;
	private ScrollerPicker cityPicker;
	private ScrollerPicker counyPicker;
	private AddrAdapter provAdapter;
	private AddrAdapter cityAdapter;
	private AddrAdapter counyAdapter;
	/** 选择监听 */
	private OnSelectingListener onSelectingListener;
	/** 刷新界面 */
	private static final int REFRESH_VIEW = 0x001;
	/** 临时日期 */
	private int tempProvinceIndex = -1;
	private int temCityIndex = -1;
	private int tempCounyIndex = -1;
	private Context context;

	private LocateUtil cityUtil = LocateUtil.getSingleton();
	private String city_code_string;
	private String city_string;

	public LocatePicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
	}

	public LocatePicker(Context context) {
		super(context);
		this.context = context;
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		LayoutInflater.from(getContext()).inflate(R.layout.lay_city_picker, this);
		provincePicker = (ScrollerPicker) findViewById(R.id.province);
		cityPicker = (ScrollerPicker) findViewById(R.id.city);
		counyPicker = (ScrollerPicker) findViewById(R.id.couny);
		provAdapter=new AddrAdapter(cityUtil.getProvlist());
		provincePicker.setAdapter(provAdapter);
		provincePicker.setDefault(1);
		cityAdapter=new AddrAdapter(cityUtil.getCityList(provAdapter.getCode(1)));
		cityPicker.setAdapter(cityAdapter);
		cityPicker.setDefault(1);
		counyAdapter=new AddrAdapter(cityUtil.getCounyList(cityAdapter.getCode(1)));
		counyPicker.setAdapter(counyAdapter);
		counyPicker.setDefault(1);
		provincePicker.setOnSelectListener(new OnSelectListener() {

			@Override
			public void endSelect(int id, String text) {
				System.out.println("id-->" + id + "text----->" + text);
				if (text.equals("") || text == null||id==0){
					cityPicker.setEnable(false);
					counyPicker.setEnable(false);
					Message message = new Message();
					message.what = REFRESH_VIEW;
					handler.sendMessage(message);
					return;
				}else{
					cityPicker.setEnable(true);
					counyPicker.setEnable(true);
				}
				if (tempProvinceIndex != id) {
					System.out.println("endselect");
					String selectDay = cityPicker.getSelectedText();
					if (selectDay == null || selectDay.equals(""))
						return;
					String selectMonth = counyPicker.getSelectedText();
					if (selectMonth == null || selectMonth.equals(""))
						return;
					// 城市数组
					cityAdapter.setData(cityUtil.getCityList(provAdapter.getCode(id)));
					cityPicker.setAdapter(cityAdapter);
					cityPicker.setDefault(1);
					counyAdapter.setData(cityUtil.getCounyList(cityAdapter.getCode(1)));
					counyPicker.setAdapter(counyAdapter);
					counyPicker.setDefault(1);
					int lastDay = Integer.valueOf(provincePicker.getListSize());
					if (id > lastDay) {
						provincePicker.setDefault(lastDay - 1);
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
				if (text.equals("") || text == null)
					return;
				if (temCityIndex != id) { 
					String selectDay = provincePicker.getSelectedText();
					if (selectDay == null || selectDay.equals(""))
						return;
					String selectMonth = counyPicker.getSelectedText();
					if (selectMonth == null || selectMonth.equals(""))
						return;
					counyAdapter.setData(cityUtil.getCounyList(cityAdapter.getCode(id)));
					counyPicker.setAdapter(counyAdapter);
					counyPicker.setDefault(1);
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
					String selectDay = provincePicker.getSelectedText();
					if (selectDay == null || selectDay.equals(""))
						return;
					String selectMonth = cityPicker.getSelectedText();
					if (selectMonth == null || selectMonth.equals(""))
						return;
					// 城市数组
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
			public void selecting(int id, String text) {}
		});
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

	public String getCity_code_string() {
		return city_code_string;
	}

	public String getCity_string() {
		city_string = provincePicker.getSelectedText() + cityPicker.getSelectedText() + counyPicker.getSelectedText();
		return city_string;
	}

	public interface OnSelectingListener {

		public void selected(boolean selected);
	}
	
	class AddrAdapter extends ScrollerAdapter<LocateInfo>{
		
		private List<LocateInfo> dataList;
		public AddrAdapter(List<LocateInfo> list){
			this.dataList=list;
		}
		
		public void setData(List<LocateInfo> list){
			this.dataList=list;
		}
		
		@Override
		String getContent(int i) {
			return dataList.get(i).getName();
		}

		@Override
		int getSize() {
			if(dataList!=null){
				return dataList.size();
			}else{
				return 0;
			}
		}
		
		String getCode(int i){
			if(dataList!=null){
				return dataList.get(i).getId();
			}else{
				return null;
			}
		}
	}
}
