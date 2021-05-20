package com.example.gank.util.downloadutil;


import com.example.gank.util.threadPoolUtil.Request;
import com.example.gank.util.threadPoolUtil.ThreadPoolNetUtil;
import com.example.gank.util.threadPoolUtil.ThreadPoolRequest;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class DownloadUtil {

    private static final int CPU_COUNT = Runtime.getRuntime()
            .availableProcessors();
    private static final int CORE_POOL_SIZE = CPU_COUNT + 1;
    private static final int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private static final long KEEP_ALIVE = 10L;
    private static final String ENCODE = "UTF-8";

    private onDownloadFinished onDownloadFinished;

    private static final ThreadFactory tf = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);
        @Override
        public Thread newThread(Runnable runnable) {
            return new Thread(runnable,"downloadThread"+ mCount.getAndIncrement());
        }
    };

    private static ThreadPoolRequest poolRequest =  new ThreadPoolRequest.Builder(CORE_POOL_SIZE)
            .setLinkedQuene(new LinkedBlockingQueue<Runnable>())
            .setKeepLive(KEEP_ALIVE)
            .setMaxPoolSize(MAXIMUM_POOL_SIZE)
            .setThreadFactory(tf)
            .build();

    public DownloadUtil(onDownloadFinished onDownloadFinished){
        this.onDownloadFinished = onDownloadFinished;
    }

    public static void download(String url){
        Request request = new Request.Builder("GET",url)
                .setConnectionTime(8000)
                .setPoolZise(CORE_POOL_SIZE)
                .setReadTimeOut(5000)
                .build();
        for (int i = 0;i < CORE_POOL_SIZE;i++){
            DownloadRunnable runnable = new DownloadRunnable(request,i);
            ThreadPoolNetUtil.defaultSubmit(runnable);
        }

    }
}
