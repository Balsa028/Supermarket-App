package com.balsa.onlinesupermarket;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.balsa.onlinesupermarket.DatabaseFiles.ShopDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.util.ArrayList;


public class Utils {
    private static final String TAG = "Utils";
    private static int ORDER_ID = 0;


    public static ArrayList<Item> getAllItems(Context context) {
        return (ArrayList<Item>) ShopDatabase.getInstance(context).getItemDao().getAllItems();
    }

    public static void addItemToCart(Context context, Item item) {

        ShopDatabase.getInstance(context).getCartItemDao().insertCartItem(item.getId());

    }

    public static ArrayList<Item> getAllCartItems(Context context) {
       ArrayList<Item> cartItems = (ArrayList<Item>) ShopDatabase.getInstance(context).getCartItemDao().getAllCartItems();
        return cartItems;
    }

    public static void changeRate(Context context, int itemId, int newRate) {
        ShopDatabase.getInstance(context).getItemDao().updateRate(itemId,newRate);
    }

    public static void addReviews(Context context, Review review) {

        Item item = ShopDatabase.getInstance(context).getItemDao().getItemById(review.getItemID());
        ArrayList<Review> reviews = item.getReviews();
        if(reviews == null){
            reviews = new ArrayList<>();
        }
        reviews.add(review);

        Gson gson = new Gson();
        String jsonReviews = gson.toJson(reviews);
        ShopDatabase.getInstance(context).getItemDao().updateReviews(review.getItemID(),jsonReviews);

    }

    public static ArrayList<String> getCategories(Context context) {
        ArrayList<String> result = (ArrayList<String>) ShopDatabase.getInstance(context).getItemDao().getCategories();
        return result;
    }

    public static void DeleteCartItem(Context context, Item item) {
        ShopDatabase.getInstance(context).getCartItemDao().deleteCartItem(item.getId());
    }

    public static ArrayList<Item> searchByString(Context context, String text) {
        String searchString = "%"+text+"%";
        ArrayList<Item> result = (ArrayList<Item>) ShopDatabase.getInstance(context).getItemDao().searchByString(searchString);
        return result;
    }

    public static void removeItemsFromCart(Context context) {
       ShopDatabase.getInstance(context).getCartItemDao().deleteAllCartItems();
    }

    public static ArrayList<Item> getItemsByCategory(Context context, String category) {
        ArrayList<Item> result = (ArrayList<Item>) ShopDatabase.getInstance(context).getItemDao().getItemsByCategory(category);
        return result;
    }

    public static void increasePopularityPoint(Context context, Item item, int points) {

        int newPoints = item.getPopular()+points;
        ShopDatabase.getInstance(context).getItemDao().updatePopularity(item.getId(),newPoints);

    }

    public static void updateUserSuggestion(Context context, Item item, int points) {

        Log.d(TAG, "updateUserSuggestion: attepmting to add " + points + " to value of: " + item.getName());
        int newPoints = item.getUserSuggestion()+points;
        ShopDatabase.getInstance(context).getItemDao().updateUserSuggestion(item.getId(),newPoints);

    }

    public static ArrayList<Review> getReviews(Context context, int itemId) {

        Item item = ShopDatabase.getInstance(context).getItemDao().getItemById(itemId);
        ArrayList<Review> reviews = item.getReviews();
        return reviews;

    }

    public static String getLicences() {
        String licences = "Here goes licences for used libs";
        return licences;
    }

    public static int getOrderId() {
        ORDER_ID++;
        return ORDER_ID;
    }
}
