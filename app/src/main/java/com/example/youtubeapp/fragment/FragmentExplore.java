package com.example.youtubeapp.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.viewpager2.widget.ViewPager2;

import com.example.youtubeapp.MainActivity;
import com.example.youtubeapp.R;
import com.example.youtubeapp.adapter.AdapterShort;
import com.example.youtubeapp.interfacee.InterfaceDefaultValue;
import com.example.youtubeapp.interfacee.InterfaceShortLink;
import com.example.youtubeapp.item.ItemShort;
import com.example.youtubeapp.json.DataListVideoShort;

import java.util.ArrayList;
import java.util.Collections;

import okhttp3.MediaType;

public class FragmentExplore extends Fragment implements InterfaceDefaultValue, InterfaceShortLink, SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout refreshLayout;
    private ViewPager2 vpShort;
    private ArrayList<ItemShort> listShort = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore,
                container, false);
        mapping(view);

        ArrayList<String> linkShort = new ArrayList<>();

      addLink(linkShort);
//      addItem(linkShort);
      Collections.shuffle(listShort);


//        DataListVideoShort dataListVideoShort = new DataListVideoShort(requireActivity());
//
//        DataListVideoShort dataListVideoShort = new DataListVideoShort();

//        dataListVideoShort.getDataShort(requireActivity(), vpShort);

        vpShort.setAdapter(new AdapterShort(listShort));

        return view;
    }

    public void addItem(ArrayList<String> linkShort){
        for(int i = 0; i<14; i++){
            listShort.add(new ItemShort("https://rr8---sn-42u-i5ol6.googlevideo.com/videoplayback?expire=1659176981&ei=tLPkYpSjLJPThgbv5oq4CA&ip=3.239.74.186&id=o-AAtkaE3LNt3uwPJaGkjpMcmjlwApxYBpzR-XbfW3gz27&itag=18&source=youtube&requiressl=yes&spc=lT-KhkukK8JvmVoxXvpGNQ1_OVghvtY&vprv=1&mime=video%2Fmp4&ns=U88eUpAGWO6RQ4uYTVl5ob8H&gir=yes&clen=133655575&ratebypass=yes&dur=3627.607&lmt=1640266119879844&fexp=24001373,24007246&c=WEB&txp=5538434&n=hnMu2mud-r93Yu2m&sparams=expire%2Cei%2Cip%2Cid%2Citag%2Csource%2Crequiressl%2Cspc%2Cvprv%2Cmime%2Cns%2Cgir%2Cclen%2Cratebypass%2Cdur%2Clmt&sig=AOq0QJ8wRAIgHs5LshS7uLX8aFKD66ASDPXZ0C0pN7PSzcZFP-shygwCIHAAn50CSlXXuiazdW0DY9EXY8h_gxgjKOnO9yuIAIOq&redirect_counter=1&rm=sn-p5qe7d7z&req_id=70dd25de1fa7a3ee&cms_redirect=yes&cmsv=e&ipbypass=yes&mh=9a&mip=58.186.51.120&mm=31&mn=sn-42u-i5ol6&ms=au&mt=1659164222&mv=m&mvi=8&pl=24&lsparams=ipbypass,mh,mip,mm,mn,ms,mv,mvi,pl&lsig=AG3C_xAwRQIhAJfI6gwEtU2ouE9ibRvM1ozaMPptJu6bS8CTtRuHLpztAiA-Y2fe1Ic6JxzygiJnOGYdvOcrjose5c_euBA1sCBnTA%3D%3D","Dog funny video", "", "", ""));
        }

    }

    public void addLink(@NonNull ArrayList<String> listLink){
        listLink.add(VIDEO_1);
        listLink.add(VIDEO_2);
        listLink.add(VIDEO_3);
        listLink.add(VIDEO_4);
        listLink.add(VIDEO_5);
        listLink.add(VIDEO_6);
        listLink.add(VIDEO_7);
        listLink.add(VIDEO_8);
        listLink.add(VIDEO_9);
        listLink.add(VIDEO_10);
        listLink.add(VIDEO_11);
        listLink.add(VIDEO_12);
        listLink.add(VIDEO_13);
        listLink.add(VIDEO_14);
    }

    public void mapping(@NonNull View view ){
        vpShort = view.findViewById(R.id.vp_contains_short);
        refreshLayout = view.findViewById(R.id.sl_contains_short);
    }

    @Override
    public void onRefresh() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.setRefreshing(false);
            }

        }, 2000);
        Collections.shuffle(listShort);
        vpShort.setAdapter(new AdapterShort(listShort));
    }
}
