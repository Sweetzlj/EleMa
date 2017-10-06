package com.route.test.hungrydemo.model.net;


import com.route.test.hungrydemo.model.OkHttpUtils;

/**
 * Created by apple on 2017/7/19.
 */

public class HttpFactroy {

    public static IHttp create(){
        return OkHttpUtils.getInstance();
    }
}
