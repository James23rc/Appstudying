package com.example.gank.Activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.gank.R;
import com.example.gank.adapter.LaunchImproveAdapter;
import com.example.gank.util.StatusUtil;


public class WelcomeActivity extends AppCompatActivity {
    private int[] lanuchImageArray = {R.drawable.schoolpic0,
            R.drawable.schoolpic1, R.drawable.schoolpic2, R.drawable.schoolpic3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        StatusUtil.setTranslateStatus(this,true);
        ViewPager vp_launch = findViewById(R.id.vp_launch);
        // 构建一个引导页面的碎片翻页适配器
        LaunchImproveAdapter adapter = new LaunchImproveAdapter(getSupportFragmentManager(), lanuchImageArray);//
        // 给vp_launch设置引导页面适配器
        vp_launch.setAdapter(adapter);
        // 设置vp_launch默认显示第一个页面
        vp_launch.setCurrentItem(0);
    }
}
