package com.route.test.hungrydemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.TextView;

import com.route.test.hungrydemo.R;
import com.route.test.hungrydemo.model.bean.VitamioBean;

import java.util.ArrayList;

/**
 * Created by my301s on 2017/8/16.
 */

public class VitamioAdapter extends RecyclerView.Adapter<VitamioAdapter.MyViewHolder> {
    private Context context;
    private ArrayList<VitamioBean.DataBean.TrailersBean> list;
    private MediaController mMediaController;

    public VitamioAdapter(Context context, ArrayList<VitamioBean.DataBean.TrailersBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.vitamio_demo, null);

        MyViewHolder myViewHolder = new MyViewHolder(view);
        view.setTag(myViewHolder);
        return myViewHolder;
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.movie_name.setText(list.get(position).getMovieName());
      //  holder.surface_view.setVideoPath(list.get(position).getHightUrl());//设置播放地址
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

     //   private final VideoView surface_view;
        private final TextView movie_name;

        public MyViewHolder(View itemView) {
            super(itemView);
          //  surface_view = itemView.findViewById(R.id.view);
            movie_name = itemView.findViewById(R.id.movie_name);
         /*   mMediaController = new MediaController(context);//实例化控制器
            mMediaController.show(5000);//控制器显示5s后自动隐藏
            surface_view.setMediaController(mMediaController);//绑定控制器
            surface_view.setVideoQuality(MediaPlayer.VIDEOQUALITY_HIGH);//设置播放画质 高画质
            surface_view.requestFocus();//取得焦点
            surface_view.start();*/
        }


    }
}
