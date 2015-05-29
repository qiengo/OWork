package com.wzh.lgtrans.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import com.wzh.lgtrans.R;
import com.wzh.lib.struct.IdName;
import com.wzh.lib.util.JsonUtil;
import com.wzh.lib.view.ScrollerAdapter;
import com.wzh.lib.view.ScrollerPicker;
import com.wzh.lib.view.ScrollerPicker.OnSelectListener;

public class CarTypeDialog extends Dialog {
	private static final String TAG = "CarTypeDialog";
	private ScrollerPicker typePicker;
	private ScrollerPicker longPicker;
	private CarTypeAdapter typeAdapter;
	private CarLongAdapter longAdapter;
	/** 选择监听 */
	private OnSelectingListener onSelectingListener;
	/** 刷新界面 */
	private static final int REFRESH_VIEW = 0x001;
	/** 临时日期 */
	private int tempProvinceIndex = -1;
	private int temCityIndex = -1;

	public CarTypeDialog(final Context context,String carType,String carLong) {
		super(context);
		setTitle("选择车型车长");
		setContentView(R.layout.dialog_carpicker);
		setCanceledOnTouchOutside(true);

		typePicker = (ScrollerPicker) findViewById(R.id.sp_cartype_type);
		longPicker = (ScrollerPicker) findViewById(R.id.sp_cartype_long);
		typeAdapter = new CarTypeAdapter(false);
		longAdapter = new CarLongAdapter(false);
		typeAdapter.setData(JsonUtil.getCarTypelist());
		typePicker.setAdapter(typeAdapter);
		initSelect(carType,carLong);
		typePicker.setOnSelectListener(new OnSelectListener() {

			@Override
			public void endSelect(int id, String text) {
				System.out.println("id-->" + id + ",text----->" + text);
				if (text.equals("") || text == null || typeAdapter.isAllItem(id)) {
					longPicker.setEnable(false);
					Message message = new Message();
					message.what = REFRESH_VIEW;
					handler.sendMessage(message);
					return;
				} else {
					longPicker.setEnable(true);
				}

				if (tempProvinceIndex != id) {
					Log.i(TAG, "province endselect");
					String selectDay = longPicker.getSelectedText();
					if (selectDay != null && !selectDay.equals("")) {
						longAdapter.setData(JsonUtil.getCarLongList(typeAdapter.getCode(id)));
						longPicker.setAdapter(longAdapter);
						longPicker.setDefault(1);
					}
					int lastDay = Integer.valueOf(typePicker.getListSize());
					if (id > lastDay) {
						typePicker.setDefault(lastDay - 1);
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
		longPicker.setOnSelectListener(new OnSelectListener() {

			@Override
			public void endSelect(int id, String text) {
				if (text.equals("") || text == null || longAdapter.isAllItem(id)) {
					Message message = new Message();
					message.what = REFRESH_VIEW;
					handler.sendMessage(message);
					return;
				} 

				if (temCityIndex != id) {
					Log.i(TAG, "city endselect");
					String selectProv = typePicker.getSelectedText();
					if (selectProv == null || selectProv.equals(""))
						return;
					int lastDay = Integer.valueOf(longPicker.getListSize());
					if (id > lastDay) {
						longPicker.setDefault(lastDay - 1);
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

		findViewById(R.id.btn_citypicker_cancel).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				dismiss();
			}
		});
		findViewById(R.id.btn_citypicker_ok).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				IdName selType = typeAdapter.getDataList().get(typePicker.getSelected());
				String carLong=longAdapter.getDataList().get(longPicker.getSelected());
				onConfirm(selType.getName(),carLong);
			}
		});
	}

	public void onConfirm(String cartype,String carlong) {
		dismiss();
	}

	public void initSelect(String carType,String carLong) {
		int typeId = 0;
		int longId = 0;
		int type_count = typeAdapter.getDataList().size();
		for (int i = 0; i < type_count; i++) {
			IdName idName = typeAdapter.getDataList().get(i);
			if (idName.getName().equals(carType)) {
				typeId = i;
				break;
			}
		}
		typePicker.setDefault(typeId);
		
		longAdapter.setData(JsonUtil.getCarLongList(typeAdapter.getCode(typeId)));
		longPicker.setAdapter(longAdapter);
		int long_count = longAdapter.getDataList().size();
		for (int i = 0; i < long_count; i++) {
			String temp = longAdapter.getDataList().get(i);
			if (temp.equals(carLong)) {
				longId = i;
				break;
			}
		}
		longPicker.setDefault(longId);
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
				longPicker.invalidate();
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

	class CarTypeAdapter extends ScrollerAdapter<IdName> {

		public CarTypeAdapter(boolean isAllEnable) {
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
	class CarLongAdapter extends ScrollerAdapter<String> {

		public CarLongAdapter(boolean isAllEnable) {
			super(isAllEnable);
		}

		@Override
		public String getContent(int i) {
			return dataList.get(i);
		}
	}

}
