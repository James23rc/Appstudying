package com.example.gank.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.gank.R;

import java.util.ArrayList;
import java.util.List;


public class ScendFragment extends Fragment implements View.OnClickListener{
    private TabLayout tabLayout;
    private ViewPager mViewPager;
    private MyFragmentAdapter adapter;
    private List<Fragment> fragments = new ArrayList<>();
    private String[] titles = {"全部","Android","前端","App","拓展资源","iOS","瞎推荐","休息视频","福利"};


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View scendLayout = inflater.inflate(R.layout.scend_fragment, container, false);
        tabLayout = scendLayout.findViewById(R.id.tab_layout);
        mViewPager = scendLayout.findViewById(R.id.view_pager2);
        fragments.add(new AllFragment());
        fragments.add(new AndroidFragment());
        fragments.add(new AheadFragment());
        fragments.add(new AppFragment());
        fragments.add(new ExpandFragment());
        fragments.add(new IosFragment());
        fragments.add(new RecmdFragment());
        fragments.add(new VideoFragent());
        fragments.add(new WelfareFragment());

        adapter = new MyFragmentAdapter(getChildFragmentManager(),fragments,titles);
        mViewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.getTabAt(0).select();
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        return scendLayout;

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

        }

    }

    public class MyFragmentAdapter extends FragmentPagerAdapter {
        public List<Fragment> fragmentList;
        public String[] titles;

        public MyFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList, String[] titles) {
            super(fm);
            this.fragmentList = fragmentList;
            this.titles = titles;
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }
    }
}