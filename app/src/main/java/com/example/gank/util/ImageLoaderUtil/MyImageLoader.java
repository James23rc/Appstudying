package com.example.gank.util.ImageLoaderUtil;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Build.VERSION_CODES;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StatFs;
import android.widget.ImageView;

import com.example.gank.R;
import com.example.gank.util.CloseUtil;
import com.example.gank.util.threadPoolUtil.ThreadPoolNetUtil;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyImageLoader {

    public static final int MESSAGE_POST_RESULT = 1;
    private static final int TAG_KEY_URI = R.id.image_uri;
    private static final int IO_BUFFER_SIZE = 8 * 1024;
    private MyLruCache lruCache;
    private MyDiskLruCache diskLruCache;

    private Context mContext;

    @SuppressLint("CI_StaticFieldLeak")
    private static volatile MyImageLoader instance;

    private MyImageLoader(Context context) {
        mContext = context.getApplicationContext();
        lruCache = new MyLruCache();
        diskLruCache = new MyDiskLruCache(mContext);
    }

    //单例获取MyImageLoader对象
    public static MyImageLoader getInstance(Context context) {
        if (instance == null) {
            synchronized (MyImageLoader.class) {
                if (instance == null) {
                    instance = new MyImageLoader(context);
                }
            }
        }
        return instance;
    }

    //使用handler进行主线程更新UI
    private Handler mMainHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            MyImageLoader.LoaderResult result = (MyImageLoader.LoaderResult) msg.obj;
            ImageView imageView = result.imageView;
            imageView.setImageBitmap(result.bitmap);
            imageView.setTag(TAG_KEY_URI,true);
        }
    };

    //异步加载Bitmap
    public void asyncLoadBitmap(final String uri, final ImageView imageView,
                                final int reqWidth, final int reqHeight) {
        imageView.setTag(TAG_KEY_URI, uri);
        Bitmap bitmap = lruCache.getBitmapFormMemoryCache(uri);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
            return;
        }

        Runnable loadBitmapTask = new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = loadBitmap(uri, reqWidth, reqHeight);
                if (bitmap != null && uri.equals(imageView.getTag(TAG_KEY_URI))) {
                    //下载bitmap然后交给handler更新UI
                    MyImageLoader.LoaderResult result = new MyImageLoader.LoaderResult(imageView, uri, bitmap);
                    mMainHandler.obtainMessage(MESSAGE_POST_RESULT, result).sendToTarget();
                }
            }
        };
        //线程池工具类直接调用线程池默认处理方法
        ThreadPoolNetUtil.defaultSubmit(loadBitmapTask);
    }

    //同步加载Bitmap
    public Bitmap loadBitmap(String uri, int reqWidth, int reqHeight) {
        Bitmap bitmap = lruCache.getBitmapFormMemoryCache(uri);
        if (bitmap != null) {
            return bitmap;
        }
        try {
            bitmap = diskLruCache.downloadBitmapFromHttp(uri, reqWidth, reqHeight);
            if (bitmap != null) {
                return bitmap;
            }
            bitmap = diskLruCache.getFormDiskLruCache(uri, reqWidth, reqHeight);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (bitmap == null && !MyDiskLruCache.mIsDiskLruCacheCreated) {
            bitmap = downloadBitmapFromUrl(uri);
        }
        return bitmap;
    }

    private Bitmap downloadBitmapFromUrl(String urlString) {
        Bitmap bitmap = null;
        HttpURLConnection urlConnection = null;
        BufferedInputStream in = null;

        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(),
                    IO_BUFFER_SIZE);
            bitmap = BitmapFactory.decodeStream(in);
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            CloseUtil.close(in);
        }
        return bitmap;
    }

    @SuppressLint("UsableSpace")
    private long getUsableSpace(File path) {
        return path.getUsableSpace();
    }

    private static class LoaderResult {
        public ImageView imageView;
        public String uri;
        public Bitmap bitmap;

        public LoaderResult(ImageView imageView, String uri, Bitmap bitmap) {
            this.imageView = imageView;
            this.uri = uri;
            this.bitmap = bitmap;
        }
    }

}

