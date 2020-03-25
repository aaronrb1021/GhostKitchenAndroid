package com.example.ghostkitchenandroid.model;

public class BuyerDetails {

    private long userId;
    private String name;
    private String phone;
    private UserAddress userAddress;

    public BuyerDetails(long userId, String name, String phone, UserAddress userAddress) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.userAddress = userAddress;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public UserAddress getUserAddress() {
        return userAddress;
    }

    public void setUserAddress(UserAddress userAddress) {
        this.userAddress = userAddress;
    }
}
