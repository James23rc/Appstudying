package com.example.gank.fragment;


import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.gank.Activity.TimeActivity;
import com.example.gank.Activity.WebActivity;
import com.example.gank.R;
import com.example.gank.adapter.NewsAdapter;
import com.example.gank.base.WrapContentListView;
import com.example.gank.bean.BannerBean;
import com.example.gank.bean.GankBean;
import com.example.gank.bean.NewBean;
import com.example.gank.bean.NewsBean;
import com.example.gank.util.HttpUtil;
import com.example.gank.util.threadPoolUtil.Request;
import com.example.gank.util.threadPoolUtil.ThreadPoolNetUtil;
import com.google.gson.Gson;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;

public class FirstFragment extends Fragment implements OnBannerListener {
    private final static String URL = "https://gank.io/api/today";
    private final static String BINNER_URL = "https://www.wanandroid.com/banner/json";
    private List<NewsBean> newsBeanList = new ArrayList<>();
    private List<NewsBean> newsBeanList2 = new ArrayList<>();
    private List<NewsBean> newsBeanList3 = new ArrayList<>();
    private List<NewsBean> newsBeanList4 = new ArrayList<>();
    private List<NewsBean> newsBeanList5 = new ArrayList<>();
    private List<NewsBean> newsBeanList6 = new ArrayList<>();
    private Banner banner;
    private WrapContentListView listView2,listView3,listView4,listView5,listView6, androidList;
    private NewsAdapter adapter1,adapter2,adapter3, adapter4,adapter5,adapter6;
    private ImageView homeImage;
    private NewBean newBean;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ArrayList<String> images;
    private ArrayList<String> urls;
    private ArrayList<String> texts;
    private View firstFragment;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        firstFragment = inflater.inflate(R.layout.first_fragment, container, false);
        initView();
        initUrl();
        ThreadPoolNetUtil.get(URL, new Request.Callback<String>() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                newBean = gson.fromJson(response,NewBean.class);
                initData();
                sendMsg();
            }

