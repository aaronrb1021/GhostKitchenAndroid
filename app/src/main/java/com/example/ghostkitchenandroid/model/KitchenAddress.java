package com.example.ghostkitchenandroid.model;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class KitchenAddress extends Address implements Serializable {

    private Kitchen kitchen;

    public KitchenAddress() {

    }

    public KitchenAddress(String addressName, String name, String addressLine1, String addressLine2, String city, String state, String zip, String phone) {
        super(addressName, name, addressLine1, addressLine2, city, state, zip, phone);
    }

    @NonNull
    @Override
    public String toString() {
        if (addressLine2 == null)
            return addressLine1 + "\n" + city + ", " + state + " " + zip;
        return addressLine1 + " " + addressLine2 + "\n" + city + ", " + state + " " + zip;
    }

}
