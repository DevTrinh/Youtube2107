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
import com.example.youtubeapp.item.ItemInfoChannel;

public class FragmentChannelAbout extends Fragment {
    private ItemInfoChannel itemInfoChannel;

    public FragmentChannelAbout(ItemInfoChannel itemInfoChannel) {
        this.itemInfoChannel = itemInfoChannel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_channel_about, container, false);
        Log.d("NOTIFICATION: ", "Fragment About Is Create");
        return view;
    }
}
