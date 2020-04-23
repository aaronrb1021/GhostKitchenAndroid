package com.example.ghostkitchenandroid.network.kitchen;

import android.os.AsyncTask;
import android.util.Log;

import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.model.User;
import com.example.ghostkitchenandroid.network.advice.DualObjectWrapper;
import com.example.ghostkitchenandroid.network.advice.ResultWithData;

import java.io.EOFException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Response;

public class KitchenRepo {

    private static KitchenRepo kitchenRepo;

    private static KitchenService kitchenService = KitchenServiceInstance.getInstance();

    private MutableLiveData<ArrayList<Kitchen>> kitchensLiveData = new MutableLiveData<>();
    private MutableLiveData<Kitchen> kitchenLiveData;

    public static KitchenRepo getInstance() {
        if (kitchenRepo == null)
            kitchenRepo = new KitchenRepo();
        return kitchenRepo;
    }

    public LiveData<ArrayList<Kitchen>> getKitchensLiveData() {
        return kitchensLiveData;
    }

    public LiveData<Kitchen> getKitchenLiveData() {
        kitchenLiveData = new MutableLiveData<>();
        return kitchenLiveData;
    }

    public void updateKitchen(Kitchen kitchen) {
        new UpdateKitchenTask(this).execute(kitchen);
    }

    public ResultWithData<Kitchen> createKitchen(Kitchen kitchen, User user) {
        try {
            return new CreateKitchenTask().execute(new DualObjectWrapper<Kitchen, User>(kitchen, user)).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new ResultWithData<>("Exception error");
    }

    public void fetchKitchensByUser(User user) {
        new FetchKitchensByUserTask(this).execute(user);
    }

    public void fetchAllKitchens() {
        new FetchAllKitchensTask(this).execute();
    }

    private static class CreateKitchenTask extends AsyncTask<DualObjectWrapper<Kitchen, User>, Void, ResultWithData<Kitchen>> {

        @Override
        protected ResultWithData<Kitchen> doInBackground(DualObjectWrapper<Kitchen, User>... objectWrappers) {
            try {
                Call<ResultWithData<Kitchen>> call = kitchenService.addKitchen(objectWrappers[0]);
                Response<ResultWithData<Kitchen>> response = call.execute();
                return response.body();
            } catch (EOFException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new ResultWithData<>("Connection error");
        }
    }

    private static class FetchKitchensByUserTask extends AsyncTask<User, Void, ArrayList<Kitchen>> {

        private KitchenRepo kitchenRepo;

        FetchKitchensByUserTask(KitchenRepo kitchenRepo) {
            this.kitchenRepo = kitchenRepo;
        }

        @Override
        protected ArrayList<Kitchen> doInBackground(User... users) {
            try {
                return kitchenService.getKitchensByUser(users[0]).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ArrayList<>();
        }

        @Override
        protected void onPostExecute(ArrayList<Kitchen> kitchens) {
            kitchenRepo.kitchensLiveData.setValue(kitchens);
            kitchenRepo = null;
        }
    }

    private static class FetchAllKitchensTask extends AsyncTask<Void, Void, ArrayList<Kitchen>> {

        private KitchenRepo kitchenRepo;

        FetchAllKitchensTask(KitchenRepo kitchenRepo) {
            this.kitchenRepo = kitchenRepo;
        }

        @Override
        protected ArrayList<Kitchen> doInBackground(Void... voids) {
            try {
                return kitchenService.getAllKitchens().execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ArrayList<>();
        }

        @Override
        protected void onPostExecute(ArrayList<Kitchen> kitchens) {
            kitchenRepo.kitchensLiveData.setValue(kitchens);
            kitchenRepo = null;
        }
    }

    private static class UpdateKitchenTask extends AsyncTask<Kitchen, Void, Kitchen> {

        private WeakReference<KitchenRepo> weakReference;

        private UpdateKitchenTask(KitchenRepo kitchenRepo) {
            weakReference = new WeakReference<>(kitchenRepo);
        }

        @Override
        protected Kitchen doInBackground(Kitchen... kitchens) {
            try {
                return kitchenService.updateKitchen(kitchens[0]).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Kitchen kitchen) {
            weakReference.get().kitchenLiveData.setValue(kitchen);
        }
    }
}
