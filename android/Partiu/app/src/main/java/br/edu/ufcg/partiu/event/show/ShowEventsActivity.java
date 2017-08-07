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
import br.edu.ufcg.partiu.model.Event;
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

        ListView eventList = (ListView) findViewById(R.id.list_view);

        List<Event> events = new ArrayList<Event>();

        Event e1 = new Event(), e2 = new Event();
        e1.setName("Teste 1");
        e2.setName("Teste 2");

        e1.setDescription("nome: " + presenter.userService.loggedUser().getName() + " foto: " + presenter.userService.loggedUser().getUrlPhoto());
        e2.setDescription("Nada com tudo");

        e1.setAddress("Rua das palmeiras");
        e2.setAddress("Rua das bananeiras");

        e1.setStartDate(new Date());
        e2.setStartDate(new Date());

        events.add(e1);
        events.add(e2);

        ArrayAdapter<Event> adapter = new ArrayAdapter<Event>(this, android.R.layout.simple_list_item_1, events);

        eventList.setAdapter(adapter);

    }



}
