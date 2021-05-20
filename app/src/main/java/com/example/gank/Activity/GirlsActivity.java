package com.example.gank.Activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.gank.R;
import com.example.gank.util.StatusUtil;

public class GirlsActivity extends AppCompatActivity {
    private ImageView mImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_girls);
        StatusUtil.setTranslateStatus(this,true);
        mImageView = findViewById(R.id.girls_iamgeview);
        Intent intent = getIntent();
        String data = intent.getStringExtra("url");
        Glide.with(this).load(data).into(mImageView);//加载图片
    }
}
