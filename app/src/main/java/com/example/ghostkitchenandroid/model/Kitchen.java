package com.example.ghostkitchenandroid.model;

import java.io.Serializable;

import androidx.annotation.NonNull;

public class Kitchen implements Comparable<Kitchen>, Serializable {

    private long id;
    private String name;
    private KitchenAddress kitchenAddress;
    private String imageBytes;

    public Kitchen() {

    }

    public Kitchen(String name, KitchenAddress kitchenAddress, String imageBytes) {
        this.name = name;
        this.kitchenAddress = kitchenAddress;
        this.imageBytes = imageBytes;
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

    public String getImageBytes() {
        return imageBytes;
    }

    public void setImageBytes(String imageBytes) {
        this.imageBytes = imageBytes;
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
