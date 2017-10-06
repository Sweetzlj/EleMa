package com.route.test.hungrydemo.controler;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.route.test.hungrydemo.R;
import com.route.test.hungrydemo.adapter.SearchAdapter;
import com.route.test.hungrydemo.base.BaseActivity;
import com.route.test.hungrydemo.model.db.DaoMaster;
import com.route.test.hungrydemo.model.db.DaoSession;
import com.route.test.hungrydemo.model.db.User;
import com.route.test.hungrydemo.model.db.UserDao;
import com.route.test.hungrydemo.view.MyListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends BaseActivity {

    @BindView(R.id.et_search2)
    EditText etSearch;
    @BindView(R.id.tv_quxiao)
    TextView tvQuxiao;
    @BindView(R.id.ll_title_search)
    LinearLayout llTitleSearch;
    @BindView(R.id.ll_title_container)
    LinearLayout llTitleContainer;
    @BindView(R.id.tv_tip)
    TextView tvTip;
    @BindView(R.id.lv_search)
    MyListView lvSearch;
    @BindView(R.id.tv_clear)
    TextView tvClear;
    @BindView(R.id.scroll)
    ScrollView scroll;
    private UserDao userDao;
    private List<User> list;
    List<String> tiltes = new ArrayList<>();
    private Dialog dialog;
    private String tempName;

    @Override
    protected void initData() {
        queryData(tempName);
        // 搜索框的键盘搜索键点击回调
        etSearch.setOnKeyListener(new View.OnKeyListener() {// 输入完后按键盘上的搜索键

            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {// 修改回车键功能
                    // 先隐藏键盘
                    ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                            getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                    // 按完搜索键后将当前查询的关键字保存起来,如果该关键字已经存在就不执行保存
                    boolean hasData = hasData(etSearch.getText().toString().trim());
                    if (!hasData) {
                        insertData(etSearch.getText().toString().trim());
                        queryData("");
                    }
                    // TODO 根据输入的内容模糊查询商品，并跳转到另一个界面，由你自己去实现
                    Toast.makeText(SearchActivity.this, "clicked!", Toast.LENGTH_SHORT).show();

                }
                return false;
            }
        });

        // 搜索框的文本变化实时监听
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().trim().length() == 0) {
                    tvTip.setText("搜索历史");
                } else {
                    tvTip.setText("搜索结果");
                }
                tempName = etSearch.getText().toString();
                // 根据tempName去模糊查询数据库中有没有数据
                queryData(tempName);

            }
        });
    }

    @Override
    protected void initId() {

    }

    private boolean hasData(String trim) {
        list = userDao.queryBuilder().build().list();
        return false;
    }

    private void queryData(String tempName) {
        list = userDao.queryBuilder().build().list();
        if (list.size() > 0) {
            scroll.setVisibility(View.VISIBLE);
        } else {
            scroll.setVisibility(View.GONE);
        }
        // 创建adapter适配器对象
        SearchAdapter adapter = new SearchAdapter(SearchActivity.this, list);
        // 设置适配器
        lvSearch.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        lvSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                etSearch.setText(list.get(position).getName());
            }
        });
    }

    private void insertData(String tempName) {
        User user = new User();
        user.setName(tempName);
        userDao.insert(user);
    }

    private void deleteData() {
        userDao.deleteAll();
    }

    @Override
    protected int getLayout() {
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(SearchActivity.this, "user.db", null);
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getReadableDb());
        DaoSession daoSession = daoMaster.newSession();
        userDao = daoSession.getUserDao();
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        return R.layout.activity_search;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    @OnClick({R.id.tv_quxiao, R.id.tv_clear})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_quxiao:
                finish();
                break;
            case R.id.tv_clear:
                dialog = new AlertDialog.Builder(SearchActivity.this)
                        .setCancelable(false)
                        .setTitle("确定清空所有搜索历史吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                deleteData();
                                queryData("");
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create();
                dialog.show();
                break;
        }
    }
}
