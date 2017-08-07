package br.edu.ufcg.partiu.show_events;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import br.edu.ufcg.partiu.R;
import br.edu.ufcg.partiu.MainApplication;


public class ShowEventsActivity extends AppCompatActivity {

    @Inject
    ShowEventsPresenter presenter;

    ShowEventsFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_events);

        fragment = (ShowEventsFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new ShowEventsFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

        ((MainApplication) getApplication())
                .getComponent()
                .newShowEventsComponent()
                .showEventsModule(new ShowEventsModule(fragment))
                .build()
                .inject(this);

    }

}
