package com.example.ghostkitchenandroid.ui.customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.network.user.UserRepo;
import com.example.ghostkitchenandroid.ui.lists.KitchenListFragment;
import com.google.android.material.navigation.NavigationView;

public class CustomerActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private TextView tvName;
    private FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer);

        initViews();

        config();

        navigationView.setNavigationItemSelectedListener(makeNavigationItemSelectedListener());

        displayShop();
    }

    private void initViews() {
        toolbar = findViewById(R.id.customer_toolbar);
        drawerLayout = findViewById(R.id.customer_drawer_layout);
        navigationView = findViewById(R.id.customer_nav_view);
        tvName = navigationView.getHeaderView(0).findViewById(R.id.nav_name_tv);
        fragmentContainer = findViewById(R.id.customer_fragment_container);
    }

    private void config() {
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        tvName.setText(UserRepo.getLoggedInUser().getFirstName() + " " + UserRepo.getLoggedInUser().getLastName());
    }

    private NavigationView.OnNavigationItemSelectedListener makeNavigationItemSelectedListener() {
        return item -> {
            switch (item.getItemId()) {
                case R.id.nav_customer_shop:
                    getSupportFragmentManager().beginTransaction().replace(fragmentContainer.getId(), CustomerShopFragment.newInstance(), "CustomerShopFragment").commit();
                    getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    drawerLayout.closeDrawer(GravityCompat.START);
                    getSupportActionBar().setTitle(R.string.shop);
                    return true;
                case R.id.nav_customer_orders:
                    getSupportFragmentManager().beginTransaction().replace(fragmentContainer.getId(), CustomerOrderFragment.newInstance(), "CustomerOrderFragment").addToBackStack(null).commit();
                    drawerLayout.closeDrawer(GravityCompat.START);
                    getSupportActionBar().setTitle(R.string.orders);
                    return true;
            }
            return false;
        };
    }


    private void displayShop() {
        getSupportFragmentManager().beginTransaction().replace(fragmentContainer.getId(), new CustomerShopFragment()).commit();
        navigationView.setCheckedItem(R.id.nav_customer_shop);
    }
}
