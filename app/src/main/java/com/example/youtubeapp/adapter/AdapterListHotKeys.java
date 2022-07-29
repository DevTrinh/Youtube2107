package com.example.youtubeapp.adapter;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubeapp.R;
import com.example.youtubeapp.interfacee.InterfaceClickWithString;

import java.util.ArrayList;

public class AdapterListHotKeys extends RecyclerView.Adapter {

    private ArrayList<String> listKey;
    private InterfaceClickWithString interfaceClickWithString;


    private final int TYPE_0 = 0;

    public AdapterListHotKeys(ArrayList<String> listKey,
                              InterfaceClickWithString interfaceClickWithString) {
        this.listKey = listKey;
        this.interfaceClickWithString = interfaceClickWithString;
    }

    @NonNull
    @Override
    public ItemHotKeyViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent, int viewType) {
        View view;
        if (TYPE_0 ==  viewType){
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_hot_keywords2,
                            parent, false);
        }
        else{
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_hot_keywords,
                            parent, false);
        }
        return new ItemHotKeyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String value = listKey.get(position);
        ItemHotKeyViewHolder itemHotKeyViewHolder = (ItemHotKeyViewHolder) holder;
        itemHotKeyViewHolder.tvKeyWords.setText(value);

        itemHotKeyViewHolder.tvKeyWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceClickWithString.onClickWithString(itemHotKeyViewHolder.tvKeyWords.getText().toString()+"");
            }
        });

    }

    @Override
    public int getItemCount() {
        if (listKey != null) {
            return listKey.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0){
            return 0;
        }
        return 1;
    }

    public class ItemHotKeyViewHolder extends RecyclerView.ViewHolder {

        private TextView tvKeyWords;

        public ItemHotKeyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKeyWords = itemView.findViewById(R.id.tv_key_words);
        }
    }
}