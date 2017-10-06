package com.route.test.hungrydemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.route.test.hungrydemo.R;
import com.route.test.hungrydemo.model.db.User;

import java.util.List;

/**
 * Created by my301s on 2017/8/1.
 */

public class SearchAdapter extends android.widget.BaseAdapter {
    private Context context ;
    private List<User> list;

    public SearchAdapter(Context context, List<User> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null){
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.sousuo_item,null);
            holder.name= (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }
        User user = list.get(position);
        holder.name.setText(user.getName());
        return convertView;
    }
    static class ViewHolder {
        private TextView name;
    }
}
