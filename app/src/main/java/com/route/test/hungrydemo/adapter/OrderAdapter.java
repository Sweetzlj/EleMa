package com.route.test.hungrydemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.route.test.hungrydemo.R;
import com.route.test.hungrydemo.model.bean.DingDanDetail;

import java.util.ArrayList;

/**
 * Created by my301s on 2017/8/16.
 */
public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<DingDanDetail.GoodsInfosBean> list;

    public OrderAdapter(Context context, ArrayList<DingDanDetail.GoodsInfosBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_item, null);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
       /* holder.tv_order_item_money.setText("￥"+list.get(position).getNewPrice()+"");
        holder.tv_order_item_foods.setText(list.get(position).getName()+"等三件商品");*/
        holder.tv_order_item_seller_name.setText("肯德基宅急送（文化路店）");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        private final TextView tv_order_item_foods;
        private final TextView tv_order_item_money;
        private final TextView tv_order_item_seller_name;

        public MyViewHolder(View itemView) {
            super(itemView);
         tv_order_item_seller_name = itemView.findViewById(R.id.tv_order_item_seller_name);
            tv_order_item_foods = itemView.findViewById(R.id.tv_order_item_foods);
            tv_order_item_money = itemView.findViewById(R.id.tv_order_item_money);

        }
    }
}
