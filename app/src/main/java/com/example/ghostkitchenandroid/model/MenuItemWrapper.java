package com.example.ghostkitchenandroid.model;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MenuItemWrapper implements Comparable<MenuItemWrapper> {

    private Item item;
    private String category;
    private boolean isCategory;

    public MenuItemWrapper(Item item) {
        this.item = item;
    }

    public MenuItemWrapper(String category) {
        this.category = category;
        isCategory = true;
    }

    public Item getItem() {
        return item;
    }

    public String getCategory() {
        return category;
    }

    public boolean isCategory() {
        return isCategory;
    }

    @Override
    public int compareTo(MenuItemWrapper o) {
        if (o.isCategory && isCategory) //both categories
            return category.compareTo(o.category);
        else if (o.isCategory) { //o is category and we are item
            if (item.getCategory().equals(o.category))
                return 1;
            return item.getCategory().compareTo(o.category);
        } else if (!isCategory) {//both items
            if (item.getCategory().equals(o.item.getCategory()))
                return item.compareTo(o.item);
            return item.getCategory().compareTo(o.item.getCategory());
        }
        //o is item we are category
        if (category.equals(o.item.getCategory()))
            return -1; //returns a less than 0 value because our category should be listed above (lesser index) the items in that category
        return category.compareTo(o.item.getCategory());
    }

    @NonNull
    @Override
    public String toString() {
        if (isCategory)
            return category;
        return item.toString();
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj instanceof MenuItemWrapper) {
            MenuItemWrapper compareWrapper = (MenuItemWrapper) obj;
            if (isCategory && compareWrapper.isCategory) //both objects are categories
                return category.equals(compareWrapper.category);
            if (!isCategory && !compareWrapper.isCategory) //both objects are items
                return item.getId() == compareWrapper.item.getId();
            return false; //one object is item and other is category, so definitely not equal
        }
        return super.equals(obj);
    }
}
