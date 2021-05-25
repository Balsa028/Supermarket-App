package com.balsa.onlinesupermarket.DatabaseFiles;

import android.app.ActivityOptions;

import androidx.room.TypeConverter;

import com.balsa.onlinesupermarket.Review;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DataConverter {

    @TypeConverter
    public String reviewToJson(ArrayList<Review> reviews){
        Gson gson = new Gson();
        String json = gson.toJson(reviews);
        return json;
    }
    @TypeConverter
    public ArrayList<Review> jsonToReviews (String json){
        Gson gson = new Gson();
        Type type = new TypeToken<ArrayList<Review>>(){}.getType();
        ArrayList<Review> reviews = gson.fromJson(json,type);
        return reviews;
    }

}
