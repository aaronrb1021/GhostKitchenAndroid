package com.example.ghostkitchenandroid.ui.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.ui.lists.StoreOwnerItemListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CustomerKitchenActivity extends AppCompatActivity {

    private CustomerKitchenViewModel customerKitchenViewModel;
    private ImageView starImage;
    private BottomNavigationView bottomNavigationView;
    private View fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_kitchen);

        customerKitchenViewModel = new ViewModelProvider(this).get(CustomerKitchenViewModel.class);

        if (customerKitchenViewModel.getKitchen() == null) {
            customerKitchenViewModel.setKitchen((Kitchen) getIntent().getExtras().get("kitchen"));
        }

        setTitle(customerKitchenViewModel.getKitchen().getName());

        initViews();

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_nav_customer_kitchen_menu:
                    getSupportFragmentManager().beginTransaction().replace(fragmentContainer.getId(), new StoreOwnerItemListFragment()).commit();
                    return true;
                case R.id.bottom_nav_menu:
                    showItemList();
                    return true;
            }
            return false;
        });

        getParentFragmentManager().beginTransaction().replace(myKitchenFragmentContainer.getId(), new OrderListFragment()).commit();
        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_pending_orders);
    }

    private void initViews() {
        starImage = findViewById(R.id.customer_kitchen_star_image);
        bottomNavigationView = findViewById(R.id.customer_kitchen_bottom_nav);
        fragmentContainer = findViewById(R.id.customer_fragment_container);
    }

    private void showItemList() {
        StoreOwnerItemListFragment storeOwnerItemListFragment = new StoreOwnerItemListFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("kitchen", myKitchenViewModel.getKitchen());
        bundle.putInt("mode", StoreOwnerItemListFragment.MODE_STORE_OWNER);
        storeOwnerItemListFragment.setArguments(bundle);
        getParentFragmentManager().beginTransaction().replace(myKitchenFragmentContainer.getId(), storeOwnerItemListFragment).commit();
    }
}
