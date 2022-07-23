package com.example.youtubeapp.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubeapp.R;
import com.example.youtubeapp.item.ItemListVideoInChannel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class AdapterListVideoInChannel extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<ItemListVideoInChannel> list = new ArrayList<>();
    private static final int VIEW_LIST = 1;
    private static final int VIEW_LOAD = 2;

    public AdapterListVideoInChannel(List<ItemListVideoInChannel> list) {
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.size() - 1 == position) {
            return VIEW_LOAD;
        } else {
            return VIEW_LIST;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == VIEW_LIST) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_channel, null);
            return new ListVideoChannelViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view_arrow_down, null);
            return new LastVideoChannelViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ItemListVideoInChannel itemListVideoInChannel = list.get(position);
        ListVideoChannelViewHolder listVideoChannelViewHolder = (ListVideoChannelViewHolder) holder;
        listVideoChannelViewHolder.tvNameChannel.setText(itemListVideoInChannel.getTitleChannel());
        listVideoChannelViewHolder.tvTitle.setText(itemListVideoInChannel.getTitleVideo());
        listVideoChannelViewHolder.numberVideo.setText(itemListVideoInChannel.getNumberVideo());
        Picasso.get().load(itemListVideoInChannel.getUrlImageList()).into(listVideoChannelViewHolder.imgList);
        listVideoChannelViewHolder.tvNumberVideoInImg.setText(itemListVideoInChannel.getNumberVideo());
    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        } else {
            return 0;
        }
    }

    private class LastVideoChannelViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivLoadMore;

        public LastVideoChannelViewHolder(@NonNull View itemView) {
            super(itemView);
            ivLoadMore = itemView.findViewById(R.id.iv_load_more);
        }
    }

    private class ListVideoChannelViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTitle;
        private TextView tvNameChannel;
        private TextView numberVideo;
        private TextView tvNumberVideoInImg;
        private ImageView imgList;

        public ListVideoChannelViewHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void mapping(@NonNull View view) {
            tvTitle = view.findViewById(R.id.tv_title_list_channel);
            tvNameChannel = view.findViewById(R.id.tv_name_channel);
            numberVideo = view.findViewById(R.id.tv_number_video);
            imgList = view.findViewById(R.id.iv_list_channel);
            tvNumberVideoInImg = view.findViewById(R.id.tv_number_video_in_image);
        }
    }
}
