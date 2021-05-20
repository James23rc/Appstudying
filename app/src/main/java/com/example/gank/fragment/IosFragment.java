package com.example.gank.fragment;

import com.example.gank.base.GankFragment;
import com.example.gank.bean.LoginBean;
import com.example.gank.mvp.Presenter;
import com.example.gank.mvp.ViewInterface;


public class IosFragment extends GankFragment<Presenter> implements ViewInterface {


    @Override
    public Presenter getPresenter() {
        return new Presenter();
    }

    @Override
    public int getLayoutType() {
        return 1;
    }

    @Override
    public void netWork() {
    mPresenter.getData("iOS",20,1);
    }

    @Override
    public void reNetWork() {
        super.reNetWork();
        mPresenter.getData("iOS",10,page);
    }

}
