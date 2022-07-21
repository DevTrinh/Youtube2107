package com.example.youtubeapp.fragment;

import static com.example.youtubeapp.fragment.FragmentHome.formatData;
import static com.example.youtubeapp.fragment.FragmentHome.listItemVideo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.youtubeapp.ActivityPlayVideo;
import com.example.youtubeapp.ActivitySearchVideo;
import com.example.youtubeapp.R;
import com.example.youtubeapp.adapter.AdapterValueSearchF;
import com.example.youtubeapp.interfacee.InterfaceClickFrame;
import com.example.youtubeapp.interfacee.InterfaceDefaultValue;
import com.example.youtubeapp.item.ItemValueSearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Objects;

public class FragmentValueSearch extends Fragment implements InterfaceDefaultValue {
    private static RecyclerView rvListValueSearch;
    private static AdapterValueSearchF adapterValueSearch;
    public static ArrayList<ItemValueSearch> listValueSearch = new ArrayList<>();
    private EditText etSearch;
    private ImageView backSearch;
    private ProgressBar pbLoad;
    private String API_SEARCH = "";
    private Intent intentSearchToPlayVideo;
    public static final String EXTRA_DATA = "EXTRA_DATA";

    @SuppressLint("ClickableViewAccessibility")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_value_search,
                container, false);
        intentSearchToPlayVideo = new Intent(getContext(), ActivityPlayVideo.class);
        String valueSearch = getArguments().getString(VALUE_SEARCH);
        API_SEARCH = "https://youtube.googleapis.com/youtube/v3/search?part=snippet&maxResults=50&q="
                + valueSearch + "&key=" + API_KEY + "";
        mapping(view);
        pbLoad.setVisibility(View.VISIBLE);
        listValueSearch.clear();
        etSearch.setText(valueSearch);
        getJsonValueSearch(API_SEARCH);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rvListValueSearch.setLayoutManager(linearLayoutManager);
