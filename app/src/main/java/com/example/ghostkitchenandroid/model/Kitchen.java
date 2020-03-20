package com.example.ghostkitchenandroid.model;

public class Kitchen {

    private long id;
    private String name;
    private KitchenAddress kitchenAddress;

    public Kitchen() {

    }

    public Kitchen(String name, KitchenAddress kitchenAddress) {
        this.name = name;
        this.kitchenAddress = kitchenAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public KitchenAddress getKitchenAddress() {
        return kitchenAddress;
    }

    public void setKitchenAddress(KitchenAddress kitchenAddress) {
        this.kitchenAddress = kitchenAddress;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }
}
