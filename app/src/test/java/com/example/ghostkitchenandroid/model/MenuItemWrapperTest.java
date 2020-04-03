package com.example.ghostkitchenandroid.model;

import org.junit.Test;

import static org.junit.Assert.*;

public class MenuItemWrapperTest {

    @Test
    public void isCategory() {
        MenuItemWrapper itemWrapper1 = new MenuItemWrapper("Category Name"); //this should automatically be considered a category since it was initialized with a string instead of item
        MenuItemWrapper itemWrapper2 = new MenuItemWrapper(new Item()); //this will not be a category because it was initialized with an item

        assertTrue(itemWrapper1.isCategory());
        assertFalse(itemWrapper2.isCategory());
    }

    @Test
    public void compareTo() {
        Kitchen kitchen = new Kitchen(); //this will be our default kitchen for the below items/categories
        MenuItemWrapper item1Cat1 = new MenuItemWrapper(new Item("Item1", 33.6, "A", kitchen));
        MenuItemWrapper item2Cat1 = new MenuItemWrapper(new Item("Item2", 22.6, "A", kitchen));
        MenuItemWrapper item3Cat1 = new MenuItemWrapper(new Item("Item3", 55.63, "A", kitchen));
        MenuItemWrapper item4cat2 = new MenuItemWrapper(new Item("Item4", 10.19, "B", kitchen));
        MenuItemWrapper cat1 = new MenuItemWrapper("A");
        MenuItemWrapper cat2 = new MenuItemWrapper("B");
        MenuItemWrapper cat3 = new MenuItemWrapper("C");

        assertTrue(item1Cat1.compareTo(item2Cat1) > 0); //both items, same category
        assertTrue(item4cat2.compareTo(item1Cat1) > 0); //both item, different category
        assertTrue(item3Cat1.compareTo(cat1) > 0); //item compared to category, same category
        assertTrue(item3Cat1.compareTo(cat2) < 0); //item compared to category, different (greater) category
        assertTrue(item4cat2.compareTo(cat1) > 0); //item compared to category, different (lesser) cateogry
        assertTrue(cat1.compareTo(item1Cat1) < 0); //category compared to item, same category
        assertTrue(cat1.compareTo(item4cat2) < 0); //category compared to item, different (lesser) category
        assertTrue(cat1.compareTo(cat2) < 0); //category compared to category
    }

    @Test
    public void testEquals() {
    }
}