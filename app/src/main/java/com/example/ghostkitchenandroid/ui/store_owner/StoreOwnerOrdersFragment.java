package com.example.ghostkitchenandroid.ui.store_owner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Order;
import com.example.ghostkitchenandroid.model.StoreOwnerOrderOverview;
import com.example.ghostkitchenandroid.network.user.UserRepo;
import com.example.ghostkitchenandroid.ui.customer.CustomerOrderFragment;
import com.example.ghostkitchenandroid.ui.lists.OrderListAdapter;
import com.example.ghostkitchenandroid.ui.lists.OrderListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class StoreOwnerOrdersFragment extends Fragment {

    private StoreOwnerOrdersViewModel storeOwnerOrdersViewModel;
    private BottomNavigationView bottomNavigationView;
    private View fragmentContainer;

    public static StoreOwnerOrdersFragment newInstance() {
        return new StoreOwnerOrdersFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_store_owner_orders, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.store_owner_orders_overview_title);

        storeOwnerOrdersViewModel = new ViewModelProvider(this).get(StoreOwnerOrdersViewModel.class);

        initViews(view);

        configBottomNav();

        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_store_owner_overview);

        setObservance();

    }

    private void initViews(View view) {
        bottomNavigationView = view.findViewById(R.id.store_owner_order_bottom_nav);
        fragmentContainer = view.findViewById(R.id.store_owner_order_container);
    }

    private void setObservance() {
        storeOwnerOrdersViewModel.getStoreOwnerOrderOverviewLiveData().observe(getViewLifecycleOwner(), storeOwnerOrderOverview -> {

            storeOwnerOrdersViewModel.setStoreOwnerOrderOverview(storeOwnerOrderOverview);
            switch (bottomNavigationView.getSelectedItemId()) {
                case R.id.bottom_nav_store_owner_overview:
                    getParentFragmentManager().beginTransaction().replace(fragmentContainer.getId(), StoreOwnerOrdersOverviewFragment.newInstance(storeOwnerOrderOverview)).commit();
                    break;
                case R.id.bottom_nav_store_owner_order_all:
                    onAllClick(storeOwnerOrderOverview.getAllOrders());
                    break;
                case R.id.bottom_nav_store_owner_order_pending:
                    onPendingClick(storeOwnerOrderOverview.getAllPendingOrders());
                    break;
            }

        });
        storeOwnerOrdersViewModel.getOrderLiveData().observe(getViewLifecycleOwner(), order -> {
            if (order != null) {
                storeOwnerOrdersViewModel.fetchStoreOwnerOrderOverview(UserRepo.getLoggedInUser());
                Toast.makeText(getContext(), "Order ID " + order.getId() + " updated!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Error updating order!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configBottomNav() {
        bottomNavigationView.setOnNavigationItemSelectedListener((MenuItem item) -> {
            storeOwnerOrdersViewModel.fetchStoreOwnerOrderOverview(UserRepo.getLoggedInUser());
            return true;
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

    private void handleFragmentTransaction(ArrayList<Order> orders, int emptyText) {
        getActivity().getSupportFragmentManager().beginTransaction().replace(fragmentContainer.getId(), OrderListFragment.newInstance(makeOrderListAdapter(orders, getString(emptyText))), "StoreOwnerListFragment").commit();
    }

    private OrderListAdapter makeOrderListAdapter(ArrayList<Order> orders, String emptyText) {
        return new OrderListAdapter(getContext(), orders) {
            @Override
            public void onCardClick(Order order) {
                new StoreOwnerOrderDialog(order, storeOwnerOrdersViewModel).show(getParentFragmentManager(), "StoreOwnerOrderDialog");
            }

            @Override
            public String getEmptyDisplayText() {
                return emptyText;
            }
        };
    }
}
