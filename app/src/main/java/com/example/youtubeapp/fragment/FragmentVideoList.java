package com.example.youtubeapp.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubeapp.R;
import com.example.youtubeapp.adapter.AdapterVideoList;
import com.example.youtubeapp.interfacee.InterfaceClickWithPosition;
import com.example.youtubeapp.item.ItemListVideoInChannel;
import com.example.youtubeapp.item.ItemVideoInList;
import com.example.youtubeapp.json.DataDetailsVideoInList;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class FragmentVideoList extends Fragment {

    private TextView  tvTitleList;
    private TextView  tvNameChannel;
    private TextView tvNumberVideo;
    private TextView tvDes;
    private ImageView ivSaveList, ivDownLoadList, ivShareList;
    private Button    btPlayList, btShuffleList;
    private ImageView ivBack;
    private ImageView ivSearch;
    private ImageView ivMenuTopNav;

    private RecyclerView rvListVideo;
    private AdapterVideoList adapterVideoList;
    private ArrayList<ItemVideoInList> listVideo = new ArrayList<>();

    private int positionStart = 0, positionEnd = 10;
    private GifImageView ivLoadMore;

    private ItemListVideoInChannel itemListVideoInChannel;

    public FragmentVideoList(ItemListVideoInChannel itemListVideoInChannel) {
        this.itemListVideoInChannel = itemListVideoInChannel;
    }

    @SuppressLint("SetTextI18n")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_video_list, container, false);
        mapping(view);

        ivLoadMore.setImageResource(R.drawable.gif_load_more);

        Log.d("ID LIST: ", itemListVideoInChannel.getIdList());

        DataDetailsVideoInList dataDetailsVideoInList = new DataDetailsVideoInList(itemListVideoInChannel.getIdList());

        tvNumberVideo.setText(itemListVideoInChannel.getNumberVideo() + " videos");
        tvNameChannel.setText(itemListVideoInChannel.getTitleChannel());
        tvTitleList.setText(itemListVideoInChannel.getTitleList());
        tvDes.setText(itemListVideoInChannel.getDescribe());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvListVideo.setLayoutManager(linearLayoutManager);
        adapterVideoList = new AdapterVideoList(listVideo,
                new InterfaceClickWithPosition() {
            @Override
            public void onClickWithPosition(int position) {
                Log.d("TITLE: ", listVideo.get(position).getTitleVideo());
            }
        });
        rvListVideo.setAdapter(adapterVideoList);

        dataDetailsVideoInList.getData(getActivity(), listVideo,
                adapterVideoList, positionStart,
                positionEnd, ivLoadMore);


        ivLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivLoadMore.setImageResource(R.drawable.gif_load_more);
                positionStart = positionEnd;
                positionEnd += 10;
                ivLoadMore.setEnabled(false);
                dataDetailsVideoInList.getData(getActivity(), listVideo,
                        adapterVideoList,
                        positionStart, positionEnd, ivLoadMore );
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });

        return view;
    }

    public void mapping(@NonNull View view){
        tvDes = view.findViewById(R.id.tv_description);
        ivLoadMore = view.findViewById(R.id.iv_load_more);
        tvTitleList = view.findViewById(R.id.tv_title_list);
        tvNameChannel = view.findViewById(R.id.tv_name_channel);
        ivSaveList = view.findViewById(R.id.iv_save_list);
        ivDownLoadList = view.findViewById(R.id.iv_download_list);
        ivShareList = view.findViewById(R.id.iv_share_list);
        btPlayList = view.findViewById(R.id.bt_play_list);
        btShuffleList = view.findViewById(R.id.bt_shuffle);
        ivBack = view.findViewById(R.id.iv_back_channel);
        ivSearch = view.findViewById(R.id.iv_search_in_channel);
        ivMenuTopNav = view.findViewById(R.id.iv_menu_vertical_channel);
        tvNumberVideo = view.findViewById(R.id.tv_number_video);
        rvListVideo = view.findViewById(R.id.rv_list_video);
    }
}
