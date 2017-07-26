package br.edu.ufcg.partiu.service;

import android.content.Context;

import com.facebook.login.LoginManager;

import br.edu.ufcg.partiu.base.ServiceCallback;
import br.edu.ufcg.partiu.model.User;
import br.edu.ufcg.partiu.service.repository.UserRepository;
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
}
