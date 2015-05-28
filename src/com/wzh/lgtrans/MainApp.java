
package com.wzh.lgtrans;
import android.app.Application;
import android.content.Context;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.wzh.lgtrans.util.JsonUtil;

/**
 * 应用启动类
 * 该类中加载全局配置参数
 *
 */
public class MainApp extends Application{
	private static MainApp mInstance = null;

	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
		AppConfig.loadConfigs(this);
		initVolly(this);
		initImageLoader(this);
		JsonUtil.loadCityData(this);
		JsonUtil.loadCarData(this);
	}

	public static MainApp getInstance() {
		return mInstance;
	}
	
	public void initVolly(Context context){
		MyVolley.init(context);
	}
	/**
	 * 初始化图像加载类，设置缓存参�?<br>
	 * 如果�?启时有闪屏动画，则在此界面初始化Loader，如果没有则在TbApp的创建函数中初始�?<br>
	 * 在SplashActivity中异步调用�??
	 * @param context
	 */
	public void initImageLoader(Context context) {
		// This configuration tuning is custom. You can tune every option, you may tune some of them,
		// or you can create default configuration by the following method.
		
		// ImageLoaderConfiguration.createDefault(this);
		
		DisplayImageOptions defaultOptions = new DisplayImageOptions.Builder()
			.cacheInMemory(true)//启用内存缓存
			.cacheOnDisc(true)//启用硬盘缓存
			.considerExifParams(true)//是否考虑EXIF标识�?
			.build();
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
				.denyCacheImageMultipleSizesInMemory()//相同名字只存�?�?
				.discCacheFileNameGenerator(new Md5FileNameGenerator())//使用MD5命名方法
				.tasksProcessingOrder(QueueProcessingType.LIFO)//后进先出的处理顺�?
				.defaultDisplayImageOptions(defaultOptions)//使用默认显示选项
				.memoryCacheSizePercentage(30)//30% memory cache 使用30%内寸缓存
				.discCacheSize(20*1024*1024)//20M disk cache 使用20M硬盘缓存
				.discCacheDir(AppConfig.CACHE_FOLDER) // 指定缓存路径
				.specifyScreen(false) // 缓存图片时是否指定所显示的特定屏幕，不同尺寸屏幕是否可以共用缓存
				.build();

		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);
	}

}
