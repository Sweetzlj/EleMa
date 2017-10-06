package com.route.test.hungrydemo.fragment;


import android.content.Context;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.route.test.hungrydemo.R;
import com.route.test.hungrydemo.base.BaseFragment;
import com.route.test.hungrydemo.config.Urls;
import com.route.test.hungrydemo.model.bean.VideoBean;
import com.route.test.hungrydemo.model.biz.HomeModeImpl;
import com.route.test.hungrydemo.model.net.CallBacks;
import com.route.test.hungrydemo.widget.MediaHelp;
import com.route.test.hungrydemo.widget.VideoSuperPlayer;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MoreFragment extends BaseFragment {
    private List<VideoBean.DataBean.TrailersBean> mList;
    private ListView mListView;
    private boolean isPlaying;
    private int indexPostion = -1;
    private MAdapter mAdapter;
    private HomeModeImpl homeMode;
    private ArrayList<String> mListImage;
    private ArrayList<String> mListTitle;

    @Override
    public void onDestroy() {
        MediaHelp.release();
        super.onDestroy();
    }

    @Override
    public void onResume() {
        MediaHelp.resume();
        super.onResume();
    }

    @Override
    public void onPause() {
        MediaHelp.pause();
        super.onPause();
    }

    @Override
    protected void initView(View view) {
        mListView = (ListView) view.findViewById(R.id.list);
        mList = new ArrayList();
    }

    @Override
    protected void updateTitleBar() {

    }

    @Override
    protected int layoutId() {
        return R.layout.fragment_more;
    }

    @Override
    protected void initData() {
        homeMode = new HomeModeImpl();
        homeMode.getHomeList(Urls.VATIMIO, new CallBacks() {
            @Override
            public void Success(String resultData) {
                Gson gson= new Gson();
                VideoBean videoBean = gson.fromJson(resultData, VideoBean.class);

                mList.addAll(videoBean.getData().getTrailers());
                mAdapter = new MAdapter(getActivity());
                mListView.setAdapter(mAdapter);
                initBanner();
                mListView.setOnScrollListener(new AbsListView.OnScrollListener() {

                    @Override
                    public void onScrollStateChanged(AbsListView view, int scrollState) {

                    }

                    @Override
                    public void onScroll(AbsListView view, int firstVisibleItem,
                                         int visibleItemCount, int totalItemCount) {
                        if ((indexPostion < mListView.getFirstVisiblePosition() || indexPostion > mListView
                                .getLastVisiblePosition()) && isPlaying) {
                            indexPostion = -1;
                            isPlaying = false;
                            mAdapter.notifyDataSetChanged();
                            MediaHelp.release();
                        }
                    }
                });
            }

            @Override
            public void Fail(String Error) {

            }
        });
    }

    class MAdapter extends BaseAdapter {
        private Context context;
        LayoutInflater inflater;

        public MAdapter(Context context) {
            this.context = context;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public VideoBean.DataBean.TrailersBean getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public View getView(int position, View v, ViewGroup parent) {
            GameVideoViewHolder holder = null;
            if (v == null) {
                holder = new GameVideoViewHolder();
                v = inflater.inflate(R.layout.list_video_item, parent, false);
                holder.mVideoViewLayout = (VideoSuperPlayer) v.findViewById(R.id.video);
                holder.mPlayBtnView = (ImageView) v.findViewById(R.id.play_btn);
                holder.info_title=v.findViewById(R.id.info_title);
                holder.icon = v.findViewById(R.id.icon);
                v.setTag(holder);
            } else {
                holder = (GameVideoViewHolder) v.getTag();
            }
            Glide.with(context).load(mList.get(position).getCoverImg()).into(holder.icon);
            holder.info_title.setText(mList.get(position).getMovieName());
            holder.mPlayBtnView.setOnClickListener(new MyOnclick(
                    holder.mPlayBtnView, holder.mVideoViewLayout, position));
            if (indexPostion == position) {
                holder.mVideoViewLayout.setVisibility(View.VISIBLE);
            } else {
                holder.mVideoViewLayout.setVisibility(View.GONE);
                holder.mVideoViewLayout.close();
            }
            return v;
        }

        class MyOnclick implements View.OnClickListener {
            VideoSuperPlayer mSuperVideoPlayer;
            ImageView mPlayBtnView;
            int position;

            public MyOnclick(ImageView mPlayBtnView,
                             VideoSuperPlayer mSuperVideoPlayer, int position) {
                this.position = position;
                this.mSuperVideoPlayer = mSuperVideoPlayer;
                this.mPlayBtnView = mPlayBtnView;
            }

            @Override
            public void onClick(View v) {
                MediaHelp.release();
                indexPostion = position;
                isPlaying = true;
                mSuperVideoPlayer.setVisibility(View.VISIBLE);
                mSuperVideoPlayer.loadAndPlay(MediaHelp.getInstance(), mList
                        .get(position).getUrl(), 0, false);
                mSuperVideoPlayer.setVideoPlayCallback(new MyVideoPlayCallback(
                        mPlayBtnView, mSuperVideoPlayer, mList.get(position)));
                notifyDataSetChanged();
            }
        }

        class MyVideoPlayCallback implements VideoSuperPlayer.VideoPlayCallbackImpl {
            ImageView mPlayBtnView;
            VideoSuperPlayer mSuperVideoPlayer;
            VideoBean.DataBean.TrailersBean info;

            public MyVideoPlayCallback(ImageView mPlayBtnView,
                                       VideoSuperPlayer mSuperVideoPlayer, VideoBean.DataBean.TrailersBean info) {
                this.mPlayBtnView = mPlayBtnView;
                this.info = info;
                this.mSuperVideoPlayer = mSuperVideoPlayer;
            }

            @Override
            public void onCloseVideo() {
                closeVideo();
            }

            @Override
            public void onSwitchPageType() {

            }

            @Override
            public void onPlayFinish() {
                closeVideo();
            }

            private void closeVideo() {
                isPlaying = false;
                indexPostion = -1;
                mSuperVideoPlayer.close();
                MediaHelp.release();
                mPlayBtnView.setVisibility(View.VISIBLE);
                mSuperVideoPlayer.setVisibility(View.GONE);
            }

        }

        class GameVideoViewHolder {

            private VideoSuperPlayer mVideoViewLayout;
            private ImageView mPlayBtnView;
            private TextView info_title;
            private ImageView icon;

        }
    }


    public void initBanner() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.banner_item, null);
        Banner banner = view.findViewById(R.id.bannerdemo);
        //设置Banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //实例化图片集合
        mListImage = new ArrayList<>();
        //将图片放入集合中
        mListImage.add("http://123.206.14.104:8080/TakeoutService/imgs/promotion/1.jpg");
        mListImage.add("http://123.206.14.104:8080/TakeoutService/imgs/promotion/2.jpg");
        mListImage.add("http://123.206.14.104:8080/TakeoutService/imgs/promotion/3.jpg");
        //设置Banner图片集合
        banner.setImages(mListImage);
        //设置Banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //实例化Title集合
        mListTitle = new ArrayList<>();
        //将标题放入集合
        mListTitle.add("第一张图片");
        mListTitle.add("第二张图片");
        mListTitle.add("第三张图片");
        //设置Banner标题集合（当banner样式有显示title时）
        banner.setBannerTitles(mListTitle);
        //设置轮播时间
        banner.setDelayTime(1000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //Banner设置方法全部调用完毕时最后调用
        banner.start();

        mListView.addHeaderView(view);
    }

    public class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //第一种方法：Glide 加载图片简单用法
            Glide.with(context).load(path).into(imageView);
        }

        //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
        @Override
        public ImageView createImageView(Context context) {
            //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
            //SimpleDraweeView simpleDraweeView=new SimpleDraweeView(context);
            return null;
        }
    }
}
