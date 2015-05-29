package com.wzh.lgtrans.activity;

import java.util.ArrayList;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.wzh.lgtrans.R;
import com.wzh.lgtrans.dialog.CarTypeDialog;
import com.wzh.lib.struct.IdName;

public class Register2Activity extends ActionBarBaseActivity {

	public static ArrayList<IdName> cityList = new ArrayList<IdName>();
	private static final int REQ_CITY = 1;
	private TextView cityView,carTypeView;
	private String carType;
	private String carLong;
	private static final String[] m_arr = {"粤","鲁","冀"};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register2);
		setActionBarTitel("完善信息");

		initData();
		cityView=(TextView)findViewById(R.id.tv_reg2_busi_city);
		carTypeView=(TextView)findViewById(R.id.tv_reg2_car_type);
		
		Spinner _spin = (Spinner)findViewById(R.id.sp_reg2_number);
        ArrayAdapter<String> ada = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, m_arr);
        ada.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        _spin.setAdapter(ada);

        
		cityView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Register2Activity.this, BusiCityActivity.class);
				startActivityForResult(intent, REQ_CITY);
			}
		});
		carTypeView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new CarTypeDialog(Register2Activity.this, carType,carLong) {
					@Override
					public void onConfirm(String cartype,String carlong) {
						carType=cartype;
						carLong=carlong;
						carTypeView.setText(carType+" "+carLong);
						dismiss();
					}
				}.show();
			}
		});

		findViewById(R.id.tv_btn_next).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Register2Activity.this, Register3Activity.class);
				startActivity(intent);
			}
		});
	}

	private void initData() {
		cityList.clear();
		for (int i = 0; i < 10; i++) {
			cityList.add(new IdName());
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
		case REQ_CITY:
			String cityStr=getCityStr();
			if(cityStr!=null&&cityStr.length()>0){
				cityView.setText(cityStr);
			}
			break;
		default:
			break;
		}
	}

	private String getCityStr() {
		String ret = "";
		for (int i = 0; i < cityList.size(); i++) {
			IdName cityInfo = cityList.get(i);
			if (cityInfo != null) {
				String temp = cityInfo.getName();
				if (temp != null && temp.length() > 0) {
					if (ret.length() > 0) {
						ret += ",";
					}
					ret += temp;
				}
			}
		}
		return ret;
	}
}
