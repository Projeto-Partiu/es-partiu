package br.edu.ufcg.partiu.login;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {

    private final LoginContract.LoginView view;

    public LoginModule(LoginContract.LoginView view) {
        this.view = view;
    }

    @LoginScope
    @Provides
    public LoginContract.LoginView providesView() {
        return view;
    }
}
