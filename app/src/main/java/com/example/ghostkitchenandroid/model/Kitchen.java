package com.example.ghostkitchenandroid.model;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class Kitchen implements Comparable<Kitchen>, Serializable {

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

    @Override
    public int compareTo(Kitchen o) {
        if (id == o.getId())
            return 0;
        return name.compareTo(o.getName());
    }

    @NonNull
    @Override
    public String toString() {
        return "Kitchen: " + name + "\n" + kitchenAddress.toString();
    }
}
