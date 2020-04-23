package com.example.ghostkitchenandroid.ui.store_owner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.model.State;

public class EditKitchenFragment extends AddKitchenFragment {

    private EditKitchenViewModel editKitchenViewModel;

    public static EditKitchenFragment newInstance(Kitchen kitchen) {
        EditKitchenFragment editKitchenFragment = new EditKitchenFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("kitchen", kitchen);
        editKitchenFragment.setArguments(bundle);
        return editKitchenFragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(editKitchenViewModel.getKitchen().getName());

        displayKitchenData();
        setObservance();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        editKitchenViewModel = new ViewModelProvider(this).get(EditKitchenViewModel.class);

        Bundle bundle = getArguments();
        if (bundle != null) {
            editKitchenViewModel.setKitchen((Kitchen) bundle.get("kitchen"));
        }
    }

    private void displayKitchenData() {
        Kitchen kitchen = editKitchenViewModel.getKitchen();

        etKitchenName.setText(kitchen.getName());
        etAddressLine1.setText(kitchen.getKitchenAddress().getAddressLine1());
        etAddressLine2.setText(kitchen.getKitchenAddress().getAddressLine2());
        etCity.setText(kitchen.getKitchenAddress().getCity());
        etZip.setText(kitchen.getKitchenAddress().getZip());
        etPhone.setText(kitchen.getKitchenAddress().getPhone());
        spState.setSelection(State.valueOf(kitchen.getKitchenAddress().getState()).ordinal());

        btAddKitchen.setText(R.string.submit);
        btAddKitchen.setOnClickListener(v -> {
            submitKitchenUpdates();
        });
    }

    private void submitKitchenUpdates() {
        editKitchenViewModel.submit(
                etKitchenName.getText().toString().trim(),
                etAddressLine1.getText().toString().trim(),
                etAddressLine2.getText().toString().trim(),
                etCity.getText().toString().trim(),
                (State) spState.getSelectedItem(),
                etZip.getText().toString().trim(),
                etPhone.getText().toString().trim()
        );
    }

    private void setObservance() {
        editKitchenViewModel.getKitchenLiveData().observe(getViewLifecycleOwner(), kitchen -> {
            if (kitchen != null) {
                Toast.makeText(getContext(), "Kitchen updated!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Kitchen update FAILED!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
