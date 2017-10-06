package com.route.test.hungrydemo.fragment.homedetail;


import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PointF;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.route.test.hungrydemo.R;
import com.route.test.hungrydemo.adapter.MenuAdapter;
import com.route.test.hungrydemo.adapter.RightAdapter;
import com.route.test.hungrydemo.base.BaseFragment;
import com.route.test.hungrydemo.config.Urls;
import com.route.test.hungrydemo.controler.ShopCarActivity;
import com.route.test.hungrydemo.model.bean.Array;
import com.route.test.hungrydemo.model.bean.DeatilBean;
import com.route.test.hungrydemo.model.bean.HomeDetailBean;
import com.route.test.hungrydemo.model.biz.HomeModeImpl;
import com.route.test.hungrydemo.model.biz.IHomeModel;
import com.route.test.hungrydemo.model.db.DaoMaster;
import com.route.test.hungrydemo.model.db.DaoSession;
import com.route.test.hungrydemo.model.db_shop.Shop;
import com.route.test.hungrydemo.model.db_shop.ShopDao;
import com.route.test.hungrydemo.model.net.CallBacks;
import com.route.test.hungrydemo.view.BezierTypeEvaluator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ShangPinFragment extends BaseFragment {

    int i = 0;
    private ListView lv;
    private ListView lv_ritht;
    private IHomeModel iHomeModel;
    private List<DeatilBean> list = new ArrayList<>();
    private List<DeatilBean.ListBean> list_righht = new ArrayList<>();
    private ArrayList<Integer> showTitle;
    private int currentItem;
    private ImageView mImageViewShopCat;
    private RelativeLayout main_layout;
    private TextView count;
    private RightAdapter rightAdapter;
    private TextView tv_titlemenu;
    private ArrayList<Array> arrayList;
    private TextView textcount;
    private Array bean;
    private ShopDao shopDao;
    private String s;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String data;

    @Override
    protected void initView(View view) {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(getContext(), "shop.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getReadableDb());
        DaoSession daoSession = daoMaster.newSession();
        shopDao = daoSession.getShopDao();

        iHomeModel = new HomeModeImpl();
        textcount = view.findViewById(R.id.textcount1);
        lv = view.findViewById(R.id.lv);
        lv_ritht = view.findViewById(R.id.lv_ritht);
        mImageViewShopCat = view.findViewById(R.id.fab);
        main_layout = view.findViewById(R.id.main_layout);
        tv_titlemenu = view.findViewById(R.id.tv_titlemenu);

        sharedPreferences = getActivity().getSharedPreferences("da", Context.MODE_PRIVATE);
        data = sharedPreferences.getString("data", "");
        textcount.setText(data);
        if(data.equals("")){
            textcount.setVisibility(View.GONE);
        }else {
            textcount.setVisibility(View.VISIBLE);
        }
    }
    @Override
    protected void updateTitleBar() {

    }

    @Override
    protected void initData() {

        iHomeModel.getHomeList(Urls.SELLER, new CallBacks() {
            @Override
            public void Success(String resultData) {
                Gson gson = new Gson();
                HomeDetailBean homeDetailBean = gson.fromJson(resultData, HomeDetailBean.class);
                final String data = homeDetailBean.getData();
                list.addAll((Collection<? extends DeatilBean>) gson.fromJson(data, new TypeToken<ArrayList<DeatilBean>>() {
                }.getType()));
                final MenuAdapter menuAdapter = new MenuAdapter(getContext(), list);
                lv.setAdapter(menuAdapter);
                arrayList = new ArrayList();
                for (int a = 0; a < list.size(); a++) {
                    for (int i = 0; i < list.get(a).getList().size(); i++) {
                        arrayList.add(new Array(list.get(a).getName(),
                                list.get(a).getList().get(i).getForm(),
                                list.get(a).getList().get(i).getIcon(),
                                list.get(a).getList().get(i).getId(),
                                list.get(a).getList().get(i).getMonthSaleNum(),
                                list.get(a).getList().get(i).getName(),
                                list.get(a).getList().get(i).getNewPrice(),
                                list.get(a).getList().get(i).getOldPrice(),
                                i, 0));
                    }
                }
                List<Shop> gous = shopDao.loadAll();
               // if (gous.size()>0) {
                    for (int i = 0; i < arrayList.size(); i++) {
                          Shop shop=new Shop(null,arrayList.get(i).getName(),arrayList.get(i).getIcon(),arrayList.get(i).getNum(),arrayList.get(i).getNewPrice());
                        shopDao.insert(shop);
                    }
               // }
                rightAdapter = new RightAdapter(getContext(), arrayList);
                lv_ritht.setAdapter(rightAdapter);
                BeiSaiEr();

                List<Shop> gous1 = shopDao.loadAll();
                if (gous1.size()!=0) {
                    for (int j = 0; j < gous1.size(); j++) {
                        for (int a = 0; a < arrayList.size(); a++) {
                            if (gous1.get(j).getId() - 1 == a) {
                                Array array = arrayList.get(a);
                                array.setNum(gous1.get(j).getNumber());
                            }
                        }
                    }
                }

                //ListView两级联动
                tv_titlemenu.setText(ShangPinFragment.this.list.get(i).getName());
                showTitle = new ArrayList<Integer>();
                for (int i = 0; i < arrayList.size(); i++) {
                    if (i == 0) {
                        showTitle.add(i);
                    } else if (!arrayList.get(i).getAname().equals(arrayList.get(i - 1).getAname())) {
                        showTitle.add(i);
                    }
                }

                lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        menuAdapter.setSelectItem(i);
                        menuAdapter.notifyDataSetInvalidated();
                        lv_ritht.setSelection(showTitle.get(i));
                        tv_titlemenu.setText(arrayList.get(showTitle.get(i)).getAname());
                    }
                });
                lv_ritht.setOnScrollListener(new AbsListView.OnScrollListener() {
                    private int scrollState;

                    @Override
                    public void onScrollStateChanged(AbsListView absListView, int scrollState) {
                        this.scrollState = scrollState;
                    }

                    @Override
                    public void onScroll(AbsListView absListView, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                        if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                            return;
                        }
                        int current = showTitle.indexOf(firstVisibleItem);
                        System.out.println(current + "dd" + firstVisibleItem);
                        if (currentItem != current && current >= 0) {
                            currentItem = current;
                            tv_titlemenu.setText(arrayList.get(showTitle.get(currentItem)).getAname());
                            menuAdapter.setSelectItem(currentItem);
                            menuAdapter.notifyDataSetInvalidated();
                        }
                    }
                });
            }

            @Override
            public void Fail(String Error) {

            }
        });

        mImageViewShopCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getContext(), ShopCarActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_shang_pin;
    }

    public void BeiSaiEr() {
        //减号按钮
        rightAdapter.setShortClick(new RightAdapter.ShortClick() {

            private Array bean2;

            @Override
            public void click(View v, int position) {
                for (int i = 0; i < arrayList.size(); i++) {
                    if (i == position) {
                        bean2 = arrayList.get(i);
                        int k = bean2.getNum() - 1;
                        bean2.setNum(k);
                        Shop gou=new Shop((long)position+1,bean.getName(),bean.getIcon(),bean.getNum(),bean.getNewPrice());
                        shopDao.update(gou);
                    }
                }
                i--;
                textcount.setText(i + "");
                s = String.valueOf(i);
                if ("0".equals(s)) {
                    textcount.setVisibility(View.GONE);
                }else{
                    textcount.setVisibility(View.VISIBLE);
                }
                rightAdapter.notifyDataSetChanged();
            }
        });
        //加号按钮
        rightAdapter.setDialogControl(new RightAdapter.IDialogControl() {



            @Override
            public void getPosition(final View view, int position) {

                for (int i = 0; i < arrayList.size(); i++) {
                    if (i == position) {
                        //点击的对应的item的bean数据源的num数据加1
                        //找到点击的item对应的bean对象
                        bean = arrayList.get(i);
                        //让bean对象对应的数据加1
                        int k = bean.getNum() + 1;
                        //把加1后的数据赋值给我们点击的那个Bean对象
                        bean.setNum(k);
                        Shop gou=new Shop((long)position+1,bean.getName(),bean.getIcon(),bean.getNum(),bean.getNewPrice());
                        shopDao.update(gou);
                    }
                }

                rightAdapter.notifyDataSetChanged();
                //贝塞尔起始数据点
                int[] startPosition = new int[2];
                //贝塞尔结束数据点
                int[] endPosition = new int[2];
                //控制点
                int[] recyclerPosition = new int[2];

                view.getLocationInWindow(startPosition);
                mImageViewShopCat.getLocationInWindow(endPosition);
                lv_ritht.getLocationInWindow(recyclerPosition);

                final PointF startF = new PointF();
                final PointF endF = new PointF();
                PointF controllF = new PointF();

                startF.x = startPosition[0];
                startF.y = startPosition[1] - recyclerPosition[1];
                endF.x = endPosition[0];
                endF.y = endPosition[1] - recyclerPosition[1];
                controllF.x = endF.x;
                controllF.y = startF.y;

                final ImageView imageView = new ImageView(getContext());
                main_layout.addView(imageView);
                imageView.setImageResource(R.mipmap.food_button_add);
                imageView.setVisibility(View.VISIBLE);
                imageView.setX(startF.x);
                imageView.setY(startF.y);

                ValueAnimator valueAnimator = ValueAnimator.ofObject(new BezierTypeEvaluator(controllF), startF, endF);
                valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                    @Override
                    public void onAnimationUpdate(ValueAnimator animation) {
                        PointF pointF = (PointF) animation.getAnimatedValue();
                        imageView.setX(pointF.x);
                        imageView.setY(pointF.y);
                        Log.i("wangjtiao", "viewF:" + view.getX() + "," + view.getY());
                    }
                });

                ObjectAnimator objectAnimatorX = new ObjectAnimator().ofFloat(mImageViewShopCat, "scaleX", 0.6f, 1.0f);
                ObjectAnimator objectAnimatorY = new ObjectAnimator().ofFloat(mImageViewShopCat, "scaleY", 0.6f, 1.0f);
                objectAnimatorX.setInterpolator(new AccelerateInterpolator());
                objectAnimatorY.setInterpolator(new AccelerateInterpolator());
                AnimatorSet set = new AnimatorSet();
                set.play(objectAnimatorX).with(objectAnimatorY).after(valueAnimator);
                set.setDuration(800);
                set.start();

                valueAnimator.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animator) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animator) {
                        // 购物车的数量加1
                        i++;

                        textcount.setText(String.valueOf(i));
                        textcount.setVisibility(View.VISIBLE);

                        // 把移动的图片imageview从父布局里移除
                        main_layout.removeView(imageView);

                    }

                    @Override
                    public void onAnimationCancel(Animator animator) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animator) {
                        count.setText("");
                    }
                });
            }
        });
    }

    @Override
    public void onStop() {
        super.onStop();
        sharedPreferences = getActivity().getSharedPreferences("da", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString("data",textcount.getText().toString() );
        editor.commit();
    }
}
