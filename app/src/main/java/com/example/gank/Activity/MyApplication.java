package com.example.gank.Activity;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;


public class MyApplication extends Application {
    private static MyApplication mContext;
    public static RefWatcher refWatcher;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext=this;
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return;
        }
        refWatcher = LeakCanary.install(this);
    }

    public static MyApplication getMyApp(){
        return  mContext;
    }
}
