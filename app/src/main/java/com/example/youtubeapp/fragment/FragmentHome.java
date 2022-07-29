package com.example.youtubeapp.fragment;

import static com.example.youtubeapp.MainActivity.btSheetPlay;
import static com.example.youtubeapp.MainActivity.fragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.youtubeapp.ActivityPlayVideo;
import com.example.youtubeapp.MainActivity;
import com.example.youtubeapp.R;
import com.example.youtubeapp.adapter.AdapterListHotKeys;
import com.example.youtubeapp.adapter.AdapterMainVideo;
import com.example.youtubeapp.adapter.AdapterMainVideoYoutube;
import com.example.youtubeapp.interfacee.InterfaceClickFrame;
import com.example.youtubeapp.interfacee.InterfaceClickWithString;
import com.example.youtubeapp.interfacee.InterfaceDefaultValue;
import com.example.youtubeapp.interfacee.InterfaceSenData;
import com.example.youtubeapp.item.ItemVideoMain;
import com.example.youtubeapp.item.ItemVideoMainn;
import com.example.youtubeapp.json.DataSearchWithKey;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import pl.droidsonroids.gif.GifImageView;


public class FragmentHome extends Fragment implements InterfaceDefaultValue,
        SwipeRefreshLayout.OnRefreshListener {
    private SwipeRefreshLayout rfMain;
    private GifImageView pbLoadListVideoMain;
    private GifImageView ivLoadMore;

    private InterfaceSenData interfaceSenData;

    public static AdapterListHotKeys adapterListHotKeys;

    public RecyclerView rvListVideoMain, rvListHotKeys;
    public static ArrayList<ItemVideoMain> listItemVideo = new ArrayList<>();
    public static ArrayList<ItemVideoMainn> listVideoWithKey = new ArrayList<>();
    public static AdapterMainVideoYoutube adapterMainVideoYoutube;
    public static AdapterMainVideo adapterMainVideo;
    public static String testUrlAvtChannel;
    private TextView tvExplore;

    public DataSearchWithKey dataSearchWithKey;

    public String valueKeyClick = "";

    private int positionStart = 0, positionEnd = 12;

    private Fragment fragment = this;


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        interfaceSenData = (InterfaceSenData) context;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("NotifyDataSetChanged")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home,
                container, false);
        mapping(view);

        ivLoadMore.setVisibility(View.GONE);
        pbLoadListVideoMain.setVisibility(View.VISIBLE);

//        rfMain.setOnRefreshListener(this);
        LinearLayoutManager linearLayoutManager =
                new LinearLayoutManager(getContext());
        rvListVideoMain.setLayoutManager(linearLayoutManager);
        LinearLayoutManager linearLayoutManagerHorizontal =
                new LinearLayoutManager(getContext(),
                        LinearLayoutManager.HORIZONTAL, false);
        rvListHotKeys.setLayoutManager(linearLayoutManagerHorizontal);

        adapterListHotKeys = new AdapterListHotKeys(getListKey(),
                new InterfaceClickWithString() {
                    @Override
                    public void onClickWithString(String value) {
                        Log.d("AHIHI", value);
                        positionStart = 0;
                        positionEnd = 12;
                        listVideoWithKey.clear();
                        listItemVideo.clear();
                        adapterMainVideoYoutube.notifyDataSetChanged();

                        ivLoadMore.setImageResource(R.drawable.gif_load_more);
                        valueKeyClick = value;
                        dataSearchWithKey = new DataSearchWithKey(valueKeyClick);

                        adapterMainVideo = new AdapterMainVideo(listVideoWithKey,
                                new InterfaceClickFrame() {
                                    @Override
                                    public void onClickTitle(int position) {

                                    }

                                    @Override
                                    public void onClickImage(int position) {

                                    }

                                    @Override
                                    public void onClickMenu(int position) {
                                        FragmentMenuItemVideoMain fragmentMenuItemVideoMain =
                                                new FragmentMenuItemVideoMain();
                                        fragmentMenuItemVideoMain.show(getActivity()
                                                .getSupportFragmentManager(), getTag());
                                    }

                                    @Override
                                    public void onClickAvtChannel(int position) {
                                        FragmentChannel fragmentChannel = (FragmentChannel) getActivity().getSupportFragmentManager().findFragmentByTag(FRAGMENT_CHANNEL);
                                        fragmentTransaction =
                                                getActivity().getSupportFragmentManager().beginTransaction();
                                        fragmentChannel = new FragmentChannel(
                                                listVideoWithKey.get(position).getIdChannel()
                                                        + "", getActivity());
                                        fragmentTransaction.add(R.id.cl_contains_search,
                                                fragmentChannel, FRAGMENT_CHANNEL);
                                        fragmentTransaction.addToBackStack(FRAGMENT_CHANNEL);
                                        fragmentTransaction.commit();
                                    }

                                    @Override
                                    public void onClickSubs(int position) {

                                    }

                                    @Override
                                    public void onClickContains(int position) {

                                    }
                                });

                        rvListVideoMain.setAdapter(adapterMainVideo);
                        dataSearchWithKey.getData(getActivity(), listVideoWithKey,
                                adapterMainVideo, positionStart,
                                positionEnd, ivLoadMore);
                    }
                });

        rvListHotKeys.setAdapter(adapterListHotKeys);
        adapterListHotKeys.notifyDataSetChanged();

        getJsonApiYoutube(positionStart, positionEnd);
