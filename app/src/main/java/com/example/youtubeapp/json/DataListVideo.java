package com.example.youtubeapp.json;

import android.content.Context;
import android.widget.Adapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.youtubeapp.adapter.AdapterListInChannel;
import com.example.youtubeapp.item.ItemListVideoInChannel;

import org.json.JSONObject;

import java.util.ArrayList;

public class DataListVideo {
    public void getDetailList(Context context,
                              ArrayList<ItemListVideoInChannel> listVideoInChannels,
                              AdapterListInChannel adapterListInChannel, String url,
                              int start, int end){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
