package com.wzh.lgtrans.activity;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.wzh.lgtrans.R;

public class InviteActivity extends ActionBarBaseActivity {
	private ListView contactList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_invite);
		contactList = (ListView) findViewById(R.id.list_invite_contact);
	}

	public void getContact() {
		// 获得所有的联系人
		Cursor cur = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
		// 循环遍历
		if (cur.moveToFirst()) {
			int idColumn = cur.getColumnIndex(ContactsContract.Contacts._ID);
			int displayNameColumn = cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
			do {
				// 获得联系人的ID号
				String contactId = cur.getString(idColumn);
				// 获得联系人姓名
				String disPlayName = cur.getString(displayNameColumn);
				// 查看该联系人有多少个电话号码。如果没有这返回值为0
				int phoneCount = cur.getInt(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
				if (phoneCount > 0) {
					// 获得联系人的电话号码
					Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
					Cursor mobilePhone = getContentResolver().query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId + " and "
									+ ContactsContract.CommonDataKinds.Phone.TYPE + "="
									+ ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE, null, null);
					if (phones.moveToFirst()) {
						do {
							// 遍历所有的电话号码
							String phoneNumber = phones.getString(phones
									.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
							System.out.println(phoneNumber);
						} while (phones.moveToNext());
					}
				}
			} while (cur.moveToNext());
		}
	}

	class ContactAdapter extends BaseAdapter {
		/**
		 * 存放内容数据的数组list
		 */
		// public ArrayList<ItemCatg> mList = new ArrayList<ItemCatg>();
		/**
		 * 实例化布局的对象
		 */
		private final LayoutInflater mInflater;

		public ContactAdapter(Context ctx) {
			mInflater = LayoutInflater.from(ctx);
		}

		// public void setContentList(ArrayList<ItemCatg> list){
		// mList=list;
		// }

		@Override
		public int getCount() {
			return 10;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Holder holder = null;
			if (convertView != null) {
				holder = (Holder) convertView.getTag();
			} else {
				holder = new Holder();
				convertView = mInflater.inflate(R.layout.item_list_myhuodan_un, null);
				// holder.title=(TextView)convertView.findViewById(R.id.tv_list_catg_title);
				// holder.subtitle=(TextView)convertView.findViewById(R.id.tv_list_catg_subtitle);
				// holder.img=(ImageView)convertView.findViewById(R.id.iv_list_catg_cover);
				convertView.setTag(holder);
			}
			// ItemCatg item=mList.get(position);
			// holder.title.setText(item.title);
			// holder.subtitle.setText(item.subtitle);
			return convertView;
		}

		/**
		 * view的存放类
		 */
		class Holder {
			TextView title;
			TextView subtitle;
			ImageView img;
		}
	}

}
