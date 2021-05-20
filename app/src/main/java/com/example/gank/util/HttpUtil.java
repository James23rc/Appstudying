package com.example.gank.util;

import com.example.gank.net.OkHttpManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class HttpUtil {
    public static void sendOkHttpRequest(String address, Callback callback) {
        Request request = new Request.Builder().url(address).build();
        OkHttpManager.getOkHttp().newCall(request).enqueue(callback);
    }
}
