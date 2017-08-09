package br.edu.ufcg.partiu.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.login.LoginManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import br.edu.ufcg.partiu.base.ServiceCallback;
import br.edu.ufcg.partiu.model.Event;
import br.edu.ufcg.partiu.model.User;
import br.edu.ufcg.partiu.service.repository.UserRepository;
import br.edu.ufcg.partiu.util.Constants;
import br.edu.ufcg.partiu.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Lucas on 17/07/2017.
 */

public class UserServiceImpl implements UserService {

    private final Context context;
    private final UserRepository repository;

    public UserServiceImpl(Context context, UserRepository repository) {
        this.context = context;
        this.repository = repository;
    }

    @Override
    public Void createUser(User user, final ServiceCallback<User> callback) {
        repository.createUser(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    response.body().saveInPreferences(context);
                } else {
                    try {
                        LoginManager.getInstance().logOut();
                    } catch (Exception e) {
                    }
                }

                callback.onResponse(response.body(), response);
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                try {
                    LoginManager.getInstance().logOut();
                } catch (Exception e) {
                }

                callback.onError(t);
            }
        });

        return null;
    }

    @Override
    public User loggedUser() {
        SharedPreferences prefs = Util.getPreferences(context);

        if (!prefs.getBoolean(Constants.LOGGED_USER, false)) {
            return null;
        }

        Gson gson = new GsonBuilder().create();

        User loggedUser = new User();

        loggedUser.setId(prefs.getString(Constants.ID_SOCIAL_NETWORK, ""));
        loggedUser.setName(prefs.getString(Constants.NAME_USER, ""));
        loggedUser.setUrlPhoto(prefs.getString(Constants.URL_PHOTO_USER, ""));
        loggedUser.setToken(prefs.getString(Constants.TOKEN, ""));
        loggedUser.set_Id(prefs.getString(Constants.ID_USER, ""));
        try {
            loggedUser.setFollowing((List<String>) gson.fromJson(prefs.getString(Constants.FOLLOWING_USER, "[]"), User.class.getDeclaredField("following").getGenericType()));
        } catch (NoSuchFieldException e) {
        }

        return loggedUser;
    }

    @Override
    public Void logout(final ServiceCallback<Void> callback) {
        repository.logout(loggedUser().getToken()).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                SharedPreferences.Editor prefs = Util.getPreferences(context).edit();

                prefs.putBoolean(Constants.LOGGED_USER, false);

                prefs.remove(Constants.NAME_USER);
                prefs.remove(Constants.ID_SOCIAL_NETWORK);
                prefs.remove(Constants.URL_PHOTO_USER);
                prefs.remove(Constants.TOKEN);

                prefs.apply();

                try {
                    LoginManager.getInstance().logOut();
                } catch (Exception e) {
                    callback.onError(e);
                    return;
                }

                callback.onResponse(null, response);
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                callback.onError(t);
            }
        });

        return null;
    }

    @Override
    public Void findUsers(String query, final ServiceCallback<List<User>> callback) {
        repository.findUsers(query, loggedUser().getToken()).enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                callback.onResponse(response.body(), response);
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                callback.onError(t);
            }
        });

        return null;
    }
}
