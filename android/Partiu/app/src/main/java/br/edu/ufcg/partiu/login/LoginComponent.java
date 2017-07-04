package br.edu.ufcg.partiu.login;

import dagger.Subcomponent;

@LoginScope
@Subcomponent(modules = LoginModule.class)
public interface LoginComponent {

    void inject(LoginActivity activity);

    @Subcomponent.Builder
    interface Builder {

        Builder loginModule(LoginModule module);

        LoginComponent build();

    }
}
