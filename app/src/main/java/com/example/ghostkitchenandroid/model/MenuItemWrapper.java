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
        if (o.isCategory && isCategory)
            return o.category.compareTo(category);
        else if (o.isCategory)
            return item.getCategory().compareTo(o.category);
        else if (!isCategory) //they item we item
            return item.compareTo(o.item);
        //they item and we cat
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
