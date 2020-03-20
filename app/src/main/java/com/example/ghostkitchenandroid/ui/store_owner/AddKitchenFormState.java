package com.example.ghostkitchenandroid.ui.store_owner;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.model.State;
import com.example.ghostkitchenandroid.utils.UserCredValidity;

class AddKitchenFormState {

    private int currentError;

    //constants
    public static final int NO_ERROR = 0;
    public static final int NAME_ERROR = 1;
    public static final int ADDRESS1_ERROR = 2;
    public static final int ADDRESS2_ERROR = 3;
    public static final int CITY_ERROR = 4;
    public static final int STATE_ERROR = 5;
    public static final int ZIP_ERROR = 6;
    public static final int PHONE_ERROR = 7;


    AddKitchenFormState(String name, String addressLine1, String city, State state, String zip, String phone) {
        if (!UserCredValidity.isValid(name)) {
            currentError = NAME_ERROR;
            return;
        }
        if (!UserCredValidity.isValid(addressLine1)) {
            currentError = ADDRESS1_ERROR;
            return;
        }
        if (!UserCredValidity.isValid(city)) {
            currentError = CITY_ERROR;
            return;
        }
        if (!UserCredValidity.isStateValid(state)) {
            currentError = STATE_ERROR;
            return;
        }
        if (!UserCredValidity.isZipValid(zip)) {
            currentError = ZIP_ERROR;
            return;
        }
        if (!UserCredValidity.isPhoneValid(phone)) {
            currentError = PHONE_ERROR;
            return;
        }
        currentError = NO_ERROR;
    }

    int getCurrentError() {
        return currentError;
    }
}
