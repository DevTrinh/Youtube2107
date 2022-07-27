package com.example.youtubeapp.json;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.youtubeapp.R;
import com.example.youtubeapp.adapter.AdapterListInChannel;
import com.example.youtubeapp.interfacee.InterfaceDefaultValue;
import com.example.youtubeapp.item.ItemListVideoInChannel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class DataListVideo implements InterfaceDefaultValue {


    public DataListVideo(String id) {
        this.id = id;
    }

    String id = "";

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void getDetailList(Context context,
                              ArrayList<ItemListVideoInChannel> listVideoInChannels,
                              AdapterListInChannel adapterListInChannel,
                              int start, int end, GifImageView iv){
        String url = PATH_API
                +"playlists?part=snippet%2CcontentDetails&channelId="
                + getId() +"&maxResults=50&key="
                +API_KEY;

        Log.d("URL: ", url);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int endItem = end;
                boolean isCheckLastItem = true;
                try {
                    String idList = "";
                    String titleList = "";
                    String urlImageList = "";
                    String numberVideo = "";
                    String describe = "";
                    String titleChannel;

                    JSONArray jsonItems = response.getJSONArray(ITEMS);

                    if (endItem >= jsonItems.length()){
                        endItem = jsonItems.length();
                        isCheckLastItem = false;
//                        Log.d("IS LAST CHECK: ", true+"");
                    }
                    if (start < endItem){
//                    Log.d("JSON ITEMS: ", jsonItems.length()+"");
                        for(int i = start; i<endItem; i++){
                            JSONObject jsonItem = jsonItems.getJSONObject(i);
                            idList = jsonItem.getString(ID);

                            JSONObject jsonSnippet = jsonItem.getJSONObject(SNIPPET);
                            titleList = jsonSnippet.getString(TITLE);

                            JSONObject jsonThumb = jsonSnippet.getJSONObject(THUMBNAIL);

                            if (jsonThumb.has(HIGH)){
                                JSONObject jsonHigh = jsonThumb.getJSONObject(HIGH);
                                urlImageList = jsonHigh.getString(URL);
                            }else if (jsonThumb.has(MAX_RES)){
                                JSONObject jsonHigh = jsonThumb.getJSONObject(MAX_RES);
                                urlImageList = jsonHigh.getString(URL);
                            }
                            describe = jsonSnippet.getString(DESCRIPTION);
                            titleChannel = jsonSnippet.getString(CHANNEL_TITLE);

                            JSONObject jsonDetails = jsonItem.getJSONObject(CONTENT_DETAILS);
                            numberVideo = jsonDetails.getString(ITEM_COUNT);


//                            Log.d("TITLE CHANNEL: ", titleChannel);
//                            Log.d("JSON: ",idList );
//                            Log.d("JSON: ",titleList );
//                            Log.d("JSON: ",urlImageList );
//                            Log.d("JSON: ",titleChannel );
//                            Log.d("JSON: ",numberVideo );
//                            Log.d("JSON: ",describe );
                            listVideoInChannels.add(new ItemListVideoInChannel(idList,
                                    titleList, urlImageList,
                                    titleChannel, numberVideo,
                                    describe));
                            adapterListInChannel.notifyItemChanged(i);
                        }
                    }

                    if (!isCheckLastItem){
                        iv.setVisibility(View.GONE);
                    }
                    else{
                        iv.setVisibility(View.VISIBLE);
                        iv.setImageResource(R.drawable.ic_arrow_down);
                        iv.setEnabled(true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
