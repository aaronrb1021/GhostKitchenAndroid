package com.example.ghostkitchenandroid.model;

import java.util.Locale;

public class Item {

    private long id;
    private String name;
    private double price;
    private String description;
    private String priceString;

    public Item(String name, double price) {
        this(name, price, "");
    }

    public Item(String name, double price, String description) {
        this.name = name;
        this.description = description;
        this.price = price;
        priceToString();
    }

    private void priceToString() {
        priceString = String.format(Locale.US,"%4.2f", price);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPriceString() {
        return priceString;
    }

    public void setPriceString(String priceString) {
        this.priceString = priceString;
    }
}
