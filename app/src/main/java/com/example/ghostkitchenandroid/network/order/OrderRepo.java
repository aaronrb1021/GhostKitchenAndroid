package com.example.ghostkitchenandroid.network.order;

import android.os.AsyncTask;
import android.util.Log;

import com.example.ghostkitchenandroid.model.Order;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class OrderRepo {

    private static OrderRepo orderRepo;

    private OrderService orderService = OrderServiceInstance.getInstance();
    private MutableLiveData<Order> orderLiveData;
    private MutableLiveData<ArrayList<Order>> orderListLiveData;

    public static OrderRepo getInstance() {
        if (orderRepo == null)
            orderRepo = new OrderRepo();

        return orderRepo;
    }

    public void createOrder(Order order) {
        new CreateOrderTask(this).execute(order);
    }

    public LiveData<Order> getOrderLiveData() {
        orderLiveData = new MutableLiveData<>();
        return orderLiveData;
    }

    public LiveData<ArrayList<Order>> getOrderListLiveData() {
        if (orderListLiveData == null)
            orderListLiveData = new MutableLiveData<>();
        return orderListLiveData;
    }

    private static class CreateOrderTask extends AsyncTask<Order, Void, Order> {

        private WeakReference<OrderRepo> orderRepoWeakReference;

        private CreateOrderTask(OrderRepo orderRepo) {
            orderRepoWeakReference = new WeakReference<>(orderRepo);
        }

        @Override
        protected Order doInBackground(Order... orders) {
            try {
                return orderRepoWeakReference.get().orderService.createOrder(orders[0]).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Order order) {
            if (order != null)
                orderRepoWeakReference.get().orderLiveData.setValue(order);
        }
    }
}
