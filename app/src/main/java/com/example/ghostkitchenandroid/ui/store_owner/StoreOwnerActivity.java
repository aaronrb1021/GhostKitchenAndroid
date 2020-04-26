package com.example.ghostkitchenandroid.ui.store_owner;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.network.user.UserRepo;
import com.example.ghostkitchenandroid.ui.lists.KitchenListAdapter;
import com.example.ghostkitchenandroid.ui.lists.KitchenListFragment;
import com.example.ghostkitchenandroid.ui.login.LoginActivity;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

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
        tvName = navigationView.getHeaderView(0).findViewById(R.id.nav_name_tv);
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
        getSupportFragmentManager().beginTransaction().replace(fragmentContainer.getId(), KitchenListFragment.newInstance(KitchenListAdapter.MODE_STORE_OWNER)).commit();
        navigationView.setCheckedItem(R.id.nav_store_owner_my_kitchens);
    }

    private NavigationView.OnNavigationItemSelectedListener makeNavigationItemSelectedListener() {
        return new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_store_owner_my_kitchens:
                        handleFragmentTransaction(KitchenListFragment.newInstance(KitchenListAdapter.MODE_STORE_OWNER), "MyKitchensFragment");
                        getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        getSupportActionBar().setTitle(R.string.my_kitchens_toolbar_title);
                        return true;
                    case R.id.nav_store_owner_add_kitchen:
                        handleFragmentTransaction(new AddKitchenFragment(), "AddKitchenFragment");
                        return true;
                    case R.id.nav_store_owner_orders:
                        handleFragmentTransaction(StoreOwnerOrdersFragment.newInstance(), "StoreOwnerOrdersFragment");
                        return true;
                    case R.id.nav_store_owner_logout:
                        UserRepo.logout(StoreOwnerActivity.this);
                        return false;
                }
                return false;
            }
        };
    }

    private void handleFragmentTransaction(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction().replace(fragmentContainer.getId(), fragment, tag).addToBackStack(null).commit();
        drawerLayout.closeDrawer(GravityCompat.START);
    }

}
