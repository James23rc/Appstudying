package com.example.gank.mvp;


import com.example.gank.base.BaseModelInterface;
import com.example.gank.bean.LoginBean;

public interface RegisterModelInter extends BaseModelInterface {
    void register(String username,
                  String password,
                  String repassword,
                  Response response);

    interface Response{
        void success(LoginBean loginBean);
    }
}
