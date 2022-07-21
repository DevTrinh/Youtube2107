package com.example.youtubeapp.adapter;


import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubeapp.R;
import com.example.youtubeapp.interfacee.InterfaceClickWithString;

import java.util.ArrayList;

public class AdapterOptionsSetting extends RecyclerView.Adapter<AdapterOptionsSetting.ItemSettingViewHolder> {

    private InterfaceClickWithString interfaceClickWithString;
    private ArrayList<String> listOptionsSetting;

    public AdapterOptionsSetting(ArrayList<String> listOptionsSetting,
                                 InterfaceClickWithString interfaceClickWithString) {
        this.interfaceClickWithString = interfaceClickWithString;
        this.listOptionsSetting = listOptionsSetting;
    }

    @NonNull
    @Override
    public ItemSettingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_options_setting, null);
       return new ItemSettingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemSettingViewHolder holder,
                                 @SuppressLint("RecyclerView") int position) {
        String itemOptionsSetting = listOptionsSetting.get(position);
        holder.tvOptions.setText(itemOptionsSetting);
        holder.tvOptions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                interfaceClickWithString.onClickWithString(holder.tvOptions.getText().toString()+"");
            }
        });
    }

    @Override
    public int getItemCount() {
        if (listOptionsSetting == null){
            return 0;
        }
        return listOptionsSetting.size();
    }

    public class ItemSettingViewHolder extends RecyclerView.ViewHolder{

        private TextView tvOptions;

        public ItemSettingViewHolder(@NonNull View itemView) {
            super(itemView);
            tvOptions = itemView.findViewById(R.id.tv_item_options_setting);
        }
    }
}
