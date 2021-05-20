package com.example.gank.util;

import java.io.Closeable;
import java.io.IOException;

public final class CloseUtil {
    private CloseUtil(){}

    public static void close(Closeable closeable){
        if (null != closeable){
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
