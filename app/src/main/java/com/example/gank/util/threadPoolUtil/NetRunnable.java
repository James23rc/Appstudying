package com.example.gank.util.threadPoolUtil;

import com.example.gank.util.CloseUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;

class NetRunnable implements Runnable {
    private String url;
    private String method;
    private static final String ENCODE = "UTF-8";
    private static int READ_TIME_OUT;
    private static int CONNECT_TIME_OUT;
    private Request.Callback<String> callback;
    private String postParams;

    public NetRunnable(Request request){
        this.url = request.getUrl();
        this.method = request.getMethod();
        READ_TIME_OUT = Request.getReadTimeOut();
        CONNECT_TIME_OUT = Request.getConnectTimeOut();
        this.callback = request.getCallback();
        this.postParams = request.getParams();
    }
    @Override
    public void run() {
        if (url != null){
            if (method.equals("GET")){
                get();
            }else if (method.equals("POST")){
                post(postParams);
            }
        }
    }
    private void get(){
        final String _url = url;
        URL url = null;
        try {
            url = new URL(_url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            callback.onError(e.getMessage());
            return;
        }
        BufferedReader bufferedReader = null;
        StringBuffer response = new StringBuffer();
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setReadTimeout(READ_TIME_OUT);
            urlConnection.setConnectTimeout(CONNECT_TIME_OUT);
            urlConnection.setRequestMethod(method);
            urlConnection.setUseCaches(false);
            urlConnection.setRequestProperty("connection", "close");
            urlConnection.connect();
            int code = urlConnection.getResponseCode();
            if (code >= 200 && code < 400) {
                bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), ENCODE));
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
                callback.onSuccess(response.toString());
            } else {
                bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getErrorStream(), ENCODE));
                String line = null;
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }
                callback.onError(response.toString());
            }
        } catch (SocketTimeoutException e) {
            e.printStackTrace();
            callback.onError(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            callback.onError(e.getMessage());
        } finally {
            CloseUtil.close(bufferedReader);
        }
    }

    private void post(String params){
        BufferedReader bufferedReader = null;
        try {
            URL httpUrl = new URL(url);
            HttpURLConnection httpURLConnection = (HttpURLConnection) httpUrl.openConnection();
            //设置请求头header
            //httpURLConnection.setRequestProperty("test-header","post-header-value");
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setReadTimeout(READ_TIME_OUT);
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setConnectTimeout(CONNECT_TIME_OUT);
            httpURLConnection.setUseCaches(false);
            httpURLConnection.connect();

            //设置请求参数
            OutputStream outputStream = httpURLConnection.getOutputStream();
//          String params="name="+ URLEncoder.encode(name, "utf-8")+"&password="+ URLEncoder.encode(password, "utf-8");
            outputStream.write(params.getBytes());

            //获取内容
            InputStream inputStream = httpURLConnection.getInputStream();
            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            final StringBuffer stringBuffer = new StringBuffer();
            String line = null;
            int statusCode = httpURLConnection.getResponseCode();
            if (statusCode >= 200 && statusCode < 400){
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
                callback.onSuccess(stringBuffer.toString());
            }else{
                bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream(), ENCODE));
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuffer.append(line);
                }
                callback.onError(stringBuffer.toString());
            }

        } catch (Exception e) {

        } finally {
            CloseUtil.close(bufferedReader);
        }

    }
}
