package com.example.youtubeapp.item;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.youtubeapp.R;

public class ItemSuggestSearch extends RecyclerView.Adapter<ItemSuggestSearch.ItemSuggestSearchViewHolder> {

    @NonNull
    @Override
    public ItemSuggestSearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemSuggestSearchViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ItemSuggestSearchViewHolder extends RecyclerView.ViewHolder{

        private TextView value;
        public ItemSuggestSearchViewHolder(@NonNull View itemView) {
            super(itemView);
            value = itemView.findViewById(R.id.tv_suggest);
        }
    }
}
