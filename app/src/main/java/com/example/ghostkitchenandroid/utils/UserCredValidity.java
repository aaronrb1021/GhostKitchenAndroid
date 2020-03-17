package com.example.ghostkitchenandroid.utils;

import android.util.Patterns;

public abstract class UserCredValidity {
    public static boolean isUserNameValid(String username) {
        return Patterns.EMAIL_ADDRESS.matcher(username).matches();
    }

    public static boolean isPasswordValid(String password) {
        return password != null && password.trim().length() >= 8;
    }

    public static boolean isPassword2Valid(String password1, String password2) {
        return password1.equals(password2);
    }

    public static boolean isPhoneValid(String phone) {
        if (phone == null)
            return false;
        String plainPhone = phone.trim().replace("-", "");
        return plainPhone.length() == 10 || plainPhone.length() == 11;
    }
}
