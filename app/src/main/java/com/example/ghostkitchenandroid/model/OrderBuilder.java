package com.example.ghostkitchenandroid.model;

public class OrderBuilder {

    private double taxFee;
    private byte status;
    private Kitchen kitchen;
    private BuyerDetails buyerDetails;
    private Cart cart;
    private String pickupName;
    private double deliveryFee;
    private UserAddress deliveryAddress;
    private boolean isPickup;

    private OrderBuilder() {

    }

    public static OrderBuilder forPickup() {
        OrderBuilder orderBuilder = new OrderBuilder();
        orderBuilder.isPickup = true;
        return orderBuilder;
    }

    public static OrderBuilder forDelivery() {
        return new OrderBuilder();
    }

    public OrderBuilder setCart(Cart cart) {
        this.cart = cart;
        return this;
    }

    public OrderBuilder setBuyerDetails(BuyerDetails buyerDetails) {
        this.buyerDetails = buyerDetails;
        return this;
    }

    public OrderBuilder setKitchen(Kitchen kitchen) {
        this.kitchen = kitchen;
        return this;
    }

    public OrderBuilder setTaxFee(double taxFee) {
        this.taxFee = taxFee;
        return this;
    }

    public OrderBuilder setOptionalStatus(byte status) {
        this.status = status;
        return this;
    }

    public OrderBuilder setPickupName(String name) {
        this.pickupName = name;
        return this;
    }

    public OrderBuilder setDeliveryFee(double deliveryFee) {
        this.deliveryFee = deliveryFee;
        return this;
    }

    public OrderBuilder setDeliveryAddress(UserAddress deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
        return this;
    }

    public Order create() {
        if (status == Order.STATUS_PENDING && isPickup)
            return new Order(cart, buyerDetails, kitchen, taxFee, pickupName);
        else if (isPickup)
            return new Order(cart, buyerDetails, kitchen, taxFee, status, pickupName);
        else
            return new Order(cart, buyerDetails, kitchen, taxFee, status, deliveryAddress);
    }

}
