package com.example.gank.mvp;


import com.example.gank.base.BasePresenter;
import com.example.gank.bean.LoginBean;

public class LoginPresenter extends BasePresenter<LoginViewInterface> {
    private LoginModel model = new LoginModel();
    public void checkLogin(String username, String password){
        model.login(username, password, new LoginModelInter.Response() {



            @Override
            public void success(LoginBean loginBean) {
                mView.loginShow(loginBean);
            }
        });
    }
}
