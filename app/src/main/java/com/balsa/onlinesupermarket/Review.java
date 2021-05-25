package com.balsa.onlinesupermarket;

public class Review {
    private int itemID;
    private String username;
    private String text;
    private String date;

    public Review(int itemID, String username, String text, String date) {
        this.itemID = itemID;
        this.username = username;
        this.text = text;
        this.date = date;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Review{" +
                "itemID=" + itemID +
                ", username='" + username + '\'' +
                ", text='" + text + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
