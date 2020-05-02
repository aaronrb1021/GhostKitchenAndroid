package com.example.ghostkitchenandroid.model;

import java.io.Serializable;

public class UserAddress extends Address implements Serializable {

    private User user;

    public UserAddress() {
        super("", "", "N/A", "PICKUP", "", "", "", "");
    }

    public UserAddress(User user, String name, String addressLine1, String addressLine2, String city, String state, String zip, String phone) {
        super("", name, addressLine1, addressLine2, city, state, zip, phone);
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
