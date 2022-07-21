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
import com.example.youtubeapp.item.ItemInfoChannel;

public class AdapterViewPagerChannel extends FragmentStateAdapter {

    private ItemInfoChannel itemInfoChannel;
    public AdapterViewPagerChannel(@NonNull FragmentActivity fragmentActivity, ItemInfoChannel itemInfoChannel) {
        super(fragmentActivity);
        this.itemInfoChannel = itemInfoChannel;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new FragmentChannelHome(itemInfoChannel);
            case 1:
                return new FragmentChannelVideo(itemInfoChannel);
            case 2:
                return new FragmentChannelPlayL(itemInfoChannel);
            case 3:
                return new FragmentChannelCommunity(itemInfoChannel);
            case 4:
                return new FragmentChannelRelate(itemInfoChannel);
            case 5:
                return new FragmentChannelAbout(itemInfoChannel);
            default:
                return new FragmentChannelHome(itemInfoChannel);
        }
    }

    @Override
    public int getItemCount() {
        return 6;
    }
}
