package br.edu.ufcg.partiu.login;

import dagger.Module;
import dagger.Provides;

@LoginScope
@Module
public class LoginModule {

    private final LoginContract.View view;

    public LoginModule(LoginContract.View view) {
        this.view = view;
    }

    @LoginScope
    @Provides
    public LoginContract.View providesView() {
        return view;
    }
}
