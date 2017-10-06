package com.route.test.hungrydemo.model.net;

import java.util.Map;

/**
 * Created by my301s on 2017/8/11.
 */

public interface IHttp {
    void get(String url, Map<String,String> map,CallBacks callBacks);


}
