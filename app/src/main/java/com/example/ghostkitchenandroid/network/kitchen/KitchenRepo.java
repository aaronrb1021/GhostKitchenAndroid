package com.example.ghostkitchenandroid.network.kitchen;

import android.os.AsyncTask;

import com.example.ghostkitchenandroid.model.Kitchen;
import com.example.ghostkitchenandroid.model.User;
import com.example.ghostkitchenandroid.network.advice.DualObjectWrapper;
import com.example.ghostkitchenandroid.network.advice.Result;
import com.example.ghostkitchenandroid.network.advice.ResultWithData;

import java.io.EOFException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Response;

public class KitchenRepo {

    private static KitchenRepo kitchenRepo;

    private static KitchenService kitchenService = KitchenServiceInstance.getInstance();

    public static KitchenRepo getInstance() {
        if (kitchenRepo == null)
            kitchenRepo = new KitchenRepo();
        return kitchenRepo;
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
}
