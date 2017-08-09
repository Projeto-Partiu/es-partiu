package br.edu.ufcg.partiu.settings;

import javax.inject.Inject;

import br.edu.ufcg.partiu.base.ServiceCallback;
import br.edu.ufcg.partiu.service.UserService;
import retrofit2.Response;

/**
 * Created by lucas on 05/08/17.
 */

public class SettingsPresenter implements SettingsContract.Presenter {

    private final SettingsContract.View view;
    private final UserService userService;

    @Inject
    public SettingsPresenter(SettingsContract.View view, UserService userService) {
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
    public void onLogoutButtonClick() {
        userService.logout(new ServiceCallback<Void>() {

            @Override
            public void onResponse(Void object, Response<Void> response) {
                view.goToLoginScreen();
            }

            @Override
            public void onError(Throwable error) {
                view.showToast("Erro ao tentar realizar logout");
            }
        });
    }
}
