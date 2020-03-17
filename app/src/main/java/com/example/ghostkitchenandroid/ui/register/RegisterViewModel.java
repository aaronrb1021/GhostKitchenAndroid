package com.example.ghostkitchenandroid.ui.register;

import android.util.Patterns;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.utils.UserCredValidity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>();

    public LiveData<RegisterFormState> getRegisterFormState() {
        return registerFormState;
    }

    public void registerDataChanged(
            String username,
            String password1,
            String password2,
            String firstName,
            String lastName,
            String phone
    ) {
        if (!UserCredValidity.isUserNameValid(username)) {
            registerFormState.setValue(new RegisterFormState(R.string.invalid_username, null, null, null, null, null));
        } else if (!UserCredValidity.isPasswordValid(password1)) {
            registerFormState.setValue(new RegisterFormState(null, R.string.invalid_password, null, null, null, null));
        } else if (!UserCredValidity.isPassword2Valid(password1, password2)) {
            registerFormState.setValue(new RegisterFormState(null, null, R.string.invalid_password_2, null, null, null));
        } else if (firstName.trim().length() == 0) {
            registerFormState.setValue(new RegisterFormState(null, null, null, R.string.required, null, null));
        } else if (lastName.trim().length() == 0) {
            registerFormState.setValue(new RegisterFormState(null, null, null, null, R.string.required, null));
        } else if (!UserCredValidity.isPhoneValid(phone)) {
            registerFormState.setValue(new RegisterFormState(null, null, null, null, null, R.string.invalid_phone));
        } else {
            registerFormState.setValue(new RegisterFormState(true));
        }
    }


    public void register(String trim, String trim1, String trim2, String trim3, String trim4) {
        //TODO
    }
}
