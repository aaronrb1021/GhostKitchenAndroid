package com.example.ghostkitchenandroid.model;

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
            if (itemWrapperArrayList.contains(item.getCategory())) {
                itemWrapperArrayList.add(new MenuItemWrapper(item));
            } else {
                itemWrapperArrayList.add(new MenuItemWrapper(item.getCategory()));
                categoriesCount.getAndIncrement();
            }
        });
        Arrays.sort(itemWrapperArrayList.toArray());
    }

    private void initCategoriesArray(AtomicInteger categoriesCount) {
        categories = new String[categoriesCount.get()];
        itemWrapperArrayList.stream().forEach(itemWrapperSet -> {
            int index = 0;
            if (itemWrapperSet.isCategory())
                categories[index++] = itemWrapperSet.getCategory();
        });
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
