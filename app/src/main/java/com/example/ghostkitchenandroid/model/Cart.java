package com.example.ghostkitchenandroid.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

public class Cart implements Serializable {

    private LinkedList<Item> items;
    private HashMap<Long, Integer> quantities;
    private int numOfItems;

    public Cart() {
        items = new LinkedList<>();
        quantities = new HashMap<>();
    }

    /**
     * Use this method to update an existing item to a new quantity. Also accepts
     * 0 as an argument and will remove the item when such an argument is received.
     * @param item
     * @param newQuantity
     * @return
     */
    public boolean updateQuantity(Item item, int newQuantity) {

        if (!quantities.containsKey(item.getId()))
            return false;

        if (newQuantity == 0) {
            remove(item);
            return true;
        }

        quantities.put(item.getId(), newQuantity);

        return true;
    }

    /**
     *
     * This method is called for both updating existing quantities in the cart and for placing new items in the cart.
     * @param item the item being added/updated in cart
     * @param quantity the quantity to be put or added in the cart.
     * @author Aaron
     */

    public void add(Item item, int quantity) {
        if (quantities.containsKey(item.getId())) {
            int currentQuantity = quantities.get(item.getId());
            quantities.put(item.getId(), currentQuantity + quantity);
        } else {
            items.add(item);
            quantities.put(item.getId(), quantity);
            numOfItems++;
        }
    }

    public boolean remove(Item item) {
        items.remove(item);
        quantities.remove(item.getId());
        numOfItems--;
        return true;
    }

    /**
     *
     * This method is called for removing a specific quantity of a given item from the cart. This method should not be called for
     * complete removal of the items from the cart. Instead call remove(Item item) for that purpose.
     * @param item the item being updated in the cart
     * @param quantity the quantity to be removed from the cart
     * @return <code>false</code> if the item isn't contained in the cart to be updated
     * @author Aaron
     */

    public boolean remove(Item item, int quantity) {
        if (quantities.containsKey(item.getId())) {
            int currentQuantity = quantities.get(item.getId());
            quantities.put(item.getId(), currentQuantity - quantity);
            return true;
        }
        return false;
    }

    public int getNumOfItems() {
        return numOfItems;
    }

    /**
     *
     * @return <code>double</code> value of all items in the cart multiplied by their respective quantities.
     * @author Aaron
     */
    public double getSubtotal() {
        double subtotal = 0;
        Iterator<Item> iterator = items.iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            subtotal += item.getPrice() * quantities.get(item.getId());
        }
        return subtotal;
    }

    /**
     *
     * @return <code>double</code> value of all items in the cart multiplied by their respective quantities, converted to a string
     * format "$%4.2f" ($X.XX)
     * @author Aaron
     */

    public String getSubtotalString() {
        return String.format("$%4.2f", getSubtotal());
    }

    public int getQuantityOfItem(Item item) {
        return quantities.get(item.getId());
    }

    public int getQuantityOfItem(int position) {
        return quantities.get(items.get(position).getId());
    }

    public Item getItem(int position) {
        return items.get(position);
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

}

