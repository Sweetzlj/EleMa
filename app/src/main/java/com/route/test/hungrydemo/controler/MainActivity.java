package com.route.test.hungrydemo.controler;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;

import com.route.test.hungrydemo.R;
import com.route.test.hungrydemo.base.BaseActivity;

import util.UpdateAppUtils;

public class MainActivity extends BaseActivity {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    finish();
                    break;
            }
        }
    };

    @Override
    protected void initData() {
        //打印屏幕分辨率
        Display display = getWindowManager().getDefaultDisplay();
        Log.e("width-display :", display.getWidth() + "");
        Log.e("heigth-display :", display.getHeight() + "");
//版本更新
        UpdateAppUtils.from(this)//Activity名
                .serverVersionCode(2)  //服务器versionCode
                .serverVersionName("2.0") //服务器versionName
                .apkPath("http://123.206.14.104:8080/FileUploadDemo/files/wxl.apk") //最新apk下载地址
                .update();
        handler.sendEmptyMessageDelayed(1, 2000);
    }

    @Override
    protected void initId() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }
}
