package com.example.gank.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gank.Activity.MyApplication;
import com.example.gank.R;
import com.example.gank.bean.GankBean;
import com.example.gank.util.ImageLoaderUtil.MyImageLoader;
import com.example.gank.util.NetStateUtil;

import java.util.List;

public class GankAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<GankBean.Results> mAndroidItems;
    private OnItemClickListener1 mOnItemClickListener;
    private GankBean.Results contact;
    private Context mContext;
    private int itemType;
    private MyImageLoader mImageLoader;
    public boolean mCanGetBitmapFromNetWork;
    public boolean isDisplayed;
    private Drawable mDefaultBitmapDrawable;
    public boolean mIsGridViewIdle = true;

    private static int Main_Picture_Item =1;
    private int Last_Item_state = 100;
    private  int Main_NoPicture_Item = 2;

    public void setOnItemClickListener(OnItemClickListener1 onItemClickListener1) {
        this.mOnItemClickListener = onItemClickListener1;
    }


    public GankAdapter(List<GankBean.Results> contacts, Context context, int itemType) {
        this.mAndroidItems = contacts;
        this.mContext = context;
        this.itemType = itemType;
        mDefaultBitmapDrawable = context.getResources().getDrawable(R.drawable.no_banner);
        mImageLoader = MyImageLoader.getInstance(context);
        mCanGetBitmapFromNetWork = NetStateUtil.isNetworkConnected(MyApplication.getMyApp());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Last_Item_state) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_footer_view, parent, false);
            return new LastViewHolder(view);
        }else if (viewType == Main_Picture_Item){
            //常规item的类型设置 带有图片
            View view = LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.type_item, parent, false);
                            return new ContactViewHolder(view);
        }else{
            //常规类型 无图片
            if (itemType == 1){
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_nopicture, parent, false);
                return new NoPictureHolder(view);
            }else if (itemType == 2){
                //休息视频的item类型设置
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_video,parent,false);
                return new VideoHolder(view);
            }
            //大量图片展示的item类型设置
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_girls_card, parent, false);
            return new WelfareHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof LastViewHolder) {
            ((LastViewHolder) holder).tvBottom.setText("上拉加载更多...");
        } else {
            loadData(holder,position);
        }
    }
    private void loadData(final RecyclerView.ViewHolder holder, final int position){
        contact = mAndroidItems.get(position);
        if (holder instanceof ContactViewHolder){
            final ContactViewHolder holder1 = (ContactViewHolder) holder;
            final String tag = (String) holder1.imageView.getTag();
            //标题、作者、事件展示内容
            holder1.titleText.setText(mAndroidItems.get(position).getDesc());
            holder1.writerText.setText(mAndroidItems.get(position).getWho());
            holder1.dataText.setText(mAndroidItems.get(position).getPublishedAt());
            //如果有图片 加载图片
             final String uri = mAndroidItems.get(position).images[0];
            if (!uri.equals(tag)){
                holder1.imageView.setImageDrawable(mDefaultBitmapDrawable);

            }
            if (mIsGridViewIdle && mCanGetBitmapFromNetWork) {
                holder1.imageView.setTag(uri);
                mImageLoader.asyncLoadBitmap(uri, holder1.imageView, 80, 100);
            }


            clickHandler(holder1.itemView,holder1,position);
        }
        else if (holder instanceof NoPictureHolder) {
            final NoPictureHolder noPictureHolder = (NoPictureHolder) holder;
            noPictureHolder.titleText.setText(mAndroidItems.get(position).getDesc());
            noPictureHolder.dataText.setText(mAndroidItems.get(position).getPublishedAt());
            noPictureHolder.writerText.setText(mAndroidItems.get(position).getWho());
            clickHandler(noPictureHolder.click,noPictureHolder,position);
        }else if (holder instanceof VideoHolder){
            final VideoHolder holder2 = (VideoHolder) holder;
            holder2.titleTV.setText(mAndroidItems.get(position).getDesc());
            holder2.writerTV.setText(mAndroidItems.get(position).getWho());
            holder2.timeTV.setText(mAndroidItems.get(position).getPublishedAt());
            clickHandler(holder2.click,holder2,position);
        }else{

            final WelfareHolder holder3 = (WelfareHolder) holder;
            final String uri = mAndroidItems.get(position).getUrl();
            final String tag = (String) holder3.girlImageView.getTag();
            if (!uri.equals(tag)){
                holder3.girlImageView.setImageDrawable(mDefaultBitmapDrawable);
                isDisplayed = false;
            }
            if (mIsGridViewIdle && mCanGetBitmapFromNetWork) {
                holder3.girlImageView.setTag(uri);
                mImageLoader.asyncLoadBitmap(uri, holder3.girlImageView, 160, 280);
                isDisplayed = true;
            }
            clickHandler(holder3.girlImageView,holder3,position);
        }
    }
    private void clickHandler( View clickview, final RecyclerView.ViewHolder holder, final int position){
        final boolean islike[] = {false};
        if (mOnItemClickListener != null) {
            if (clickview != null) {
                clickview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        //点击事件监听接口
                        mOnItemClickListener.onItemClick(holder.itemView, position,
                                contact.getDesc());
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {
        return mAndroidItems.size()+1;
    }

    @Override
    public int getItemViewType(int position) {
        if (position+1 == getItemCount()){
            return Last_Item_state;
        } else if (mAndroidItems.get(position).getImages() == null){
            return Main_NoPicture_Item;
        } else {
            return Main_Picture_Item;
        }
    }

    public static class ContactViewHolder extends RecyclerView.ViewHolder {
        View mView;
        TextView titleText;
        TextView writerText;
        TextView dataText;
        ImageView imageView;

        public ContactViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            titleText = itemView.findViewById(R.id.scend_item_title);
            writerText = itemView.findViewById(R.id.scend_item_writer);
            dataText = itemView.findViewById(R.id.scend_item_data);
            imageView = itemView.findViewById(R.id.scend_item_imageview);
        }
    }

    public static class LastViewHolder extends RecyclerView.ViewHolder {
        TextView tvBottom;
        ProgressBar mProgressBar;
        public LastViewHolder(View itemView) {
            super(itemView);
            mProgressBar = itemView.findViewById(R.id.progressbar);
            tvBottom = itemView.findViewById(R.id.tvBottom);

        }
    }
    public static class VideoHolder extends RecyclerView.ViewHolder{
        TextView titleTV;
        TextView writerTV;
        TextView timeTV;
        View click;

        public VideoHolder(@NonNull View itemView) {
            super(itemView);
            click = itemView.findViewById(R.id.video_click);
            titleTV = itemView.findViewById(R.id.video_item_title);
            writerTV = itemView.findViewById(R.id.video_item_writer);
            timeTV = itemView.findViewById(R.id.video_item_data);
        }
    }
    public static class WelfareHolder extends RecyclerView.ViewHolder{
        ImageView girlImageView;

        public WelfareHolder(@NonNull View itemView) {
            super(itemView);
            girlImageView = itemView.findViewById(R.id.item__card_girls);
        }
    }
    public static class NoPictureHolder extends RecyclerView.ViewHolder{
        TextView titleText;
        TextView writerText;
        TextView dataText;
        View click;

        public NoPictureHolder(@NonNull View itemView) {
            super(itemView);
            titleText = itemView.findViewById(R.id.nopicture_item_title);
            writerText = itemView.findViewById(R.id.nopicture_item_writer);
            dataText = itemView.findViewById(R.id.nopicture_item_time);
            click = itemView.findViewById(R.id.nopicture_click_item);
        }
    }
}

