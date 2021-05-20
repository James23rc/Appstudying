package com.example.gank.util.ImageLoaderUtil;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;

import java.io.IOException;

public class MyLruCache implements ImageCache{

    LruCache<String,Bitmap> bitmapLruCache;

    public MyLruCache(){
        initImageLoader();
    }

    private void initImageLoader(){
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory()) / 1024;
        final int cacheSize = maxMemory / 6;
        bitmapLruCache = new LruCache<String, Bitmap>(cacheSize){
            @Override
            protected int sizeOf(@NonNull String key, @NonNull Bitmap value) {
                return value.getRowBytes() * value.getHeight() / 1024;
            }
        };
    }

    public  void putBitmapToMemoryCache(String url,Bitmap bitmap){
        bitmapLruCache.put(url, bitmap);
    }

    public  Bitmap getBitmapFormMemoryCache(String url){
        return bitmapLruCache.get(url);
    }

    @Override
    public Bitmap get(String url, int reqWidth, int reqHeight) throws IOException {
        return getBitmapFormMemoryCache(url);
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        put(url, bitmap);
    }
}
