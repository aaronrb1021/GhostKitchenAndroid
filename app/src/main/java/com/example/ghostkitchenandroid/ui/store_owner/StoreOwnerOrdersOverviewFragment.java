package com.example.ghostkitchenandroid.ui.store_owner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.model.StoreOwnerOrderOverview;
import com.example.ghostkitchenandroid.utils.Format;

public class StoreOwnerOrdersOverviewFragment extends Fragment {

    public static final String STORE_OWNER_ORDER_OVERVIEW_ARG = "StoreOwnerOrderOverview";

    private StoreOwnerOrderOverview storeOwnerOrderOverview;
    private TextView totalSales, totalOrders, currentPendingOrders, topKitchenInfo;

    public static StoreOwnerOrdersOverviewFragment newInstance(StoreOwnerOrderOverview storeOwnerOrderOverview) {
        StoreOwnerOrdersOverviewFragment fragment = new StoreOwnerOrdersOverviewFragment();
        Bundle args = new Bundle();
        args.putSerializable(STORE_OWNER_ORDER_OVERVIEW_ARG, storeOwnerOrderOverview);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            storeOwnerOrderOverview = (StoreOwnerOrderOverview) getArguments().get(STORE_OWNER_ORDER_OVERVIEW_ARG);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_overview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        configViews();
    }

    private void initViews(View view) {
        totalSales = view.findViewById(R.id.store_owner_overview_total_sales);
        totalOrders = view.findViewById(R.id.store_owner_overview_total_orders);
        currentPendingOrders = view.findViewById(R.id.store_owner_overview_pending_orders);
        topKitchenInfo = view.findViewById(R.id.store_owner_overview_top_kitchen_info);
    }

    private void configViews() {
        totalSales.setText(Format.getFormattedPrice(storeOwnerOrderOverview.getTotalSales()));
        totalOrders.setText(String.valueOf(storeOwnerOrderOverview.getTotalNumOfOrders()));
        currentPendingOrders.setText(String.valueOf(storeOwnerOrderOverview.getNumOfPendingOrders()));

        Kitchen topKitchen = storeOwnerOrderOverview.getTopKitchen();
        topKitchenInfo.setText(topKitchen.getName() + "\n" + storeOwnerOrderOverview.getNumOfOrdersByKitchen(topKitchen.getId()) + "\n" + storeOwnerOrderOverview.getSalesByKitchen(topKitchen.getId()));
    }
}
