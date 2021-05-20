package com.example.gank.util.threadPoolUtil;

 class MyThreadPoolFactory {

    public static ThreadPool getFixedThreadPool(ThreadPoolRequest request){
        return FixedThreadPool.getFixedThreadPool(request);
    }

    public static ThreadPool getCacheThreadPool(){
        return CacheThreadPool.getThreadPool();
    }

    public static ThreadPool getDefaultThreadPooL(ThreadPoolRequest request){
        return MyThreadPoolExecutor.getExecutor(request);
    }
}
