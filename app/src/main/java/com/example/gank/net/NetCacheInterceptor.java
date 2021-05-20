package com.example.gank.net;

import android.content.Context;
import android.text.TextUtils;

import com.example.gank.util.NetStateUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class NetCacheInterceptor implements Interceptor {
    private Context context;
    public NetCacheInterceptor(Context context){
        this.context = context;
    }

    public static final int DEFAULT_MAX_AGE = 60;

    public static final int DEFAULT_MAX_STALE = 60 * 60 * 24 * 7;

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();
        if(!NetStateUtil.isNetworkConnected(context)){
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE)
                    .build();
        }
        Response originalResponse = chain.proceed(request);
        Response responseLatest = null;
        if (!NetStateUtil.isNetworkConnected(context)) {

            String iCacheControl = request.cacheControl().toString();
            if(!TextUtils.isEmpty(iCacheControl)) {

                responseLatest = originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", iCacheControl)
                        .build();
            } else {

                responseLatest = originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + DEFAULT_MAX_STALE)
                        .build();
            }
        } else {

            responseLatest= originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + DEFAULT_MAX_STALE)
                    .build();
        }
        return responseLatest;
    }
}
