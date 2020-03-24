package com.example.ghostkitchenandroid.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Order {

    private long id;
    private HashMap<Long, Item> itemHashMap;
    private String totalString;
    private double total;
    private byte status;
    private Date date;
    private BuyerDetails buyerDetails;

    //CONSTANTS
    public static final byte STATUS_PENDING = 0;
    public static final byte STATUS_READY = 1;
    public static final byte STATUS_COMPLETE = 2;
    public static final byte STATUS_CANCELLED = 3;

    public Order(HashMap<Long, Item> itemHashMap, BuyerDetails buyerDetails, double total) {
        this(itemHashMap, buyerDetails, total, STATUS_PENDING);
    }

    public Order(HashMap<Long, Item> itemHashMap, BuyerDetails buyerDetails, double total, byte status) {
        this.itemHashMap = itemHashMap;
        this.buyerDetails = buyerDetails;
        this.total = total;
        this.status = status;
        date = new Date(System.currentTimeMillis());
        totalToString();
    }

    private void totalToString() {
        totalString = String.format(Locale.US,"%4.2f", total);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public HashMap<Long, Item> getItemHashMap() {
        return itemHashMap;
    }

    public void setItemHashMap(HashMap<Long, Item> itemHashMap) {
        this.itemHashMap = itemHashMap;
    }

    public String getTotalString() {
        return totalString;
    }

    public void setTotalString(String totalString) {
        this.totalString = totalString;
    }

    public BuyerDetails getBuyerDetails() {
        return buyerDetails;
    }

    public void setBuyerDetails(BuyerDetails buyerDetails) {
        this.buyerDetails = buyerDetails;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
