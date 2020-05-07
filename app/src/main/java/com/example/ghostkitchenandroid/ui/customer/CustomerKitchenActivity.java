package com.example.ghostkitchenandroid.ui.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.model.User;
import com.example.ghostkitchenandroid.network.user.UserRepo;
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
            customerKitchenViewModel.setKitchen((Kitchen) getIntent().getExtras().get("kitchen"));
        }

        initViews();

        toolbar.setTitle(customerKitchenViewModel.getKitchen().getName());

        configBottomNav();

        setObservance();

        configImageButton();
    }

    private void initViews() {
        starImage = findViewById(R.id.customer_kitchen_star_image);
        bottomNavigationView = findViewById(R.id.customer_kitchen_bottom_nav);
        fragmentContainer = findViewById(R.id.customer_kitchen_fragment_container);
        toolbar = findViewById(R.id.customer_kitchen_toolbar);
    }

    private void configBottomNav() {
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.bottom_nav_customer_kitchen_menu:
                    getSupportFragmentManager().beginTransaction().replace(fragmentContainer.getId(), CustomerItemListFragment.newInstance(customerKitchenViewModel.getKitchen())).commit();
                    return true;
                case R.id.bottom_nav_customer_kitchen_info:
                    getSupportFragmentManager().beginTransaction().replace(fragmentContainer.getId(), CustomerKitchenInfoFragment.newInstance(customerKitchenViewModel.getKitchen())).commit();
                    return true;
            }
            return false;
        });

        bottomNavigationView.setSelectedItemId(R.id.bottom_nav_customer_kitchen_menu);
    }

    private void setObservance() {
        customerKitchenViewModel.getUserLiveData().observe(this, user -> {
            if (user != null) {
                if (user.getKitchenIds().contains(customerKitchenViewModel.getKitchen().getId())) {
                    starImage.setImageDrawable(getDrawable(R.drawable.ic_star_filled_gold_24dp));
                } else {
                    starImage.setImageDrawable(getDrawable(R.drawable.ic_star_border_black_24dp));
                }
            } else {
                Toast.makeText(this, "Connection error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void configImageButton() {
        if (UserRepo.getLoggedInUser().getKitchenIds().contains(customerKitchenViewModel.getKitchen().getId())) {
            starImage.setImageDrawable(getDrawable(R.drawable.ic_star_filled_gold_24dp));
        }

        starImage.setOnClickListener(v -> {
            User currentUser = UserRepo.getLoggedInUser();
            if (currentUser.getKitchenIds().contains(customerKitchenViewModel.getKitchen().getId())) {
                currentUser.getKitchenIds().remove(customerKitchenViewModel.getKitchen().getId());
            } else {
                currentUser.getKitchenIds().add(customerKitchenViewModel.getKitchen().getId());
            }
            customerKitchenViewModel.updateUser(currentUser);
        });
    }
}
