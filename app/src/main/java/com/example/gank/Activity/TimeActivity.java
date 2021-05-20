package com.example.gank.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import com.example.gank.R;
import com.example.gank.bean.NewsJsonBean;


import java.util.ArrayList;
import java.util.List;

public class TimeActivity extends AppCompatActivity {
    private List<NewsJsonBean> newsBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time);
        ListView listView7 =findViewById(R.id.lv_main7);
    }
}
