package com.example.youtubeapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

import com.example.youtubeapp.adapter.AdapterOptionsSetting;
import com.example.youtubeapp.interfacee.InterfaceClickWithString;

import java.util.ArrayList;

public class ActivitySetting extends AppCompatActivity {
    private RecyclerView rvItemSettings;
    private AdapterOptionsSetting adapterOptionsSetting;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        mapping();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvItemSettings.setLayoutManager(linearLayoutManager);
        adapterOptionsSetting = new AdapterOptionsSetting(getListOptions(),
                new InterfaceClickWithString() {
            @Override
            public void onClickWithString(String value) {
                Toast.makeText(ActivitySetting.this, value+"", Toast.LENGTH_SHORT).show();
            }
        });
        rvItemSettings.setAdapter(adapterOptionsSetting);
        adapterOptionsSetting.notifyDataSetChanged();
    }

    public void mapping(){
        rvItemSettings = findViewById(R.id.rv_options_setting);
    }

    public ArrayList<String> getListOptions(){
        ArrayList<String> list = new ArrayList<>();
        list.add("General");
        list.add("Autoplay");
        list.add("Video quality");
        list.add("Downloads");
        list.add("Watch on TV");
        list.add("History & privacy");
        list.add("Try new futures");
        list.add("Purchases and memberships");
        list.add("Billing & payments");
        list.add("Notifications");
        list.add("Connected apps");
        list.add("Live chat");
        list.add("Captions");
        list.add("Accessibility");
        list.add("About");
        return list;
    }
}