            @Override
            public void onError(String error) {

            }
        });

        //设置样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE_INSIDE);
        //设置图片加载器
        banner.setImageLoader(new MyImageLoader());
        //设置轮播的动画效果
        banner.setBannerAnimation(Transformer.ZoomOutSlide);
        //轮播图片的文字
        banner.setBannerTitles(texts);
        //设置轮播间隔时间
        banner.setDelayTime(3000);
        //设置是否为自动轮播，默认是true
        banner.isAutoPlay(true);
        //设置指示器的位置，小点点，居中显示
        banner.setIndicatorGravity(BannerConfig.CENTER);

        homeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.START);
            }
        });

        ImageView imageView = firstFragment.findViewById(R.id.image_first);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent0 = new Intent(getActivity(), TimeActivity.class);
                getActivity().startActivity(intent0);
            }
        });

        listView4.setAdapter(adapter4);
        listView5.setAdapter(adapter5);
        listView6.setAdapter(adapter6);
        androidList.setAdapter(adapter1);
        listView2.setAdapter(adapter2);
        listView3.setAdapter(adapter3);

        click();

        return firstFragment;

    }
    private void initView(){
        urls = new ArrayList<>();
        images = new ArrayList<>();
        texts = new ArrayList<>();
        banner = firstFragment.findViewById(R.id.banner);
        banner.setOnBannerListener(this);
        drawerLayout = getActivity().findViewById(R.id.drawer_layout);
        toolbar = firstFragment.findViewById(R.id.toolbar_first);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        homeImage = firstFragment.findViewById(R.id.nav_home);

        androidList = firstFragment.findViewById(R.id.lv_main);
        listView2 = firstFragment.findViewById(R.id.lv_main2);
        listView3 = firstFragment.findViewById(R.id.lv_main3);
        listView4 = firstFragment.findViewById(R.id.lv_main4);
        listView5 = firstFragment.findViewById(R.id.lv_main5);
        listView6 = firstFragment.findViewById(R.id.lv_main6);

        adapter1 = new NewsAdapter(this.getActivity(), R.layout.listview_item, newsBeanList);
        adapter2 = new NewsAdapter(this.getActivity(), R.layout.listview_item, newsBeanList2);
        adapter3 = new NewsAdapter(this.getActivity(), R.layout.listview_item, newsBeanList3);
        adapter4 = new NewsAdapter(this.getActivity(), R.layout.listview_item, newsBeanList4);
        adapter5 = new NewsAdapter(this.getActivity(), R.layout.listview_item, newsBeanList5);
        adapter6 = new NewsAdapter(this.getActivity(), R.layout.listview_item, newsBeanList6);

    }
    private void click(){
        androidList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(parent.getContext(), WebActivity.class);
                intent.putExtra("itemurl",
                        newBean.getResults().getAndroid().get(position).getUrl());
                startActivity(intent);

            }
        });
        listView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(parent.getContext(), WebActivity.class);
                intent.putExtra("itemurl",
                        newBean.getResults().getIOS().get(position).getUrl());
                startActivity(intent);


            }
        });
        listView3.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(parent.getContext(), WebActivity.class);
                intent.putExtra("itemurl",
                        newBean.getResults().get瞎推荐().get(position).getUrl());
                startActivity(intent);


            }
        });
        listView4.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(parent.getContext(), WebActivity.class);
                intent.putExtra("itemurl",
                        newBean.getResults().get拓展资源().get(position).getUrl());
                startActivity(intent);


            }
        });
        listView5.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(parent.getContext(), WebActivity.class);
                intent.putExtra("itemurl",
                        newBean.getResults().get福利().get(position).getUrl());
                startActivity(intent);


            }
        });
        listView6.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(parent.getContext(), WebActivity.class);
                intent.putExtra("itemurl",
                        newBean.getResults().get休息视频().get(position).getUrl());
                startActivity(intent);


            }
        });
    }

    private Handler handler = new Handler(){
        public void handleMessage(Message msg){
            switch (msg.what){
                case 1:
                    adapter6.notifyDataSetChanged();
                    adapter5.notifyDataSetChanged();
                    adapter4.notifyDataSetChanged();
                    adapter3.notifyDataSetChanged();
                    adapter2.notifyDataSetChanged();
                    adapter1.notifyDataSetChanged();
                    break;
                case 2:
                    banner.setImages(images)
                            //轮播图的监听
                            //开始调用的方法，启动轮播图。
                            .start();
                    break;
                default:
                    break;
            }
        }
    };
    public void sendMsg(){
        ThreadPoolNetUtil.defaultSubmit(new Runnable() {
            @Override
            public void run() {
                Message message = Message.obtain();
                message.what=1;
                handler.sendMessage(message);
            }
        });
    }
    private void loaded(){
        ThreadPoolNetUtil.defaultSubmit(new Runnable() {
            @Override
            public void run() {
                Message ma = Message.obtain();
                ma.what = 2;
                handler.sendMessage(ma);
            }
        });
    }
    private void initData(){
        //android
        if (newBean.getResults().getAndroid() != null) {
            for (int i = 0; i < newBean.getResults().getAndroid().size(); i++) {
                NewsBean ab = new NewsBean(
                        newBean.getResults().getAndroid().get(i).getDesc(),
                        newBean.getResults().getAndroid().get(i).getWho(),
                        newBean.getResults().getAndroid().get(i).getPublishedAt());
                newsBeanList.add(ab);
            }

        }
        //ios
        if (newBean.getResults().getIOS() != null) {
            for (int i = 0; i < newBean.getResults().getIOS().size(); i++) {
                NewsBean ab = new NewsBean(
                        newBean.getResults().getIOS().get(i).getDesc(),
                        newBean.getResults().getIOS().get(i).getWho(),
                        newBean.getResults().getIOS().get(i).getPublishedAt());
                newsBeanList2.add(ab);
            }
        }

        //recommend
        if (newBean.getResults().get瞎推荐() != null) {
            for (int i = 0; i < newBean.getResults().get瞎推荐().size(); i++) {
                NewsBean ab = new NewsBean(
                        newBean.getResults().get瞎推荐().get(i).getDesc(),
                        newBean.getResults().get瞎推荐().get(i).getWho(),
                        newBean.getResults().get瞎推荐().get(i).getPublishedAt());
                newsBeanList3.add(ab);
            }
        }
        if (newBean.getResults().get拓展资源() != null){
            for (int i = 0; i<newBean.getResults().get拓展资源().size(); i++){
                NewsBean ab = new NewsBean(newBean.getResults()
                        .get拓展资源().get(i).getDesc(),newBean.getResults()
                        .get拓展资源().get(i).getWho(),newBean.getResults()
                        .get拓展资源().get(i).getPublishedAt());
                newsBeanList4.add(ab);
            }
        }

        //
        if (newBean.getResults().get福利() != null) {
            for (int i = 0; i < newBean.getResults().get福利().size(); i++) {
                NewsBean ab = new NewsBean(newBean.getResults().get福利().get(i).getDesc(),
                        newBean.getResults().get福利().get(i).getWho(),
                        newBean.getResults().get福利().get(i).getPublishedAt());
                newsBeanList5.add(ab);
            }
        }
        //
        if (newBean.getResults().get休息视频() != null) {
            for (int i = 0; i < newBean.getResults().get休息视频().size(); i++) {
                NewsBean ab = new NewsBean(newBean.getResults().get休息视频().get(i).getDesc(),
                        newBean.getResults().get休息视频().get(i).getWho(),
                        newBean.getResults().get休息视频().get(i).getPublishedAt());
                newsBeanList6.add(ab);
            }
        }
    }
    private void initUrl(){
        ThreadPoolNetUtil.get(BINNER_URL, new Request.Callback<String>() {
            @Override
            public void onSuccess(String response) {
                Gson gson = new Gson();
                BannerBean bannerBean = gson.fromJson(response,BannerBean.class);
                selectUrl(bannerBean);

            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void selectUrl(BannerBean bannerBean){

        if (bannerBean != null && bannerBean.getErrorCode() == 0){
            images.clear();
            texts.clear();
            texts.clear();
        }else{
            return;
        }
        for (int i = 0; i < bannerBean.getData().size(); i++){
            images.add(bannerBean.getData().get(i).getImagePath());
            texts.add(bannerBean.getData().get(i).getTitle());
            urls.add(bannerBean.getData().get(i).getUrl());
        }
        if (urls.size() != 0 && texts.size() != 0 && images.size() != 0){
            loaded();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(true);
    }

    @Override
    public void OnBannerClick(int position) {
        Intent intent = new Intent(getActivity(),WebActivity.class);
        intent.putExtra("itemurl",urls.get(position));
        startActivity(intent);
    }
    private class MyImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, final ImageView imageView) {
            Glide.with(context.getApplicationContext()).load((String) path).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    int width = resource.getIntrinsicWidth();
                    int height = resource.getIntrinsicHeight();
                    if (width > height){
                        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    }else if (width <= height){
                        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    }
                    return false;
                }
            }).into(imageView);
        }
    }

}


