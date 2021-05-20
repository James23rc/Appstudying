package com.example.gank.mvp;

import android.util.Log;


import com.example.gank.bean.GankBean;
import com.example.gank.net.RetrofitHelper;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Model implements ModelInterface {

    @Override
    public void getData(String type, int counts, int pages, final Response response) {
        RetrofitHelper.getObservable(type,counts,pages).
                subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GankBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(GankBean gankBean) {
                        response.success(gankBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d("TAG",e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


}
