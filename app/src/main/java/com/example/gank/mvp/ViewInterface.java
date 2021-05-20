package com.example.gank.mvp;





import com.example.gank.base.BaseViewInterface;
import com.example.gank.bean.GankBean;
import com.example.gank.bean.LoginBean;

import java.util.List;

public interface ViewInterface extends BaseViewInterface {
    void textShow(List<GankBean.Results> results);


}
