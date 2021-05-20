package com.example.gank.util.threadPoolUtil;

public class Request {
    private String url;
    private String method;
    private static final String ENCODE = "UTF-8";
    private static int READ_TIME_OUT;
    private static int CONNECT_TIME_OUT;
    private int poolSize;
    private Callback<String> callback;
    private String params;

    public void setCallback(Callback<String> callback) {
        this.callback = callback;
    }

    public Request(Builder builder){
        this.url = builder.url;
        this.method = builder.method;
        READ_TIME_OUT = builder.readTime;
        CONNECT_TIME_OUT = builder.connectTime;
        this.params = builder.params;
        this.poolSize = builder.poolSize;
    }

    public int getPoolSize() {
        return poolSize;
    }

    public String getParams() {
        return params;
    }

    public String getUrl() {
        return url;
    }

    public Callback<String> getCallback() {
        return callback;
    }

    public static int getConnectTimeOut() {
        return CONNECT_TIME_OUT;
    }

    public static int getReadTimeOut() {
        return READ_TIME_OUT;
    }

    public static String getENCODE() {
        return ENCODE;
    }

    public String getMethod() {
        return method;
    }

    public interface Callback<T> {
        void onSuccess(T response);
        void onError(T error);
    }

    public static class Builder{
        private String params;
        private String method;
        private String url;
        private int poolSize = 8;
        private int readTime = 8 * 1000;
        private int connectTime = 8 * 1000;
        public Builder(String method,String url){
            this.url = url;
            this.method = method;
        }
        public Builder setPoolZise(int poolSize){
            this.poolSize = poolSize;
            return this;
        }
        public Builder setReadTimeOut(int time){
            this.readTime = time;
            return this;
        }
        public Builder setConnectionTime(int time){
            this.connectTime = time;
            return this;
        }
        public Builder setPostParams(String params){
            this.params = params;
            return this;
        }
        public Request build(){
            return new Request(this);
        }
    }
}

