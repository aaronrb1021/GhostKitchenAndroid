package com.example.ghostkitchenandroid.ui.register;

import android.util.Log;
import android.util.Patterns;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.network.Result;
import com.example.ghostkitchenandroid.data.model.User;
import com.example.ghostkitchenandroid.network.user.UserRepo;
import com.example.ghostkitchenandroid.utils.UserCredValidity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class RegisterViewModel extends ViewModel {

    private MutableLiveData<RegisterFormState> registerFormState = new MutableLiveData<>();
    private MutableLiveData<Result<User>> userResultLiveData = new MutableLiveData<>();

    public RegisterViewModel() {

    }

    LiveData<RegisterFormState> getRegisterFormState() {
        return registerFormState;
    }

    LiveData<Result<User>> getResultLiveData() {
        return userResultLiveData;
    }

    void registerDataChanged(
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


    void register(String email, String password, String firstName, String lastName, String phone, boolean isStoreOwner) {
        Log.i("isStoreOwnder", String.valueOf(isStoreOwner));
        userResultLiveData.setValue(UserRepo.createUser(new User(email.trim(), password.trim(), firstName.trim(), lastName.trim(), phone.trim(), isStoreOwner)));
    }
}
