package com.example.gank.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.gank.R;
import com.example.gank.bean.GankBean;
import com.example.gank.videoplayer.VideoPlayer;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class VideoPlayListAdatper extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<GankBean.Results> mAndroidItems;
    private OnItemClickListener1 mOnItemClickListener;
    private Context mContect;

    public  int currentPosition = 1;
    private static int Main_Item=1;
    private int Last_Item_state = 100;

    public void setOnItemClickListener(OnItemClickListener1 onItemClickListener1) {
        this.mOnItemClickListener = onItemClickListener1;
    }

    public VideoPlayListAdatper(List<GankBean.Results> contacts, Context context) {
        this.mAndroidItems = contacts;
        this.mContect = context;

    }
    public void setPlayPosition(int position) {
        currentPosition = position;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == Last_Item_state) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_footer_view, parent, false);
            return new VideoPlayListAdatper.LastViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_video_play, parent, false);
            return new VideoPlayListAdatper.VideoViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof VideoPlayListAdatper.LastViewHolder) {
            ((VideoPlayListAdatper.LastViewHolder) holder).tvBottom.setText("上拉加载更多...");

        } else {
            final VideoPlayListAdatper.VideoViewHolder holder1 = (VideoPlayListAdatper.VideoViewHolder) holder;
            //获取到条目对应的数据
            GankBean.Results info = mAndroidItems.get(position);
            //传递给条目里面的MyVideoPlayer
            holder1.videoPlayer.setPlayData(info);
            //把条目的下标传递给MyVideoMediaController对象
            holder1.videoPlayer.mediaController.setPosition(position);
            //把Adapter对象传递给MyVideoMediaController对象
            holder1.videoPlayer.mediaController.setAdapter(this);
            if(position != currentPosition){
                //设置为初始化状态
                holder1.videoPlayer.initViewDisplay();
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
        }else {
            return Main_Item;
        }
    }


    public static class VideoViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.iv_bg)
        ImageView ivBg;
        @BindView(R.id.videoPlayer)
        VideoPlayer videoPlayer;
        @BindView(R.id.iv_author)
        ImageView ivAuthor;
        @BindView(R.id.tv_author_name)
        TextView tvAuthorName;
        @BindView(R.id.tv_play_count)
        TextView tvPlayCount;
        @BindView(R.id.iv_comment)
        ImageView ivComment;
        @BindView(R.id.tv_comment_count)
        TextView tvCommentCount;
        @BindView(R.id.iv_comment_more)
        ImageView ivCommentMore;

        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

        }
    }

    public class LastViewHolder extends RecyclerView.ViewHolder {
        TextView tvBottom;
        ProgressBar mProgressBar;
        public LastViewHolder(View itemView) {
            super(itemView);
            mProgressBar = itemView.findViewById(R.id.progressbar);
            tvBottom = itemView.findViewById(R.id.tvBottom);

        }
    }
}
