package com.example.gank.util.ImageLoaderUtil;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.StatFs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MyDiskLruCache implements ImageCache{
    private static final long DISK_CACHE_SIZE = 1024 * 1024 * 80;
    private static final int IO_BUFFER_SIZE = 8 * 1024;
    private static final int DISK_CACHE_INDEX = 0;
    public static boolean mIsDiskLruCacheCreated = false;
    private Context mContext;
    private static DiskLruCache mDiskLruCache;
    private static BitmapLoader bitmapLoader;
    private MyLruCache lruCache;

    public MyDiskLruCache(Context context){
        this.mContext = context.getApplicationContext();
        initDiskLruCache();
        lruCache = new MyLruCache();
        bitmapLoader = new BitmapLoader();
    }

    //初始化 使用open方法创建吗DiskLruCache对象
    private void initDiskLruCache(){
    File diskCacheDir = getDiskCacheDir(mContext, "bitmap");
        if (!diskCacheDir.exists()) {
        diskCacheDir.mkdirs();
    }
        if (getUsableSpace(diskCacheDir) > DISK_CACHE_SIZE) {
        try {
            mDiskLruCache = DiskLruCache.open(diskCacheDir, 1, 1,
                    DISK_CACHE_SIZE);
            mIsDiskLruCacheCreated = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
      }
    }


    @Override
    public Bitmap get(String url, int reqWidth, int reqHeight) throws IOException {
        return getFormDiskLruCache(url, reqWidth, reqHeight);
    }

    @Override
    public void put(String url, Bitmap bitmap) {
        addDiskLruCache(url);
    }


    //添加缓存到DiskLruCache中
    public  void addDiskLruCache(String url){
        String key = hashKeyFormUrl(url);
        try {
            DiskLruCache.Editor editor = mDiskLruCache.edit(key);
            if (editor != null){
                OutputStream outputStream = editor.newOutputStream(DISK_CACHE_INDEX);
                if (downloadUrlToStream(url,outputStream)){
                    editor.commit();
                }else{
                    editor.abort();
                }
            }
            mDiskLruCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //从缓存中读取

    public  Bitmap getFormDiskLruCache(String url,int reqWidth,int reqHeight) throws IOException{
        if (Looper.myLooper() == Looper.getMainLooper()) {
            return null;
        }
        if (mDiskLruCache == null) {
            return null;
        }

        Bitmap bitmap = null;
        String key = hashKeyFormUrl(url);

            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
            if (snapshot != null){
                FileInputStream fileInputStream = (FileInputStream) snapshot
                        .getInputStream(DISK_CACHE_INDEX);

                FileDescriptor fileDescriptor = fileInputStream.getFD();
                bitmap = bitmapLoader.
                        decodeSampledBitmapFromFileDescriptor(
                                fileDescriptor,reqWidth,reqHeight);

                if (bitmap != null){
                    lruCache.putBitmapToMemoryCache(url,bitmap);
                }
            }

        return bitmap;
    }

    public Bitmap downloadBitmapFromHttp(String url,int reqWeith,int reqHeight)throws IOException{
        if (Looper.myLooper() == Looper.getMainLooper()) {
            return null;
        }
        if (mDiskLruCache == null) {
            return null;
        }
        String key = hashKeyFormUrl(url);
        DiskLruCache.Editor editor = mDiskLruCache.edit(key);
        if (editor != null){
            OutputStream outputStream = editor.newOutputStream(DISK_CACHE_INDEX);
            if (downloadUrlToStream(url,outputStream)){
                editor.commit();
            }else{
                editor.abort();
            }
            mDiskLruCache.flush();
        }
        return getFormDiskLruCache(url, reqWeith, reqHeight);

    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    private long getUsableSpace(File path) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return path.getUsableSpace();
        }
        final StatFs stats = new StatFs(path.getPath());
        return (long) stats.getBlockSize() * (long) stats.getAvailableBlocks();
    }


    private File getDiskCacheDir(Context context, String uniqueName) {
        boolean externalStorageAvailable = Environment
                .getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
        final String cachePath;
        if (externalStorageAvailable) {
            cachePath = context.getExternalCacheDir().getPath();
        } else {
            cachePath = context.getCacheDir().getPath();
        }

        return new File(cachePath + File.separator + uniqueName);
    }
    //将url转换成md5的格式的key

    private static String hashKeyFormUrl(String url) {
        String cacheKey;
        try {
            final MessageDigest mDigest = MessageDigest.getInstance("MD5");
            mDigest.update(url.getBytes());
            cacheKey = bytesToHexString(mDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            cacheKey = String.valueOf(url.hashCode());
        }
        return cacheKey;
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    private static boolean downloadUrlToStream(String urlString,
                                               OutputStream outputStream) {
        HttpURLConnection urlConnection = null;
        BufferedOutputStream out = null;
        BufferedInputStream in = null;

        try {
            final URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(urlConnection.getInputStream(),
                    IO_BUFFER_SIZE);
            out = new BufferedOutputStream(outputStream, IO_BUFFER_SIZE);

            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            return true;
        } catch (IOException e) {

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            try {
                if (out != null) out.close();
                if (in != null) in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
