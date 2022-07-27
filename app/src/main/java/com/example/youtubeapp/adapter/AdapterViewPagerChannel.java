package com.example.youtubeapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.youtubeapp.fragment.FragmentChannelAbout;
import com.example.youtubeapp.fragment.FragmentChannelCommunity;
import com.example.youtubeapp.fragment.FragmentChannelHome;
import com.example.youtubeapp.fragment.FragmentChannelPlayL;
import com.example.youtubeapp.fragment.FragmentChannelRelate;
import com.example.youtubeapp.fragment.FragmentChannelVideo;
import com.example.youtubeapp.item.ItemDetailsVideo;

public class AdapterViewPagerChannel extends FragmentStateAdapter {

    private ItemDetailsVideo itemDetailsVideo;

    public AdapterViewPagerChannel(@NonNull FragmentActivity fragmentActivity, ItemDetailsVideo itemDetailsVideo) {
        super(fragmentActivity);
        this.itemDetailsVideo = itemDetailsVideo;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentChannelHome(itemDetailsVideo);
            case 1:
                return new FragmentChannelVideo(itemDetailsVideo);
            case 2:
                return new FragmentChannelPlayL(itemDetailsVideo);
            case 3:
                return new FragmentChannelCommunity(itemDetailsVideo);
            case 4:
                return new FragmentChannelRelate(itemDetailsVideo);
            case 5:
                return new FragmentChannelAbout(itemDetailsVideo);
            default:
                return new FragmentChannelHome(itemDetailsVideo);
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }
}