//        THIS IS CLEAR NOT LOAD AGAIN
        listItemVideo.clear();
//        Toast.makeText(getContext(), "SIZE: "+listItemVideo.size(), Toast.LENGTH_SHORT).show();
        adapterMainVideoYoutube = new AdapterMainVideoYoutube(listItemVideo,
                new InterfaceClickFrame() {
                    @Override
                    public void onClickTitle(int position) {
//                        Intent intentMainToPlayVideo =
//                                new Intent(getContext(), ActivityPlayVideo.class);
//                        intentMainToPlayVideo.putExtra(VALUE_ITEM_VIDEO,
//                                listItemVideo.get(position));
//                        startActivity(intentMainToPlayVideo);
                        MainActivity mainActivity = new MainActivity();
                        mainActivity.playVideo(listItemVideo.get(position).getIdVideo());
                    }

                    @Override
                    public void onClickImage(int position) {
//                        Intent intentMainToPlayVideo =
//                                new Intent(getContext(), ActivityPlayVideo.class);
//                        intentMainToPlayVideo.putExtra(VALUE_ITEM_VIDEO,
//                                listItemVideo.get(position));
//                        startActivity(intentMainToPlayVideo);
                    }

                    @Override
                    public void onClickMenu(int position) {
                        FragmentMenuItemVideoMain fragmentMenuItemVideoMain =
                                new FragmentMenuItemVideoMain();
                        fragmentMenuItemVideoMain.show(getActivity()
                                .getSupportFragmentManager(), getTag());
                    }

                    @Override
                    public void onClickAvtChannel(int position) {
//                        Log.d(listItemVideo.get(position).getIdChannel()+"", "hihihi");
//                        getActivity().getSupportFragmentManager().beginTransaction().remove(fragment).commit();

                        FragmentChannel fragmentChannel = (FragmentChannel) getActivity().getSupportFragmentManager().findFragmentByTag(FRAGMENT_CHANNEL);
                        fragmentTransaction =
                                getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentChannel = new FragmentChannel(
                                listItemVideo.get(position).getIdChannel()
                                        + "", getActivity());
                        fragmentTransaction.add(R.id.cl_contains_search,
                                fragmentChannel, FRAGMENT_CHANNEL);
                        fragmentTransaction.addToBackStack(FRAGMENT_CHANNEL);
                        fragmentTransaction.commit();
                    }

                    @Override
                    public void onClickSubs(int position) {

                    }

                    @Override
                    public void onClickContains(int position) {

                    }
                });

        rvListVideoMain.setAdapter(adapterMainVideoYoutube);

        ivLoadMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ivLoadMore.setImageResource(R.drawable.gif_load_more);
                positionStart = positionEnd;
                positionEnd += 10;
                ivLoadMore.setEnabled(false);
                if (listItemVideo.size() > 0) {
                    getJsonApiYoutube(positionStart, positionEnd);
                } else {
                    dataSearchWithKey = new DataSearchWithKey(valueKeyClick);
                    dataSearchWithKey.getData(getActivity(), listVideoWithKey,
                            adapterMainVideo,
                            positionStart, positionEnd, ivLoadMore);
                }
