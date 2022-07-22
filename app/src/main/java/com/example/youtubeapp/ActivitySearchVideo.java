package com.example.youtubeapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.youtubeapp.adapter.AdapterHistorySearch;
import com.example.youtubeapp.interfacee.InterfaceClickSearch;
import com.example.youtubeapp.interfacee.InterfaceDefaultValue;
import com.example.youtubeapp.item.ItemSearch;
import com.example.youtubeapp.preferences.PrefSearch;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ActivitySearchVideo extends AppCompatActivity implements InterfaceDefaultValue {
    private static final int REQUEST_CODE_VALUE = 1000;
    private RecyclerView rvHistorySearch;
    public static AdapterHistorySearch adapterHistorySearch;
    public EditText etSearch;
    private ArrayList<ItemSearch> listItemSearch = new ArrayList<>();
    private ArrayList<ItemSearch> listRevert = new ArrayList<>();
    private ArrayList<String> listSearchString = new ArrayList<>();
    private TextView tvHistorySearch;
    private PrefSearch prefSearch;
    private ImageView ivBackHome, ivMic;

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_video);
        mapping();
        Log.d("CREATE: ", "Activity Search History");

        prefSearch = new PrefSearch(this);

        if (prefSearch.getArrayList(PREF_SEARCH) != null) {
            listSearchString = prefSearch.getArrayList(PREF_SEARCH);
            for (int i = 0; i < listSearchString.size(); i++) {
                listItemSearch.add(new ItemSearch(listSearchString.get(i) + ""));
//                Log.d("SIZEEEEEEEEE "+i, listSearchString.get(i)+"");
            }
            for (int i = listItemSearch.size() - 1; i > 0; i--) {
                listRevert.add(new ItemSearch(listItemSearch.get(i).getString()));
            }
        }
//        listSearchString = prefSearch.getArrayList(PREF_SEARCH);

        //get data Preferences

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvHistorySearch.setLayoutManager(linearLayoutManager);
        adapterHistorySearch = new AdapterHistorySearch(listRevert,
                new InterfaceClickSearch() {
                    @Override
                    public void onClickTextHistory(int position) {
                        etSearch.setText(listRevert.get(position).getString());
                        toValueSearch(listRevert.get(position).getString());
                    }

                    @Override
                    public void onClickIconRightHistory(int position) {
                        Toast.makeText(ActivitySearchVideo.this,
                                listRevert.get(position).getString() + "",
                                Toast.LENGTH_SHORT).show();
                        etSearch.setText(listRevert.get(position).getString());
                    }

                    @Override
                    public void onCLickFrameItem(int position) {
                        etSearch.setText(listRevert.get(position).getString());
                        toValueSearch(listRevert.get(position).getString());
                    }
                });
        rvHistorySearch.setAdapter(adapterHistorySearch);
        adapterHistorySearch.notifyDataSetChanged();
        etSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    Log.d("VALUEEEEEEEEEEEEEEEEE", etSearch.getText().toString()+"");
                    boolean isSave = false;
                    for (int i = 0; i < listSearchString.size(); i++) {
                        if ((etSearch.getText().toString()).equals(listSearchString.get(i))) {
                            Log.d(etSearch.getText().toString(), listSearchString.get(i));
                            isSave = true;
                        }
                    }
                    if (!isSave) {
                        listSearchString.add(etSearch.getText().toString() + "");
                    }
                    toValueSearch(etSearch.getText().toString() + "");
                }
                return false;
            }
        });

        ivBackHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ivMic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSpeak();
            }
        });

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                Log.d("beforeTextChanged", s+"");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("onTextChanged", s+"");

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("afterTextChanged", s+"");
                getValueSuggest(s+"");
            }
        });
    }

    public void getValueSuggest(String value){
        String url = "https://serpapi.com/search.json?engine=google_autocomplete&q="
                + value
                + "&api_key=3ff4d635aed8e716681c89fb69b0bd38ce716a7988bd16a9875cd6806b6dfbaf";
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonItems = response.getJSONArray(SUGGESTIONS);
                    Log.d("LENGTH SUGGEST: ", jsonItems.length()+"");
                    for(int i = 0; i<jsonItems.length(); i++){

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ActivitySearchVideo.this, error+" FALSE GET SUGGEST SEARCH !", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(jsonObjectRequest);
    }

    private void getSpeak(){
        try {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "please say something...");
            startActivityForResult(intent, REQUEST_CODE_VALUE);
        } catch(ActivityNotFoundException e) {
            String appPackageName = "com.google.android.googlequicksearchbox";
            try {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW,
                        Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        }
    }

    // This callback is invoked when the Speech Recognizer returns.
// This is where you process the intent and extract the speech text from the intent.
    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if (requestCode == REQUEST_CODE_VALUE && resultCode == RESULT_OK) {
            List<String> results = data.getStringArrayListExtra(
                    RecognizerIntent.EXTRA_RESULTS);
            String spokenText = results.get(0);
            toValueSearch(spokenText);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onResume() {
        adapterHistorySearch.notifyDataSetChanged();
        super.onResume();
    }

    public void toValueSearch(String value) {
        prefSearch.saveArrayList(listSearchString, PREF_SEARCH);
        Intent returnMain = new Intent(ActivitySearchVideo.this, MainActivity.class);
        returnMain.putExtra(VALUE_SEARCH, value + "");
        startActivity(returnMain);
    }


    public void mapping() {
        ivMic = findViewById(R.id.iv_mic);
        ivBackHome = findViewById(R.id.ic_back_search);
        rvHistorySearch = findViewById(R.id.rv_history_search);
        tvHistorySearch = findViewById(R.id.tv_suggest);
        etSearch = findViewById(R.id.et_search_video);
    }
}