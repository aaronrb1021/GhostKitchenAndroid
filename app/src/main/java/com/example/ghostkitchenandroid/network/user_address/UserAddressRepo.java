package com.example.ghostkitchenandroid.network.user_address;

import android.os.AsyncTask;

import com.example.ghostkitchenandroid.model.User;
import com.example.ghostkitchenandroid.model.UserAddress;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class UserAddressRepo {

    private static UserAddressRepo userAddressRepo;

    private UserAddressService userAddressService = UserAddressServiceInstance.getInstance();
    private MutableLiveData<UserAddress> userAddressLiveData;
    private MutableLiveData<ArrayList<UserAddress>> userAddressListLiveData;

    public static UserAddressRepo newInstance() {
        if (userAddressRepo == null)
            userAddressRepo = new UserAddressRepo();
        return userAddressRepo;
    }

    public LiveData<UserAddress> getUserAddressLiveData() {
        userAddressLiveData = new MutableLiveData<>();
        return userAddressLiveData;
    }

    public LiveData<ArrayList<UserAddress>> getUserAddressListLiveData() {
        userAddressListLiveData = new MutableLiveData<>();
        return userAddressListLiveData;
    }

    public void saveUserAddress(UserAddress userAddress) {
        new SaveUserAddressTask(this).execute(userAddress);
    }

    public void fetchUserAddressesByUser(User user) {
        new FetchUserAddressesByUserTask(this).execute(user);
    }

    private static class SaveUserAddressTask extends AsyncTask<UserAddress, Void, UserAddress> {

        private WeakReference<UserAddressRepo> userAddressRepoWeakReference;

        private SaveUserAddressTask(UserAddressRepo userAddressRepo) {
            userAddressRepoWeakReference = new WeakReference<>(userAddressRepo);
        }

        @Override
        protected UserAddress doInBackground(UserAddress... userAddresses) {
            try {
                return userAddressRepoWeakReference.get().userAddressService.saveUserAddress(userAddresses[0]).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(UserAddress userAddress) {
            if (userAddress != null)
                userAddressRepoWeakReference.get().userAddressLiveData.setValue(userAddress);
        }
    }

    private static class FetchUserAddressesByUserTask extends AsyncTask<User, Void, ArrayList<UserAddress>> {

        private WeakReference<UserAddressRepo> userAddressRepoWeakReference;

        private FetchUserAddressesByUserTask(UserAddressRepo userAddressRepo) {
            userAddressRepoWeakReference = new WeakReference<>(userAddressRepo);
        }

        @Override
        protected ArrayList<UserAddress> doInBackground(User... users) {
            try {
                return userAddressRepoWeakReference.get().userAddressService.fetchUserAddressesByUserId(String.valueOf(users[0].getId())).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<UserAddress> userAddresses) {
            if (userAddresses != null)
                userAddressRepoWeakReference.get().userAddressListLiveData.setValue(userAddresses);
        }
    }

}
