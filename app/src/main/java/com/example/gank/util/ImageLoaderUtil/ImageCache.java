package com.example.gank.util.ImageLoaderUtil;

import android.graphics.Bitmap;

import java.io.IOException;

public interface ImageCache {
    Bitmap get(String url, int reqWidth, int reqHeight) throws IOException;
    void put(String url, Bitmap bitmap);
}
