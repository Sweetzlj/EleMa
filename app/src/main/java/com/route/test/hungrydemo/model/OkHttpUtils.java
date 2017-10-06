package com.route.test.hungrydemo.model;

import com.route.test.hungrydemo.MyApp;
import com.route.test.hungrydemo.model.net.CallBacks;
import com.route.test.hungrydemo.model.net.IHttp;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by my301s on 2017/8/11.
 */

public class OkHttpUtils implements IHttp{
    private static OkHttpUtils okHttpUtils;
    private final OkHttpClient client;

    private OkHttpUtils(){
        client = new OkHttpClient.Builder().build();
    }
    public static OkHttpUtils getInstance(){
        if(okHttpUtils==null){
            synchronized (OkHttpUtils.class){
                if(okHttpUtils==null){
                   okHttpUtils=new OkHttpUtils();
                }
            }
        }
        return okHttpUtils;
    }

    @Override
    public void get(String url, Map<String, String> map, final CallBacks callBacks) {
        StringBuffer stringBuffer = new StringBuffer();
        if(map!=null && map.size()>0){
            stringBuffer.append(url);
            stringBuffer.append("?");
            Set<String> keySet = map.keySet();
            for(String key:keySet){
                String value = map.get(key);
                stringBuffer.append(key).append("=").append(value).append("&");
            }
            stringBuffer.substring(0,stringBuffer.length()-1);
        }

        Request request = new Request.Builder().url(url).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                MyApp.mBaseActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callBacks.Fail(e.getMessage());
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String resultData = response.body().string();
                MyApp.mBaseActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        callBacks.Success(resultData);
                    }
                });
            }
        });
    }
}
