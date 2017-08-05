package br.edu.ufcg.partiu.settings;

import javax.inject.Inject;

import br.edu.ufcg.partiu.service.UserService;

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

    }
}
