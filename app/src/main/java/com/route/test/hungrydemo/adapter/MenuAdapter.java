package com.route.test.hungrydemo.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.route.test.hungrydemo.R;
import com.route.test.hungrydemo.model.bean.DeatilBean;

import java.util.List;

/**
 * Created by my301s on 2017/8/13.
 */

public class MenuAdapter extends BaseAdapter {
    public static int selectItem = 0;

    private int getSelectItem() {
        return selectItem;
    }

    public static void setSelectItem(int selectItem) {
        MenuAdapter.selectItem = selectItem;
    }

    private Context context;
    private List<DeatilBean> deatilBeen;

    public MenuAdapter(Context context, List<DeatilBean> deatilBeen) {
        this.context = context;
        this.deatilBeen = deatilBeen;
    }

    @Override
    public int getCount() {
        return deatilBeen.size();
    }

    @Override
    public Object getItem(int i) {
        return deatilBeen.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_menu, null);
            holder.name_menu = view.findViewById(R.id.name_menu);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        DeatilBean deatilBean = deatilBeen.get(i);
        holder.name_menu.setText(deatilBean.getName());
        if (i == selectItem) {
            holder.name_menu.setBackgroundColor(Color.WHITE);
            holder.name_menu.setTextColor(Color.BLACK);
        } else {
            holder.name_menu.setBackgroundColor(context.getResources().getColor(R.color.gray3));
            holder.name_menu.setTextColor(Color.BLACK);
        }
        return view;
    }
    static class ViewHolder {
        private TextView name_menu;
    }

}
