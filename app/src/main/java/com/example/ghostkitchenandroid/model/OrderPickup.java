package com.example.ghostkitchenandroid.model;

//public class OrderPickup extends Order {
//
//    private String pickupName;
//
//    public OrderPickup(Cart cart, BuyerDetails buyerDetails, Kitchen kitchen, double taxFee, String pickupName) {
//        this(cart, buyerDetails, kitchen, taxFee, STATUS_PENDING, pickupName);
//    }
//
//    public OrderPickup(Cart cart, BuyerDetails buyerDetails, Kitchen kitchen, double taxFee, byte status, String pickupName) {
//        super(cart, buyerDetails, kitchen, taxFee, status);
//        this.pickupName = pickupName;
//    }
//
//    public String getPickupName() {
//        return pickupName;
//    }
//
//    public void setPickupName(String pickupName) {
//        this.pickupName = pickupName;
//    }
//
//    public static class OrderPickupBuilder {
//        private Cart cart;
//        private BuyerDetails buyerDetails;
//        private Kitchen kitchen;
//        private double taxFee;
//        private byte status;
//        private String pickupName;
//
//        public OrderPickupBuilder setCart(Cart cart) {
//            this.cart = cart;
//            return this;
//        }
//
//        public OrderPickupBuilder setBuyerDetails(BuyerDetails buyerDetails) {
//            this.buyerDetails = buyerDetails;
//            return this;
//        }
//
//        public OrderPickupBuilder setKitchen(Kitchen kitchen) {
//            this.kitchen = kitchen;
//            return this;
//        }
//
//        public OrderPickupBuilder setTaxFee(double taxFee) {
//            this.taxFee = taxFee;
//            return this;
//        }
//
//        public OrderPickupBuilder setOptionalStatus(byte status) {
//            this.status = status;
//            return this;
//        }
//
//        public OrderPickupBuilder setPickupName(String name) {
//            this.pickupName = name;
//            return this;
//        }
//
//        public OrderPickup create() {
//            if (status == Order.STATUS_PENDING)
//                return new OrderPickup(cart, buyerDetails, kitchen, taxFee, pickupName);
//            return new OrderPickup(cart, buyerDetails, kitchen, taxFee, status, pickupName);
//        }
//    }
//}
