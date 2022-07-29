package com.example.youtubeapp.json;

import android.content.Context;
import android.util.Log;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.youtubeapp.R;
import com.example.youtubeapp.adapter.AdapterVideoList;
import com.example.youtubeapp.interfacee.InterfaceDefaultValue;
import com.example.youtubeapp.item.ItemVideoInList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class DataDetailsVideoInList implements InterfaceDefaultValue {
    public String getIdList() {
        return idList;
    }

    public void setIdList(String idList) {
        this.idList = idList;
    }

    private String idList = "";

    public DataDetailsVideoInList(String idList) {
        this.idList = idList;
    }


    public void getData(Context context, ArrayList<ItemVideoInList> list,
                        AdapterVideoList adapterVideoList, int start,
                        int end, GifImageView iv) {

        String url = PATH_API
                + "playlistItems?part=snippet&maxResults=50&playlistId="
                + getIdList() + "&key="
                + API_KEY;

        Log.d("URL: ", url);
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                int endItem = end;
                boolean isCheckLastItem = true;
                try {
                    String urlImage = "";
                    String titleVideo = "";
                    String titleChannel = "";
                    String idVideo = "";

                    JSONArray jsonItems = response.getJSONArray(ITEMS);
                    if (endItem >= jsonItems.length()) {
                        endItem = jsonItems.length();
                        isCheckLastItem = false;
//                        Log.d("IS LAST CHECK: ", true+"");
                    }

                    if (start < endItem) {
                        for (int i = start; i < endItem; i++) {
                            JSONObject jsonItem = jsonItems.getJSONObject(i);

                            JSONObject jsonSnippet = jsonItem.getJSONObject(SNIPPET);
                            titleVideo = jsonSnippet.getString(TITLE);
                            JSONObject jsonThumb = jsonSnippet.getJSONObject(THUMBNAIL);
                            JSONObject jsonHigh;
                            if (!jsonThumb.has(HIGH)){
                                Log.d("AHIHI: ", "this is link !");
                            }
                            else {

                                jsonHigh = jsonThumb.getJSONObject(HIGH);
                                urlImage = jsonHigh.getString(URL);
                                Log.d("AHIHI: "+ i, "this is link !"+urlImage);
                            }
                            titleChannel = jsonSnippet.getString(CHANNEL_TITLE);
                            JSONObject jsonResource = jsonSnippet.getJSONObject(RESOURCE_ID);
                            idVideo = jsonResource.getString(ID_VIDEO);

                            list.add(new ItemVideoInList(urlImage, titleVideo, titleChannel, idVideo));
                            adapterVideoList.notifyItemChanged(i);
//                              Log.d("LENGTH: ", jsonItems.length() + "");
//                              Log.d("ITEM: ", urlImage+"");
//                              Log.d("ITEM: ", titleChannel+"");
//                              Log.d("ITEM: ", titleVideo+"");
//                              Log.d("ITEM: ", idVideo+"");
                        }
                    }

                    if (!isCheckLastItem) {
                        iv.setVisibility(View.GONE);
                    } else {
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
                Log.d("ERROOOOOOOO: ", error + "");
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
