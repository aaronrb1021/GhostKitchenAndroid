package com.example.ghostkitchenandroid.ui.register;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.data.model.User;
import com.example.ghostkitchenandroid.network.Result;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterActivity extends AppCompatActivity {

    private RegisterViewModel registerViewModel;
    private TextInputEditText etEmail;
    private TextInputEditText etPassword1;
    private TextInputEditText etPassword2;
    private TextInputEditText etFirstName;
    private TextInputEditText etLastName;
    private TextInputEditText etPhone;
    private Button btRegister;
    private ProgressBar progressBar;
    private CheckBox cbIsStoreOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);

        initViews();

        setObservance();

        setTextWatchers();

        setOnClicks();

    }

    private void setOnClicks() {
        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                btRegister.setEnabled(false);
                if (registerViewModel.getRegisterFormState().getValue().isDataValid()) {
                    registerViewModel.register(
                            etEmail.getText().toString().trim(),
                            etPassword1.getText().toString().trim(),
                            etFirstName.getText().toString().trim(),
                            etLastName.getText().toString().trim(),
                            etPhone.getText().toString().trim(),
                            cbIsStoreOwner.isChecked()
                    );
                }
            }
        });
    }

    private void setTextWatchers() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                registerViewModel.registerDataChanged(
                        etEmail.getText().toString(),
                        etPassword1.getText().toString(),
                        etPassword2.getText().toString(),
                        etFirstName.getText().toString(),
                        etLastName.getText().toString(),
                        etPhone.getText().toString()
                );
            }
        };

        etEmail.addTextChangedListener(textWatcher);
        etPassword1.addTextChangedListener(textWatcher);
        etPassword2.addTextChangedListener(textWatcher);
        etFirstName.addTextChangedListener(textWatcher);
        etLastName.addTextChangedListener(textWatcher);
        etPhone.addTextChangedListener(textWatcher);
    }

    private void setObservance() {
        registerViewModel.getRegisterFormState().observe(this, registerFormState -> {
            if (registerFormState == null)
                return;
            if (registerFormState.isDataValid()) {
                btRegister.setEnabled(true);
                return;
            }
            if (registerFormState.getUsernameError() != null) {
                etEmail.setError(getString(registerFormState.getUsernameError()));
                return;
            }
            if (registerFormState.getPassword1Error() != null) {
                etPassword1.setError(getString(registerFormState.getPassword1Error()));
                return;
            }
            if (registerFormState.getPassword2Error() != null) {
                etPassword2.setError(getString(registerFormState.getPassword2Error()));
                return;
            }
            if (registerFormState.getFirstNameError() != null) {
                etFirstName.setError(getString(registerFormState.getFirstNameError()));
                return;
            }
            if (registerFormState.getLastNameError() != null) {
                etLastName.setError(getString(registerFormState.getLastNameError()));
                return;
            }
            if (registerFormState.getPhoneError() != null) {
                etPhone.setError(getString(registerFormState.getPhoneError()));
                return;
            }
        });

        registerViewModel.getResultLiveData().observe(this, result -> {
            if (result instanceof Result.Success) {
                Toast.makeText(getApplicationContext(), ((Result.Success) result).toString(), Toast.LENGTH_LONG).show();
                finish();//TODO add startup activity
            } else if (result instanceof Result.Error) {
                Toast.makeText(getApplicationContext(), ((Result.Error) result).getError().getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
            } else {
                Toast.makeText(getApplicationContext(), "Register failed!", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void initViews() {
        etEmail = findViewById(R.id.register_email_et);
        etPassword1 = findViewById(R.id.register_password1_et);
        etPassword2 = findViewById(R.id.register_password2_et);
        etFirstName = findViewById(R.id.register_first_name_et);
        etLastName = findViewById(R.id.register_last_name_et);
        etPhone = findViewById(R.id.register_phone_et);
        btRegister = findViewById(R.id.register_bt);
        progressBar = findViewById(R.id.register_progress_bar);
        cbIsStoreOwner = findViewById(R.id.register_store_owner_cb);
    }

}
