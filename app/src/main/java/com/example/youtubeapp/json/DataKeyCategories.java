package com.example.youtubeapp.json;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.youtubeapp.interfacee.InterfaceDefaultValue;

import org.json.JSONObject;

public class DataKeyCategories implements InterfaceDefaultValue {
    private Context context;
    private String url = PATH_API
            +"videoCategories?part=snippet&regionCode=VN&key="
            +API_KEY;

    public DataKeyCategories(Context context) {
        this.context = context;
    }

    public void getJsonVideoCategories(){
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url
                , null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("ERROOO: ",error+"");
            }
        });
        requestQueue.add(jsonObjectRequest);
    }
}
