package com.balsa.onlinesupermarket.DatabaseFiles;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "cart_item")
public class CartItem {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int itemID;

    public CartItem(int itemID) {
        this.itemID = itemID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }
}
