package com.example.ghostkitchenandroid.model;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Order {

    private long id;
    @SerializedName("pickup")
    private boolean isPickup;
    private double deliveryFee;
    private double taxFee;
    private byte status;
    private long orderDateInMillis;
    private String pickupName;
    private Kitchen kitchen;
    private UserAddress deliveryAddress;
    private BuyerDetails buyerDetails;
    private Cart cart;

    //CONSTANTS
    public static final byte STATUS_PENDING = 0;
    public static final byte STATUS_READY = 1;
    public static final byte STATUS_COMPLETE = 2;
    public static final byte STATUS_CANCELLED = 3;

    public Order() {

    }

    /**
     * This constructor should be used for submitted a pickup order with default PENDING status.
     * @param cart
     * @param buyerDetails
     * @param kitchen
     * @param taxFee
     */
    public Order(Cart cart, BuyerDetails buyerDetails, Kitchen kitchen, double taxFee, String pickupName) {
        this(cart, buyerDetails, kitchen, taxFee, STATUS_PENDING, pickupName);
    }

    /**
     * This constructor should be used for a pickup order with a custom status.
     * @param cart
     * @param buyerDetails
     * @param kitchen
     * @param taxFee
     * @param status
     */
    public Order(Cart cart, BuyerDetails buyerDetails, Kitchen kitchen, double taxFee, byte status, String pickupName) {
        this(cart, buyerDetails, kitchen, taxFee, status, new UserAddress());
        this.pickupName = pickupName;
        isPickup = true;
    }

    /**
     * Constructor for delivery order with default PENDING status.
     * @param cart
     * @param buyerDetails
     * @param kitchen
     * @param taxFee
     * @param deliveryAddress
     */
    public Order(Cart cart, BuyerDetails buyerDetails, Kitchen kitchen, double taxFee, UserAddress deliveryAddress) {
        this(cart, buyerDetails, kitchen, taxFee, STATUS_PENDING, deliveryAddress);
    }

    /**
     * Constructor for delivery with custom status.
     * @param cart
     * @param buyerDetails
     * @param kitchen
     * @param taxFee
     * @param status
     * @param deliveryAddress
     */
    public Order(Cart cart, BuyerDetails buyerDetails, Kitchen kitchen, double taxFee, byte status, UserAddress deliveryAddress) {
        this.cart = cart;
        this.buyerDetails = buyerDetails;
        this.taxFee = taxFee;
        this.status = status;
        this.kitchen = kitchen;
        this.deliveryAddress = deliveryAddress;
        orderDateInMillis = System.currentTimeMillis();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public BuyerDetails getBuyerDetails() {
        return buyerDetails;
    }

    public void setBuyerDetails(BuyerDetails buyerDetails) {
        this.buyerDetails = buyerDetails;
    }

    public double getTotal() {
        return cart.getSubtotal() + taxFee + deliveryFee;
    }

    public String getTotalString() {
        return String.format(Locale.US,"$%4.2f", getTotal());
    }

    public byte getStatus() {
        return status;
    }

    public void setStatus(byte status) {
        this.status = status;
    }

    public long getOrderDateInMillis() {
        return orderDateInMillis;
    }

    public void setOrderDateInMillis(long orderDateInMillis) {
        this.orderDateInMillis = orderDateInMillis;
    }

    public double getDeliveryFee() {
        return deliveryFee;
    }

    public void setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    public double getTaxFee() {
        return taxFee;
    }

    public void setTaxFee(double taxFee) {
        this.taxFee = taxFee;
    }

    public Cart getCart() {
        return cart;
    }

    public Kitchen getKitchen() {
        return kitchen;
    }

    public void setKitchen(Kitchen kitchen) {
        this.kitchen = kitchen;
    }

    public UserAddress getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(UserAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public boolean isPickup() {
        return isPickup;
    }

    public void setPickup(boolean isPickup) {
        this.isPickup = isPickup;
    }

    public String getPickupName() {
        return pickupName;
    }

    public void setPickupName(String pickupName) {
        this.pickupName = pickupName;
    }

    public String getDateString() {
        Date date = new Date(orderDateInMillis);

        return date.toString();
    }
}