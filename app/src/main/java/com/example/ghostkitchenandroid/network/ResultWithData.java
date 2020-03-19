package com.example.ghostkitchenandroid.network;

import android.util.Log;

import com.google.gson.annotations.SerializedName;

import androidx.annotation.NonNull;

public class ResultWithData<T> {

    @SerializedName("error")
    private boolean isError;
    private String errorMessage;
    private T data;

    public ResultWithData() {

    }

    public ResultWithData(String errorMessage) {
        isError = true;
        this.errorMessage = errorMessage;
    }

    public ResultWithData(T data) {
        this.data = data;
    }

    public boolean isError() {
        return isError;
    }

    public void setIsError(boolean isError) {
        this.isError = isError;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        if (isError)
            return errorMessage;
        else
            return data.toString();
    }
}
