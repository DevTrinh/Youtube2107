package com.example.youtubeapp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.youtubeapp.ActivitySearchVideo;
import com.example.youtubeapp.MainActivity;
import com.example.youtubeapp.R;
import com.example.youtubeapp.adapter.AdapterViewPagerChannel;
import com.example.youtubeapp.interfacee.InterfaceDefaultValue;
import com.example.youtubeapp.item.ItemInfoChannel;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class FragmentChannel extends Fragment implements InterfaceDefaultValue {

    private TabLayout tabLayout;
    private ViewPager2 viewPagerChannel;
    private AdapterViewPagerChannel adtViewPager;
    private ItemInfoChannel itemInfoChannel;
    private TextView tvTitleChannel;
    private ImageView ivCancel;
    private ImageView ivSearch;
    private ImageView ivMenu;
    private String idChannel= "";

    public FragmentChannel(String idChannel) {
        this.idChannel = idChannel;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_channel, container, false);
        mapping(view);
        Log.d("AAAAAAA", "ID channel "+ idChannel);

        final String urlInfoChannel = "https://youtube.googleapis.com/youtube/v3/channels?part=snippet%2CcontentDetails%2Cstatistics%2C%20brandingSettings&id="
                +idChannel+"&maxResults=50&key="+API_KEY;

        getJsonData(urlInfoChannel);

        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        ivSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentToSearch = new Intent(getContext(),
                        ActivitySearchVideo.class);
                startActivity(intentToSearch);
            }
        });

        return view;
    }

    public void getJsonData(String url){
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            String urlAvt;
            String urlBanner;
            String timeCreateChannel;
            String titleChannel;
            String description;
            String urlListUpload;
            String viewCount;
            String subscriberCount;
            String videoCount;
            String idChannel;
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonItems = response.getJSONArray(ITEMS);
                    JSONObject jsonItem = jsonItems.getJSONObject(0);
                    idChannel = jsonItem.getString(ID);
//                    Log.d("ID CHANNEL: ", idChannel);

//                    SNIPPET
                    JSONObject jsonSnippet = jsonItem.getJSONObject(SNIPPET);
                    titleChannel = jsonSnippet.getString(TITLE);
                    tvTitleChannel.setText(titleChannel);
//                    Log.d("TITLE:  ", titleChannel);

                    description = jsonSnippet.getString(DESCRIPTION);
                    timeCreateChannel = jsonSnippet.getString(PUBLISHED_AT);
                    JSONObject jsonThumbnail = jsonSnippet.getJSONObject(THUMBNAIL);
                    JSONObject jsonHigh = jsonThumbnail.getJSONObject(HIGH);
                    urlAvt = jsonHigh.getString(URL);
//                    Log.d("URL AVT CHANNEL: ", urlAvt);

                    JSONObject jsonBrandingSetting = jsonItem.getJSONObject(BRANDING_SETTING);
                    JSONObject jsonImageBrand = jsonBrandingSetting.getJSONObject(IMAGE);
                    urlBanner = jsonImageBrand.getString(BANNER_EXTERNAL_URL);
//                    Log.d("URL BANNER: ", urlBanner);

//                    STATISTICS
                    JSONObject jsonStatics = jsonItem.getJSONObject(STATISTICS);
                    viewCount = jsonStatics.getString(VIEW_COUNT);
//                    Log.d("VIEW COUNT: ", viewCount);
                    subscriberCount = jsonStatics.getString(SUBSCRIBE_COUNT);
                    Log.d("SUBSCRIBE: ",subscriberCount);
                    videoCount = jsonStatics.getString(VIDEO_COUNT);
//                    Log.d("VIEW COUNT: ", videoCount);

//                    CONTENT DETAILS
                    JSONObject jsonContentDetails = jsonItem.getJSONObject(CONTENT_DETAILS);
                    JSONObject jsonRelatedPlayList = jsonContentDetails.getJSONObject(RELATED_PLAY_LIST);
                    urlListUpload = jsonRelatedPlayList.getString(UPLOADS);
//                    Log.d("UPLOAD: ", urlListUpload);


                    itemInfoChannel = new ItemInfoChannel(urlAvt, urlBanner,
                            timeCreateChannel, titleChannel,
                            description, urlListUpload,
                            viewCount, subscriberCount,
                            videoCount, idChannel);

                    adtViewPager = new AdapterViewPagerChannel(requireActivity(), itemInfoChannel);
                    viewPagerChannel.setAdapter(adtViewPager);
                    viewPagerChannel.setOffscreenPageLimit(3);

                    new TabLayoutMediator(tabLayout, viewPagerChannel, (tab, position) -> {
                        switch (position){
                            case 0:
                                tab.setText("Home");
                                break;
                            case 1:
                                tab.setText("Videos");
                                break;
                            case 2:
                                tab.setText("Playlists");
                                break;
                            case 3:
                                tab.setText("Community");
                                break;
                            case 4:
                                tab.setText("Channels");
                                break;
                            case 5:
                                tab.setText("About");
                                break;
                        }
                    }).attach();
//                    Toast.makeText(getActivity(), itemInfoChannel+"", Toast.LENGTH_SHORT).show();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error+" Json Data Info Channel", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    public void mapping(@NonNull View view){
        tabLayout = view.findViewById(R.id.tl_contains_element);
        viewPagerChannel = view.findViewById(R.id.vp_contains_content);
        tvTitleChannel = view.findViewById(R.id.tv_title_top_channel);
        ivCancel = view.findViewById(R.id.iv_back_channel);
        ivSearch = view.findViewById(R.id.iv_search_in_channel);
        ivMenu = view.findViewById(R.id.iv_menu_vertical_channel);
//        Toast.makeText(getActivity(), "this is mapping", Toast.LENGTH_SHORT).show();
    }
}
