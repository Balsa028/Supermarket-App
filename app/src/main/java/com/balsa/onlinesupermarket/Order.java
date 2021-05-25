package com.balsa.onlinesupermarket;

import com.bumptech.glide.util.Util;

import java.util.ArrayList;

public class Order {
    private int id;
    private String address;
    private String zipCode;
    private String email;
    private double totalPrice;
    private String phoneNumber;
    private ArrayList<Item> cartItems;
    private String paymentMethod;
    private String success;

    public Order(String address, String zipCode, String email, double totalPrice, String phoneNumber, ArrayList<Item> cartItems, String paymentMethod, String success) {
        this.id = Utils.getOrderId();
        this.address = address;
        this.zipCode = zipCode;
        this.email = email;
        this.totalPrice = totalPrice;
        this.phoneNumber = phoneNumber;
        this.cartItems = cartItems;
        this.paymentMethod = paymentMethod;
        this.success = success;
    }

    public Order() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public ArrayList<Item> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<Item> cartItems) {
        this.cartItems = cartItems;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", email='" + email + '\'' +
                ", totalPrice=" + totalPrice +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", cartItems=" + cartItems +
                ", paymentMethod='" + paymentMethod + '\'' +
                ", success='" + success + '\'' +
                '}';
    }
}
