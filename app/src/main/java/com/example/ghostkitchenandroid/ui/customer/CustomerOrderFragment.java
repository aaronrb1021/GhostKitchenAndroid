package com.example.ghostkitchenandroid.ui.customer;

import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Order;
import com.example.ghostkitchenandroid.ui.lists.OrderListAdapter;
import com.example.ghostkitchenandroid.ui.lists.OrderListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class CustomerOrderFragment extends Fragment {

    private CustomerOrderViewModel customerOrderViewModel;
    private View fragmentContainer;
    private BottomNavigationView bottomNavigationView;

    public static CustomerOrderFragment newInstance() {
        return new CustomerOrderFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_order, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        customerOrderViewModel = new ViewModelProvider(this).get(CustomerOrderViewModel.class);

        setObservance();

        initViews(view);

        configBottomNav();
    }

    private void initViews(View view) {
        fragmentContainer = view.findViewById(R.id.customer_order_container);
        bottomNavigationView = view.findViewById(R.id.customer_order_bottom_nav);
    }

    private void configBottomNav() {
        bottomNavigationView.setOnNavigationItemSelectedListener((MenuItem item) -> {
            customerOrderViewModel.fetchOrders();
            return true;
        });
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_customer_order_pending);

    }

    private void setObservance() {
        customerOrderViewModel.getOrderListLiveData().observe(getViewLifecycleOwner(), orders -> {
            switch (bottomNavigationView.getSelectedItemId()) {
                case R.id.bottom_nav_customer_order_pending:
                    onPendingClick(orders);
                    break;
                case R.id.bottom_nav_customer_order_all:
                    onAllClick(orders);
                    break;
                case R.id.bottom_nav_customer_order_completed:
                    onCompletedClick(orders);
                    break;
            }
        });
    }

    private void onPendingClick(ArrayList<Order> orders) {
        ArrayList<Order> pendingOrders = new ArrayList<>();

        orders.stream().forEach(order -> {
            if (order.getStatus() == Order.STATUS_PENDING)
                pendingOrders.add(order);
        });

        handleFragmentTransaction(pendingOrders, R.string.no_pending_orders);
    }

    private void onAllClick(ArrayList<Order> orders) {
        handleFragmentTransaction(orders, R.string.no_orders);
    }

    private void onCompletedClick(ArrayList<Order> orders) {
        ArrayList<Order> completedOrders = new ArrayList<>();

        orders.stream().forEach(order -> {
            if (order.getStatus() == Order.STATUS_COMPLETE)
                completedOrders.add(order);
        });

        handleFragmentTransaction(completedOrders, R.string.no_completed_orders);
    }

    private OrderListAdapter makeOrderListAdapter(ArrayList<Order> orders, String emptyText) {
        return new OrderListAdapter(getContext(), orders) {
            @Override
            public void onCardClick(Order order) {

            }

            @Override
            public String getEmptyDisplayText() {
                return emptyText;
            }
        };
    }

    private void handleFragmentTransaction(ArrayList<Order> orders, int emptyText) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(fragmentContainer.getId(), OrderListFragment.newInstance(makeOrderListAdapter(orders, getString(emptyText))), "CustomerOrderListFragment").commit();
    }

}
