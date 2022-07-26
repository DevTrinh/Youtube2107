package com.example.youtubeapp.adapter;

import android.annotation.SuppressLint;
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
import com.example.youtubeapp.item.ItemListVideoInChannel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterListInChannel extends RecyclerView.Adapter<AdapterListInChannel.ListInChannelViewHolder> {

    private ArrayList<ItemListVideoInChannel> listVideoInChannels = new ArrayList<>();
    private InterfaceClickWithPosition interfaceClickWithPosition;

    public AdapterListInChannel(ArrayList<ItemListVideoInChannel> listVideoInChannels,
                                InterfaceClickWithPosition interfaceClickWithPosition) {
        this.listVideoInChannels = listVideoInChannels;
        this.interfaceClickWithPosition = interfaceClickWithPosition;
    }

    @NonNull
    @Override
    public ListInChannelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_list_channel, parent, false);
        return new ListInChannelViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ListInChannelViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {
        ItemListVideoInChannel itemListVideoInChannel = listVideoInChannels.get(position);
        Picasso.get().load(itemListVideoInChannel.getUrlImageList()).into(holder.ivList);
        holder.tvList.setText(itemListVideoInChannel.getTitleList());
        holder.tvNumberVideo.setText(itemListVideoInChannel.getNumberVideo()+ " videos");
        holder.tvNumberVideoInImg.setText(itemListVideoInChannel.getNumberVideo());
        holder.tvTitleChannel.setText(itemListVideoInChannel.getTitleChannel());
        holder.clContainsListInChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceClickWithPosition.onClickWithPosition(position);
            }
        });

        holder.ivMenuVertical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceClickWithPosition.onClickWithPosition(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listVideoInChannels == null){
            return 0;
        }
        return listVideoInChannels.size();
    }

    public class ListInChannelViewHolder extends RecyclerView.ViewHolder{
        private ConstraintLayout clContainsListInChannel;
        private ImageView ivList;
        private TextView tvList;
        private TextView tvNumberVideoInImg;
        private TextView tvTitleChannel;
        private TextView tvNumberVideo;
        private ImageView ivMenuVertical;
        public ListInChannelViewHolder(@NonNull View itemView) {
            super(itemView);
            ivMenuVertical = itemView.findViewById(R.id.iv_menu_right_list_channel);
            clContainsListInChannel = itemView.findViewById(R.id.cl_contains_list_in_video);
            ivList = itemView.findViewById(R.id.iv_list_channel);
            tvList = itemView.findViewById(R.id.tv_title_list_channel);
            tvNumberVideoInImg = itemView.findViewById(R.id.tv_number_video_in_image);
            tvTitleChannel = itemView.findViewById(R.id.tv_name_channel);
            tvNumberVideo = itemView.findViewById(R.id.tv_number_video);
        }
    }
}
