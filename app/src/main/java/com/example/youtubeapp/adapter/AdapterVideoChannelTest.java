package com.example.youtubeapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubeapp.R;
import com.example.youtubeapp.interfacee.InterfaceClickWithPosition;
import com.example.youtubeapp.interfacee.InterfaceClickWithString;
import com.example.youtubeapp.item.ItemVideoInChannel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterVideoChannelTest extends RecyclerView.Adapter<AdapterVideoChannelTest.ItemVideoInChannelViewHolder> {

    private ArrayList<ItemVideoInChannel> list;
    private Context context;
    private InterfaceClickWithPosition interfaceClickWithPosition;

    public AdapterVideoChannelTest(ArrayList<ItemVideoInChannel> list, Context context, InterfaceClickWithPosition interfaceClickWithPosition) {
        this.list = list;
        this.context = context;
        this.interfaceClickWithPosition = interfaceClickWithPosition;
    }

    @NonNull
    @Override
    public ItemVideoInChannelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_videos_channel, null);
        return new ItemVideoInChannelViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull ItemVideoInChannelViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {
        int width = (int) (context.getResources().getDisplayMetrics().widthPixels*0.45);
        int widthText = (int) (context.getResources().getDisplayMetrics().widthPixels*0.4);

        ItemVideoInChannel itemVideoInChannel = list.get(position);

        holder.tvTimeUp.setText(itemVideoInChannel.getTimeUpVideo());
        holder.tvTitleVideo.setText(itemVideoInChannel.getTitleVideo());
        holder.tvViewCount.setText(itemVideoInChannel.getViewCount());
        Picasso.get().load(itemVideoInChannel.getUrlImage()).into(holder.ivVideo);
        holder.ivVideo.getLayoutParams().width = width;
        holder.tvTitleVideo.getLayoutParams().width = widthText;
        holder.flContains.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceClickWithPosition.onClickWithPosition(position);
            }
        });
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

    public class ItemVideoInChannelViewHolder extends RecyclerView.ViewHolder{
        private FrameLayout  flContains;
        private ImageView ivVideo;
        private TextView tvTitleVideo;
        private TextView tvTimeUp;
        private TextView tvViewCount;
        private ConstraintLayout clContainsVideoChannel;

        public ItemVideoInChannelViewHolder(@NonNull View itemView) {
            super(itemView);
            flContains = itemView.findViewById(R.id.cl_item_video_channel);
            ivVideo = itemView.findViewById(R.id.iv_videos_in_channel);
            tvTitleVideo = itemView.findViewById(R.id.tv_title_video_channel);
            tvTimeUp = itemView.findViewById(R.id.tv_time_up);
            tvViewCount = itemView.findViewById(R.id.tv_amount_view);
        }
    }
}
