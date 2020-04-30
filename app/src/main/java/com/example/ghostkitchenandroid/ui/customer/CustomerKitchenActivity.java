package com.example.ghostkitchenandroid.ui.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import androidx.appcompat.widget.Toolbar;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.ui.lists.CustomerItemListFragment;
import com.example.ghostkitchenandroid.ui.lists.StoreOwnerItemListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CustomerKitchenActivity extends AppCompatActivity {

    private CustomerKitchenViewModel customerKitchenViewModel;
    private ImageView starImage;
    private BottomNavigationView bottomNavigationView;
    private View fragmentContainer;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_kitchen);

        customerKitchenViewModel = new ViewModelProvider(this).get(CustomerKitchenViewModel.class);

        if (customerKitchenViewModel.getKitchen() == null) {
            Log.i("kitchensnullness1", String.valueOf(((Kitchen) getIntent().getExtras().get("kitchen")).getKitchenAddress() == null));
            customerKitchenViewModel.setKitchen((Kitchen) getIntent().getExtras().get("kitchen"));
        }

        initViews();

        toolbar.setTitle(customerKitchenViewModel.getKitchen().getName());

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_nav_customer_kitchen_menu:
                    getSupportFragmentManager().beginTransaction().replace(fragmentContainer.getId(), CustomerItemListFragment.newInstance(customerKitchenViewModel.getKitchen())).commit();
                    return true;
                case R.id.bottom_nav_customer_kitchen_info:
                    Log.i("kitchennullness", customerKitchenViewModel.getKitchen().toString());
                    getSupportFragmentManager().beginTransaction().replace(fragmentContainer.getId(), CustomerKitchenInfoFragment.newInstance(customerKitchenViewModel.getKitchen())).commit();
                    return true;
            }
            return false;
        });

        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_customer_kitchen_menu);
    }

    private void initViews() {
        starImage = findViewById(R.id.customer_kitchen_star_image);
        bottomNavigationView = findViewById(R.id.customer_kitchen_bottom_nav);
        fragmentContainer = findViewById(R.id.customer_kitchen_fragment_container);
        toolbar = findViewById(R.id.customer_kitchen_toolbar);
    }
}
