package com.route.test.hungrydemo.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.route.test.hungrydemo.R;
import com.route.test.hungrydemo.model.bean.Array;

import java.math.BigDecimal;
import java.util.ArrayList;

import static com.route.test.hungrydemo.R.id.ib_remove;

/**
 * Created by my301s on 2017/8/13.
 */

public class RightAdapter extends BaseAdapter {


    private IDialogControl dialogControl;
    private ShortClick shortClick;

    private Context context;
    private ArrayList<Array> listBeen;
    private String num;

    public void setShortClick(ShortClick shortClick) {
        this.shortClick = shortClick;
    }

    public RightAdapter(Context context, ArrayList<Array> listBeen) {
        this.context = context;
        this.listBeen = listBeen;
    }

    public void setDialogControl(IDialogControl dialogControl) {
        this.dialogControl = dialogControl;
    }

    @Override
    public int getCount() {
        return listBeen.size();
    }

    @Override
    public Object getItem(int i) {
        return listBeen.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_goods, null);
            holder.tv_title_right = view.findViewById(R.id.tv_title_right);
            holder.tv_newprice = view.findViewById(R.id.tv_newprice);
            holder.tv_oldprice = view.findViewById(R.id.tv_oldprice);
            holder.tv_yueshaoshounum = view.findViewById(R.id.tv_yueshaoshounum);
            holder.tv_name = view.findViewById(R.id.tv_name11);
            holder.tv_zucheng = view.findViewById(R.id.tv_zucheng);
            holder.iv_icon = view.findViewById(R.id.iv_icon);
            holder.ib_add = view.findViewById(R.id.ib_add);
            holder.textcount = view.findViewById(R.id.textcount);
            holder.ib_remove = view.findViewById(ib_remove);
            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }
        final Array listBea = listBeen.get(i);
        //保留小数点后两位
        BigDecimal bd = new BigDecimal(listBea.getNewPrice() + "");
        bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
        String name = bd.toString();
        //textview加中划线
        holder.tv_oldprice.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG | Paint.ANTI_ALIAS_FLAG);  // 设置中划线并加清晰
        holder.tv_title_right.setText(listBea.getAname());
        holder.tv_name.setText(listBea.getName());
        holder.tv_zucheng.setText(listBea.getForm());
        holder.tv_yueshaoshounum.setText(listBea.getMonthSaleNum() + "");
        holder.tv_newprice.setText(name);
        holder.tv_oldprice.setText(listBea.getOldPrice() + "");
        holder.textcount.setText(listBea.getNum() + "");
        // 按钮的点击事件
        num = String.valueOf(listBea.getNum());
        if(num.equals("0")){
            holder.ib_remove.setVisibility(View.GONE);
            holder.textcount.setVisibility(View.GONE);
        }else{
            holder.ib_remove.setVisibility(View.VISIBLE);
            holder.textcount.setVisibility(View.VISIBLE);
        }
        holder.ib_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogControl.getPosition(view, i);

            }
        });

        holder.ib_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                shortClick.click(view, i);

            }
        });
        Glide.with(context).load(listBea.getIcon()).into(holder.iv_icon);
        if (i == 0) {
            holder.tv_title_right.setVisibility(View.GONE);
        } else if (!TextUtils.equals(listBea.getAname(), listBeen.get(i - 1).getAname())) {
            holder.tv_title_right.setVisibility(View.VISIBLE);
        } else {
            holder.tv_title_right.setVisibility(View.GONE);
        }
        return view;
    }


    static class ViewHolder {
        private TextView tv_title_right;
        private ImageView iv_icon;
        private TextView tv_name;
        private TextView tv_zucheng;
        private TextView tv_yueshaoshounum;
        private TextView tv_newprice;
        private TextView tv_oldprice;
        private ImageButton ib_add;
        private TextView textcount;
        private ImageButton ib_remove;
    }

    // 内部接口
    public interface IDialogControl {
        public void getPosition(View view, int position);
    }
    public interface ShortClick {
        public void click(View v, int position);
    }

}
