package com.example.ghostkitchenandroid.network.user;

import android.os.AsyncTask;

import com.example.ghostkitchenandroid.network.advice.Result;
import com.example.ghostkitchenandroid.model.User;
import com.example.ghostkitchenandroid.network.advice.ResultWithData;

import java.io.EOFException;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

import retrofit2.Call;
import retrofit2.Response;

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

    public static ResultWithData<User> getUser(User user) {
        try {
            return new GetUserTask().execute(user).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
            return new ResultWithData<>("Execution Error");
        } catch (InterruptedException e) {
            e.printStackTrace();
            return new ResultWithData<>("Interrupted Error");
        }
    }

    private static class GetUserTask extends AsyncTask<User, Void, ResultWithData<User>> {

        @Override
        protected ResultWithData<User> doInBackground(User... users) {
            try {
                ResultWithData<User> result = userService.getUser(users[0]).execute().body();
                return result;
            } catch (IOException e) {
                e.printStackTrace();
                return new ResultWithData<>("IOException");
            } catch (Exception e) {
                e.printStackTrace();
                return new ResultWithData<>("Unknown error");
            }
        }

        @Override
        protected void onPostExecute(ResultWithData<User> userResult) {
            if (!userResult.isError()) {
                loggedInUser = userResult.getData();
            }
        }
    }

    private static class CreateUserTask extends AsyncTask<User, Void, Result<User>> {

        @Override
        protected Result<User> doInBackground(User... users) {
            try {
                Call<User> call = userService.createUser(users[0]);
                Response<User> response = call.execute();
                User serverSideUser = response.body();
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

        }
    }
}
