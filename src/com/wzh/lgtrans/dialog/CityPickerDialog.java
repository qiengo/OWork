package com.wzh.lgtrans.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;

import com.wzh.lgtrans.R;

public class CityPickerDialog extends Dialog {
	private String ret="asdf";

	public CityPickerDialog(final Context context) {
		super(context);
		setTitle("选择城市");
		setContentView(R.layout.dialog_citypicker);
		setCanceledOnTouchOutside(true);

		findViewById(R.id.btn_citypicker_ok).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				onConfirm(ret);
			}
		});
	}

	public void onConfirm(String ret) {
		dismiss();
	}

}
