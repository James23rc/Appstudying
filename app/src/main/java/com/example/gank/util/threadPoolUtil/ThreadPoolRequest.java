package com.example.gank.util.threadPoolUtil;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;

public class ThreadPoolRequest {
    private static final int CPU_COUNT = Runtime.getRuntime()
            .availableProcessors();
    private BlockingQueue linkedBlockingQueue = new LinkedBlockingQueue<>();
    private int CORE_POOL_SIZE = CPU_COUNT + 1;
    private int MAXIMUM_POOL_SIZE = CPU_COUNT * 2 + 1;
    private long KEEP_ALIVE = 10L;
    private ThreadFactory threadFactory = new ThreadFactory() {
        @Override
        public Thread newThread(Runnable r) {
            return new Thread(r);
        }
    };

    public ThreadPoolRequest(Builder builder){
        if (builder.linkedBlockingQueue != null) linkedBlockingQueue = builder.linkedBlockingQueue;

        if (builder.threadFactory != null) threadFactory = builder.threadFactory;

        if (builder.coreThreadSize > 0){
            CORE_POOL_SIZE = builder.coreThreadSize;
        }
        if (builder.maxSize > 0){
            MAXIMUM_POOL_SIZE = builder.maxSize;
        }
        if (builder.keepLive > 0){
            KEEP_ALIVE = builder.keepLive;
        }
    }

    public ThreadFactory getThreadFactory() {
        return threadFactory;
    }

    public BlockingQueue getBlockingQueue() {
        return linkedBlockingQueue;
    }

    public  int getCorePoolSize() {
        return CORE_POOL_SIZE;
    }

    public int getMaximumPoolSize() {
        return MAXIMUM_POOL_SIZE;
    }

    public long getKeepAlive() {
        return KEEP_ALIVE;
    }

    public static class Builder{
        private int coreThreadSize = 0;
        private int maxSize = 0;
        private long keepLive = 0;
        private ThreadFactory threadFactory;
        private LinkedBlockingQueue linkedBlockingQueue;

        public Builder(int coreThreadSize){
            this.coreThreadSize = coreThreadSize;
        }

        public Builder setThreadFactory(ThreadFactory threadFactory){
            this.threadFactory = threadFactory;
            return this;
        }
        public Builder setMaxPoolSize(int maxPoolSize){
            this.maxSize = maxPoolSize;
            return this;
        }
        public Builder setKeepLive(long keepLive){
            this.keepLive = keepLive;
            return this;
        }
        public Builder setLinkedQuene(LinkedBlockingQueue linkedQuene){
            this.linkedBlockingQueue = linkedQuene;
            return this;
        }
        public ThreadPoolRequest build(){
            return new ThreadPoolRequest(this);
        }
    }
}
