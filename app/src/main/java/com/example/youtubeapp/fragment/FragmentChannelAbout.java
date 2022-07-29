package com.example.youtubeapp.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.youtubeapp.R;
import com.example.youtubeapp.format.FormatTime;
import com.example.youtubeapp.interfacee.InterfaceDefaultValue;
import com.example.youtubeapp.item.ItemDetailsVideo;

public class FragmentChannelAbout extends Fragment implements InterfaceDefaultValue {

    private TextView tvDescription;
    private TextView tvLinkChannel;
    private TextView tvCountry;
    private TextView tvTimeRegister;
    private TextView tvGrowth;

    private ItemDetailsVideo itemDetailsVideo;
    private FormatTime formatTime;

    public FragmentChannelAbout(ItemDetailsVideo itemDetailsVideo) {
        this.itemDetailsVideo = itemDetailsVideo;
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channel_about, container, false);
        mapping(view);

        tvDescription.setText(itemDetailsVideo.getDescription());
        tvLinkChannel.setText(PATH_YOUTUBE_CHANNEL+itemDetailsVideo.getIdChannel());
        tvTimeRegister.setText(itemDetailsVideo.getTimeCreateChannel());
        tvCountry.setText(itemDetailsVideo.getCountry());
       formatTime = new FormatTime(itemDetailsVideo.getViewCount());
       tvGrowth.setText(formatTime.formatNumberSequence() +" views");

        Log.d("NOTIFICATION: ", "Fragment About Is Create");
        return view;
    }
    public void mapping(@NonNull View view){
        tvDescription = view.findViewById(R.id.tv_description_details);
        tvLinkChannel = view.findViewById(R.id.tv_link_channel);
        tvCountry = view.findViewById(R.id.tv_country_channel);
        tvTimeRegister = view.findViewById(R.id.tv_time_register_channel);
        tvGrowth = view.findViewById(R.id.tv_view_channel);
    }
}
