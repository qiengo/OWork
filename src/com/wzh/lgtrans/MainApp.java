
package com.wzh.lgtrans;
import android.app.Application;
import android.content.Context;

import com.wzh.lgtrans.util.JsonUtil;

/**
 * 应用启动类
 * 该类中加载全局配置参数
 * @author 王植桦 ewangzhihua@yeah.net
 * @version 创建时间：2014年3月14日
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
//		initImageLoader(this);
		JsonUtil.loadCityData(this);
		JsonUtil.loadCarData(this);
	}

	public static MainApp getInstance() {
		return mInstance;
	}
	
	public void initVolly(Context context){
		MyVolley.init(context);
	}
	

}
