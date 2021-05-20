package com.example.gank.mvp;


import com.example.gank.bean.LoginBean;
import com.example.gank.net.RetrofitHelper;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class RegisterModel implements RegisterModelInter{
    @Override
    public void register(String username,
                         String password,
                         String repassword,
                         final Response response) {
        RetrofitHelper.registerCheck(username, password, repassword)
                .subscribe(new Observer<LoginBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginBean loginBean) {
                      response.success(loginBean);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
