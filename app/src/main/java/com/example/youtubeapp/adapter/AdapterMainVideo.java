package com.example.youtubeapp.adapter;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubeapp.R;
import com.example.youtubeapp.interfacee.InterfaceClickFrame;
import com.example.youtubeapp.item.ItemVideoMainn;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterMainVideo extends
        RecyclerView.Adapter<AdapterMainVideo.ItemVideoMainnViewHolder> {

    private ArrayList<ItemVideoMainn> listItemVideoMainn;
    private InterfaceClickFrame interfaceClickFrame;

    public AdapterMainVideo(ArrayList<ItemVideoMainn> listItemVideoMainn,
                            InterfaceClickFrame interfaceClickFrame) {
        this.listItemVideoMainn = listItemVideoMainn;
        this.interfaceClickFrame = interfaceClickFrame;
    }

    @NonNull
    @Override
    public ItemVideoMainnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_video, parent, false);
        return new ItemVideoMainnViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemVideoMainnViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {
        ItemVideoMainn itemVideoMain = listItemVideoMainn.get(position);
        if (itemVideoMain == null) {
            return;
        }
        Picasso.get().load(itemVideoMain.getUrlImageChannel()).into(holder.ivAvtChannel);
        Picasso.get().load(itemVideoMain.getUrlImage()).into(holder.youTubeThumbnailView);
        holder.tvTitleMainItem.setText(itemVideoMain.getTitleVideo());
        holder.tvTimeUp.setText(itemVideoMain.getTimeUp());
        holder.tvNameChannel.setText(itemVideoMain.getTitleChannel());
        holder.tvViewer.setText(itemVideoMain.getViewer());


        holder.youTubeThumbnailView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceClickFrame.onClickImage(position);
            }
        });

        holder.tvTitleMainItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceClickFrame.onClickTitle(position);
            }
        });
        holder.ivMenuVertical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceClickFrame.onClickMenu(position);
            }
        });

        holder.ivAvtChannel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceClickFrame.onClickAvtChannel(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listItemVideoMainn == null) {
            return 0;
        }
        return listItemVideoMainn.size();
    }

    public class ItemVideoMainnViewHolder extends RecyclerView.ViewHolder {
        ImageView youTubeThumbnailView;
        ImageView ivAvtChannel, ivMenuVertical;
        TextView tvTitleMainItem, tvNameChannel, tvViewer, tvTimeUp;

        public ItemVideoMainnViewHolder(@NonNull View itemView) {
            super(itemView);
            mapping(itemView);
        }

        @SuppressLint("CutPasteId")
        public void mapping(@NonNull View view) {
            ivMenuVertical = view.findViewById(R.id.iv_item_main_menu_vertical);
            youTubeThumbnailView = view.findViewById(R.id.iv_item_video);
            ivAvtChannel = view.findViewById(R.id.iv_item_main_avt_video);
            tvNameChannel = view.findViewById(R.id.tv_item_main_name_channel);
            tvTimeUp = view.findViewById(R.id.tv_item_main_time_up);
            tvTitleMainItem = view.findViewById(R.id.tv_item_main_title_video);
            tvViewer = view.findViewById(R.id.tv_item_main_view_count);
        }
    }
}