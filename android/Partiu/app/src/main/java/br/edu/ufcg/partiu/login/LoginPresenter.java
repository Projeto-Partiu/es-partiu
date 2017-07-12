package br.edu.ufcg.partiu.login;

import javax.inject.Inject;

public class LoginPresenter implements LoginContract.LoginPresenter {

    private LoginContract.LoginView view;

    @Inject
    public LoginPresenter(LoginContract.LoginView view) {
        this.view = view;
    }

    @Inject
    public void setupListeners() {
        view.setPresenter(this);
    }

    @Override
    public void start() {

    }


}
