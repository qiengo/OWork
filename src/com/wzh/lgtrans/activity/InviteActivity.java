package com.wzh.lgtrans.activity;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.wzh.lgtrans.R;

public class InviteActivity extends ActionBarBaseActivity {
	private ListView contactView;
	private ContactAdapter contactAdapter;
	private ProgressBar progressBar;
	private ContactTask contactTask;
	private final int MSG_DATA_CHANGE = 0x001;

	@SuppressLint("HandlerLeak")
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case MSG_DATA_CHANGE:
				contactAdapter.notifyDataSetChanged();
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invite);
		setActionBarTitel("邀请朋友");
		
		contactView = (ListView) findViewById(R.id.list_invite_contact);
		progressBar = (ProgressBar) findViewById(R.id.pb_invite);
		contactAdapter = new ContactAdapter(this);
		contactView.setAdapter(contactAdapter);
	}

	@Override
	protected void onResume() {
		if (contactTask == null) {
			contactTask = new ContactTask();
		}
		contactTask.execute();
		super.onResume();
	}

	@Override
	protected void onPause() {
		if (contactTask != null) {
			contactTask.cancel(true);
			contactTask = null;
		}
		super.onPause();
	}

	// public void getContact() {
	// contactList.clear();
	// Cursor cursor =
	// getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
	// null, null,
	// null, null);
	// while (cursor.moveToNext()) {
	// People people = new People();
	// people.name =
	// cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
	// people.mobilePhone =
	// cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
	// contactList.add(people);
	// }
	// cursor.close();
	// }

	class ContactTask extends AsyncTask<Void, Void, Void> {
		private ArrayList<People> list = new ArrayList<People>();

		@Override
		protected void onPreExecute() {
			list.clear();
			progressBar.setVisibility(View.VISIBLE);
		}

		@Override
		protected Void doInBackground(Void... params) {
			Cursor cursor = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
					null, null);
			while (cursor.moveToNext()) {
				People people = new People();
				people.name = cursor.getString(cursor
						.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
				people.mobilePhone = cursor.getString(cursor
						.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
				list.add(people);
			}
			cursor.close();
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			contactAdapter.setDataList(list);
			contactAdapter.notifyDataSetChanged();
//			handler.sendEmptyMessage(MSG_DATA_CHANGE);
			progressBar.setVisibility(View.GONE);
		}

	}

	class ContactAdapter extends BaseAdapter {

		private ArrayList<People> dataList = new ArrayList<People>();

		/**
		 * 实例化布局的对象
		 */
		private final LayoutInflater mInflater;

		public ContactAdapter(Context ctx) {
			mInflater = LayoutInflater.from(ctx);
		}

		public void setDataList(ArrayList<People> list) {
			dataList = list;
		}

		@Override
		public int getCount() {
			return dataList.size();
		}

		@Override
		public Object getItem(int position) {
			return position;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder = null;
			if (convertView != null) {
				holder = (Holder) convertView.getTag();
			} else {
				holder = new Holder();
				convertView = mInflater.inflate(R.layout.item_list_invite, null);
				holder.name = (TextView) convertView.findViewById(R.id.tv_item_invite_name);
				holder.phone = (TextView) convertView.findViewById(R.id.tv_item_invite_phone);
				convertView.setTag(holder);
			}
			People people = dataList.get(position);
			holder.name.setText(people.name);
			holder.phone.setText(people.mobilePhone);
			return convertView;
		}

		/**
		 * view的存放类
		 */
		class Holder {
			TextView name;
			TextView phone;
			ImageView img;
		}
	}

	class People {
		String name;
		String mobilePhone;

		public People() {
		}

		@Override
		public String toString() {
			return "name=" + name + ",mobilePhone=" + mobilePhone;
		}

	}

}
