package com.route.test.hungrydemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.route.test.hungrydemo.R;
import com.route.test.hungrydemo.model.db_shop.Shop;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by my301s on 2017/8/15.
 */

public class ShopAdapter extends RecyclerView.Adapter<ShopAdapter.MyViewHolder> {
    private Context context;
    private List<Shop> list;

    public ShopAdapter(Context context, List<Shop> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shop, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        //保留小数点后两位
        BigDecimal bd = new BigDecimal(list.get(position).getPrice() + "");
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        String name = bd.toString();
        holder.item_tv_name.setText(list.get(position).getName());
        holder.item_tv_price.setText(name);
        holder.item_tv_num.setText(list.get(position).getNumber() + "");
        Glide.with(context).load(list.get(position).getImg()).into(holder.item_iv);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private final ImageView item_iv;
        private final TextView item_tv_name;
        private final TextView item_tv_price;
        private final TextView item_tv_num;

        public MyViewHolder(View itemView) {
            super(itemView);
            item_iv = itemView.findViewById(R.id.item_iv);
            item_tv_name = itemView.findViewById(R.id.item_tv_name);
            item_tv_price = itemView.findViewById(R.id.item_tv_price);
            item_tv_num = itemView.findViewById(R.id.item_tv_num);
        }
    }
}
