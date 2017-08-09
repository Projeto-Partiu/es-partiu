package br.edu.ufcg.partiu.profile_search;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import javax.inject.Inject;

import br.edu.ufcg.partiu.MainApplication;
import br.edu.ufcg.partiu.R;
import butterknife.ButterKnife;

public class ProfileSearchActivity extends AppCompatActivity {

    @Inject
    ProfileSearchPresenter presenter;

    private ProfileSearchFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_search);

        ButterKnife.bind(this);

        fragment = (ProfileSearchFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new ProfileSearchFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

        ((MainApplication) getApplication())
                .getComponent()
                .newProfileSearchComponent()
                .profileSearchModule(new ProfileSearchModule(fragment))
                .build()
                .inject(this);
    }
}
