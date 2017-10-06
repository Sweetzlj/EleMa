package com.route.test.hungrydemo.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.route.test.hungrydemo.R;
import com.route.test.hungrydemo.adapter.OrderAdapter;
import com.route.test.hungrydemo.base.BaseFragment;
import com.route.test.hungrydemo.config.Urls;
import com.route.test.hungrydemo.model.bean.DingDan;
import com.route.test.hungrydemo.model.bean.DingDanDetail;
import com.route.test.hungrydemo.model.biz.HomeModeImpl;
import com.route.test.hungrydemo.model.net.CallBacks;

import java.util.ArrayList;
import java.util.Collection;

/**
 * A simple {@link Fragment} subclass.
 */
public class DingDanFragment extends BaseFragment {


    private RecyclerView rv_order_list;
    private HomeModeImpl homeMode;
    private ArrayList<DingDanDetail.GoodsInfosBean> list =  new ArrayList<>();

    @Override
    protected void initView(View view) {
        rv_order_list = view.findViewById(R.id.rv_order_list);
        GridLayoutManager layoutManager  =  new GridLayoutManager(getContext(),1);
        rv_order_list.setLayoutManager(layoutManager);
    }

    @Override
    protected void updateTitleBar() {

    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_ding_dan;
    }

    @Override
    protected void initData() {
        homeMode=new HomeModeImpl();
        homeMode.getHomeList(Urls.ORDER, new CallBacks() {
            @Override
            public void Success(String resultData) {
               Log.e("DingDanFragment", resultData);
                Gson gson = new Gson();
                DingDan dingDan = gson.fromJson(resultData, DingDan.class);
                String data = dingDan.getData();

                list.addAll((Collection<? extends  DingDanDetail.GoodsInfosBean>) gson.fromJson(data, new TypeToken<ArrayList< DingDanDetail.GoodsInfosBean>>() {
                                                }.getType()));

                OrderAdapter orderAdapter = new OrderAdapter(getContext(),list);
                rv_order_list.setAdapter(orderAdapter);
            }

            @Override
            public void Fail(String Error) {

            }
        });
    }
}
