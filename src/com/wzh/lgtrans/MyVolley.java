/**
 * Copyright 2013 Ognyan Bankov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wzh.lgtrans;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


/**
 * Http的帮助类，封装了网络通信的方法。<br>
 * 封装了访问网络的异步线程机制，并且可以处理多次同时访问请求的调度。
 * Helper class that is used to provide references to initialized RequestQueue(s) and ImageLoader(s)
 * 
 * @author Ognyan Bankov
 * 
 */
public class MyVolley {
	
    private static RequestQueue mRequestQueue;
    
    private MyVolley() {}

    /**
     * 初始化网络通信类
     * @param context
     */
    public static void init(Context context) {
    	//初始化网络请求队列，用于管理所有的网络请求，整个app只使用一个队列。
        mRequestQueue = Volley.newRequestQueue(context);
    }

    /**
     * 获取网络请求队列实例
     * @return
     */
    public static RequestQueue getRequestQueue() {
        if (mRequestQueue != null) {
            return mRequestQueue;
        } else {
            throw new IllegalStateException("RequestQueue not initialized");
        }
    }
}
