package com.example.youtubeapp.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubeapp.R;
import com.example.youtubeapp.interfacee.InterfaceClickSearch;
import com.example.youtubeapp.item.ItemSearch;

import java.util.ArrayList;

public class AdapterSuggestSearch extends RecyclerView.Adapter<AdapterSuggestSearch.ItemSuggestSearch> {

    private ArrayList<ItemSearch> arrayList = new ArrayList<>();
    private InterfaceClickSearch interfaceClickSearch;
    public AdapterSuggestSearch(ArrayList<ItemSearch> arrayList, InterfaceClickSearch interfaceClickSearch) {
        this.arrayList = arrayList;
        this.interfaceClickSearch = interfaceClickSearch;
    }

    @NonNull
    @Override
    public ItemSuggestSearch onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_suggest_search, parent, false);
        return new ItemSuggestSearch(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemSuggestSearch holder,
                                 @SuppressLint("RecyclerView") int position) {
        ItemSearch itemSearch = arrayList.get(position);
        ItemSuggestSearch itemSuggestSearch = (ItemSuggestSearch) holder;
        itemSuggestSearch.tvItem.setText(itemSearch.getString());
        itemSuggestSearch.clItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceClickSearch.onCLickFrameItem(position);
            }
        });
        itemSuggestSearch.ivUpEdt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceClickSearch.onClickIconRightHistory(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (arrayList != null){
            return arrayList.size();
        }
        return 0;
    }

    protected class ItemSuggestSearch extends RecyclerView.ViewHolder{

        private ImageView ivUpEdt;
        private ConstraintLayout clItem;
        private TextView tvItem;

        public ItemSuggestSearch(@NonNull View itemView) {
            super(itemView);
            clItem = itemView.findViewById(R.id.cl_item_suggest);
            ivUpEdt = itemView.findViewById(R.id.iv_arrow_search);
            tvItem = itemView.findViewById(R.id.tv_suggest);
        }
    }
}
