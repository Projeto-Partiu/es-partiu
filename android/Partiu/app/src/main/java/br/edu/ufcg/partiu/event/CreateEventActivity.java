package br.edu.ufcg.partiu.event;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import javax.inject.Inject;

import br.edu.ufcg.partiu.MainApplication;
import br.edu.ufcg.partiu.R;
import butterknife.ButterKnife;

public class CreateEventActivity extends AppCompatActivity {

    @Inject
    CreateEventPresenter presenter;

    CreateEventFragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        if (fragment == null) {
            fragment = new CreateEventFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

        ((MainApplication) getApplication())
                .getComponent()
                .newCreateEventComponent()
                .createEventModule(new CreateEventModule(fragment))
                .build()
                .inject(this);

        fragment.setPresenter(presenter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return super.onSupportNavigateUp();
    }
}
