package com.example.apple.codingtest.network;

import android.content.Context;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.apple.codingtest.config.ApiEndpoint;
import com.example.apple.codingtest.config.Config;

import org.json.JSONArray;
/*
        This class handles the network request and response and pass the
        data to the calling Activity/Screen
 */
public class NetworkHandler {

    private RequestQueue mRequestQueue;
    INetwork resultCallback;
    Context context;


    //constructor to pass context from Activity and interface or else it will give null pointer exception
    public NetworkHandler(Context context,INetwork resultCallback){
        this.context=context;
        this.resultCallback=resultCallback;

    }

    public void getAllCountryInfo()
    {
        mRequestQueue = Volley.newRequestQueue(context);
        JsonArrayRequest request = new JsonArrayRequest(Config.BASE_URL+ ApiEndpoint.GETALL,
                new Response.Listener<JSONArray>() {

                   //send the success response back to activity
                    @Override
                    public void onResponse(JSONArray jsonArray) {

                        resultCallback.successArray(jsonArray);
                    }
                },
                new Response.ErrorListener() {

                    //send the error response back to activity
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {

                        resultCallback.errorArray(volleyError);
                    }
                });

        mRequestQueue.add(request);
    }



}
