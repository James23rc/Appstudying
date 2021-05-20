package com.example.gank.Activity;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.gank.R;
import com.example.gank.fragment.FirstFragment;
import com.example.gank.fragment.LastFragment;
import com.example.gank.fragment.ScendFragment;
import com.example.gank.util.StatusUtil;

import static android.net.ConnectivityManager.TYPE_WIFI;
import static android.provider.ContactsContract.CommonDataKinds.Email.TYPE_MOBILE;

public class CenterActivity extends AppCompatActivity implements View.OnClickListener {
    private FragmentAdapter mAdapter;
    private TabLayout tabLayout;
    private NavigationView navigationView;
    private ViewPager mViewPager;
    private DrawerLayout drawerLayout;
    private IntentFilter intentFilter;
    private NetworkChangeReceiver networkChangeReceiver;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);//活动中心的布局
        init();
        StatusUtil.setStatusBarColor(this, Color.RED);//沉浸式状态栏 红色
        mViewPager.setOffscreenPageLimit(3);//ViewPager的缓存为3帧  setOffscreenPageLimit预加载页面数量
        mViewPager.setCurrentItem(1,true);
        mAdapter = new FragmentAdapter(getSupportFragmentManager());//
        mViewPager.setAdapter(mAdapter);

        navigationView.setNavigationItemSelectedListener(new NavigationView.//侧拉菜单的实现
                OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_developer:
                    case R.id.nav_like:
                    case R.id.nav_money:
                        if (drawerLayout.isDrawerOpen(Gravity.START)){
                                drawerLayout.closeDrawers();
                        }
                        break;
                    case R.id.nav_unlogin:
                        Intent intent = new Intent(CenterActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                        break;
                }
                return true;
            }
        });

        tabLayout.addTab(tabLayout.newTab().setText("搜索").setIcon(R.drawable.search));//??
        tabLayout.addTab(tabLayout.newTab().setText("首页").setIcon(R.drawable.center));
        tabLayout.addTab(tabLayout.newTab().setText("设置").setIcon(R.drawable.setting));
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

        //监听网络变化
        intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        networkChangeReceiver = new NetworkChangeReceiver();
        registerReceiver(networkChangeReceiver,intentFilter);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(networkChangeReceiver);
    }

    @SuppressLint("WrongViewCast")
    private void init(){
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_window);
        tabLayout = findViewById(R.id.tab_layout);
        mViewPager = findViewById(R.id.view_pager);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){

        }
    }

    class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isAvailable()){
                switch (networkInfo.getType()) {
                    case TYPE_MOBILE:
                        Toast.makeText(context, "正在使用2G/3G/4G网络", Toast.LENGTH_SHORT).show();
                        break;
                    case TYPE_WIFI:
                        Toast.makeText(context, "正在使用WiFi网络", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }else {
                Toast.makeText(context,"网络不可用", Toast.LENGTH_SHORT).show();
            }

        }
    }
    //adapter data与ui的桥梁 数据与前端 首页滑动显示
    public class FragmentAdapter extends FragmentPagerAdapter {
        private String[] mBlowView = new String[]{"首页", "知识体系", "项目"};

        public FragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 2){
                return new LastFragment();
            }else if (position == 1){
                return new ScendFragment();
            }
            return new FirstFragment();
        }
        @Override
        public int getCount() {
            return mBlowView.length;
        }//问帆哥作用？？

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return mBlowView[position];
        }//问帆哥作用？？
    }



}
