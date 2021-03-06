package com.example.gank.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ListView;

public class WrapContentListView extends ListView {
    public WrapContentListView(Context context) {
        super(context);
    }
    public WrapContentListView(Context context, AttributeSet attrs, int defStyleAttr){
        super(context,attrs,defStyleAttr);
    }
    public WrapContentListView(Context context, AttributeSet attrs){
        super(context,attrs);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec,expandSpec);
    }

    @Override
    public void setOnItemClickListener(@Nullable OnItemClickListener listener) {
        super.setOnItemClickListener(listener);
    }
}
