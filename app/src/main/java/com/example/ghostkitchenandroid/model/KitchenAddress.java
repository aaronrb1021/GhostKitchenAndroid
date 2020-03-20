package com.example.ghostkitchenandroid.model;

public class KitchenAddress extends Address {

    private Kitchen kitchen;

    public KitchenAddress() {

    }

    public KitchenAddress(String addressName, String name, String addressLine1, String addressLine2, String city, String state, String zip, String phone) {
        super(addressName, name, addressLine1, addressLine2, city, state, zip, phone);
    }

    //    @OneToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "kitchen_id")
//    private Kitchen kitchen;

//    public Kitchen getKitchen() {
//        return kitchen;
//    }
//
//    public void setKitchen(Kitchen kitchen) {
//        this.kitchen = kitchen;
//    }
}
