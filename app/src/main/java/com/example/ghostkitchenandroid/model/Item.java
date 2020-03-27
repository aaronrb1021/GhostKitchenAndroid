package com.example.ghostkitchenandroid.model;

import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class Item implements Comparable<Item> {

    private long id;
    private String name;
    private double price;
    private String description;
    private String category;
    private String priceString;
    private Kitchen kitchen;

    public Item() {

    }

    public Item(String name, double price, String category, Kitchen kitchen) {
        this(name, price, category, kitchen, "");
    }

    public Item(String name, double price, String category, Kitchen kitchen, String description) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.description = description;
        this.kitchen = kitchen;
        priceToString();
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setKitchen(Kitchen kitchen) {
        this.kitchen = kitchen;
    }

    private void priceToString() {
        priceString = String.format(Locale.US,"$%4.2f", price);
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

    @Override
    public int compareTo(Item o) {
        if (category.equals(o.category))
            return (int) (o.price - price);
        return category.compareTo(o.category);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof Item)
            return id == ((Item) obj).id;
        return super.equals(obj);
    }

    @NonNull
    @Override
    public String toString() {
        return "Item:[name = " + name + ", price = " + priceString + ", category = " + category + "]";
    }
}
