package com.balsa.onlinesupermarket;

import android.app.DownloadManager;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.balsa.onlinesupermarket.DatabaseFiles.DataConverter;

import java.util.ArrayList;
@Entity(tableName = "item")
public class Item implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String description;
    private String imageURL;
    private String category;
    private double price;
    private int availableAmount;
    private int rate;
    private int userSuggestion;
    private int popular;
    @TypeConverters(DataConverter.class)
    private ArrayList<Review> reviews;

    //constructor for room database
    public Item(String name, String description, String imageURL, String category, double price, int availableAmount, int rate, int userSuggestion, int popular, ArrayList<Review> reviews) {
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.category = category;
        this.price = price;
        this.availableAmount = availableAmount;
        this.rate = rate;
        this.userSuggestion = userSuggestion;
        this.popular = popular;
        this.reviews = reviews;
    }

    @Ignore
    public Item(String name, String description, String imageURL, String category, double price, int availableAmount) {
        this.name = name;
        this.description = description;
        this.imageURL = imageURL;
        this.category = category;
        this.price = price;
        this.availableAmount = availableAmount;
        this.rate = 0;
        this.userSuggestion = 0;
        this.popular = 0;
        reviews = new ArrayList<>();
    }
    @Ignore
    protected Item(Parcel in) {
        id = in.readInt();
        name = in.readString();
        description = in.readString();
        imageURL = in.readString();
        category = in.readString();
        price = in.readDouble();
        availableAmount = in.readInt();
        rate = in.readInt();
        userSuggestion = in.readInt();
        popular = in.readInt();
    }
    @Ignore
    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getAvailableAmount() {
        return availableAmount;
    }

    public void setAvailableAmount(int availableAmount) {
        this.availableAmount = availableAmount;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public int getUserSuggestion() {
        return userSuggestion;
    }

    public void setUserSuggestion(int userSuggestion) {
        this.userSuggestion = userSuggestion;
    }

    public int getPopular() {
        return popular;
    }

    public void setPopular(int popular) {
        this.popular = popular;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }
    @Ignore
    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", imageURL='" + imageURL + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", availableAmount=" + availableAmount +
                ", rate=" + rate +
                ", userSuggestion=" + userSuggestion +
                ", popular=" + popular +
                ", reviews=" + reviews +
                '}';
    }
    @Ignore
    @Override
    public int describeContents() {
        return 0;
    }
    @Ignore
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(imageURL);
        dest.writeString(category);
        dest.writeDouble(price);
        dest.writeInt(availableAmount);
        dest.writeInt(rate);
        dest.writeInt(userSuggestion);
        dest.writeInt(popular);
    }
}
