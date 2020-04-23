package com.example.ghostkitchenandroid.ui.store_owner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.model.State;
import com.example.ghostkitchenandroid.network.user.UserRepo;

public class AddKitchenFragment extends Fragment {

    private AddKitchenViewModel addKitchenViewModel;
    protected EditText etKitchenName, etAddressLine1, etAddressLine2, etCity, etZip, etPhone;
    protected Spinner spState;
    protected Button btAddKitchen;

    public static AddKitchenFragment newInstance() {

        return new AddKitchenFragment();

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_kitchen, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        addKitchenViewModel = new ViewModelProvider(this).get(AddKitchenViewModel.class);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.add_kitchen_toolbar_title);

        initViews();

        setListeners();

        setObservance();

        setOnClickListener();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initViews() {
        etKitchenName = getActivity().findViewById(R.id.add_kitchen_name_et);
        etAddressLine1 = getActivity().findViewById(R.id.add_kitchen_address1_et);
        etAddressLine2 = getActivity().findViewById(R.id.add_kitchen_address2_et);
        etCity = getActivity().findViewById(R.id.add_kitchen_city_et);
        etZip = getActivity().findViewById(R.id.add_kitchen_zip_et);
        etPhone = getActivity().findViewById(R.id.add_kitchen_phone_et);
        btAddKitchen = getActivity().findViewById(R.id.add_kitchen_bt);

        spState = getActivity().findViewById(R.id.add_kitchen_state_spinner);
        spState.setAdapter(new ArrayAdapter<State>(getContext(), android.R.layout.simple_spinner_dropdown_item, State.values()));
    }

    private void setListeners() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                userChangedData();
            }
        };
        etKitchenName.addTextChangedListener(textWatcher);
        etAddressLine1.addTextChangedListener(textWatcher);
        etAddressLine2.addTextChangedListener(textWatcher);
        etCity.addTextChangedListener(textWatcher);
        etZip.addTextChangedListener(textWatcher);
        etPhone.addTextChangedListener(textWatcher);

        spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userChangedData();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void userChangedData() {
        addKitchenViewModel.userChangedData(
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
        addKitchenViewModel.getFormStateLiveData().observe(getViewLifecycleOwner(), formState -> {
            switch (formState.getCurrentError()) {
                case AddKitchenFormState.NAME_ERROR:
                    etKitchenName.setError(getString(R.string.required));
                    btAddKitchen.setEnabled(false);
                    break;
                case AddKitchenFormState.ADDRESS1_ERROR:
                    etAddressLine1.setError(getString(R.string.required));
                    btAddKitchen.setEnabled(false);
                    break;
                case AddKitchenFormState.ADDRESS2_ERROR:
                    etAddressLine2.setError(getString(R.string.required));
                    btAddKitchen.setEnabled(false);
                    break;
                case AddKitchenFormState.CITY_ERROR:
                    etCity.setError(getString(R.string.required));
                    btAddKitchen.setEnabled(false);
                    break;
                case AddKitchenFormState.STATE_ERROR:
//                    ((TextView) spState.getSelectedView()).setError(getString(R.string.required));
                    btAddKitchen.setEnabled(false);
                    break;
                case AddKitchenFormState.ZIP_ERROR:
                    etZip.setError(getString(R.string.required));
                    btAddKitchen.setEnabled(false);
                    break;
                case AddKitchenFormState.PHONE_ERROR:
                    etPhone.setError(getString(R.string.required));
                    btAddKitchen.setEnabled(false);
                    break;
                case AddKitchenFormState.NO_ERROR:
                    btAddKitchen.setEnabled(true);
                    break;
            }
        });

        addKitchenViewModel.getResultLiveData().observe(getViewLifecycleOwner(), result -> {
            if (result.isError()) {
                Toast.makeText(getContext(), result.getErrorMessage(), Toast.LENGTH_LONG).show();
                btAddKitchen.setEnabled(false);
            } else if (result.getData() != null) {
                Toast.makeText(getContext(), result.getData().getName() + " created!", Toast.LENGTH_LONG).show();
                UserRepo.getLoggedInUser().getKitchenIds().add(result.getData().getId());
                init();
            } else {
                Toast.makeText(getContext(), "Creation failed", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void init() {
        etKitchenName.setText("");
        etAddressLine1.setText("");
        etAddressLine2.setText("");
        etCity.setText("");
        spState.setSelection(0);
        etZip.setText("");
        etPhone.setText("");
    }

    private void setOnClickListener() {
        btAddKitchen.setOnClickListener(v -> {
            addKitchenViewModel.submit(
                    etKitchenName.getText().toString().trim(),
                    etAddressLine1.getText().toString().trim(),
                    etAddressLine2.getText().toString().trim(),
                    etCity.getText().toString().trim(),
                    (State) spState.getSelectedItem(),
                    etZip.getText().toString().trim(),
                    etPhone.getText().toString().trim()
            );
        });
    }
}
