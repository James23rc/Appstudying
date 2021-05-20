package com.example.gank.mvp;


import com.example.gank.base.BaseModelInterface;
import com.example.gank.bean.LoginBean;

public interface LoginModelInter extends BaseModelInterface {

    void login(String username, String password, Response response);
    interface Response{
        void success(LoginBean loginBean);
    }
}
