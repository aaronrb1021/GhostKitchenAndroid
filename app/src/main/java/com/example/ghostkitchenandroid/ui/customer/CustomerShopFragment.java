package com.example.ghostkitchenandroid.ui.customer;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.ui.lists.KitchenListAdapter;
import com.example.ghostkitchenandroid.ui.lists.KitchenListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.w3c.dom.Text;

public class CustomerShopFragment extends Fragment {

    private CustomerShopViewModel customerShopViewModel;
    private TextView tvFilter;
    private View listContainer;
    private BottomNavigationView bottomNavigationView;

    public static CustomerShopFragment newInstance() {
        return new CustomerShopFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_customer_shop, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        customerShopViewModel = new ViewModelProvider(this).get(CustomerShopViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvFilter = view.findViewById(R.id.customer_shop_filter_tv);
        listContainer = view.findViewById(R.id.customer_shop_list_container);
        bottomNavigationView = view.findViewById(R.id.customer_shop_bottom_nav);

        bottomNavigationView.setOnNavigationItemSelectedListener(makeNavListener());

        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_shop_explore);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private BottomNavigationView.OnNavigationItemSelectedListener makeNavListener() {
        return item -> {
            switch (item.getItemId()) {
                case R.id.bottom_nav_shop_explore:
                    displayKitchenList();
                    return true;
            }
            return false;
        };
    }

    private void displayKitchenList() {
        getParentFragmentManager().beginTransaction().replace(listContainer.getId(), KitchenListFragment.newInstance(KitchenListAdapter.MODE_CUSTOMER)).commit();
    }
}
