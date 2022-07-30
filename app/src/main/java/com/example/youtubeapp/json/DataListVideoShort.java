package com.example.youtubeapp.json;

import android.content.Context;
import android.util.Log;
import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.youtubeapp.interfacee.InterfaceDefaultValue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;


public class DataListVideoShort  implements InterfaceDefaultValue {

    private String linkShortPlay;

    public String getLinkShortPlay() {
        return linkShortPlay;
    }

    public void setLinkShortPlay(String linkShortPlay) {
        this.linkShortPlay = linkShortPlay;
    }

    public void getDataShort(Context context, ViewPager2 vp){
        String url = PATH_API
                + "search?part=snippet&maxResults=50&q=short&type=video&videoDuration=short&key="
                + API_KEY;
        Log.d("URL: ", url);
        RequestQueue requestQueue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,
                null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String urlShort = "";
                    String titleChannel = "";
                    String titleShort = "";
                    String urlAvtChannel = "";
                    String idChannel = "";
                    String idShort;

                    boolean isAdd = true;

                    JSONArray jsonItems = response.getJSONArray(ITEMS);

                    for (int i = 0; i<jsonItems.length(); i++){
                        JSONObject jsonItem = jsonItems.getJSONObject(i);

                        JSONObject jsonId = jsonItem.getJSONObject(ID);
                        idShort = jsonId.getString(ID_VIDEO);

                        JSONObject jsonSnippet = jsonItem.getJSONObject(SNIPPET);
                        idChannel = jsonSnippet.getString(CHANNEL_ID);
                        titleShort = jsonSnippet.getString(TITLE);
                        JSONObject jsonThumb = jsonSnippet.getJSONObject(THUMBNAIL);
                        JSONObject jsonHigh  = jsonThumb.getJSONObject(HIGH);
                        urlAvtChannel = jsonHigh.getString(URL);
                        titleChannel = jsonSnippet.getString(CHANNEL_TITLE);

                        urlShort = PATH_LINK_SHORT +idShort;

                        getLinkShortMp4(context, urlShort, vp);

                        Log.d("HIHI", getLinkShortPlay()+"");
//                        Log.d("LINK: ", linkShortPlay);
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


    public void getLinkShortMp4(Context context, String urlShortJson, ViewPager2 vp){
        Log.d("ITEM: ", urlShortJson);
        RequestQueue requestShortPlay = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectShortPlay = new JsonObjectRequest(Request.Method.GET, urlShortJson, null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                boolean isAdd = true;
                String linkShortPlay = "";
                try {
                    JSONArray jsonItems = response.getJSONArray("links");
                    if (jsonItems.length() != 0){
                        linkShortPlay = jsonItems.getString(0);
                        Log.d("AHIHI", linkShortPlay);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.d("HIHII", error+"");
            }
        });
        requestShortPlay.add(jsonObjectShortPlay);
    }
}
