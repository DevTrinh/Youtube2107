package com.example.youtubeapp.adapter;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubeapp.R;
import com.example.youtubeapp.item.ItemShort;

import java.util.ArrayList;

public class AdapterShort extends RecyclerView.Adapter<AdapterShort.ItemShortViewHolder> {
    public AdapterShort(ArrayList<ItemShort> list) {
        this.list = list;
    }

    private ArrayList<ItemShort> list;

    @NonNull
    @Override
    public ItemShortViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ItemShortViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_short, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ItemShortViewHolder holder, int position) {
        holder.setListVideo(list.get(position));

    }

    @Override
    public int getItemCount() {
        if (list != null){
            return list.size();
        }
        else{
            return 0;
        }
    }


    public static class ItemShortViewHolder extends RecyclerView.ViewHolder{

        private TextView tvTitleChannel, tvTitleShort;
        private VideoView vvShort;
        private ProgressBar pbLoadShort;


        public ItemShortViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitleChannel  = itemView.findViewById(R.id.tv_title_channel);
            tvTitleShort = itemView.findViewById(R.id.tv_title_short);
            vvShort = itemView.findViewById(R.id.vv_item_video);
            pbLoadShort = itemView.findViewById(R.id.pb_item_short);
        }
        @SuppressLint("ClickableViewAccessibility")

        public void setListVideo (final ItemShort itemShort){
            tvTitleChannel.setText(itemShort.getTitleChannel());
            tvTitleShort.setText(itemShort.getTitleShort());
            vvShort.setVideoPath(itemShort.getUrlShort());
            vvShort.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    pbLoadShort.setVisibility(View.GONE);
                    mp.start();
                }
            });


            vvShort.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    if (vvShort.isPlaying()){
                        vvShort.pause();
                        return false;
                    }
                    else{
                        vvShort.start();
                    }
                    return false;
                }
            });
        }
    }
}
