package com.example.ghostkitchenandroid.model;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;

public class KitchenMenu {

//    private TreeSet<MenuItemWrapper> itemWrapperSet = new TreeSet<>();
    ArrayList<MenuItemWrapper> itemWrapperArrayList = new ArrayList<>();
    private String[] categories;

    public KitchenMenu(ArrayList<Item> items) {
        AtomicInteger categoriesCount = new AtomicInteger();
        initItemWrapperArrayList(items, categoriesCount);
        initCategoriesArray(categoriesCount);
    }

    private void initItemWrapperArrayList(ArrayList<Item> items, AtomicInteger categoriesCount) {
        items.stream().forEach(item -> {
            if (itemWrapperArrayList.contains(new MenuItemWrapper(item.getCategory()))) {
                itemWrapperArrayList.add(new MenuItemWrapper(item));
            } else {
                itemWrapperArrayList.add(new MenuItemWrapper(item.getCategory()));
                itemWrapperArrayList.add(new MenuItemWrapper(item));
                categoriesCount.getAndIncrement();
            }
        });
        Arrays.sort(itemWrapperArrayList.toArray());
        Log.i("itemwrapperlisttostring", itemWrapperArrayList.toString());
    }

    private void initCategoriesArray(AtomicInteger categoriesCount) {
        categories = new String[categoriesCount.get()];
        categoriesCount.set(0);
        itemWrapperArrayList.stream().forEach(itemWrapper -> {
            if (itemWrapper.isCategory())
                categories[categoriesCount.getAndIncrement()] = itemWrapper.getCategory();
        });
        Log.i("categoriestostring", Arrays.toString(categories));
    }

    public int getNumOfCategories() {
        return categories.length;
    }

    public ArrayList<MenuItemWrapper> getItemWrapperList() {
        return itemWrapperArrayList;
    }

    public String[] getCategories() {
        return categories;
    }
}
