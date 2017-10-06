package com.route.test.hungrydemo.controler;

import android.os.Bundle;
import android.support.annotation.IdRes;
import android.view.KeyEvent;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.route.test.hungrydemo.R;
import com.route.test.hungrydemo.base.BaseActivity;
import com.route.test.hungrydemo.base.FragmentBuilder;
import com.route.test.hungrydemo.fragment.DingDanFragment;
import com.route.test.hungrydemo.fragment.FirstFragment;
import com.route.test.hungrydemo.fragment.MoreFragment;
import com.route.test.hungrydemo.fragment.MyFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity {


    @BindView(R.id.MainFrameLayout)
    FrameLayout MainFrameLayout;
    @BindView(R.id.main_ZongHe)
    RadioButton mainZongHe;
    @BindView(R.id.main_DongTan)
    RadioButton mainDongTan;
    @BindView(R.id.main_FaXian)
    RadioButton mainFaXian;
    @BindView(R.id.main_WoDe)
    RadioButton mainWoDe;
    @BindView(R.id.Main_RadioGroup)
    RadioGroup MainRadioGroup;

    @Override
    protected void initData() {

        MainRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.main_ZongHe:
                        FragmentBuilder.getFragmentBuilder().containerId(R.id.MainFrameLayout).satrt(FirstFragment.class).build();
                        break;
                    case R.id.main_DongTan:
                        FragmentBuilder.getFragmentBuilder().containerId(R.id.MainFrameLayout).satrt(DingDanFragment.class).build();
                        break;
                    case R.id.main_FaXian:
                        FragmentBuilder.getFragmentBuilder().containerId(R.id.MainFrameLayout).satrt(MyFragment.class).build();
                        break;
                    case R.id.main_WoDe:
                        FragmentBuilder.getFragmentBuilder().containerId(R.id.MainFrameLayout).satrt(MoreFragment.class).build();
                        break;
                }
            }
        });
    }


    @Override
    protected void initId() {
        FragmentBuilder.getFragmentBuilder().containerId(R.id.MainFrameLayout).satrt(FirstFragment.class).build();

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_home;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    private long firstTime = 0;
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                long secondTime = System.currentTimeMillis();
                if (secondTime - firstTime > 2000) {
                    Toast.makeText(HomeActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                    firstTime = secondTime;
                    return true;
                } else {
                    System.exit(0);
                }
                break;
        }
        return super.onKeyUp(keyCode, event);
    }

}
