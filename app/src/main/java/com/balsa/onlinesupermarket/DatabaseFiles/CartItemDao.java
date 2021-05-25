package com.balsa.onlinesupermarket.DatabaseFiles;

import androidx.room.Dao;
import androidx.room.Query;
import com.balsa.onlinesupermarket.Item;
import java.util.List;

@Dao
public interface CartItemDao {
    @Query("INSERT into cart_item (itemID) VALUES(:id)")
    void insertCartItem(int id);

    @Query("SELECT item.id,item.name,item.description,item.imageURL,item.category,item.price," +
            "item.availableAmount,item.rate,item.userSuggestion,item.popular,item.reviews FROM item JOIN cart_item ON " +
            "cart_item.itemId=item.id")
    List<Item> getAllCartItems();

    @Query("DELETE FROM cart_item WHERE itemID=:id")
    void deleteCartItem(int id);

    @Query("DELETE FROM cart_item")
    void deleteAllCartItems();

}
