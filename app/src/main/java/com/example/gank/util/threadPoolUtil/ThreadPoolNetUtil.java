package com.example.gank.util.threadPoolUtil;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;


public class ThreadPoolNetUtil {

    private static final int CPU_COUNT = Runtime.getRuntime()
            .availableProcessors();
    public static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAX_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final long KEEP_ALIVE = 10L;
    private static final String ENCODE = "UTF-8";
    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "ImageLoader#" + mCount.getAndIncrement());
        }
    };

    private static ThreadPoolRequest defaultPoolRequest =  new ThreadPoolRequest.Builder(CORE_POOL_SIZE)
            .setKeepLive(KEEP_ALIVE)
            .setMaxPoolSize(MAX_POOL_SIZE)
            .setThreadFactory(sThreadFactory)
            .setLinkedQuene(new LinkedBlockingQueue<Runnable>())
            .build();

    public static void defaultSubmit(Runnable runnable){

        MyThreadPoolFactory.getDefaultThreadPooL(defaultPoolRequest)
                .getInstance().execute(runnable);
    }

    public static void get(String url, final Request.Callback<String> callback){
        Request request = new Request.Builder("GET",url)
                .setConnectionTime(5000)
                .setPoolZise(10)
                .setReadTimeOut(5000)
                .build();
        request.setCallback(callback);
        NetRunnable runnable = new NetRunnable(request);
        defaultSubmit(runnable);
    }

    public static void post(String url,String params,Request.Callback callback){
    Request request = new Request.Builder("POST",url)
            .setConnectionTime(5000)
            .setReadTimeOut(5000)
            .setPostParams(params)
            .build();
        request.setCallback(callback);
    NetRunnable runnable = new NetRunnable(request);
    defaultSubmit(runnable);
    }

    public static void test(String url,String username,String password,final Request.Callback<String> callback){
        String params = null;
        try {
            params = "username="+ URLEncoder.encode(username, ENCODE)+"&password="+ URLEncoder.encode(password, ENCODE);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        Request request = new Request.Builder("POST",url)
                .setConnectionTime(5000)
                .setReadTimeOut(5000)
                .setPostParams(params)
                .build();
        request.setCallback(callback);
        NetRunnable runnable = new NetRunnable(request);
        defaultSubmit(runnable);
    }

}

