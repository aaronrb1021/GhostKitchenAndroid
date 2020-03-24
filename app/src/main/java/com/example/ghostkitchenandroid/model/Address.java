package com.example.ghostkitchenandroid.model;

import android.util.Log;

import androidx.annotation.NonNull;

public abstract class Address {

    protected String addressName, name, addressLine1, addressLine2, city, state, zip, phone;
    private long id;

    public Address() {

    }

    public Address(String addressName, String name, String addressLine1, String addressLine2, String city, String state, String zip, String phone) {
        this.addressName = addressName;
        this.name = name;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        formatPhone(phone);
    }

    private void formatPhone(String phone) {
        String phoneWithReplacements = phone.replace("[()- ]", "");
        Log.i("phoneWithReplacements", phoneWithReplacements);//todo
        if (phoneWithReplacements.length() == 10)
            this.phone = "(" + phoneWithReplacements.substring(0, 3) + ")" + "-" + phoneWithReplacements.substring(3, 6) + "-" + phoneWithReplacements.substring(6, 10);
        else if (phoneWithReplacements.length() == 11)
            this.phone = phoneWithReplacements.charAt(0) + "-(" + phoneWithReplacements.substring(1, 4) + ")" + "-" + phoneWithReplacements.substring(4, 7) + "-" + phoneWithReplacements.substring(7, 11);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getId() {
        return id;
    }

    public String getAddressName() {
        return addressName;
    }

    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @NonNull
    @Override
    public String toString() {
        return "Attn: " + name + ", " + addressLine1 + " " + addressLine2 + ", " + city + " " + state + " " + zip;
    }
}
