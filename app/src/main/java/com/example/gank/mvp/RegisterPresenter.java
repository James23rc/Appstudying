package com.example.gank.mvp;


import com.example.gank.base.BasePresenter;
import com.example.gank.bean.LoginBean;

public class RegisterPresenter extends BasePresenter<RegisterViewInterface> {

    private RegisterModel model = new RegisterModel();

    public void checkRegister(String username, String password, String repassword){

        model.register(username, password, repassword, new RegisterModelInter.Response() {
            @Override
            public void success(LoginBean loginBean) {
                mView.registerShow(loginBean);
            }
        });
    }
}
