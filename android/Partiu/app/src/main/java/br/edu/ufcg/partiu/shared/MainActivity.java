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

import br.edu.ufcg.partiu.MainApplication;
import br.edu.ufcg.partiu.R;
import br.edu.ufcg.partiu.feed.FeedFragment;
import br.edu.ufcg.partiu.feed.FeedModule;
import br.edu.ufcg.partiu.feed.FeedPresenter;
import br.edu.ufcg.partiu.settings.SettingsFragment;
import br.edu.ufcg.partiu.settings.SettingsModule;
import br.edu.ufcg.partiu.settings.SettingsPresenter;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lucas on 26/07/17.
 */

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    public static final String CURRENT_FRAGMENT_KEY = "br.edu.ufcg.partiu.shared.MainActivity.CURRENT_FRAGMENT_KEY";

    @BindView(R.id.bottom_navigation)
    BottomNavigationView navigationView;

    private Fragment currentDisplayedFragment;

    @Inject
    FeedPresenter feedPresenter;
    FeedFragment feedFragment;

    @Inject
    SettingsPresenter settingsPresenter;
    SettingsFragment settingsFragment;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        navigationView.setOnNavigationItemSelectedListener(this);

        initializeActivity(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (currentDisplayedFragment instanceof FeedFragment) {
            outState.putInt(CURRENT_FRAGMENT_KEY, R.id.feed_icon);
        } else if (currentDisplayedFragment instanceof SettingsFragment) {
            outState.putInt(CURRENT_FRAGMENT_KEY, R.id.settings_icon);
        } else {
            outState.putInt(CURRENT_FRAGMENT_KEY, -1);
        }
    }

    private void initializeActivity(Bundle savedInstanceState) {
        initializeFragments();

        if (savedInstanceState != null && savedInstanceState.getInt(CURRENT_FRAGMENT_KEY) != -1) {
            int displayedFragmentId = savedInstanceState.getInt(CURRENT_FRAGMENT_KEY);

            currentDisplayedFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);

            switch (displayedFragmentId) {
                case R.id.feed_icon:
                    feedFragment = (FeedFragment) currentDisplayedFragment;
                    break;
                case R.id.settings_icon:
                    settingsFragment = (SettingsFragment) currentDisplayedFragment;
            }

            handleNavigation(displayedFragmentId);
        } else {
            navigateToFeed();
        }

        ((MainApplication) getApplication())
                .getComponent()
                .newMainComponent()
                .feedModule(new FeedModule(feedFragment))
                .settingsModule(new SettingsModule(settingsFragment))
                .build()
                .inject(this);
    }

    private void initializeFragments() {
        feedFragment = new FeedFragment();
        settingsFragment = new SettingsFragment();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return handleNavigation(item.getItemId());
    }

    private boolean handleNavigation(int itemId) {
        switch (itemId) {
            case R.id.feed_icon:
                navigateToFeed();
                return true;
            case R.id.profile_icon:
                return true;
            case R.id.settings_icon:
                navigateToSettings();
                return true;
        }

        return false;
    }

    private void navigateToFeed() {
        attachFragmentToContainer(feedFragment);
    }

    private void navigateToSettings() {
        attachFragmentToContainer(settingsFragment);
    }

    private void attachFragmentToContainer(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

        if (currentDisplayedFragment != null) {
            transaction.replace(R.id.fragment_container, fragment);
        } else {
            transaction.add(R.id.fragment_container, fragment);
        }

        currentDisplayedFragment = fragment;

        transaction.commit();
    }
}
