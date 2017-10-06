package com.route.test.hungrydemo.controler;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.route.test.hungrydemo.R;
import com.route.test.hungrydemo.adapter.TabPagerAdapter;
import com.route.test.hungrydemo.base.BaseActivity;
import com.route.test.hungrydemo.fragment.homedetail.PingJiaFragment;
import com.route.test.hungrydemo.fragment.homedetail.ShangJiaFragment;
import com.route.test.hungrydemo.fragment.homedetail.ShangPinFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeDetailActivity extends BaseActivity {

    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.vp)
    ViewPager vp;
    private ArrayList<Fragment> list = new ArrayList<>();

    @Override
    protected void initData() {

        list.clear();

        list.add(new ShangPinFragment());
        list.add(new PingJiaFragment());
        list.add(new ShangJiaFragment());

        TabPagerAdapter pagerAdapter = new TabPagerAdapter(getSupportFragmentManager(),list);
        vp.setAdapter(pagerAdapter);

        tabs.setupWithViewPager(vp);
        tabs.setTabMode(TabLayout.MODE_FIXED);
    }

    @Override
    protected void initId() {

    }

    @Override
    protected int getLayout() {
        return R.layout.activity_home_detail;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
