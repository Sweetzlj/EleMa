package com.route.test.hungrydemo.base;


import android.app.Application;

import org.xutils.x;

/**
 * 需要初始化的在这里application初始化
 */
public class MyApp extends Application {
	public static BaseActivity mBaseActivity;
	public static BaseFragment mBaseLastFragment;
	@Override
	public void onCreate() {
		x.Ext.init(this);
		super.onCreate();
	}
}
