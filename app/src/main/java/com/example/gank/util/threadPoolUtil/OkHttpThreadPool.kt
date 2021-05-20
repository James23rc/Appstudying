package com.example.gank.util.threadPoolUtil


import java.util.concurrent.*

class OkHttpThreadPool : ThreadPool{
    override fun getInstance(): Executor = ThreadPoolExecutor(0,Int.MAX_VALUE,60,TimeUnit.SECONDS,
            SynchronousQueue(),threadFactory("Dispatcher",false))

    fun threadFactory(
            name: String,
            daemon: Boolean
    ): ThreadFactory = ThreadFactory { runnable ->
        Thread(runnable,name).apply {
            isDaemon = daemon
        }
    }
}