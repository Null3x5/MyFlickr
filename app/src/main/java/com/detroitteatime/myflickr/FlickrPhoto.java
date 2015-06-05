package com.detroitteatime.myflickr;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mark on 4/30/15.
 */
public class FlickrPhoto {
    private String id,day,min,max,night,eve,morn,wtf,description,city,country;

    public FlickrPhoto(JSONObject jsonDay,JSONObject data) throws JSONException {

        this.id = (String) jsonDay.optString("dt");

        this.day = (String) jsonDay.getJSONObject("temp").getString("day");
        this.min = (String) jsonDay.getJSONObject("temp").getString("min");
        this.max = (String) jsonDay.getJSONObject("temp").getString("max");
        this.night = (String) jsonDay.getJSONObject("temp").getString("night");
        this.eve = (String) jsonDay.getJSONObject("temp").getString("eve");
        this.morn = (String) jsonDay.getJSONObject("temp").getString("morn");


        this.setCity((String) data.getJSONObject("city").getString("name"));
        this.setCountry((String) data.getJSONObject("city").getString("country"));
        Log.i("supposedly a city", city);
        JSONArray arr = jsonDay.getJSONArray("weather");

        for (int i = 0; i < arr.length(); i++)
        {
            this.wtf = arr.getJSONObject(i).getString("id");
            this.description = arr.getJSONObject(i).getString("description");
        }

    }

    //breaks the list
    public static ArrayList<FlickrPhoto> makeDayList(String photoData) throws JSONException {
        ArrayList<FlickrPhoto> flickrPhotos = new ArrayList<>();
        JSONObject data = new JSONObject(photoData);
        JSONArray dayArray = data.optJSONArray("list");


        for (int i = 0; i < dayArray.length(); i++) {
            JSONObject day = (JSONObject) dayArray.get(i);
            FlickrPhoto currentDay = new FlickrPhoto(day,data);
            flickrPhotos.add(currentDay);
        }
        return flickrPhotos;
    }
    public String getCountry() {
        return country;
    }

    public void setCountry(String country) { this.country = country; }

    public String getCity() {
        return city;
    }

    public void setCity(String city) { this.city = city; }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String owner) {
        this.day = owner;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String secret) {
        this.min = secret;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String server) {
        this.max = server;
    }

    public String getNight() {
        return night;
    }

    public void setNight(String farm) {
        this.night = farm;
    }

    public String getEve() {
        return eve;
    }

    public void setEve(String title) {
        this.eve = title;
    }

    public String getMorn() {
        return morn;
    }

    public void setMorn(String isPublic) {
        this.morn = isPublic;
    }

    public String getWtf() {
        return wtf;
    }

    public void setWtf(String isFriend) {
        this.wtf = isFriend;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String isFamily) {
        this.description = isFamily;
    }

}
