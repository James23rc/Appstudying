package com.example.gank.util.threadPoolUtil;

import android.annotation.SuppressLint;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

class CacheThreadPool implements ThreadPool {
    private static CacheThreadPool cacheThreadPool;

    private static ExecutorService instance;

    public static CacheThreadPool getThreadPool(){
        if (cacheThreadPool == null){
            synchronized (CacheThreadPool.class){
                if (cacheThreadPool == null){
                    cacheThreadPool = new CacheThreadPool();
                }
            }
        }
        return cacheThreadPool;
    }
    @SuppressLint("CI_NotAllowInvokeExecutorsMethods")
    private CacheThreadPool(){
        instance = Executors.newCachedThreadPool();
    }


    @Override
    public ExecutorService getInstance() {

        return instance;
    }
}
