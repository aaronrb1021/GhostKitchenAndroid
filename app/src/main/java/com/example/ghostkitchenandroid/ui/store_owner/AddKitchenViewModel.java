package com.example.ghostkitchenandroid.ui.store_owner;

import com.example.ghostkitchenandroid.model.State;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddKitchenViewModel extends ViewModel {

    private MutableLiveData<AddKitchenFormState> formStateLiveData = new MutableLiveData<>();

    public AddKitchenViewModel() {

    }

    LiveData<AddKitchenFormState> getFormStateLiveData() {
        return formStateLiveData;
    }

    void userChangedData(String name, String addressLine1, String addressLine2, String city, State state, String zip, String phone) {
        formStateLiveData.setValue(
                new AddKitchenFormState(
                        name,
                        addressLine1,
                        city,
                        state,
                        zip,
                        phone
                )
        );
    }
}
