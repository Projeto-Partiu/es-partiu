package br.edu.ufcg.partiu.profile;

import android.os.Bundle;

import java.util.List;

import javax.inject.Inject;

import br.edu.ufcg.partiu.base.ServiceCallback;
import br.edu.ufcg.partiu.model.Event;
import br.edu.ufcg.partiu.model.User;
import br.edu.ufcg.partiu.service.UserService;
import retrofit2.Response;

/**
 * Created by lucas on 06/08/17.
 */

public class ProfilePresenter implements ProfileContract.Presenter {

    private final ProfileContract.View view;
    private final UserService userService;

    @Inject
    public ProfilePresenter(ProfileContract.View view, UserService userService) {
        this.view = view;
        this.userService = userService;
    }

    @Inject
    public void setupListeners() {
        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public Bundle provideBundle(User user) {
        Bundle bundle = new Bundle();
        bundle.putString("USER_NAME", user.getName());
        bundle.putString("USER_PHOTO", user.getUrlPhoto());
        bundle.putBoolean("USER_FOLLOWING", userService.loggedUser().getFollowing().contains(user.getId()));

        return bundle;

    }

    @Override
    public void searchProfiles(String query) {

        userService.findUsers(query, new ServiceCallback<List<User>>() {
            @Override
            public void onResponse(List<User> users, Response<List<User>> response) {
                view.showUsers(users);
            }

            @Override
            public void onError(Throwable error) {
                view.showToast("Não foi encontramos nenhum usuário");
            }
        });
    }


}
