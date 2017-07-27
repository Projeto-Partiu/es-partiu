package br.edu.ufcg.partiu.shared;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import javax.inject.Inject;

import br.edu.ufcg.partiu.AppComponent;
import br.edu.ufcg.partiu.MainApplication;
import br.edu.ufcg.partiu.R;
import br.edu.ufcg.partiu.feed.FeedFragment;
import br.edu.ufcg.partiu.feed.FeedModule;
import br.edu.ufcg.partiu.feed.FeedPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lucas on 26/07/17.
 */

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView navigationView;

    private AppComponent component;

    private Fragment currentDisplayedFragment;

    @Inject
    FeedPresenter feedPresenter;
    FeedFragment feedFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        component = ((MainApplication) getApplication())
                .getComponent();

        navigationView.setOnNavigationItemSelectedListener(this);

        initializeActivity();
    }

    private void initializeActivity() {
        if (getSupportFragmentManager().findFragmentById(R.id.fragment_container) == null) {
            navigateToFeed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.feed_icon:
                navigateToFeed();
                return true;
            case R.id.profile_icon:
                return true;
            case R.id.settings_icon:
                return true;
        }

        return false;
    }

    private void navigateToFeed() {
        if (feedFragment == null) {
            feedFragment = new FeedFragment();

            attachFragmentToContainer(feedFragment);
        }

        component
                .newFeedComponent()
                .feedModule(new FeedModule(feedFragment))
                .build()
                .inject(this);
    }

    private void attachFragmentToContainer(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (currentDisplayedFragment == null) {
            transaction
                    .remove(currentDisplayedFragment)
                    .add(R.id.fragment_container, fragment);
        } else {
            transaction.replace(R.id.fragment_container, fragment);
        }

        currentDisplayedFragment = fragment;

        transaction.commit();
    }
}