//                Log.d("LOAD DATA MORE: ", positionEnd+"");
            }
        });

        tvExplore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (adapterMainVideo != null) {
                    listItemVideo.clear();
                    adapterMainVideo.notifyDataSetChanged();
                }
                if (adapterMainVideoYoutube != null) {
                    listVideoWithKey.clear();
                    adapterMainVideoYoutube.notifyDataSetChanged();
                }
                positionStart = 0;
                positionEnd = 10;
                rvListVideoMain.setAdapter(adapterMainVideoYoutube);
                getJsonApiYoutube(positionStart, positionEnd);
            }
        });

        rfMain.setOnRefreshListener(this);
        return view;
    }

    private void getJsonApiYoutube(int start, int end) {
        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                API_YOUTUBE_MAIN_VIDEO, null,
                new Response.Listener<JSONObject>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(JSONObject response) {
                        int endItem = end;
                        boolean isCheckLastItem = true;
//                        Log.d("I'm Here !", endItem+"");
                        try {
                            String idVideo = "";
                            String titleVideo = "";
                            String publishedAt = "";
                            String idChannel = "";
                            String urlThumbnail = "";
                            String channelName = "";
                            String viewCount = "";
                            String numberLiker = "";
                            String commentCount = "";
                            String description = "";
                            JSONArray jsonItems = response.getJSONArray(ITEMS);
//                          CHECK LOAD MORE
                            if (endItem > jsonItems.length()) {
                                endItem = jsonItems.length();
                                isCheckLastItem = false;
                            }
                            if (start < endItem) {
//                                Log.d("LOAD MORE: ", end+"");
//                            Log.d("AAAAAAAAAAAAA", jsonItems.length() + "");
                                for (int i = start; i < endItem; i++) {
                                    JSONObject jsonItem = jsonItems.getJSONObject(i);
                                    idVideo = jsonItem.getString(ID);
//                                Log.d("ID: "+i, idVideo);
                                    JSONObject jsonSnippet = jsonItem.getJSONObject(SNIPPET);
//                                Log.d("DESCRIPTION: ", jsonSnippet.getString("description"));
                                    description = jsonSnippet.getString(DESCRIPTION);
                                    titleVideo = jsonSnippet.getString(TITLE);
//                                Log.d("Title: "+i, titleVideo);
                                    channelName = jsonSnippet.getString(CHANNEL_TITLE);
//                                Log.d("Channel name "+i, channelName);
                                    publishedAt = formatTimeUpVideo(jsonSnippet
                                            .getString(PUBLISHED_AT) + "");
//                                Log.d(PUBLISHED_AT+i,publishedAt);
                                    idChannel = jsonSnippet.getString(CHANNEL_ID);
                                    getInfoVideo(listItemVideo, idChannel, i, isCheckLastItem);

//                                Log.d("ID CHANNEL "+i, idChannel);
                                    JSONObject jsonThumbnail = jsonSnippet.getJSONObject(THUMBNAIL);
                                    JSONObject jsonStandard = jsonThumbnail.getJSONObject(HIGH);
                                    urlThumbnail = jsonStandard.getString(URL);
//                                Log.d("THUMBNAIL "+i, urlThumbnail);
                                    JSONObject jsonStatistics = jsonItem.getJSONObject(STATISTICS);
                                    if (jsonStatistics.has(VIEW_COUNT)) {
                                        viewCount = formatData(jsonStatistics.getInt(VIEW_COUNT)) + " views";
                                    } else {
                                        viewCount = " ";
                                    }
//                                Log.d("View Count: "+i, viewCount);
                                    if (jsonStatistics.has(LIKED_COUNT)) {
                                        numberLiker = formatData(jsonStatistics.getInt(LIKED_COUNT));
                                    }
//                                Log.d("Number like"+i,numberLiker);
                                    if (jsonStatistics.has(COMMENT_COUNT)) {
                                        commentCount = formatData(Integer
                                                .parseInt(jsonStatistics.getString(COMMENT_COUNT)));
                                    }
//                                Log.d("Comment Count"+i, commentCount);
                                    listItemVideo.add(new ItemVideoMain(titleVideo,
                                            urlThumbnail, idChannel, channelName,
                                            viewCount, publishedAt, idVideo,
                                            commentCount, numberLiker, description));
                                }
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

    //    GET INFO URL CHANNEL, AMOUNT SUBSCRIBE
    public void getInfoVideo(ArrayList<ItemVideoMain> list,
                             String ID_CHANNEL, int position,
                             boolean isLoad) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                "https://youtube.googleapis.com/youtube/v3/channels?part=snippet%2Cstatistics&id="
                        + ID_CHANNEL + "&key=" + API_KEY + "",
                null, new Response.Listener<JSONObject>() {
            public void onResponse(JSONObject response) {
                try {
//                    Log.d("TAG", "https://youtube.googleapis.com/youtube/v3/channels?part=snippet%2Cstatistics&id="
//                    + ID_CHANNEL + "&key=" + API_KEY );
                    JSONArray jsonItems = response.getJSONArray(ITEMS);
                    JSONObject jsonItem = jsonItems.getJSONObject(0);
                    JSONObject jsonSnippet = jsonItem.getJSONObject(SNIPPET);
                    JSONObject jsonThumbnail = jsonSnippet.getJSONObject(THUMBNAIL);
                    JSONObject jsonHigh = jsonThumbnail.getJSONObject(HIGH);
                    testUrlAvtChannel = jsonHigh.getString(URL);
                    if (jsonThumbnail.has(HIGH)) {
                        list.get(position).setUrlAvtChannel(jsonHigh.getString(URL) + "");
//                    Log.d("LINK "+position, jsonHigh.getString(URL));
                    }
                    JSONObject jsonStatics = jsonItem.getJSONObject(STATISTICS);
                    if (jsonStatics.has(SUBSCRIBE_COUNT)) {
                        list.get(position).setNumberSubscribe(formatData
                                (Integer.parseInt(jsonStatics.getString(SUBSCRIBE_COUNT))) + " Subscribers");
//                    Log.d("AAAAA " + position, urlChannel);
                    }
                    adapterMainVideoYoutube.notifyItemChanged(position);
                    if (!isLoad) {
                        ivLoadMore.setVisibility(View.GONE);
                    } else {
                        ivLoadMore.setVisibility(View.VISIBLE);
                        ivLoadMore.setImageResource(R.drawable.ic_arrow_down);
                        ivLoadMore.setEnabled(true);
                    }
                    pbLoadListVideoMain.setVisibility(View.GONE);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "FALSE GET URL AVT CHANNEL",
                        Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    @NonNull
    private ArrayList<String> getListKey() {
        ArrayList<String> list = new ArrayList<>();
        list.add("All");
        list.add("Gaming");
        list.add("Animal");
        list.add("Music");
        list.add("News");
        list.add("Soccer");
        list.add("Army");
        list.add("Entertainment");
        list.add("Programing");
        return list;
    }

    @NonNull
    public static String formatData(int value) {
        String arr[] = {"", "K", "M", "B", "T", "P", "E"};
        int index = 0;
        while ((value / 1000) >= 1) {
            value = value / 1000;
            index++;
        }
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        return String.format("%s%s", decimalFormat.format(value), arr[index]);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String formatTimeUpVideo(String time) {
        String timeEnd = java.time.Clock.systemUTC().instant().toString();
        String timeStart = time;
        Instant start = Instant.parse(timeStart);
        Instant end = Instant.parse(timeEnd);

        long duration = Duration.between(start, end).toMillis();
        int hour = (int) TimeUnit.MILLISECONDS.toHours(duration);
        int min = (int) (TimeUnit.MILLISECONDS.toMinutes(duration)
                - TimeUnit.MILLISECONDS.toHours(duration) * 60);
//        int second = (int) (TimeUnit.MILLISECONDS.toSeconds(duration) - minutes);
        String timeUp = "";
        if (hour > 8760) {
            timeUp = (hour / 8760) + " year ago";
        }
        if (hour > 720 && hour < 8760) {
            timeUp = (hour / 720) + " month ago";
        }
        if (hour > 168 && hour < 720) {
            timeUp = (hour / 168) + " week ago";
        }
        if (hour < 168 && hour > 24) {
            timeUp = (hour / 24) + " day ago";
        }
        if (hour > 1 && hour < 24) {
            timeUp = (hour) + " hour ago";
        }
        if (hour < 1) {
            timeUp = min + "min ago";
        }
        return timeUp;
    }

    public void mapping(@NonNull View view) {
//        clChannelSearch = view.findViewById(R.id.cl_contains_search);
        ivLoadMore = view.findViewById(R.id.iv_load_more);
        rfMain = view.findViewById(R.id.rf_layout_main);
        pbLoadListVideoMain = view.findViewById(R.id.pb_load_list_video_main);
        rvListHotKeys = view.findViewById(R.id.lv_hot_keywords);
        rvListVideoMain = view.findViewById(R.id.rv_list_video_main);
        tvExplore = view.findViewById(R.id.tv_main_topic1);

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onRefresh() {
        ivLoadMore.setVisibility(View.GONE);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                rfMain.setRefreshing(false);
                ivLoadMore.setVisibility(View.VISIBLE);
            }

        }, 2000);
        listItemVideo.clear();
        positionStart = 0;
        positionEnd = 10;
        getJsonApiYoutube(positionStart, positionEnd);
        adapterMainVideoYoutube.notifyDataSetChanged();

    }
}
