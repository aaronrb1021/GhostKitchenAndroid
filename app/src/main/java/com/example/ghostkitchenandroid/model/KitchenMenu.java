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

    /**
     * This method will wrap each of our items in a wrapper and will wrap each UNIQUE category into a wrapper of the same class.
     * These wrappers will then be sorted to create a proper menu, consisting of a category, followed by the items in said category, so on until all items have been accounted for.
     * @param items List of items to be wrapped and sorted into a menu
     * @param categoriesCount Keeps track of the number of categories we have
     */
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
        Collections.sort(itemWrapperArrayList); //uses the sorting logic in our MenuItemWrapper
    }

    private void initCategoriesArray(AtomicInteger categoriesCount) {
        categories = new String[categoriesCount.get()];
        categoriesCount.set(0);
        itemWrapperArrayList.stream().forEach(itemWrapper -> {
            if (itemWrapper.isCategory())
                categories[categoriesCount.getAndIncrement()] = itemWrapper.getCategory();
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
