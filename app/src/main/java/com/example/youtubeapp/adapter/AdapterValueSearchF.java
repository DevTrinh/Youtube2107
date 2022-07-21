package com.example.youtubeapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubeapp.R;
import com.example.youtubeapp.interfacee.InterfaceClickFrame;
import com.example.youtubeapp.interfacee.InterfaceDefaultValue;
import com.example.youtubeapp.item.ItemValueSearch;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterValueSearchF extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements InterfaceDefaultValue {

    private static final int  TYPE_CHANNEL = 1;
    private static final int  TYPE_VIDEO = 2;
    private static final int  TYPE_LIST = 3;

    private final String typeVideo = KIND_VIDEO;
    private final String typeList = KIND_LIST;
    private final String typeChannel = KIND_CHANNEL;

    private ArrayList<ItemValueSearch> listValueSearch;
    private InterfaceClickFrame interfaceClickFrame;
    private Context context;

    public AdapterValueSearchF(ArrayList<ItemValueSearch> listValueSearch,
                               InterfaceClickFrame interfaceClickFrame,
                               Context context) {
        this.listValueSearch = listValueSearch;
        this.interfaceClickFrame = interfaceClickFrame;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (TYPE_LIST == viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_value_list_search,null);
            return new ItemListViewHolder(view);
        }
        else if (TYPE_CHANNEL == viewType){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_value_channel_search,null);
            return new ItemChannelViewHolder(view);
        }
        else{
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video,null);
            return new ItemVideoViewHolder(view);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {
        ItemValueSearch itemValueSearch = listValueSearch.get(position);

        int width = (context.getResources().getDisplayMetrics().widthPixels);
        int height = (context.getResources().getDisplayMetrics().heightPixels);

//        Toast.makeText(context, width+"/"+height, Toast.LENGTH_SHORT).show();
        if (itemValueSearch == null){
            return;
        }
        if (TYPE_LIST == holder.getItemViewType()){
            ItemListViewHolder itemListViewHolder = (ItemListViewHolder) holder;
            Picasso.get().load(itemValueSearch.getUrlImage()).into(itemListViewHolder.ivList);
            itemListViewHolder.ivList.requestLayout();
            itemListViewHolder.ivList.getLayoutParams().width = width;
            itemListViewHolder.tvNameList.setText(itemValueSearch.getTitle());
            if (itemValueSearch.getNumberVideoList()  == null){
                itemListViewHolder.tvNumberVideo.setText("is loading...");
            }
            itemListViewHolder.tvNumberVideo.setText(itemValueSearch.getNumberVideoList() + " videos");
            itemListViewHolder.tvAmountVideo.setText(itemValueSearch.getNumberVideoList());
            itemListViewHolder.ivMenuList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    interfaceClickFrame.onClickMenu(position);
                }
            });

            itemListViewHolder.tvNameList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    interfaceClickFrame.onClickTitle(position);
                }
            });

            itemListViewHolder.ivList.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    interfaceClickFrame.onClickImage(position);
                }
            });

        }
        else if (TYPE_VIDEO == holder.getItemViewType()){
            ItemVideoViewHolder itemVideoViewHolder = (ItemVideoViewHolder) holder;
            Picasso.get().load(itemValueSearch.getUrlImage()).into(itemVideoViewHolder.ivImageAvt);
            itemVideoViewHolder.ivImageAvt.requestLayout();
            itemVideoViewHolder.ivImageAvt.getLayoutParams().width = width;
            Picasso.get().load(itemValueSearch.getUrlAvtChannel()).into(itemVideoViewHolder.ivChannel);
            itemVideoViewHolder.tvTimeUp.setText(itemValueSearch.getTimeUp());
            itemVideoViewHolder.tvViewer.setText(itemValueSearch.getViewCount());
            itemVideoViewHolder.tvNameChannel.setText(itemValueSearch.getChannelTitle());
            itemVideoViewHolder.tvTitleVideo.setText(itemValueSearch.getTitle());

            itemVideoViewHolder.tvTitleVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*TITTLE ONCLICK*/
                    interfaceClickFrame.onClickTitle(position);
                }
            });
            itemVideoViewHolder.ivImageAvt.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*IMAGEVIEW ONCLICK*/
                    interfaceClickFrame.onClickImage(position);
                }
            });
            itemVideoViewHolder.ivChannel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    interfaceClickFrame.onClickAvtChannel(position);
                }
            });

            itemVideoViewHolder.ivMenuVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    interfaceClickFrame.onClickMenu(position);
                }
            });
        }
        if (TYPE_CHANNEL == holder.getItemViewType()){
            ItemChannelViewHolder itemChannelViewHolder = (ItemChannelViewHolder) holder;
            Picasso.get().load(itemValueSearch.getUrlAvtChannel()).into(itemChannelViewHolder.ivChannel);
            itemChannelViewHolder.tvNameChannel.setText(itemValueSearch.getChannelTitle());
            itemChannelViewHolder.tvNumberSubs.setText(itemValueSearch.getNumberSubscribe());

            itemChannelViewHolder.clChannelSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    interfaceClickFrame.onClickContains(position);
                }
            });

            itemChannelViewHolder.ivMenuChannel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   interfaceClickFrame.onClickMenu(position);
                }
            });

            itemChannelViewHolder.tvSubs.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    interfaceClickFrame.onClickSubs(position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (listValueSearch != null){
            return listValueSearch.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        ItemValueSearch itemValueSearch = listValueSearch.get(position);
//        VIDEO
        if (itemValueSearch.getKind().equals(typeVideo)){
            return TYPE_VIDEO;
        }
//      CHANNEL
        else if (itemValueSearch.getKind().equals(typeChannel)) {
            return TYPE_CHANNEL;
        }
//      LIST
        if (itemValueSearch.getKind().equals(typeList)){
            return TYPE_LIST;
        }
        return super.getItemViewType(position);
    }

    public class ItemVideoViewHolder extends RecyclerView.ViewHolder {
        private ImageView ivImageAvt;
        private ImageView ivChannel, ivMenuVideo;
        private TextView tvTitleVideo;
        private TextView tvNameChannel;
        private TextView tvViewer;
        private TextView tvTimeUp;

        public ItemVideoViewHolder(@NonNull View itemView) {
            super(itemView);
            mapping(itemView);
        }

        public void mapping(@NonNull View view) {
            ivMenuVideo = view.findViewById(R.id.iv_item_main_menu_vertical);
            ivImageAvt = view.findViewById(R.id.iv_item_video);
            ivChannel = view.findViewById(R.id.iv_item_main_avt_video);
            tvTitleVideo = view.findViewById(R.id.tv_item_main_title_video);
            tvNameChannel = view.findViewById(R.id.tv_item_main_name_channel);
            tvViewer = view.findViewById(R.id.tv_item_main_view_count);
            tvTimeUp = view.findViewById(R.id.tv_item_main_time_up);
        }
    }
    public class ItemListViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivList;
        private TextView tvNameList;
        private TextView tvNameChannel;
        private TextView tvNumberVideo;
        private ImageView ivMenuList;
        private TextView tvAmountVideo;
        public ItemListViewHolder(@NonNull View itemView) {
            super(itemView);
            mapping(itemView);
        }

        public void mapping(@NonNull View view){
            tvAmountVideo = view.findViewById(R.id.tv_amount_video);
            ivList = view.findViewById(R.id.iv_item_video);
            tvNameList = view.findViewById(R.id.tv_name_list_search);
            tvNameChannel = view.findViewById(R.id.tv_name_channel_list);
            tvNumberVideo  = view.findViewById(R.id.tv_number_video_list);
            ivMenuList = view.findViewById(R.id.iv_menu_list_search);
        }
    }

    public class ItemChannelViewHolder extends RecyclerView.ViewHolder{
        private ImageView ivMenuChannel;
        private TextView tvSubs;
        private ImageView ivChannel;
        private TextView tvNameChannel;
        private TextView tvNumberSubs;
        private ConstraintLayout clChannelSearch;
        public ItemChannelViewHolder(@NonNull View itemView) {
            super(itemView);
            mapping(itemView);
        }

        public void mapping(@NonNull View view){
            clChannelSearch = view.findViewById(R.id.cl_channel_search);
            tvSubs = view.findViewById(R.id.tv_sub_search);
            ivMenuChannel = view.findViewById(R.id.iv_menu_channel_search);
            ivChannel = view.findViewById(R.id.iv_channel_search);
            tvNameChannel = view.findViewById(R.id.tv_name_channel_search);
            tvNumberSubs = view.findViewById(R.id.tv_number_channel_sub_search);
        }
    }
}
