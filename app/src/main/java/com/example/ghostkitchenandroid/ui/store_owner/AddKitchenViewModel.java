package com.example.ghostkitchenandroid.ui.store_owner;

import com.example.ghostkitchenandroid.model.Address;
import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.model.KitchenAddress;
import com.example.ghostkitchenandroid.model.State;
import com.example.ghostkitchenandroid.network.advice.ResultWithData;
import com.example.ghostkitchenandroid.network.kitchen.KitchenRepo;
import com.example.ghostkitchenandroid.network.user.UserRepo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddKitchenViewModel extends ViewModel {

    private MutableLiveData<AddKitchenFormState> formStateLiveData = new MutableLiveData<>();
    private MutableLiveData<ResultWithData<Kitchen>> resultLiveData = new MutableLiveData<>();
    private KitchenRepo kitchenRepo = KitchenRepo.getInstance();

    public AddKitchenViewModel() {

    }

    LiveData<ResultWithData<Kitchen>> getResultLiveData() {
        return resultLiveData;
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

    void submit(String name, String addressLine1, String addressLine2, String city, State state, String zip, String phone, String bytes) {
        KitchenAddress address = new KitchenAddress(name, name, addressLine1, addressLine2, city, state.toString(), zip, phone);
        Kitchen kitchen = new Kitchen(name, address, bytes);
        resultLiveData.setValue(kitchenRepo.createKitchen(kitchen, UserRepo.getLoggedInUser()));
    }

}
