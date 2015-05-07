package com.wzh.logistics;

import android.content.Context;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * 应用配置类 包含应用的各种全局参数变量
 * @author Administrator
 *
 */
public class AppConfig {

	/**
	 * 是否启用XMl缓存
	 */
	public static final boolean ENABLE_XML_CACHE=true;
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
	public static final String CACHE_FOLDER ="Wiwo";
	
	/**
	 * 是否使用本地json文件，用于演示。
	 */
	public static final boolean USE_LOCAL_JSON =true;
	/**
	 * 获取百度地图服务器数据用到的数据。
	 */
	public static final String	MAP_AK ="qKjfG1BtWPG4poMGvBsEc6ui";
	public static final int		MAP_TABLE_ID =61558;

	/**
	 * 启动app是加载设备信息。
	 * load the configuration when running
	 * @param context
	 */
	public static void loadConfigs(Context context) {
		final Resources resources = context.getResources();
		final DisplayMetrics dm = resources.getDisplayMetrics();
		SCREEN_DENSITY = dm.density;
		SCREEN_WIDTH = dm.widthPixels;
		SCREEN_HEIGHT = dm.heightPixels;
		
		if (BuildConfig.DEBUG) {
//			final int densityDpi = dm.densityDpi;
//			final float scaledDensity = dm.scaledDensity;
			Log.i("TbConfigs",
					" ---------------------------------------------\n" +
					"| widthPixels: "+SCREEN_WIDTH+", heightPixels: "+SCREEN_HEIGHT+
					" ---------------------------------------------");
		}
	}
	
	/**
	 * 获取设备屏幕高度
	 * @return
	 */
	public static int getScreenHeight(){
//		return SCREEN_HEIGHT;
		return Math.max(SCREEN_WIDTH,SCREEN_HEIGHT);
	}
	
	/**
	 * 获取设备屏幕宽度
	 * @return
	 */
	public static int getScreenWidth(){
//		return SCREEN_WIDTH;
		return Math.min(SCREEN_WIDTH,SCREEN_HEIGHT);
	}

}
