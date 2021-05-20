package com.example.gank.fragment;

import com.example.gank.base.GankFragment;

import com.example.gank.bean.LoginBean;
import com.example.gank.mvp.Presenter;
import com.example.gank.mvp.ViewInterface;


public class WelfareFragment extends GankFragment<Presenter> implements ViewInterface {


    @Override
    public Presenter getPresenter() {
        return new Presenter();
    }

    @Override
    public int getLayoutType() {
        return 3;
    }

    @Override
    public void netWork() {
    mPresenter.getData("福利",20,1);
    }

    @Override
    public void reNetWork() {
        super.reNetWork();
        mPresenter.getData("福利",10,page);
    }

}
