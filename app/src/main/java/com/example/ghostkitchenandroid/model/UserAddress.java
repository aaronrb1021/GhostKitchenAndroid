package com.example.ghostkitchenandroid.model;

public class UserAddress extends Address {

    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
