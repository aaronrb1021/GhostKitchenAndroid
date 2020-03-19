package com.example.ghostkitchenandroid.ui.login;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.User;
import com.example.ghostkitchenandroid.network.advice.ResultWithData;
import com.example.ghostkitchenandroid.network.user.UserRepo;
import com.example.ghostkitchenandroid.utils.UserCredValidity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<ResultWithData<User>> loginResultLiveData = new MutableLiveData<>();

    public LoginViewModel() {

    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<ResultWithData<User>> getLoginResultLiveData() {
        return loginResultLiveData;
    }

    public void login(String email, String password) {
        ResultWithData<User> result = UserRepo.getUser(new User(email, password));
        loginResultLiveData.setValue(result);
    }

    public void loginDataChanged(String username, String password) {
        if (!UserCredValidity.isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!UserCredValidity.isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }
}
