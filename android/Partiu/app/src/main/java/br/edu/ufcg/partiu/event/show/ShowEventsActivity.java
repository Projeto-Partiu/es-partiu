package br.edu.ufcg.partiu.event.show;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import javax.inject.Inject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.edu.ufcg.partiu.R;
import br.edu.ufcg.partiu.base.ServiceCallback;
import br.edu.ufcg.partiu.event.CreateEventContract;
import br.edu.ufcg.partiu.event.CreateEventFragment;
import br.edu.ufcg.partiu.event.CreateEventPresenter;
import br.edu.ufcg.partiu.model.Event;
import br.edu.ufcg.partiu.MainApplication;
import retrofit2.Response;


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
