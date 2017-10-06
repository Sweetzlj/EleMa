package com.route.test.hungrydemo.controler;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.route.test.hungrydemo.R;
import com.route.test.hungrydemo.adapter.ShopAdapter;
import com.route.test.hungrydemo.base.BaseActivity;
import com.route.test.hungrydemo.model.db.DaoMaster;
import com.route.test.hungrydemo.model.db.DaoSession;
import com.route.test.hungrydemo.model.db_shop.Shop;
import com.route.test.hungrydemo.model.db_shop.ShopDao;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.mob.MobSDK.getContext;

public class ShopCarActivity extends BaseActivity {

    @BindView(R.id.shop_back)
    ImageView shopBack;
    @BindView(R.id.tv_total)
    TextView tvTotal;
    @BindView(R.id.tv_money)
    TextView tvMoney;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.bottom)
    RelativeLayout bottom;
    private ShopDao shopDao;
    private RecyclerView carRecycler;
    private int number;
    private double price;
    private String format;
    private double v=0;
    private List<Shop> list;

    @Override
    protected void initData() {

        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getContext(), "shop.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getReadableDb());
        DaoSession daoSession = daoMaster.newSession();
        shopDao = daoSession.getShopDao();

        list = shopDao.queryBuilder().build().list();
        ArrayList<Shop> list2=new ArrayList<>();
        for (int i = 0; i <list.size() ; i++) {
            if (list.get(i).getNumber()>0){
                list2.add(new Shop(list.get(i).getId(),list.get(i).getName(),list.get(i).getImg(),list.get(i).getNumber(),list.get(i).getPrice()));
           }
        }
        ShopAdapter shopAdapter = new ShopAdapter(this, list2);
        carRecycler.setAdapter(shopAdapter);
        for (int i = 0; i < list.size(); i++) {
            number = list.get(i).getNumber();
            price = list.get(i).getPrice();
            v += number * price;
            DecimalFormat df = new DecimalFormat("######0.00");
            format = df.format(v);
        }
        tvMoney.setText(format);
    }

    @Override
    protected void initId() {
        carRecycler = findViewById(R.id.cart_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        carRecycler.setLayoutManager(layoutManager);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_shop_car;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    public void onViewClicked() {
        Intent intent = new Intent(ShopCarActivity.this,ZhiFuActivity.class);
        intent.putExtra("money",tvMoney.getText().toString().trim());
        startActivity(intent);
        finish();
    }
}
