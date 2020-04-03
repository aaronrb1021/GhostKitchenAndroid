package com.example.ghostkitchenandroid.model;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class KitchenMenuTest {

    @Test
    public void getNumOfCategories() {
        ArrayList<Item> items = new ArrayList<>();
        Kitchen kitchen = new Kitchen();

        items.add(new Item("Item1", 23, "B", kitchen));
        items.add(new Item("Item2", 33.6, "A", kitchen));
        items.add(new Item("Item3", 3.6, "B", kitchen));
        items.add(new Item("Item4", 63.1, "K", kitchen));
        items.add(new Item("Item5", 3.56, "A", kitchen));

        KitchenMenu kitchenMenu = new KitchenMenu(items);

        assertEquals(3, kitchenMenu.getNumOfCategories());
    }

    @Test
    public void getItemWrapperList() {
        ArrayList<Item> items = new ArrayList<>();
        Kitchen kitchen = new Kitchen();

        items.add(new Item("Item1", 23, "B", kitchen));
        items.add(new Item("Item2", 33.6, "A", kitchen));
        items.add(new Item("Item3", 3.6, "B", kitchen));
        items.add(new Item("Item4", 63.1, "K", kitchen));
        items.add(new Item("Item5", 3.56, "A", kitchen));

        KitchenMenu kitchenMenu = new KitchenMenu(items);

        MenuItemWrapper item1catB = new MenuItemWrapper(items.get(0));
        MenuItemWrapper item2catA = new MenuItemWrapper(items.get(1));
        MenuItemWrapper item3catB = new MenuItemWrapper(items.get(2));
        MenuItemWrapper item4catK = new MenuItemWrapper(items.get(3));
        MenuItemWrapper item5catA = new MenuItemWrapper(items.get(4));
        MenuItemWrapper catA = new MenuItemWrapper("A");
        MenuItemWrapper catB = new MenuItemWrapper("B");
        MenuItemWrapper catK = new MenuItemWrapper("K");

        MenuItemWrapper[] correctArray = {
                catA,
                item5catA,
                item2catA,
                catB,
                item3catB,
                item1catB,
                catK,
                item4catK
        };

        assertArrayEquals(correctArray, kitchenMenu.itemWrapperArrayList.toArray());
    }

    @Test
    public void getCategories() {
        ArrayList<Item> items = new ArrayList<>();
        Kitchen kitchen = new Kitchen();

        items.add(new Item("Item1", 23, "B", kitchen));
        items.add(new Item("Item2", 33.6, "A", kitchen));
        items.add(new Item("Item3", 3.6, "B", kitchen));
        items.add(new Item("Item4", 63.1, "K", kitchen));
        items.add(new Item("Item5", 3.56, "A", kitchen));

        KitchenMenu kitchenMenu = new KitchenMenu(items);

        String[] correctArray = {
                "A",
                "B",
                "K"
        };

        assertArrayEquals(correctArray, kitchenMenu.getCategories());
    }
}