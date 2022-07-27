package com.example.youtubeapp.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.youtubeapp.R;
import com.example.youtubeapp.interfacee.InterfaceDefaultValue;
import com.example.youtubeapp.item.ItemDetailsVideo;
import com.squareup.picasso.Picasso;

public class FragmentChannelHome extends Fragment implements InterfaceDefaultValue {

    private ImageView ivBannerChannel;
    private ImageView ivAvtChannel;
    private TextView tvBioChannel;
    private TextView tvTittleChannel;
    private TextView tvSub;
    private TextView tvNumberSubs;
    private TextView tvNumberVideo;
    private ItemDetailsVideo itemDetailsVideo;

    public FragmentChannelHome(ItemDetailsVideo itemDetailsVideo) {
        this.itemDetailsVideo = itemDetailsVideo;
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channel_home, container, false);
        mapping(view);
//        Log.d("NOT NULL ! ",itemDetailsVideo.getIdChannel());
        if (itemDetailsVideo == null) {
            Toast.makeText(getContext(), "DATA INFO CHANNEL IS NULL !", Toast.LENGTH_SHORT).show();
        } else {
            Picasso.get().load(itemDetailsVideo.getUrlBanner()).into(ivBannerChannel);
            Picasso.get().load(itemDetailsVideo.getUrlAvt()).into(ivAvtChannel);
            tvTittleChannel.setText(itemDetailsVideo.getTitleChannel());
            tvNumberSubs.setText(itemDetailsVideo.getSubscriberCount());
            tvNumberVideo.setText(itemDetailsVideo.getVideoCount());
            tvBioChannel.setText("Hi I'm " + itemDetailsVideo.getTitleChannel() + " >>");
        }

        Log.d("NOTIFICATION: ", "Fragment Home Is Create");

        return view;
    }

    public void mapping(@NonNull View view) {
        tvBioChannel = view.findViewById(R.id.tv_bio_channel);
        ivBannerChannel = view.findViewById(R.id.iv_banner_channel);
        ivAvtChannel = view.findViewById(R.id.iv_avt_channel);
        tvTittleChannel = view.findViewById(R.id.tv_title_channel);
        tvSub = view.findViewById(R.id.tv_subscribe_channel);
        tvNumberSubs = view.findViewById(R.id.tv_number_sub_channel);
        tvNumberVideo = view.findViewById(R.id.tv_number_video_channel);
    }
}
