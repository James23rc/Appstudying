package com.example.gank.adapter;

import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.example.gank.bean.GankBean;

import java.util.ArrayList;

public class GirlsAdapter extends PagerAdapter implements ViewPager.OnPageChangeListener{
    private ArrayList<GankBean.Results> girlsBean = new ArrayList<>();

    public GirlsAdapter(ArrayList<GankBean.Results> bean){
        this.girlsBean = bean;
    }


    @Override
    public int getCount() {
        return girlsBean.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
