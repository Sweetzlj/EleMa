package com.route.test.hungrydemo.model.biz;

import com.route.test.hungrydemo.model.net.CallBacks;
import com.route.test.hungrydemo.model.net.HttpFactroy;

/**
 * Created by apple on 2017/7/19.
 */

public class HomeModeImpl implements IHomeModel {

    @Override
    public void getHomeList(String url, CallBacks callbacks) {
        HttpFactroy.create().get(url,null,callbacks);
    }
}
