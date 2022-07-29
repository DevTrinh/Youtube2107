package com.example.youtubeapp.adapter;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubeapp.R;
import com.example.youtubeapp.interfacee.InterfaceClickWithPosition;
import com.example.youtubeapp.item.ItemVideoInList;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterVideoList extends RecyclerView.Adapter<AdapterVideoList.ItemVideoListViewHolder> {

    private ArrayList<ItemVideoInList> listVideoInList = new ArrayList<>();
    private InterfaceClickWithPosition interfaceClickWithPosition;

    public AdapterVideoList(ArrayList<ItemVideoInList> listVideoInList,
                            InterfaceClickWithPosition interfaceClickWithPosition) {
        this.listVideoInList = listVideoInList;
        this.interfaceClickWithPosition = interfaceClickWithPosition;
    }

    @NonNull
    @Override
    public ItemVideoListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_in_list, parent, false);
        return new ItemVideoListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemVideoListViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {
        ItemVideoInList itemVideoInList = listVideoInList.get(position);
        holder.tvTitleChannel.setText(itemVideoInList.getTitleChannel());
        holder.tvTitleVideo.setText(itemVideoInList.getTitleVideo());
        Picasso.get().load(itemVideoInList.getUrlImage()).into(holder.ivVideo);
        Log.d("SHIHI", itemVideoInList.getUrlImage());
        holder.clVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceClickWithPosition.onClickWithPosition(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listVideoInList !=  null){
            return listVideoInList.size();
        }
        return 0;
    }

    public class ItemVideoListViewHolder extends RecyclerView.ViewHolder {

        private ImageView ivVideo;
        private TextView tvTitleVideo, tvTitleChannel;
        private ConstraintLayout clVideo;

        public ItemVideoListViewHolder(@NonNull View itemView) {
            super(itemView);
            ivVideo = itemView.findViewById(R.id.iv_video);
            tvTitleVideo = itemView.findViewById(R.id.tv_title_video);
            tvTitleChannel = itemView.findViewById(R.id.tv_title_channel);
            clVideo = itemView.findViewById(R.id.cl_contains_video_in_list);
        }
    }
}
