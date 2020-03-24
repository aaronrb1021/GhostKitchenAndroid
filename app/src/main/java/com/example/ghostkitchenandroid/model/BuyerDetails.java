package com.example.ghostkitchenandroid.model;

public class BuyerDetails {

    private long userId;
    private String name;
    private String phone;
    private Address address;

    public BuyerDetails(long userId, String name, String phone, Address address) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.address = address;
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

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
}
