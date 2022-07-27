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

public class FragmentChannelRelate extends Fragment {
    private ItemDetailsVideo itemDetailsVideo;

    public FragmentChannelRelate(ItemDetailsVideo itemDetailsVideo) {
        this.itemDetailsVideo = itemDetailsVideo;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channel_relate, container, false);

        Log.d("NOTIFICATION: ", "Fragment Relate Is Create");
        return view;
    }
}
