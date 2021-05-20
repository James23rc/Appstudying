package com.example.gank.base;

public class MessageEvent {
    private int message;
    public MessageEvent(int message){
     this.message = message;
    }

    public int getMessage() {
        return message;
    }

    public void setMessage(int message) {
        this.message = message;
    }
}
