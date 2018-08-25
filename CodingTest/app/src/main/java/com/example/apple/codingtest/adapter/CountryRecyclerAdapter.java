package com.example.apple.codingtest.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.apple.codingtest.R;
import com.example.apple.codingtest.model.Country;
import java.util.List;

public class CountryRecyclerAdapter extends RecyclerView.Adapter<CountryRecyclerAdapter.CountryViewHolder>{

    public List<Country> countryList;


    public CountryRecyclerAdapter(List<Country> countryList) {
        this.countryList = countryList;


    }

public class CountryViewHolder extends RecyclerView.ViewHolder {
    private TextView txtCountryName, txtCurrency, txtLanguage;
    public RelativeLayout viewBackground,viewForeground;

    public CountryViewHolder(View view) {
        super(view);
        txtCountryName = (TextView) view.findViewById(R.id.txtcountryName);
        txtCurrency = (TextView) view.findViewById(R.id.txtCurrency);
        txtLanguage = (TextView) view.findViewById(R.id.txtLanguage);
        viewBackground = view.findViewById(R.id.view_background);
        viewForeground = view.findViewById(R.id.view_foreground);
    }
}

    @Override
    public CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_item, parent, false);

        return new CountryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CountryViewHolder holder, int position) {
        Country country = countryList.get(position);
        holder.txtCountryName.setText(country.getCountryName());
        holder.txtCurrency.setText(country.getCurrency());
        holder.txtLanguage.setText(country.getLanguage());
        Log.i("Adapter",country.getLanguage());

    }

    @Override
    public int getItemCount() {
        return countryList.size();
    }

    public void removeItem(int position) {
        countryList.remove(position);

        notifyItemRemoved(position);
    }

    public void restoreItem(Country country, int position) {
        countryList.add(position, country);
        // notify item added by position
        notifyItemInserted(position);
    }

}
