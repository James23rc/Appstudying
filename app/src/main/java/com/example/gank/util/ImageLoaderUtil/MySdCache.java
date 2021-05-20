package com.example.gank.util.ImageLoaderUtil;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.gank.util.CloseUtil;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MySdCache implements ImageCache{
    static String cacheDir = "sdcard/cache";
    public Bitmap get(String url){
        return BitmapFactory.decodeFile(cacheDir);
    }

    @Override
    public Bitmap get(String url, int reqWidth, int reqHeight) throws IOException {
        return get(url);
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        putCache(url, bitmap);
    }

    public void putCache(String url, Bitmap bitmap){
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(cacheDir + url);
            bitmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }finally {
            {
                CloseUtil.close(fileOutputStream);
            }
        }
    }
}
