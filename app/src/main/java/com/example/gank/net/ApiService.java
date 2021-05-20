package com.example.gank.net;



import com.example.gank.bean.GankBean;
import com.example.gank.bean.LoginBean;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {

     @Headers({"urlname:gank"})
    @GET("api/data/{type}/{counts}/{pages}")
    Observable<GankBean> getResult(@Path("type") String type, @Path("counts") int counts, @Path("pages") int pages);

    //登录api
    @Headers({"urlname:wananzhuo"})
    @FormUrlEncoded
    @POST("user/login")
    Observable<LoginBean> login(@Field("username") String username, @Field("password") String password);


    //注册api
    @Headers({"urlname:wananzhuo"})
    @FormUrlEncoded
    @POST("user/register")
    Observable<LoginBean> register(@Field("username") String username, @Field("password") String password, @Field("repassword") String repassword);



}
