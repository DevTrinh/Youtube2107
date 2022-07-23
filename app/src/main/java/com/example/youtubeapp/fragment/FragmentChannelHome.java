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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.youtubeapp.R;
import com.example.youtubeapp.interfacee.InterfaceDefaultValue;
import com.example.youtubeapp.item.ItemInfoChannel;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

public class FragmentChannelHome extends Fragment implements InterfaceDefaultValue {

    private ImageView ivBannerChannel;
    private ImageView ivAvtChannel;
    private TextView tvBioChannel;
    private TextView tvTittleChannel;
    private TextView tvSub;
    private TextView tvNumberSubs;
    private TextView tvNumberVideo;
    private ItemInfoChannel itemInfoChannel;

    public FragmentChannelHome(ItemInfoChannel itemInfoChannel) {
        this.itemInfoChannel = itemInfoChannel;
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channel_home, container, false);
        mapping(view);
//        Log.d("NOT NULL ! ",itemInfoChannel.getIdChannel());
        if (itemInfoChannel == null) {
            Toast.makeText(getContext(), "DATA INFO CHANNEL IS NULL !", Toast.LENGTH_SHORT).show();
        } else {
            Picasso.get().load(itemInfoChannel.getUrlBanner()).into(ivBannerChannel);
            Picasso.get().load(itemInfoChannel.getUrlAvt()).into(ivAvtChannel);
            tvTittleChannel.setText(itemInfoChannel.getTitleChannel());
            tvNumberSubs.setText(itemInfoChannel.getSubscriberCount());
            tvNumberVideo.setText(itemInfoChannel.getVideoCount());
            tvBioChannel.setText("Hi I'm " + itemInfoChannel.getTitleChannel() + " >>");
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
