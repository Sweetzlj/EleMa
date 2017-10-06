package com.route.test.hungrydemo.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.androidkun.PullToRefreshRecyclerView;
import com.androidkun.callback.PullToRefreshListener;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;
import com.jude.rollviewpager.hintview.ColorPointHintView;
import com.route.test.hungrydemo.MyApp;
import com.route.test.hungrydemo.R;
import com.route.test.hungrydemo.adapter.GridHomeAdapter;
import com.route.test.hungrydemo.adapter.HomeAdapter;
import com.route.test.hungrydemo.base.BaseFragment;
import com.route.test.hungrydemo.config.Urls;
import com.route.test.hungrydemo.controler.HomeDetailActivity;
import com.route.test.hungrydemo.controler.MapActivity;
import com.route.test.hungrydemo.controler.SearchActivity;
import com.route.test.hungrydemo.model.bean.Home;
import com.route.test.hungrydemo.model.bean.HomeBean;
import com.route.test.hungrydemo.model.biz.HomeModeImpl;
import com.route.test.hungrydemo.model.net.CallBacks;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FirstFragment extends BaseFragment implements View.OnClickListener, AMapLocationListener {

    private int mDistance = 0;
    private int maxDistance = 255;//当距离在[0,255]变化时，透明度在[0,255之间变化]
    private HomeModeImpl homeModel;
    private LinearLayout ll_title_container;
    private RollPagerView rollpager;
    private PullToRefreshRecyclerView first_recycler;
    private List<HomeBean.BodyBean.SellerBean> list2 = new ArrayList<>();
    private List<String> list3 = new ArrayList<>();
    private List<HomeBean.BodyBean> list4 = new ArrayList<>();


    private List<HomeBean.HeadBean.CategorieListBean> list1 = new ArrayList<>();

    private HomeAdapter adapter;
    private PullToRefreshRecyclerView home_recycler;
    private GridHomeAdapter gridHomeAdapter;
    private TextView home_tv_address;
    private EditText et_search;
    private AMapLocationClient aMapLocationClient;
    private AMapLocationClientOption mLocationOption;
    private StringBuffer buffer;


    @Override
    protected void initView(View view) {

        aMapLocationClient = new AMapLocationClient(getContext());
        aMapLocationClient.setLocationListener(this);
        mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setNeedAddress(true);
        mLocationOption.setOnceLocation(false);
        mLocationOption.setMockEnable(false);
        mLocationOption.setInterval(2000);
        aMapLocationClient.setLocationOption(mLocationOption);

        aMapLocationClient.startLocation();

        homeModel = new HomeModeImpl();
        et_search = view.findViewById(R.id.et_search);
        home_tv_address = view.findViewById(R.id.home_tv_address);
        ll_title_container = view.findViewById(R.id.ll_title_container);
        first_recycler = view.findViewById(R.id.first_recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        first_recycler.addItemDecoration(new DividerItemDecoration(MyApp.mBaseActivity, DividerItemDecoration.VERTICAL));
        first_recycler.setLayoutManager(layoutManager);
        //是否开启下拉刷新功能
        first_recycler.setPullRefreshEnabled(true);
        //设置是否显示上次刷新的时间
        first_recycler.displayLastRefreshTime(true);
        first_recycler.setPullToRefreshListener(new PullToRefreshListener() {
            @Override
            public void onRefresh() {
                first_recycler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        first_recycler.setRefreshComplete();
                    }
                }, 2000);
            }

            @Override
            public void onLoadMore() {

            }
        });

        adapter = new HomeAdapter(getActivity(), list2, list3, list4);
        first_recycler.setAdapter(adapter);

        home_tv_address.setOnClickListener(this);
        initSearch();
    }

    public void initSearch() {
        et_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void updateTitleBar() {

    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_first;
    }

    @Override
    protected void initData() {
        initBanner();
        homeModel.getHomeList(Urls.ZHUYE, new CallBacks() {
            @Override
            public void Success(String resultData) {
                Log.e("TAG", resultData);
                Gson gson = new Gson();
                Home homeBaseBean = gson.fromJson(resultData, Home.class);

                String data = homeBaseBean.getData();
                HomeBean homeBean = gson.fromJson(data, HomeBean.class);
                list1.addAll(homeBean.getHead().getCategorieList());
                for (int i = 0; i < homeBean.getBody().size(); i++) {
                    list2.add(homeBean.getBody().get(i).getSeller());
                    list3.addAll(homeBean.getBody().get(i).getRecommendInfos());
                }
                list4.addAll(homeBean.getBody());
                adapter.notifyDataSetChanged();
                gridHomeAdapter.notifyDataSetChanged();

                initListener();
                adapter.setOnItemClickLinear(new HomeAdapter.OnItemClickLinear() {
                    @Override
                    public void onItemvlick(int position) {
                        Intent intent = new Intent(getContext(), HomeDetailActivity.class);
                        startActivity(intent);
                    }
                });
            }

            @Override
            public void Fail(String Error) {

            }
        });
    }

    private void initBanner() {
        View inflate = LayoutInflater.from(getContext()).inflate(R.layout.item_banner, null);
        rollpager = inflate.findViewById(R.id.banner);
        home_recycler = inflate.findViewById(R.id.home_recycler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 4);
        home_recycler.setLayoutManager(gridLayoutManager);
        home_recycler.setPullRefreshEnabled(false);
        home_recycler.setLoadingMoreEnabled(false);
        gridHomeAdapter = new GridHomeAdapter(getContext(), list1);
        home_recycler.setAdapter(gridHomeAdapter);

        //设置播放时间间隔
        rollpager.setPlayDelay(1000);
        //设置透明度
        rollpager.setAnimationDurtion(500);
        //设置适配器
        rollpager.setAdapter(new TestNormalAdapter());
        rollpager.setHintView(new ColorPointHintView(getContext(), Color.YELLOW, Color.WHITE));
        first_recycler.addHeaderView(inflate);

    }

    public void initListener() {
        first_recycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mDistance += dy;
                float percent = mDistance * 1f / maxDistance;//百分比
                int alpha = (int) (percent * 255);
                int argb = Color.argb(alpha, 57, 174, 255);
                setSystemBarAlpha(alpha);
            }
        });
    }

    /**
     * 设置标题栏背景透明度
     *
     * @param alpha 透明度
     */
    private void setSystemBarAlpha(int alpha) {
        if (alpha >= 255) {
            alpha = 255;
        } else if (alpha <= 125) {
            alpha = 125;
        }
        //标题栏渐变。a:alpha透明度 r:红 g：绿 b蓝
        ll_title_container.setBackgroundColor(Color.rgb(57, 174, 255));//没有透明效果
        // ll_title_container.setBackgroundColor(Color.argb(alpha, 57, 174, 255));//透明效果是由参数1决定的，透明范围[0,255]
        ll_title_container.getBackground().setAlpha(alpha);

    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), MapActivity.class);
        startActivity(intent);
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                aMapLocation.getLatitude();//获取纬度
                aMapLocation.getLongitude();//获取经度
                aMapLocation.getAccuracy();//获取精度信息
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date date = new Date(aMapLocation.getTime());
                df.format(date);//定位时间
                aMapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                aMapLocation.getCountry();//国家信息
                aMapLocation.getProvince();//省信息
                aMapLocation.getCity();//城市信息
                aMapLocation.getDistrict();//城区信息
                aMapLocation.getStreet();//街道信息
                aMapLocation.getStreetNum();//街道门牌号信息
                aMapLocation.getCityCode();//城市编码
                aMapLocation.getAdCode();//地区编码
                buffer = new StringBuffer();
                buffer.append( aMapLocation.getStreet() + ""
                                + aMapLocation.getStreetNum());
                home_tv_address.setText(aMapLocation.getDescription()+buffer.toString());
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
            }
        }
    }

    private class TestNormalAdapter extends StaticPagerAdapter {
        private String[] imgs = {
                "http://123.206.14.104:8080/TakeoutService/imgs/promotion/1.jpg",
                "http://123.206.14.104:8080/TakeoutService/imgs/promotion/2.jpg",
                "http://123.206.14.104:8080/TakeoutService/imgs/promotion/3.jpg"};

        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            Glide.with(container.getContext()).load(imgs[position]).into(view);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            return view;
        }

        @Override
        public int getCount() {
            return imgs.length;
        }
    }
}
