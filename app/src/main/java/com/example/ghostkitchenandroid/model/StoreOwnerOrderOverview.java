package com.example.ghostkitchenandroid.model;

import java.io.Serializable;
import java.util.ArrayList;

public class StoreOwnerOrderOverview implements Serializable {

    ArrayList<ArrayList<Order>> ordersLists;

    public StoreOwnerOrderOverview(ArrayList<ArrayList<Order>> ordersLists) {
        this.ordersLists = ordersLists;
    }

    public ArrayList<ArrayList<Order>> getOrdersLists() {
        return ordersLists;
    }

    public void setOrdersLists(ArrayList<ArrayList<Order>> ordersLists) {
        this.ordersLists = ordersLists;
    }

    public Kitchen getTopKitchen() {
        Kitchen topKitchen = null;
        double currentTopSales = 0;

        for (ArrayList<Order> orderList : ordersLists) {
            double kitchenSales = getSalesByKitchen(orderList.get(0).getKitchen().getId());
            if (kitchenSales > currentTopSales) {
                currentTopSales = kitchenSales;
                topKitchen = orderList.get(0).getKitchen();
            }
        }

        return topKitchen;
    }

    public String[] getIncludedKitchenNames() {
        String[] names = new String[ordersLists.size()];

        for (int i = 0; i < ordersLists.size(); i++) {
            names[i] = ordersLists.get(i).get(0).getKitchen().getName(); //checks the first order in each order list to get the name of the kitchen for the list of orders
        }

        return names;
    }

    public int getTotalNumOfOrders() {
        int numOfOrders = 0;

        for (ArrayList<Order> orderList : ordersLists) {
            numOfOrders += orderList.size();
        }

        return numOfOrders;
    }

    public int getNumOfPendingOrders() {
        int numOfOrders = 0;

        for (ArrayList<Order> orderList : ordersLists) {
            for (Order o : orderList) {
                if (o.getStatus() == Order.STATUS_PENDING) {
                    numOfOrders++;
                }
            }
        }

        return numOfOrders;
    }

    public int getNumOfOrdersByKitchen(long id) {
        int numOfOrders = 0;

        for (ArrayList<Order> orderList : ordersLists) {
            if (orderList.get(0).getKitchen().getId() == id) {
                numOfOrders = orderList.size();
                break;
            }
        }

        return numOfOrders;
    }

    public double getTotalSales() {
        double totalSales = 0;

        for (ArrayList<Order> orderList : ordersLists) {
            for (Order order : orderList) {
                totalSales += order.getTotal();
            }
        }

        return totalSales;
    }

    public double getSalesByKitchen(long id) {
        double totalSales = 0;

        for (ArrayList<Order> orderList : ordersLists) {
            if (orderList.get(0).getKitchen().getId() == id) {
                for (Order o : orderList) {
                    totalSales += o.getTotal();
                }
                break;
            }
        }

        return totalSales;
    }

    public ArrayList<Order> getAllOrders() {
        ArrayList<Order> orders = new ArrayList<>();

        for (ArrayList<Order> orderList : ordersLists) {
            orders.addAll(orderList);
        }

        return orders;
    }

    public ArrayList<Order> getAllPendingOrders() {
        ArrayList<Order> orders = new ArrayList<>();

        for (ArrayList<Order> orderList : ordersLists) {
            for (Order o : orderList) {
                if (o.getStatus() == Order.STATUS_PENDING) {
                    orders.add(o);
                }
            }
        }

        return orders;
    }

    public ArrayList<Order> getOrdersByKitchen(long id) {
        ArrayList<Order> orders = new ArrayList<>();

        for (ArrayList<Order> orderList : ordersLists) {
            if (orderList.get(0).getKitchen().getId() == id) {
                orders.addAll(orderList);
                break;
            }
        }

        return orders;
    }

    public ArrayList<Order> getPendingOrdersByKitchen(long id) {
        ArrayList<Order> orders = new ArrayList<>();

        for (ArrayList<Order> orderList : ordersLists) {
            if (orderList.get(0).getKitchen().getId() == id) {
                for (Order o : orderList) {
                    if (o.getStatus() == Order.STATUS_PENDING) {
                        orders.add(o);
                    }
                }
                break;
            }
        }

        return orders;
    }

}
