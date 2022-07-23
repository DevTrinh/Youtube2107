package com.example.youtubeapp;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.youtubeapp.adapter.AdapterHistorySearch;
import com.example.youtubeapp.adapter.AdapterSuggestSearch;
import com.example.youtubeapp.interfacee.InterfaceClickSearch;
import com.example.youtubeapp.interfacee.InterfaceDefaultValue;
import com.example.youtubeapp.item.ItemSearch;
import com.example.youtubeapp.preferences.PrefSearch;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ActivitySearchVideo extends AppCompatActivity implements InterfaceDefaultValue {
    private static final int REQUEST_CODE_VALUE = 1000;
    private RecyclerView rvHistorySearch;
    private RecyclerView rvSuggestSearch;
    public static AdapterHistorySearch adapterHistorySearch;
    public static AdapterSuggestSearch adapterSuggestSearch;
    public EditText etSearch;
    private ArrayList<ItemSearch> listItemSearch = new ArrayList<>();
    private ArrayList<ItemSearch> listRevert = new ArrayList<>();
    private ArrayList<String> listSearchString = new ArrayList<>();

    private ArrayList<ItemSearch> listSuggestSearch = new ArrayList<>();
    private TextView tvHistorySearch;
    private PrefSearch prefSearch;
    private ImageView ivBackHome, ivMic, ivCancelSearch;

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
        LinearLayoutManager layoutManagerSuggest = new LinearLayoutManager(this);

        rvSuggestSearch.setLayoutManager(layoutManagerSuggest);

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
//                        Toast.makeText(ActivitySearchVideo.this,
//                                listRevert.get(position).getString() + "",
//                                Toast.LENGTH_SHORT).show();
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
                    if (!isSave && etSearch.getText().toString().trim().length() > 0) {
                        listSearchString.add(etSearch.getText().toString() + "");
                    }
                    if (etSearch.getText().toString().trim().length()>0){
                        toValueSearch(etSearch.getText().toString() + "");
                    }
                    return true;
                }
                return false;
            }
        });

        ivCancelSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etSearch.setText("");
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
                Log.d("beforeTextChanged", s + "");
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d("onTextChanged", s + "");

            }

            @Override
            public void afterTextChanged(Editable s) {
                Log.d("afterTextChanged", s + "");
                if (s.toString().trim().length() > 0){
                    rvHistorySearch.setVisibility(View.GONE);
                    ivCancelSearch.setVisibility(View.VISIBLE);
                    rvSuggestSearch.setVisibility(View.VISIBLE);
                    new ReadJSON().execute("http://suggestqueries.google.com/complete/search?client=firefox&ds=yt&q="+s);
                    adapterSuggestSearch = new AdapterSuggestSearch(listSuggestSearch,
                            new InterfaceClickSearch() {
                                @Override
                                public void onClickTextHistory(int position) {
                                    etSearch.setText(listSuggestSearch.get(position).getString());
                                    toValueSearch(listSuggestSearch.get(position).getString());
                                }

                                @Override
                                public void onClickIconRightHistory(int position) {
                                    etSearch.setText(listSuggestSearch.get(position).getString());
                                }

                                @Override
                                public void onCLickFrameItem(int position) {
                                    etSearch.setText(listSuggestSearch.get(position).getString());
                                    toValueSearch(listSuggestSearch.get(position).getString());
                                }
                            });
                    rvSuggestSearch.setAdapter(adapterSuggestSearch);
                }
                else{
                    ivCancelSearch.setVisibility(View.GONE);
                    rvSuggestSearch.setVisibility(View.GONE);
                    rvHistorySearch.setVisibility(View.VISIBLE);

                }
            }
        });
    }

    private void getSpeak() {
        try {
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "please say something...");
            startActivityForResult(intent, REQUEST_CODE_VALUE);
        } catch (ActivityNotFoundException e) {
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

    private class ReadJSON extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder content  = new StringBuilder();
            try {
                URL url  = new URL(strings[0]);
                InputStreamReader inputStreamReader  = new InputStreamReader(url.openConnection().getInputStream());
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line = "";
                while ((line = bufferedReader.readLine()) != null){
                    content.append(line);
                }
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @SuppressLint("NotifyDataSetChanged")
        @Override
        protected void onPostExecute(String s) {
            listSuggestSearch.clear();
            super.onPostExecute(s);
            byte ptext[] = new byte[0];
            try {
                ptext = s.getBytes("ISO-8859-1");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String value = null;
            try {
                value = new String(ptext, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            try {

                JSONArray jsonArray = new JSONArray(value);
                JSONArray jsonChild = jsonArray.getJSONArray(1);
//                Log.d("JSON ARRAY: ", jsonChild.length()+"");

                String itemValue ="";
                for (int i = 0; i<jsonChild.length(); i++){
                    itemValue = jsonChild.getString(i);
                    listSuggestSearch.add(new ItemSearch(itemValue));
                    Log.d("VALUE: ", itemValue);
                }
                adapterSuggestSearch.notifyDataSetChanged();
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("LENGHT: ", listSuggestSearch.size()+"");

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
        ivCancelSearch = findViewById(R.id.iv_x_in_search);
        rvSuggestSearch = findViewById(R.id.rv_suggest);
        ivMic = findViewById(R.id.iv_mic);
        ivBackHome = findViewById(R.id.ic_back_search);
        rvHistorySearch = findViewById(R.id.rv_history_search);
        tvHistorySearch = findViewById(R.id.tv_suggest);
        etSearch = findViewById(R.id.et_search_video);
    }
}