package com.example.gank.util.threadPoolUtil;

import android.annotation.SuppressLint;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

 class FixedThreadPool implements ThreadPool {
    private static FixedThreadPool fixedThreadPool;
    private static int POOL_SIZE;
    private static ExecutorService instance;

    @SuppressLint("CI_NotAllowInvokeExecutorsMethods")
    private FixedThreadPool(){
        instance = Executors.newFixedThreadPool(POOL_SIZE);
    }

    public static FixedThreadPool getFixedThreadPool(ThreadPoolRequest request){
        POOL_SIZE = request.getCorePoolSize();
        if (fixedThreadPool == null){
            synchronized (FixedThreadPool.class){
                if (fixedThreadPool == null){
                    fixedThreadPool = new FixedThreadPool();
                }
            }
        }
        return fixedThreadPool;
    }

    private static ExecutorService getExecutor(){
        return instance;
    }

    @Override
    public ExecutorService getInstance() {
        return getExecutor();
    }

}
