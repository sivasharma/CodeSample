package com.example.apple.codingtest.model;

import java.io.Serializable;
import java.util.List;

/*
    This is the model class to hold the data it implements Serializable
    so that we can save state while passing objects
 */

public class Country implements Serializable{


    List<Country> countryList;
    String countryName,currency,language;

    public List<Country> getCountryList() {
        return countryList;
    }

    public void setCountryList(List<Country> countryList) {
        this.countryList = countryList;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }


    //this is used mostly for testing to print the object data
   /* @Override
    public String toString() {
        return "" "Country{" +
                ", language='" + language + '\'' +
                '}';

    }
*/
}
