package com.example.ghostkitchenandroid.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {

    private long id;
    private String userName;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    @SerializedName("storeOwner")
    private boolean isStoreOwner;
    private ArrayList<Long> kitchenIds;
    private CartManager cartManager;

    public User() {

    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public User(String email, String password, String firstName, String lastName, String phoneNumber, boolean isStoreOwner) {
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.isStoreOwner = isStoreOwner;
        cartManager = new CartManager();
    }

    public ArrayList<Long> getKitchenIds() {
        return kitchenIds;
    }

    public void setKitchenIds(ArrayList<Long> kitchenIds) {
        this.kitchenIds = kitchenIds;
    }

    public boolean isStoreOwner() {
        return isStoreOwner;
    }

    public void setIsStoreOwner(boolean storeOwner) {
        isStoreOwner = storeOwner;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CartManager getCartManager() {
        if (cartManager == null)
            cartManager = new CartManager();

        return cartManager;
    }

    public void setCartManager(CartManager cartManager) {
        this.cartManager = cartManager;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
