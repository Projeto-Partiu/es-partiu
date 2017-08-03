package br.edu.ufcg.partiu.service;

import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.login.LoginManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import br.edu.ufcg.partiu.base.ServiceCallback;
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
        try {
            loggedUser.setFollowing((List<String>) gson.fromJson(prefs.getString(Constants.FOLLOWING_USER, "[]"), User.class.getDeclaredField("following").getGenericType()));
        } catch (NoSuchFieldException e) {
        }

        return loggedUser;
    }
}