//        Toast.makeText(getContext(), listValueSearch.size()
//                + "", Toast.LENGTH_SHORT).show();
        adapterValueSearch = new AdapterValueSearchF(listValueSearch,
                new InterfaceClickFrame() {
                    @Override
                    public void onClickTitle(int position) {
                        if (listValueSearch.get(position).getKind().equals(KIND_VIDEO)){
                            /*IMAGEVIEW ONCLICK*/
                            Log.d("TITLE ON CLICK: " + position,
                                    listValueSearch.get(position).getTitle());
                            intentSearchToPlayVideo.putExtra(VALUE_SEARCH,
                                    listValueSearch.get(position));
                            Log.d(listValueSearch.get(position).getIdListVideo(), "HUHUH");
//                        startActivity(intentSearchToPlayVideo);
                        }
                    }

                    @Override
                    public void onClickImage(int position) {
                        /*TITTLE ONCLICK*/
                        Log.d("IMAGE VIEW ON CLICK: " + position,
                                listValueSearch.get(position).getTitle());
                        intentSearchToPlayVideo.putExtra(VALUE_SEARCH,
                                listValueSearch.get(position));
                        startActivity(intentSearchToPlayVideo);
                    }

                    @Override
                    public void onClickMenu(int position) {
                        Toast.makeText(getContext(), "MENU", Toast.LENGTH_SHORT).show();
                        FragmentMenuItemVideoMain fragmentMenuItemVideoMain =
                                new FragmentMenuItemVideoMain();
                        fragmentMenuItemVideoMain.show(getActivity()
                                .getSupportFragmentManager(), getTag());
                    }

                    @Override
                    public void onClickAvtChannel(int position) {
                        Log.d("CHANNEL VIDEO: " + position,
                                listValueSearch.get(position).getTitle());
                    }

                    @Override
                    public void onClickSubs(int position) {
                        Toast.makeText(getContext(), "Subscriber", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onClickContains(int position) {
//                        SWITCH OVER FRAGMENT SEARCH
                       displayChannel(position);
                    }
                }, getContext());

        rvListValueSearch.setAdapter(adapterValueSearch);
//        CLICK X IN etSearch
        etSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (etSearch.getRight()
                            - etSearch.getCompoundDrawables()[DRAWABLE_RIGHT]
                            .getBounds().width())) {
                        ActivitySearchVideo.etSearch.setText("");
                        backSearch();
                        return true;
                    }
                }
                return false;
            }
        });

        etSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backSearch();
            }
        });

        backSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backSearch();
            }
        });
        Toast.makeText(getContext(), getActivity().getSupportFragmentManager().getBackStackEntryCount()+"", Toast.LENGTH_SHORT).show();
        return view;
    }

    private void displayChannel(int position){
        FragmentTransaction fragmentTransaction =
                getActivity().getSupportFragmentManager().beginTransaction();
        FragmentChannel fragmentChannel = new FragmentChannel(listValueSearch.get(position).getChannelId());
        fragmentTransaction.replace(R.id.cl_contains_search,
                fragmentChannel, FRAGMENT_CHANNEL);
        fragmentTransaction.addToBackStack(FRAGMENT_CHANNEL);
        fragmentTransaction.commit();
    }

    private void getAmountList(String idList, int position){
        String URL = "https://youtube.googleapis.com/youtube/v3/playlistItems?part=snippet&maxResults=50&playlistId="
                + idList+"&key="+API_KEY;
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String amount;
                    JSONObject jsonObject = response.getJSONObject(PAGE_INFO);
                    amount = jsonObject.getString(TOTAL_RESULTS);
                    listValueSearch.get(position).setNumberVideoList(amount);
//                    Toast.makeText(getContext(), amount
//                            +"", Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error+"", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void getInfoChannel(String ID_CHANNEL, int position, int size) {
        if (position < size) {
            RequestQueue requestQueue = Volley.newRequestQueue(getContext());
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                    "https://youtube.googleapis.com/youtube/v3/channels?part=snippet%2Cstatistics&id="
                            + ID_CHANNEL + "&key=" + API_KEY + "",
                    null, new Response.Listener<JSONObject>() {
                public void onResponse(JSONObject response) {
                    try {
                        JSONArray jsonItems = response.getJSONArray(ITEMS);
                        JSONObject jsonItem = jsonItems.getJSONObject(0);
                        JSONObject jsonSnippet = jsonItem.getJSONObject(SNIPPET);
                        JSONObject jsonThumbnail = jsonSnippet.getJSONObject(THUMBNAIL);
                        JSONObject jsonHigh = jsonThumbnail.getJSONObject(HIGH);
                        if (jsonThumbnail.has(HIGH)) {
                            listValueSearch.get(position).setUrlAvtChannel(jsonHigh.getString(URL) + "");
//                            Log.d("LINKKKKKKKK " + position, jsonHigh.getString(URL));
                        }
                        JSONObject jsonStatics = jsonItem.getJSONObject(STATISTICS);
                        if (jsonStatics.has(SUBSCRIBE_COUNT)) {
                            listValueSearch.get(position).setNumberSubscribe
                                    (formatData(Integer.parseInt
                                            (jsonStatics.getString(SUBSCRIBE_COUNT)))
                                            + " Subscribers");
//                    Log.d("AAAAA " + position, urlChannel);
                        }

                        adapterValueSearch.notifyItemChanged(position);
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
    }

    private void backSearch() {
        ActivitySearchVideo.adapterHistorySearch.notifyItemChanged(0);
        getActivity().finish();
        Toast.makeText(getActivity(), "BACK", Toast.LENGTH_SHORT).show();
    }

    private void getJsonValueSearch(String API_SEARCH) {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                API_SEARCH, null, new Response.Listener<JSONObject>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String idVideo = "";
                    String timeUp = "";
                    String channelId = "";
                    String titleVideo = "";
                    String descriptionVideo = "";
                    String urlImage = "";
                    String channelTitle = "";
                    String viewCount = "";
                    String kindSearch = "";
                    String idListVideo = "";

                    JSONArray jsonItems = response.getJSONArray(ITEMS);
                    Log.d("JSON ITEMS: ", jsonItems.length() + "");

                    int size = 0;
                    for (int i = 0; i < jsonItems.length(); i++) {
                        JSONObject jsonItem = jsonItems.getJSONObject(i);
                        JSONObject jsonId = jsonItem.getJSONObject(ID);
                        kindSearch = jsonId.getString(KIND);
                        Log.d("KINDDDD", kindSearch);

                        if (kindSearch.equals(KIND_VIDEO)){
                            idVideo = jsonId.getString(ID_VIDEO);
//                            Log.d("VIDEO ID: "+i, idVideo);
                            JSONObject jsonSnippet = jsonItem.getJSONObject(SNIPPET);
//                            Log.d("JSON SNIPPET: "+i,FragmentHome.formatTimeUpVideo(jsonSnippet.getString(PUBLISHED_AT)));
                            timeUp = FragmentHome.formatTimeUpVideo(jsonSnippet.getString(PUBLISHED_AT));
                            channelId = jsonSnippet.getString(CHANNEL_ID);
//                            Log.d("CHANNEL ID: "+i, channelId);
                            getInfoChannel(channelId, i, jsonItems.length());
                            titleVideo = jsonSnippet.getString(TITLE);
//                            Log.d("TITLE CHANNEL: "+i, titleVideo);
                            descriptionVideo = jsonSnippet.getString(DESCRIPTION);
                            JSONObject jsonThumbnails = jsonSnippet.getJSONObject(THUMBNAIL);
                            JSONObject jsonHigh = jsonThumbnails.getJSONObject(HIGH);
                            urlImage = jsonHigh.getString(URL);
//                            Log.d("URL IMAGE: " + i, urlImage);
                            channelTitle = jsonSnippet.getString(CHANNEL_TITLE);
//                            Log.d("CHANNEL TITLE: "+i, channelTitle);
                            viewCount = "1.5M views";
//                            Log.d("ID CHANNEL: "+i, channelId);
                            listValueSearch.add(new ItemValueSearch(kindSearch,idVideo,
                                    timeUp, channelId, titleVideo,
                                    descriptionVideo, urlImage,
                                    channelTitle, viewCount, idListVideo));
                        }

                        if (kindSearch.equals(KIND_LIST)){
                            idListVideo = jsonId.getString(PLAY_LIST_ID);
//                            Log.d("LISTTTTTTTTTTTTTTTT", idListVideo);
                            getAmountList(idListVideo, i);
//                            Toast.makeText(getContext(), "HAHAHAHAHA", Toast.LENGTH_SHORT).show();
                            JSONObject jsonSnippet = jsonItem.getJSONObject(SNIPPET);
//                            Log.d("JSON SNIPPET: "+i,FragmentHome.formatTimeUpVideo(jsonSnippet.getString(PUBLISHED_AT)));
                            timeUp = FragmentHome.formatTimeUpVideo(jsonSnippet.getString(PUBLISHED_AT));
                            channelId = jsonSnippet.getString(CHANNEL_ID);
//                            Log.d("CHANNEL ID: "+i, channelId);
                            getInfoChannel(channelId, i - size, jsonItems.length());
                            titleVideo = jsonSnippet.getString(TITLE);
//                            Log.d("TITLE CHANNEL: "+i, titleVideo);
                            descriptionVideo = jsonSnippet.getString(DESCRIPTION);
                            JSONObject jsonThumbnails = jsonSnippet.getJSONObject(THUMBNAIL);
                            JSONObject jsonHigh = jsonThumbnails.getJSONObject(HIGH);
                            urlImage = jsonHigh.getString(URL);
//                            Log.d("URL IMAGE: " + i, urlImage);
                            channelTitle = jsonSnippet.getString(CHANNEL_TITLE);

                            listValueSearch.add(new ItemValueSearch(kindSearch,idVideo,
                                    timeUp, channelId, titleVideo,
                                    descriptionVideo, urlImage,
                                    channelTitle, viewCount, idListVideo));
                        }

                        if (kindSearch.equals(KIND_CHANNEL)){

//                            Toast.makeText(getContext(), "HAHAHAHAHA", Toast.LENGTH_SHORT).show();
                            JSONObject jsonSnippet = jsonItem.getJSONObject(SNIPPET);
//                            Log.d("JSON SNIPPET: "+i,FragmentHome.formatTimeUpVideo(jsonSnippet.getString(PUBLISHED_AT)));
                            timeUp = FragmentHome.formatTimeUpVideo(jsonSnippet.getString(PUBLISHED_AT));
                            channelId = jsonSnippet.getString(CHANNEL_ID);
//                            Log.d("CHANNEL ID: "+i, channelId);
                            getInfoChannel(channelId, i - size, jsonItems.length());
                            titleVideo = jsonSnippet.getString(TITLE);
//                            Log.d("TITLE CHANNEL: "+i, titleVideo);
                            descriptionVideo = jsonSnippet.getString(DESCRIPTION);
                            JSONObject jsonThumbnails = jsonSnippet.getJSONObject(THUMBNAIL);
                            JSONObject jsonHigh = jsonThumbnails.getJSONObject(HIGH);
                            urlImage = jsonHigh.getString(URL);
                            Log.d("URL IMAGE: " + i, urlImage);
                            channelTitle = jsonSnippet.getString(CHANNEL_TITLE);
                            listValueSearch.add(new ItemValueSearch(kindSearch,idVideo,
                                    timeUp, channelId, titleVideo,
                                    descriptionVideo, urlImage,
                                    channelTitle, viewCount, idListVideo));
                        }
                        pbLoad.setVisibility(View.GONE);
                    }
                } catch (JSONException ignored) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error + " Co len ban manh a",
                        Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);

    }

    public void mapping(@NonNull View view) {
        pbLoad = view.findViewById(R.id.pb_load_video_search);
        backSearch = view.findViewById(R.id.iv_back_after_search);
        rvListValueSearch = view.findViewById(R.id.rv_list_value_search);
        etSearch = view.findViewById(R.id.et_after_search);
    }
}
