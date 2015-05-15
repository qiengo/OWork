package com.wzh.lgtrans;

import java.io.File;

import android.content.Context;
import android.content.res.Resources;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * 应用配置类 包含应用的各种全局参数变量
 * 
 * @author Administrator
 *
 */
public class AppConfig {
	private static final String TAG = "AppConfig";
	/**
	 * 设备屏幕密度
	 */
	public static float SCREEN_DENSITY = 1.0f;
	/**
	 * 设备屏幕宽度
	 */
	private static int SCREEN_WIDTH = 0;
	/**
	 * 设备屏幕高度
	 */
	private static int SCREEN_HEIGHT = 0;
	/**
	 * 缓存文件位置
	 */
	private static final String FILE_FOLDER_STR = "wzh_cwt";
	private static final String TEMP_FOLDER_STR = FILE_FOLDER_STR+"/temp";
	public static File FILE_FOLDER_DIR;
	public static File TEMP_FOLDER_DIR;

	/**
	 * 是否使用本地json文件，用于演示。
	 */
	public static final boolean USE_LOCAL_JSON = true;
	/**
	 * 获取百度地图服务器数据用到的数据。
	 */
	public static final String MAP_AK = "qKjfG1BtWPG4poMGvBsEc6ui";
	public static final int MAP_TABLE_ID = 61558;

	/**
	 * 启动app是加载设备信息。 load the configuration when running
	 * 
	 * @param context
	 */
	public static void loadConfigs(Context context) {
		final Resources resources = context.getResources();
		final DisplayMetrics dm = resources.getDisplayMetrics();
		SCREEN_DENSITY = dm.density;
		SCREEN_WIDTH = dm.widthPixels;
		SCREEN_HEIGHT = dm.heightPixels;
		if (BuildConfig.DEBUG) {
			// final int densityDpi = dm.densityDpi;
			// final float scaledDensity = dm.scaledDensity;
			Log.i(TAG, "Screen Size: " + SCREEN_WIDTH + " x " + SCREEN_HEIGHT);
		}
		
		ensureFileFolder();
	}
	
	private static void ensureFileFolder(){
		FILE_FOLDER_DIR = new File(Environment.getExternalStorageDirectory(), FILE_FOLDER_STR);
		if (!FILE_FOLDER_DIR.exists()) {
			FILE_FOLDER_DIR.mkdirs();
			Log.i(TAG, FILE_FOLDER_DIR.getAbsolutePath() + " not exist, create it!");
		} else {
			Log.i(TAG, FILE_FOLDER_DIR.getAbsolutePath() + " already exist!");
		}
		TEMP_FOLDER_DIR = new File(Environment.getExternalStorageDirectory(), TEMP_FOLDER_STR);
		if (!TEMP_FOLDER_DIR.exists()) {
			TEMP_FOLDER_DIR.mkdirs();
			Log.i(TAG, TEMP_FOLDER_DIR.getAbsolutePath() + " not exist, create it!");
		} else {
			Log.i(TAG, TEMP_FOLDER_DIR.getAbsolutePath() + " already exist!");
		}
	}

	/**
	 * 获取设备屏幕高度
	 * 
	 * @return
	 */
	public static int getScreenHeight() {
		// return SCREEN_HEIGHT;
		return Math.max(SCREEN_WIDTH, SCREEN_HEIGHT);
	}

	/**
	 * 获取设备屏幕宽度
	 * 
	 * @return
	 */
	public static int getScreenWidth() {
		// return SCREEN_WIDTH;
		return Math.min(SCREEN_WIDTH, SCREEN_HEIGHT);
	}
}
