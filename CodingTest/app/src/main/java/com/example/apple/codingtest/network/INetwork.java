package com.example.apple.codingtest.network;

import com.android.volley.VolleyError;

import org.json.JSONArray;

public interface INetwork {

    public void errorArray(VolleyError volleyError);
    public void successArray(JSONArray jsonArray);
}
