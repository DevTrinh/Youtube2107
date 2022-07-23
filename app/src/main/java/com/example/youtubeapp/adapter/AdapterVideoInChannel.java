package com.example.youtubeapp.adapter;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubeapp.R;
import com.example.youtubeapp.item.ItemVideoInChannel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterVideoInChannel extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 1;
    private static final int TYPE_LOAD = 2;
    private boolean isLoadingAdd;
    ArrayList<ItemVideoInChannel> list;

    public AdapterVideoInChannel(ArrayList<ItemVideoInChannel> list) {
        this.list = list;
    }

    public void addFooterLoading() {
        isLoadingAdd = true;
        list.add(new ItemVideoInChannel("", "", "", ""));
    }

    public void removeFooterLoading() {
        isLoadingAdd = false;
        int position = list.size() - 1;
        ItemVideoInChannel itemVideoInChannel = list.get(position);
        if (itemVideoInChannel != null) {


        }
        list.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public int getItemViewType(int position) {
        if (list != null && position == list.size() - 1 && isLoadingAdd) {
            return TYPE_LOAD;
        }
        return TYPE_ITEM;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (TYPE_ITEM == viewType) {
            @SuppressLint("InflateParams")
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_videos_channel, parent, false);
            Log.d("TYPE VIDEO: ", TYPE_ITEM + "");
            return new ItemVideoInChannelViewHolder(view);
        } else {
            @SuppressLint("InflateParams")
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_prgessbar, parent, false);
            return new ProgressBarViewHolder(view);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder.getItemViewType() == TYPE_ITEM) {
            ItemVideoInChannel itemVideoInChannel = list.get(position);
            ItemVideoInChannelViewHolder itemVideoInChannelViewHolder = (ItemVideoInChannelViewHolder) holder;
            itemVideoInChannelViewHolder.tvViewCount.setText(itemVideoInChannel.getViewCount());
            itemVideoInChannelViewHolder.tvTitleVideo.setText(itemVideoInChannel.getTitleVideo());
            itemVideoInChannelViewHolder.tvTimeUp.setText(itemVideoInChannel.getTimeUpVideo());
            if (itemVideoInChannel.getUrlImage().trim().length() == 0) {

            } else {
                Picasso.get().load(itemVideoInChannel.getUrlImage()).into(itemVideoInChannelViewHolder.ivVideo);
            }
//
//            Log.d("LINK: ", itemVideoInChannel.getUrlImage()+"");
        }
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class ItemVideoInChannelViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivVideo;
        private TextView tvTitleVideo;
        private TextView tvTimeUp;
        private TextView tvViewCount;

        public ItemVideoInChannelViewHolder(@NonNull View itemView) {
            super(itemView);
            ivVideo = itemView.findViewById(R.id.iv_videos_in_channel);
            tvTitleVideo = itemView.findViewById(R.id.tv_title_video_channel);
            tvTimeUp = itemView.findViewById(R.id.tv_time_up);
            tvViewCount = itemView.findViewById(R.id.tv_amount_view);
        }
    }

    public class ProgressBarViewHolder extends RecyclerView.ViewHolder {
        private ProgressBar progressBar;

        public ProgressBarViewHolder(@NonNull View itemView) {
            super(itemView);
            progressBar = itemView.findViewById(R.id.pb_only_load);
        }

    }
}
