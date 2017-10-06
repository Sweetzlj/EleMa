package com.route.test.hungrydemo.controler;

import android.content.Intent;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.route.test.hungrydemo.R;
import com.route.test.hungrydemo.base.BaseActivity;
import com.route.test.hungrydemo.model.biz.HomeModeImpl;
import com.route.test.hungrydemo.model.db.DaoMaster;
import com.route.test.hungrydemo.model.db.DaoSession;
import com.route.test.hungrydemo.model.db_shop.Shop;
import com.route.test.hungrydemo.model.db_shop.ShopDao;
import com.route.test.hungrydemo.model.net.CallBacks;

import java.util.ArrayList;
import java.util.List;

public class ZhiFuActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_count_price;
    private ListView lv_zhifu;
    private ShopDao shopDao;
    private List<Shop> list;
    private TextView tv_submit;
    private String data;

    @Override
    protected void initData() {

        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(this, "shop.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getReadableDb());
        DaoSession daoSession = daoMaster.newSession();
        shopDao = daoSession.getShopDao();

        list = shopDao.queryBuilder().build().list();
        ArrayList<Shop> list2=new ArrayList<>();
        for (int i = 0; i <list.size() ; i++) {
            if (list.get(i).getNumber()>0){
                list2.add(new Shop(list.get(i).getId(),list.get(i).getName(), list.get(i).getImg(),list.get(i).getNumber(),list.get(i).getPrice()));
            }
        }

        HomeModeImpl homeMode = new HomeModeImpl();
        homeMode.getHomeList("http://123.206.14.104:8080/TakeoutService/order", new CallBacks() {
            @Override
            public void Success(String resultData) {
                data=resultData;
            }

            @Override
            public void Fail(String Error) {
            }
        });
    }

    @Override
    protected void initId() {
        Intent intent = getIntent();
        String money = intent.getStringExtra("money");
        tv_count_price = findViewById(R.id.tv_count_price);
        lv_zhifu = findViewById(R.id.lv_zhifu);
        tv_submit = findViewById(R.id.tv_submit);
        tv_count_price.setText("待支付￥"+money);
        tv_submit.setOnClickListener(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_zhi_fu;
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(ZhiFuActivity.this, data, Toast.LENGTH_SHORT).show();
    }
}
