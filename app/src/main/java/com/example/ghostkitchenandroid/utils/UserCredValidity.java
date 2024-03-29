package com.example.ghostkitchenandroid.utils;

import android.util.Patterns;

import com.example.ghostkitchenandroid.model.State;

import androidx.annotation.Nullable;

public abstract class UserCredValidity {
    public static boolean isUserNameValid(String username) {
        return Patterns.EMAIL_ADDRESS.matcher(username).matches();
    }

    public static boolean isPasswordValid(@Nullable String password) {
        return password != null && password.trim().length() >= 8;
    }

    public static boolean isPassword2Valid(String password1, String password2) {
        return password1.equals(password2);
    }

    public static boolean isPhoneValid(@Nullable String phone) {
        if (phone == null)
            return false;
        String plainPhone = phone.trim().replaceAll("[^0-9]", "");
        return plainPhone.length() == 10 || plainPhone.length() == 11;
    }

    public static boolean isZipValid(@Nullable String zip) {
        if (zip == null)
            return false;

        String zipJustDigits = zip.replace("-", "");

        if (zipJustDigits.length() == 5 || zipJustDigits.length() == 9)
            return true;

        return false;
    }

    public static boolean isStateValid(@Nullable State state) {
        if (state == null || state == State.SELECT_STATE)
            return false;
        return true;
    }

    public static boolean isExpirationValid(@Nullable String expiration) {
        if (expiration == null || expiration.replaceAll("[^0-9]", "").length() != 4)
            return false;
        return true;
    }

    public static boolean isCvvValid(@Nullable String cvv) {
        int cvvLength = (cvv != null) ? cvv.replaceAll("[^0-9]", "").length() : 0;
        if (cvvLength < 3 || cvvLength > 4)
            return false;
        return true;
    }

    public static boolean isCreditCardValid(@Nullable String cardNumber) {
        int cardLength = (cardNumber != null) ? cardNumber.replaceAll("[^0-9]", "").length() : 0;
        if (cardLength != 16)
            return false;
        return true;
    }

    /**
     * Checks if a string is not null and has a length greater than 0.
     * @param string the string to check
     * @return <code>true</code> if the string meets aformentioned specs
     */
    public static boolean isValid(@Nullable String string) {
        return string != null && string.length() > 0;
    }
}
