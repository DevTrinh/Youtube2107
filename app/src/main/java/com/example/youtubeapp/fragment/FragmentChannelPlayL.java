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
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubeapp.R;
import com.example.youtubeapp.adapter.AdapterListInChannel;
import com.example.youtubeapp.interfacee.InterfaceClickWithPosition;
import com.example.youtubeapp.interfacee.InterfaceDefaultValue;
import com.example.youtubeapp.item.ItemDetailsVideo;
import com.example.youtubeapp.item.ItemListVideoInChannel;
import com.example.youtubeapp.json.DataListVideo;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class FragmentChannelPlayL extends Fragment implements InterfaceDefaultValue {
    private ItemDetailsVideo itemDetailsVideo;
    private RecyclerView rvListPlay;
    private GifImageView  ivLoadMore;
    private TextView tvSort;
    private AdapterListInChannel adapterListInChannel;

    private ArrayList<ItemListVideoInChannel> listVideoInChannels = new ArrayList<>();

    private int positionStartLoad = 0;
    private int positionEndLoad = 10;

    public FragmentChannelPlayL(ItemDetailsVideo itemDetailsVideo) {
        this.itemDetailsVideo = itemDetailsVideo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channel_playlist, container, false);
        mapping(view);

//        ivLoadMore.setVisibility(View.GONE);

        Log.d("NOTIFICATION: ", "Fragment Play List Is Create");

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvListPlay.setLayoutManager(linearLayoutManager);

        DataListVideo dataListVideo = new DataListVideo(itemDetailsVideo.getIdChannel());

        adapterListInChannel = new AdapterListInChannel(listVideoInChannels,
                new InterfaceClickWithPosition() {
            @Override
            public void onClickWithPosition(int position) {
                Log.d("LIST: ", listVideoInChannels.get(position).getTitleList()+"");
                FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                FragmentVideoList fragmentVideoList = new FragmentVideoList(listVideoInChannels.get(position));
                fragmentTransaction.add(R.id.fl_contains_open_list, fragmentVideoList, FRAGMENT_OPEN_LIST);
                fragmentTransaction.addToBackStack(FRAGMENT_OPEN_LIST);
                fragmentTransaction.commit();
            }
        });
        rvListPlay.setAdapter(adapterListInChannel);

        dataListVideo.getDetailList(getActivity(),
                listVideoInChannels,
                adapterListInChannel,
                positionStartLoad, positionEndLoad,
                ivLoadMore);

        Log.d("SIZE: ", listVideoInChannels.size()+"");

        ivLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivLoadMore.setImageResource(R.drawable.gif_load_more);
                positionStartLoad = positionEndLoad;
                positionEndLoad += 10;
                ivLoadMore.setEnabled(false);
                dataListVideo.getDetailList(getActivity(), listVideoInChannels,
                        adapterListInChannel,
                        positionStartLoad, positionEndLoad, ivLoadMore );
            }
        });

        return view;
    }

    public void mapping(@NonNull View view) {
        ivLoadMore = view.findViewById(R.id.iv_load_more);
        rvListPlay = view.findViewById(R.id.rv_contains_list_video);
    }
}
