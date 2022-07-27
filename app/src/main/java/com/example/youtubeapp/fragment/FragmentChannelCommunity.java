package com.example.youtubeapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.youtubeapp.R;
import com.example.youtubeapp.item.ItemDetailsVideo;

public class FragmentChannelCommunity extends Fragment {
    private ItemDetailsVideo itemDetailsVideo;

    public FragmentChannelCommunity(ItemDetailsVideo itemDetailsVideo) {
        this.itemDetailsVideo = itemDetailsVideo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channel_community, container, false);
        Log.d("NOTIFICATION: ", "Fragment Community Is Create");
        return view;
    }
}
