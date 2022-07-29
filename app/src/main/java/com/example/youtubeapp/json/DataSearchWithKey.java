package com.example.youtubeapp.json;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.youtubeapp.R;
import com.example.youtubeapp.adapter.AdapterMainVideo;
import com.example.youtubeapp.adapter.AdapterMainVideoYoutube;
import com.example.youtubeapp.format.FormatTime;
import com.example.youtubeapp.interfacee.InterfaceDefaultValue;
import com.example.youtubeapp.item.ItemVideoMainn;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import pl.droidsonroids.gif.GifImageView;

public class DataSearchWithKey implements InterfaceDefaultValue {

    private String keySearch;

    public DataSearchWithKey(String keySearch) {
        this.keySearch = keySearch;
    }

    public String getKeySearch() {
        return keySearch;
    }

    public void setKeySearch(String keySearch) {
        this.keySearch = keySearch;
    }

    public void getData(Context context, ArrayList<ItemVideoMainn>  list,
                        AdapterMainVideo adapterMainVideo,
                        int start, int end, GifImageView iv){
        String url = PATH_API
                +"search?part=snippet&maxResults=50&q="
                +getKeySearch()+"&key="
                +API_KEY;

        Log.d("URL: ", url);

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                int endItem = end;
                boolean isCheckLastItem = true;
                FormatTime formatTime;
                try {
                    String urlImage = "";
                    String timeUp = "";
                    String titleVideo = "";
                    String titleChannel = "";
                    String idVideo = "";
                    String idChannel = "";
                    JSONArray jsonItems = response.getJSONArray(ITEMS);

//                    Log.d("LENGTH: ", jsonItems.length()+"");
//
                    if (endItem >= jsonItems.length()) {
                        endItem = jsonItems.length();
                        isCheckLastItem = false;
//                        Log.d("IS LAST CHECK: ", true+"");
                    }

                    if (start< endItem){
                        for (int i = start; i < endItem; i++){
                            JSONObject jsonItem = jsonItems.getJSONObject(i);
//                            GET JSON ID
                            JSONObject jsonId = jsonItem.getJSONObject(ID);
                            idVideo = jsonId.getString(ID_VIDEO);

//                            GET JSON SNIPPET
                            JSONObject jsonSnippet = jsonItem.getJSONObject(SNIPPET);
//                            formatTime = new FormatTime( jsonSnippet.getString(PUBLISHED_AT));
                            timeUp = FormatTime.formatTimeUpVideo(jsonSnippet.getString(PUBLISHED_AT));
                            idChannel = jsonSnippet.getString(CHANNEL_ID);

                            getViewerVideo(context, idVideo, list, adapterMainVideo, i);

                            titleVideo = jsonSnippet.getString(TITLE);
                            titleChannel = jsonSnippet.getString(CHANNEL_TITLE);
                            JSONObject jsonThumb = jsonSnippet.getJSONObject(THUMBNAIL);
                            JSONObject jsonHigh = jsonThumb.getJSONObject(HIGH);
                            urlImage = jsonHigh.getString(URL);

//                            Log.d("VALUE: "+i, idVideo);
//                            Log.d("VALUE: "+i, urlImageChannel);
//                            Log.d("VALUE: "+i, urlImage);
//                            Log.d("VALUE: "+i, timeUp);
//                            Log.d("VALUE: "+i, viewer);
//                            Log.d("VALUE: "+i, titleChannel);
//                            Log.d("VALUE: "+i, idChannel);
                            list.add(new ItemVideoMainn(urlImage, timeUp,
                                    titleChannel, titleVideo,
                                    idVideo, idChannel));
                            getUrlAvtChannel(context, idChannel,
                                    adapterMainVideo, list, i,
                                    isCheckLastItem, iv);


                        }
//                        Log.d("SIZE: ", list.size()+"");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("FALSE: ", error+"");
            }

        });
        requestQueue.add(jsonObjectRequest);
    }

    public void getViewerVideo(Context context, String idVideo, ArrayList<ItemVideoMainn> list,
                               AdapterMainVideo adapterMainVideo, int position){
        String url = PATH_API
                +"videos?part=statistics&id="
                +idVideo +"&key="
                + API_KEY;

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                String view = "";
                try {
                    JSONArray jsonItems  = response.getJSONArray(ITEMS);
                    JSONObject jsonItem = jsonItems.getJSONObject(   0);
                    JSONObject jsonStatic = jsonItem.getJSONObject(STATISTICS);
                    view = FormatTime.formatData(Integer.parseInt(jsonStatic.getString(VIEW_COUNT)));
                    list.get(position).setViewer(view);
                    adapterMainVideo.notifyItemChanged(position);
//                    Log.d("AJIHI", view);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("AHUHIU: ", error+"");
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    public void getUrlAvtChannel(Context context, String idChannel,
                                 AdapterMainVideo adapterMainVideo,
                                 ArrayList<ItemVideoMainn> list, int position,
                                 boolean isCheckLastItem, GifImageView iv){

        String url = PATH_API
                +"channels?part=snippet&id="
                +idChannel+"&key="
                +API_KEY;

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(JSONObject response) {
                String urlImage = "";
                try {
                    JSONArray jsonItems = response.getJSONArray(ITEMS);
                    JSONObject jsonItem = jsonItems.getJSONObject(0);
                    JSONObject jsonSnippet = jsonItem.getJSONObject(SNIPPET);
                    JSONObject jsonThumb = jsonSnippet.getJSONObject(THUMBNAIL);
                    JSONObject jsonHigh = jsonThumb.getJSONObject(HIGH);
                    urlImage = jsonHigh.getString(URL);

//                    Log.d("LINK: ", urlImage);
                    list.get(position).setUrlImageChannel(urlImage);
                    adapterMainVideo.notifyItemChanged(position);

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
                Log.d("ERRORRRR: ", error+"");
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

}
