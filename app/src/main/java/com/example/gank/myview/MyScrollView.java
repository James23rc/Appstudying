package com.example.gank.myview;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

public class MyScrollView extends ScrollView {

    private CustomizedListener mListener;

    //顶部放大View
    private View mHeaderView;

    //第一次下拉
    private boolean mIsPulling = false;

    private int mLastY;

    //头View的宽、高
    private int mHeaderWidth, mHeaderHeight;

    //时间比
    private static final int TIME_RATIO = 2;

    //距离比
    private static final float SCALE_RATIO = 0.1f;

    //最大放大倍数
    private static final float MAX_SCALE_TIMES = 1.5f;

    public static final int REFRESH_DISTANCE = 45;

    private boolean mCanScroll = true;

    public MyScrollView(@NonNull Context context) {
        super(context);
    }

    public MyScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        View view = getChildAt(0);
        if (getHeight() + getScrollY() == view.getHeight()) {
            mCanScroll = false;
            mListener.upSlide(!mCanScroll, getScrollY());
        } else {
            mCanScroll = true;
            mListener.upSlide(!mCanScroll, getScrollY());
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //此处确保View已经绘制出来了
        mHeaderWidth = mHeaderView.getMeasuredWidth();
        mHeaderHeight = mHeaderView.getMeasuredHeight();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        //设置不可过度滚动
        setOverScrollMode(OVER_SCROLL_NEVER);
        View child = getChildAt(0);
        if (child instanceof ViewGroup) {
            //获取默认第一个子View
            mHeaderView = ((ViewGroup) child).getChildAt(0);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (mHeaderView == null)
            return super.onTouchEvent(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                //第一次move记录位置
                if (!mIsPulling) {
                    if (getScrollY() == 0) {
                        mLastY = (int) ev.getY();
                    } else {
                        break;
                    }
                }
                //往上滑动时
                if (ev.getY() - mLastY < 0) {
                    return super.onTouchEvent(ev);
                }
                int distance = (int) ((ev.getY() - mLastY) * SCALE_RATIO);
                mIsPulling = true;
                setZoom(distance);
                return true;
            case MotionEvent.ACTION_UP:
                mIsPulling = false;
                replyView();
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     * 设置HeadView的缩放，（放大、缩小均通过此方法）
     */
    private void setZoom(float s) {
        float scaleTimes = (float) ((mHeaderWidth + s) / (mHeaderWidth * 1.0));
        //如超过最大放大倍数，直接返回
        if (scaleTimes > MAX_SCALE_TIMES) return;

        ViewGroup.LayoutParams layoutParams = mHeaderView.getLayoutParams();
        layoutParams.width = (int) (mHeaderWidth + s);
        layoutParams.height = (int) (mHeaderHeight * (mHeaderWidth + s) / mHeaderWidth);
        //设置控件水平居中
//         ((MarginLayoutParams) layoutParams).setMargins(
//        -(layoutParams.width - mHeaderWidth) / 2, 0, 0, 0);
        mHeaderView.setLayoutParams(layoutParams);
    }

    /**
     * 回弹
     */
    private void replyView() {
        final float distance = mHeaderView.getMeasuredWidth() - mHeaderWidth;

        if (distance >= REFRESH_DISTANCE && mListener != null) {
            mListener.refresh();
        }

        // 设置动画
        ValueAnimator anim = ObjectAnimator.ofFloat(distance, 0.0F)
                .setDuration((long) (distance * TIME_RATIO));
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                setZoom((Float) animation.getAnimatedValue());
            }
        });
        anim.start();
    }

    public interface CustomizedListener {
         void upSlide(boolean arriveBottom, int scrollY);
         void refresh();
    }

    public void setHeaderView(View v) {
        mHeaderView = v;
    }

    public void setCustomizedListener(CustomizedListener listener) {
        mListener = listener;
    }
}
