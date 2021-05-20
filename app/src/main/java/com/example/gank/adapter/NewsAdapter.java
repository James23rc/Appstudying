package com.example.gank.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.gank.R;
import com.example.gank.bean.NewsBean;


import java.util.List;


public class NewsAdapter extends ArrayAdapter<NewsBean> {

    private int resourceID;

    public NewsAdapter(Context context, int textViewResourceID, List<NewsBean> objects){
        super(context,textViewResourceID,objects);
        resourceID=textViewResourceID;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        NewsBean newsBean = getItem(position);
        View view = LayoutInflater.from(getContext()).inflate(resourceID,parent,false);
        // ImageView imageView = (ImageView)view.findViewById(R.id.iv_icon);
        TextView textView=(TextView)view.findViewById(R.id.tv_title);
        TextView textView1=(TextView)view.findViewById(R.id.tv_content);
        TextView textView2 = (TextView)view.findViewById(R.id.tv_time);
        textView.setText(newsBean.getNewsTitle());
        textView1.setText(newsBean.getNewsContent());
        textView2.setText(newsBean.getNewsTime());

        return view;
    }
}
