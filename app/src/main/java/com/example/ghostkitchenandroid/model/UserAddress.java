package com.example.ghostkitchenandroid.model;

public class UserAddress extends Address {

    private User user;

    public UserAddress() {
        super("", "", "N/A", "PICKUP", "", "", "", "");
    }

    public UserAddress(String addressName, String name, String addressLine1, String addressLine2, String city, String state, String zip, String phone) {
        super(addressName, name, addressLine1, addressLine2, city, state, zip, phone);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
