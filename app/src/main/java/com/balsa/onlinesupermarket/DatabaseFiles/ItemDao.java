package com.balsa.onlinesupermarket.DatabaseFiles;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.balsa.onlinesupermarket.Item;

import java.util.List;

@Dao
public interface ItemDao {

    @Insert
    void insertItem(Item item);

    @Query("SELECT * FROM item")
    List<Item> getAllItems();

    @Query("UPDATE item SET rate=:newRate WHERE id=:itemID")
    void updateRate(int itemID,int newRate);

    @Query("SELECT * FROM item WHERE id=:itemID")
    Item getItemById(int itemID);

    @Query("UPDATE item SET reviews=:newReviews WHERE id=:itemId")
    void updateReviews(int itemId,String newReviews);

    @Query("SELECT * FROM item WHERE name LIKE :name")
    List<Item> searchByString(String name);

    @Query("SELECT DISTINCT category FROM item")
    List<String> getCategories();

    @Query("SELECT * FROM item WHERE category=:category")
    List<Item> getItemsByCategory(String category);

    @Query("UPDATE item SET popular=:newPoints WHERE id=:id")
    void updatePopularity(int id , int newPoints);

    @Query("UPDATE item SET userSuggestion=:newPoints WHERE id=:id")
    void updateUserSuggestion(int id , int newPoints);
}
