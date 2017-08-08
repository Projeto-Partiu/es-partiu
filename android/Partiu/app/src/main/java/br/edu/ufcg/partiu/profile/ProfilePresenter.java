package br.edu.ufcg.partiu.profile;

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
    public void searchProfiles(String query) {
//        view.launchSearchActivity();

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
