package com.example.ghostkitchenandroid.ui.lists;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Order;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class OrderListFragment extends Fragment {

    private OrderListViewModel orderListViewModel;
    private RecyclerView orderListRecycler;
    private OrderListAdapter orderListAdapter;
    private TextView textView;

    public static OrderListFragment newInstance(OrderListAdapter orderListAdapter) {
        OrderListFragment orderListFragment = new OrderListFragment();
        orderListFragment.orderListAdapter = orderListAdapter;
        return orderListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_order_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        orderListViewModel = new ViewModelProvider(this).get(OrderListViewModel.class);

        initViews(view);

        config();
    }

    private void initViews(View view) {
        orderListRecycler = view.findViewById(R.id.order_list_recycler);
        textView = view.findViewById(R.id.order_list_tv);


    }

    private void config() {
        orderListRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        if (orderListAdapter != null) {
            if (orderListAdapter.getItemCount() > 0) {
                orderListRecycler.setAdapter(orderListAdapter);
            } else {
                orderListRecycler.setVisibility(View.GONE);
                textView.setVisibility(View.VISIBLE);
                textView.setText(orderListAdapter.getEmptyDisplayText());
                orderListRecycler.setAdapter(null);
            }
        }
    }
}
