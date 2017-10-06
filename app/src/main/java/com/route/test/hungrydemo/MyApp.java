package com.route.test.hungrydemo;

import com.mob.MobApplication;
import com.route.test.hungrydemo.base.BaseActivity;
import com.route.test.hungrydemo.base.BaseFragment;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.common.QueuedWork;
import com.zhy.autolayout.config.AutoLayoutConifg;

import org.xutils.x;

/**
 * Created by my301s on 2017/6/22.
 */

public class MyApp extends MobApplication {
    public static BaseActivity mBaseActivity;
    public static BaseFragment mBaseLastFragment;
    public static final String UPDATE_STATUS_ACTION = "com.umeng.message.example.action.UPDATE_STATUS";

    @Override
    public void onCreate() {
        super.onCreate();
        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
        x.Ext.init(this);
        Config.DEBUG = true;
        QueuedWork.isUseThreadPool = false;
        UMShareAPI.get(this);

        //全局异常捕获
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
        //屏幕适配
        AutoLayoutConifg.getInstance().useDeviceSize();
    }

    {
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setSinaWeibo("2828143782", "9056270e0000507b476d6c229f29c340", "http://sns.whalecloud.com");
    }
}
