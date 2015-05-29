package com.wzh.lgtrans.activity;

import java.io.File;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import com.wzh.lgtrans.R;
import com.wzh.lib.AppConfig;

public class PicUploadActivity extends ActionBarBaseActivity {

	public static final int NONE = 0;
	public static final int PHOTO_CAP = 1;// 拍照
	public static final int PHOTO_CROP = 2; // 缩放
	public static final int PHOTO_RET = 3;// 结果
	public static final String IMAGE_UNSPECIFIED = "image/*";

	private ImageView prevView;
	private File tempPic;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picupload);
		setActionBarTitel("个人信息");
		prevView = (ImageView) findViewById(R.id.iv_picUp_prev);
		tempPic=new File(AppConfig.FILE_FOLDER_DIR + "/temp.jpg");
		
		findViewById(R.id.btn_picUp_capture).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
				intent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(new File(AppConfig.FILE_FOLDER_DIR, "temp.jpg")));
				startActivityForResult(intent, PHOTO_CAP);
			}
		});
		findViewById(R.id.btn_picUp_choose).setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_PICK, null);
				intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, IMAGE_UNSPECIFIED);
				startActivityForResult(intent, PHOTO_CROP);
			}
		});
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == NONE)
			return;
		// 拍照
		if (requestCode == PHOTO_CAP) {
			// 设置文件保存路径这里放在跟目录下
			tempPic = new File(AppConfig.FILE_FOLDER_DIR+ "/temp.jpg");
			startPhotoCrop(Uri.fromFile(tempPic));
		}
		if (data == null)
			return;
		// 读取相册缩放图片
		if (requestCode == PHOTO_CROP) {
			startPhotoCrop(data.getData());
		}
		// 处理结果
		if (requestCode == PHOTO_RET) {
			prevView.setImageURI(Uri.fromFile(tempPic));
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	public void startPhotoCrop(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, IMAGE_UNSPECIFIED)   
		.putExtra("crop", "true")
		// aspectX aspectY 是宽高的比例
		.putExtra("aspectX", 1)
		.putExtra("aspectY", 1)
		.putExtra("outputX", 600)
		.putExtra("outputY", 600)
        .putExtra("scale", true)
        .putExtra("scaleUpIfNeeded", true)
        .putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempPic))
        .putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
		startActivityForResult(intent, PHOTO_RET);
	}
}
