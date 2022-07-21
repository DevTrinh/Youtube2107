package com.example.youtubeapp.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubeapp.R;
import com.example.youtubeapp.item.ItemInfoChannel;

public class FragmentChannelPlayL extends Fragment {
    private ItemInfoChannel itemInfoChannel;
    private RecyclerView rvListPlay;
    private TextView tvSort;

    public FragmentChannelPlayL(ItemInfoChannel itemInfoChannel) {
        this.itemInfoChannel = itemInfoChannel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channel_playlist, container, false);

        Log.d("NOTIFICATION: ", "Fragment Play List Is Create");
        return view;
    }

    public void mapping(){

    }
}
