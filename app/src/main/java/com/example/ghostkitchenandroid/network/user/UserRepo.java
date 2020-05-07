package com.example.ghostkitchenandroid.network.user;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;

import com.example.ghostkitchenandroid.R;
import com.example.ghostkitchenandroid.network.advice.Result;
import com.example.ghostkitchenandroid.model.User;
import com.example.ghostkitchenandroid.network.advice.ResultWithData;
import com.example.ghostkitchenandroid.ui.login.LoginActivity;

import java.io.EOFException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.concurrent.ExecutionException;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import retrofit2.Call;
import retrofit2.Response;

public class UserRepo {

    private static User loggedInUser;
    private static UserService userService = UserServiceInstance.getInstance();
    private MutableLiveData<User> userLiveData;

    public static User getLoggedInUser() {
        return loggedInUser;
    }

    public LiveData<User> getUserLiveData() {
        userLiveData = new MutableLiveData<>();
        return userLiveData;
    }

    public void updateUser(User user) {
        new UpdateUserTask(this).execute(user);
    }

    public static void logout(AppCompatActivity context) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton(R.string.logout, (DialogInterface dialog, int which) -> {
            Intent intent = new Intent(context, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
            context.finish();
        });
        builder.setNegativeButton(android.R.string.cancel, (DialogInterface dialog, int which) -> {
            dialog.dismiss();
        });
        builder.show();
    }

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
            ResultWithData<User> result = new GetUserTask().execute(user).get();
            loggedInUser = result.getData();
            return result;
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
                return new ResultWithData<>("Connection Error");
            } catch (Exception e) {
                e.printStackTrace();
                return new ResultWithData<>("Unknown error");
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
                    return new Result.Success<>(serverSideUser, "registered! You can now login.");
            } catch (EOFException e) {
                e.printStackTrace();
                return new Result.Error(new Exception("Email already taken"));
            } catch (Exception e) {
                e.printStackTrace();
                return new Result.Error(new Exception("Unknown Error"));
            }
            return new Result.Error(new Exception("Unknown Error"));
        }
    }

    private static class UpdateUserTask extends AsyncTask<User, Void, User> {

        private WeakReference<UserRepo> weakReference;

        UpdateUserTask(UserRepo userRepo) {
            weakReference = new WeakReference<>(userRepo);
        }

        @Override
        protected User doInBackground(User... users) {
            try {
                return userService.updateUser(users[0]).execute().body();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(User user) {
            if (user != null)
                weakReference.get().userLiveData.setValue(user);
        }
    }
}
