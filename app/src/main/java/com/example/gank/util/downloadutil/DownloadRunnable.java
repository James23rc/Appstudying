package com.example.gank.util.downloadutil;

import android.util.Log;


import com.example.gank.util.threadPoolUtil.Request;
import com.example.gank.util.threadPoolUtil.ThreadPoolNetUtil;

import java.io.InputStream;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.URL;

public class DownloadRunnable implements Runnable{

    private String url;
    private String method;
    private static int READ_TIME_OUT;
    private static String NAME;
    private static long POSITION;
    private static int CONNECT_TIME_OUT;
    private int threadSize = ThreadPoolNetUtil.CORE_POOL_SIZE;
    private int i;

    public DownloadRunnable(Request request, int i) {
        this.url = request.getUrl();
        this.method = request.getMethod();
        READ_TIME_OUT = Request.getReadTimeOut();
        CONNECT_TIME_OUT = Request.getConnectTimeOut();
        this.i = i;
    }

    @Override
    public void run() {
        try {
            download();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void download() throws Exception {

        final String l = url;
        URL url_ = new URL(l);
        HttpURLConnection conn = (HttpURLConnection) url_.openConnection();
        conn.setConnectTimeout(CONNECT_TIME_OUT);
        conn.setRequestMethod(method);
        //获得需要的文件夹大小
        int fileSize = conn.getContentLength();

        //创建文件夹
        RandomAccessFile randomAccessFile = new RandomAccessFile(NAME+".jpg", "rw");
        randomAccessFile.setLength(fileSize);

        //计算每条线程需要下载的数量
        int threadLength = fileSize % threadSize == 0 ? fileSize / threadSize : fileSize + 1;

        //设置每条线程从哪个位置下载
        int startPoint = i * threadLength;
        //文件从什么时候开始写入
        RandomAccessFile file = new RandomAccessFile(NAME+".jpg", "rw");
        randomAccessFile.seek(startPoint);
//        conn.setRequestProperty("Range", "bytes=" + startPoint + "-");
        if (conn.getResponseCode() == 206) {
            InputStream in = conn.getInputStream();
            byte[] by = new byte[1024];
            int len = -1;
            int length = 0;
            while (length < threadLength && (len = in.read(by)) != -1) {
                file.write(by, 0, len);
                //计算累计下载的长度
                length += len;
            }
            conn.disconnect();
            randomAccessFile.close();
            file.close();
            in.close();
            Log.d("a","下载完成");
        }
    }
}
