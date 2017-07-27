package br.edu.ufcg.partiu.login;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import br.edu.ufcg.partiu.MainApplication;
import br.edu.ufcg.partiu.R;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @Inject
    LoginPresenter presenter;

    LoginFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        fragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new LoginFragment();


            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

        ((MainApplication) getApplication())
                .getComponent()
                .newLoginComponent()
                .loginModule(new LoginModule(fragment))
                .build()
                .inject(this);
    }

}
