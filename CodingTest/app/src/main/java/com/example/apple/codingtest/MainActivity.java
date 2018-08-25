package com.example.apple.codingtest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.apple.codingtest.adapter.CountryRecyclerAdapter;
import com.example.apple.codingtest.model.Country;
import com.example.apple.codingtest.network.INetwork;
import com.example.apple.codingtest.network.NetworkHandler;
import com.example.apple.codingtest.utils.RecyclerSwipeController;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/*
@Author Siva
this class is responsible for render the UI element and handle callback of swipe gesture
we have used Volley to handle network request while UI thread and worker thread are taken are by volley
*/

public class MainActivity extends Activity implements INetwork,RecyclerSwipeController.RecyclerSwipeListener {

    TextView t1;
    private RequestQueue mRequestQueue;
    List<Country> countryList;
    TextView txtHeader;
    RecyclerView recyclerView;
    CountryRecyclerAdapter countryRecyclerAdapter;
    INetwork networkCallback;
    NetworkHandler networkHandler;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkCallback =this;
        networkHandler=new NetworkHandler(this, networkCallback);
        t1 = (TextView) findViewById(R.id.txtDefaultText);
        countryList = new ArrayList<Country>();
        txtHeader = (TextView) findViewById(R.id.txtHeader);
        txtHeader.setText("Country List");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        getApiDataArray();
        
    }

    //this block handles the recycler items from arraylist and swipe gesture callback
    public void setUpRecyclerView() {

        countryRecyclerAdapter = new CountryRecyclerAdapter(countryList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(countryRecyclerAdapter);
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback = new RecyclerSwipeController(this, 0, ItemTouchHelper.LEFT, this);
        new ItemTouchHelper(itemTouchHelperCallback).attachToRecyclerView(recyclerView);

        //block the right swipe of swap directory to left only
        ItemTouchHelper.SimpleCallback itemTouchHelperCallback1 = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                // Row is swiped from recycler view
                // remove it from adapter

            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {


               // super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        // attaching the touch helper to recycler view
        new ItemTouchHelper(itemTouchHelperCallback1).attachToRecyclerView(recyclerView);


    }


    //this block helps to connect data to API and handles json response
    private void getApiDataArray() {

        dialog = new ProgressDialog(this);
        dialog.setTitle("please wait..");
        dialog.show();
        networkHandler.getAllCountryInfo();

    }

    /*
       this block handles the API data which is in JSON Array format we have used the build in JSONOBJECT it
       can be replaced with gson
     */
    private void parseResponseAndUpdateUI(JSONArray jsonArray) {



        for (int i = 0; i < jsonArray.length(); i++) {
            String name, language = null, currency = null;
            Country country = new Country();
            try {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                name = jsonObject.getString("name");
                Log.i("name", name);
                if (jsonObject != null) {
                    country.setCountryName(jsonObject.getString("name"));
                }

                JSONArray jsonArrayCurrency = jsonObject.getJSONArray("currencies");
                for (int j = 0; j < jsonArrayCurrency.length(); j++) {
                                    /* As if the element are more than one we will take the first one other wise
                                    is also true */
                    JSONObject currencyObject = jsonArrayCurrency.getJSONObject(0);
                    currency = currencyObject.getString("name");
                    Log.i("currency", currency);
                    country.setCurrency(currencyObject.getString("name"));
                }

                JSONArray jsonArrayLanguage = jsonObject.getJSONArray("languages");
                for (int k = 0; k < jsonArrayLanguage.length(); k++) {
                                          /* As if the element are more than one we will take the first one other wise
                                    is also true */
                    JSONObject languageObject = jsonArrayLanguage.getJSONObject(0);
                    language = languageObject.getString("name");
                    Log.i("language", name);
                    country.setLanguage(languageObject.getString("name"));
                }
                t1.setText(country.getCountryName() + country.getCurrency() + country.getLanguage());

                countryList.add(country);


            } catch (JSONException e) {

                Log.i("Country:", "" + e);
            }
        }


        setUpRecyclerView();
    }




    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position) {
        if (viewHolder instanceof CountryRecyclerAdapter.CountryViewHolder) {

            countryRecyclerAdapter.removeItem(viewHolder.getAdapterPosition());

        }
    }


    @Override
    public void errorArray(VolleyError volleyError) {
                Toast.makeText(this,""+volleyError,Toast.LENGTH_LONG).show();
    }

    @Override
    public void successArray(JSONArray jsonArray) {
        if(dialog.isShowing())dialog.dismiss();
        parseResponseAndUpdateUI(jsonArray);
    }
}
