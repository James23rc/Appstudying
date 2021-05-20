package com.example.gank.mvp;


import com.example.gank.base.BasePresenter;
import com.example.gank.bean.GankBean;

import java.util.ArrayList;
import java.util.List;

public class Presenter extends BasePresenter<ViewInterface> {
private Model model = new Model();

    public void getData(String type, int counts, int pages){
        model.getData(type,counts,pages,new ModelInterface.Response() {
            @Override
            public void success(GankBean response) {
                List<GankBean.Results> list = new ArrayList<>();
                list.addAll(response.results);
                mView.textShow(list);

            }
        });
    }






}
