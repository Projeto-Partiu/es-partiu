package br.edu.ufcg.partiu.event_detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import br.edu.ufcg.partiu.MainApplication;
import br.edu.ufcg.partiu.R;

/**
 * Created by lucas on 07/08/17.
 */

public class EventDetailActivity extends AppCompatActivity {

    public static final String EVENT_ID_KEY = "br.edu.ufcg.partiu.event_detail.EventDetailActivity.EVENT_ID_KEY";

    @Inject
    EventDetailPresenter presenter;
    private EventDetailFragment fragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        fragment = (EventDetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new EventDetailFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

        fragment.setArguments(getIntent().getExtras());

        ((MainApplication) getApplication())
                .getComponent()
                .newEventDetailComponent()
                .eventDetailModule(new EventDetailModule(fragment))
                .build()
                .inject(this);
    }

    @Override
    public boolean onSupportNavigateUp() {
        super.onBackPressed();
        return super.onSupportNavigateUp();
    }
}
