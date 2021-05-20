package com.example.gank.util.threadPoolUtil;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

 class MyThreadPoolExecutor implements ThreadPool{
    private static MyThreadPoolExecutor executor;
    private static ExecutorService instance;
    private  ThreadFactory threadFactory;
    private BlockingQueue blockingQueue;
    private  int CORE_POOL_SIZE;
    private int MAXIMUM_POOL_SIZE;
    private long KEEP_ALIVE;

    private MyThreadPoolExecutor(ThreadPoolRequest request){
        init(request);
        instance = new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAXIMUM_POOL_SIZE,
                KEEP_ALIVE,
                TimeUnit.SECONDS,
                blockingQueue,
                threadFactory);
    }

    public static MyThreadPoolExecutor getExecutor(ThreadPoolRequest request){
        if (executor == null){
            synchronized (MyThreadPoolExecutor.class){
                if (executor == null){
                    executor = new MyThreadPoolExecutor(request);
                }
            }
        }
        return executor;
    }

    private void init(ThreadPoolRequest request){
        threadFactory = request.getThreadFactory();
        blockingQueue = request.getBlockingQueue();
        CORE_POOL_SIZE = request.getCorePoolSize();
        MAXIMUM_POOL_SIZE = request.getMaximumPoolSize();
        KEEP_ALIVE = request.getKeepAlive();
    }

    private static ExecutorService getExecutor(){
        return instance;
    }

    @Override
    public ExecutorService getInstance() {
        return getExecutor();
    }
}
