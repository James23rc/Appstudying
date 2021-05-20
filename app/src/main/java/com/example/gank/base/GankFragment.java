package com.example.gank.base;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Toast;

import com.example.gank.Activity.GirlsActivity;
import com.example.gank.Activity.WebActivity;
import com.example.gank.R;
import com.example.gank.adapter.GankAdapter;
import com.example.gank.adapter.OnItemClickListener1;
import com.example.gank.bean.GankBean;
import com.example.gank.mvp.ViewInterface;

import java.util.ArrayList;
import java.util.List;

public abstract class GankFragment<T extends BasePresenter> extends BaseGankFragment implements ViewInterface {

    private RecyclerView recyclerView;
    private GankAdapter gankAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private int lastVisibleItem = 1;
    public T mPresenter = null;
    public int page = 2;
    private LinearLayoutManager layoutManager;
    private GridLayoutManager gridLayoutManager;
    private List<GankBean.Results> list = new ArrayList<>();
    public abstract T getPresenter();
    public abstract int getLayoutType();
    public abstract void netWork();
    public void reNetWork(){
        page++;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_layout;
    }

    @Override
    public void initView(View view) {
        mPresenter = getPresenter();
        mPresenter.attach(this);
        gankAdapter = new GankAdapter(list,getActivity(),getLayoutType());
        recyclerView = view.findViewById(R.id.fragment_recycler);
        swipeRefreshLayout = view.findViewById(R.id.fragment_swipe_refresh);
        if (getLayoutType() == 3){
            gridLayoutManager = new GridLayoutManager(getActivity(),2);
            recyclerView.setLayoutManager(gridLayoutManager);
        }else{
            layoutManager = new LinearLayoutManager(this.getActivity());
            recyclerView.setLayoutManager(layoutManager);
        }
        /*
         * 设置Adapter
         * */
        recyclerView.setAdapter(gankAdapter);
        netWork();
        setFresh();
    }

    @Override
    public void textShow(List<GankBean.Results> results) {
        if (list.size() == 0){
            list.addAll(results);
            refrashUi();
        }else if (list.get(0).getDesc().equals( results.get(0).getDesc()) && page < 3){
            showUi();
        }else if (!list.get(0).getDesc().equals( results.get(0).getDesc()) && page < 3){
            list.clear();
            list.addAll(results);
            refrashUi();
        }else{
            list.addAll(results);
            refrashUi();
        }

    }

    @SuppressLint("ResourceAsColor")
    private void setFresh(){
        /*
         * 设置RecyclerView的点击事件
         * */
        if (gankAdapter != null){
            gankAdapter.setOnItemClickListener(new OnItemClickListener1() {
                @Override
                public void onItemClick(View view, int position, String str) {
                    Toast.makeText(getActivity(), "你点击了" + str, Toast.LENGTH_SHORT).show();
                    Intent intent1 = null;
                    if (getLayoutType() == 3){
                         intent1 = new Intent(getActivity(), GirlsActivity.class);
                    }else{
                         intent1 = new Intent(getActivity(), WebActivity.class);
                    }
                    intent1.putExtra("url",list.get(position).getUrl());
                    startActivity(intent1);
                }
            });

        }

        swipeRefreshLayout.setColorSchemeColors(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                netWork();
            }
        });
        /*
         * 上拉加载
         * */

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 ==
                        gankAdapter.getItemCount()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            /*
                             * 得到动态页数后，再次请求数据
                             * */
                            reNetWork();
                            refrashUi();
                            Toast.makeText(getActivity(), "更新了10条数据...",
                                    Toast.LENGTH_SHORT).show();
                        }
                    },0);
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //找到最后一个的位置
                if (getLayoutType() == 3){
                    lastVisibleItem = gridLayoutManager.findLastVisibleItemPosition();
                }else{
                    lastVisibleItem = layoutManager.findLastVisibleItemPosition();
                }

            }
        });

    }

    protected void refrashUi(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (gankAdapter != null){
                    gankAdapter.notifyDataSetChanged();
                    swipeRefreshLayout.setRefreshing(false);
                }
            }
        });
    }
    private void showUi(){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getActivity(),"已刷新到最新状态", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (this.mPresenter != null){
            this.mPresenter = null;
        }
    }
}