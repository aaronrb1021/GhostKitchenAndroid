package com.example.ghostkitchenandroid.ui.store_owner;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.network.user.UserRepo;
import com.google.android.material.internal.NavigationMenu;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class StoreOwnerActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;
    private TextView tvName;
    private FrameLayout fragmentContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_owner);

        initViews();

        config();

        navigationView.setNavigationItemSelectedListener(makeNavigationItemSelectedListener());

        displayKitchenList();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START))
            drawerLayout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return toggle.onOptionsItemSelected(item);
    }

    private void initViews() {
        toolbar = findViewById(R.id.store_owner_toolbar);
        drawerLayout = findViewById(R.id.store_owner_drawer_layout);
        navigationView = findViewById(R.id.store_owner_nav_view);
        tvName = navigationView.getHeaderView(0).findViewById(R.id.nav_store_owner_name);
        fragmentContainer = findViewById(R.id.store_owner_fragment_container);
    }

    private void config() {
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_open, R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        tvName.setText(UserRepo.getLoggedInUser().getFirstName() + " " + UserRepo.getLoggedInUser().getLastName());
    }

    private void displayKitchenList() {
        getSupportFragmentManager().beginTransaction().replace(fragmentContainer.getId(), new MyKitchensFragment()).commit();
        navigationView.setCheckedItem(R.id.nav_store_owner_my_kitchens);
    }

    private NavigationView.OnNavigationItemSelectedListener makeNavigationItemSelectedListener() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_store_owner_my_kitchens:
                        getSupportFragmentManager().beginTransaction().replace(fragmentContainer.getId(), new MyKitchensFragment(), "MyKitchensFragment").addToBackStack(null).commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                    case R.id.nav_store_owner_add_kitchen:
                        getSupportFragmentManager().beginTransaction().replace(fragmentContainer.getId(), new AddKitchenFragment(), "AddKitchenFragment").addToBackStack(null).commit();
                        drawerLayout.closeDrawer(GravityCompat.START);
                        return true;
                }
                return false;
            }
        };
    }
}
