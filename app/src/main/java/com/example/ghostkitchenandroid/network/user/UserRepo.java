package com.example.ghostkitchenandroid.network.user;

import android.os.AsyncTask;
import android.util.Log;

import com.example.ghostkitchenandroid.network.Result;
import com.example.ghostkitchenandroid.data.model.User;

import java.io.EOFException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.http.HTTP;

public abstract class UserRepo {

    private static User loggedInUser;
    private static UserService userService = UserServiceInstance.getInstance();

    /**
     * This method will call an asynctask, which will in turn
     * receive a result from the server. If the result comes back negative, the task will update
     * our live data which is being observed by our RegisterViewModel, and in turn our RegisterActivity.
     *
     * @param user The user that would like to register.
     */
    public static Result<User> createUser(User user) {
        try {
            return new CreateUserTask().execute(user).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return new Result.Error(new Exception("Execution Error"));
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new Result.Error(new Exception("Interrupted Error"));
        }
    }

    //private static class GetUserTask extends AsyncTask<User, Void, Result>

    private static class CreateUserTask extends AsyncTask<User, Void, Result<User>> {

        @Override
        protected Result<User> doInBackground(User... users) {
            try {
                Log.i("isStoreOwnderbefore", String.valueOf(users[0].isStoreOwner()));
                Call<User> call = userService.createUser(users[0]);
                Response<User> response = call.execute();
                User serverSideUser = response.body();
                Log.i("isStoreOwnderafter", String.valueOf(serverSideUser.isStoreOwner()));
                if (serverSideUser != null)
                    return new Result.Success<>(serverSideUser, " registered! You can now login.");
            } catch (EOFException e) {
                e.printStackTrace();
                return new Result.Error(new Exception("Email already taken"));
            } catch (Exception e) {
                e.printStackTrace();
                return new Result.Error(new Exception("Unknown Error"));
            }
            return new Result.Error(new Exception("Unknown Error"));
        }

        @Override
        protected void onPostExecute(Result<User> userResult) {
            if (userResult instanceof Result.Success) {
                loggedInUser = ((Result.Success<User>) userResult).getData();
            }
        }
    }
}
