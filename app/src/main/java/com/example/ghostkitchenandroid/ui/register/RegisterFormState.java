package com.example.ghostkitchenandroid.ui.register;

import androidx.annotation.Nullable;

class RegisterFormState {
    @Nullable
    private Integer usernameError;
    @Nullable
    private Integer password1Error;
    @Nullable
    private Integer password2Error;
    @Nullable
    private Integer firstNameError;
    @Nullable
    private Integer lastNameError;
    @Nullable
    private Integer phoneError;
    private boolean isDataValid;

    public RegisterFormState(@Nullable Integer usernameError, @Nullable Integer password1Error, @Nullable Integer password2Error, @Nullable Integer firstNameError, @Nullable Integer lastNameError, @Nullable Integer phoneError) {
        this.usernameError = usernameError;
        this.password1Error = password1Error;
        this.password2Error = password2Error;
        this.firstNameError = firstNameError;
        this.lastNameError = lastNameError;
        this.phoneError = phoneError;
    }

    RegisterFormState(boolean isDataValid) {
        this.usernameError = null;
        this.password1Error = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    Integer getUsernameError() {
        return usernameError;
    }

    @Nullable
    Integer getPassword1Error() {
        return password1Error;
    }

    @Nullable
    public Integer getPassword2Error() {
        return password2Error;
    }

    @Nullable
    public Integer getFirstNameError() {
        return firstNameError;
    }

    @Nullable
    public Integer getLastNameError() {
        return lastNameError;
    }

    @Nullable
    public Integer getPhoneError() {
        return phoneError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}
