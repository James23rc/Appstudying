package com.example.gank.net;



import com.example.gank.bean.GankBean;
import com.example.gank.bean.LoginBean;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitHelper {
    public static final String BASE_URL = "https://gank.io/";
    public static final String URL = "https://wanandroid.com/";
    private static RetrofitHelper instance;
    private Retrofit retrofit;

    private RetrofitHelper(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(OkHttpManager.getOkHttp())
                .build();
    }

    public static RetrofitHelper getInstance(){
        if (instance == null){
            synchronized (RetrofitHelper.class){
                if (instance == null){
                    instance = new RetrofitHelper();
                }
            }
        }
        return instance;
    }

    public ApiService getService(){
        ApiService service = retrofit.create(ApiService.class);
        return service;
    }

    /*
     * 登录
     * */
    public static Observable<LoginBean> loginCheck(String username, String password){
        Observable<LoginBean> observable1 = RetrofitHelper.getInstance().getService()
                .login(username,password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return observable1;
    }

    /*
     * 注册
     * */
    public static Observable<LoginBean> registerCheck(String username,
                                                      String password, String repassword){
        Observable<LoginBean> observable2 = RetrofitHelper.getInstance()
                .getService().register(username, password, repassword)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        return observable2;
    }

    /*
     * 从gank上获得数据
     * */
    public static Observable<GankBean> getObservable(String type, int counts, int pages){
        Observable<GankBean> observable =
                RetrofitHelper.getInstance().getService().getResult(type,counts,pages);
        return observable;
    }

    public static <T> ObservableTransformer<T,T> io_main(){
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return  upstream.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

}
