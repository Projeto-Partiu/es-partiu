package br.edu.ufcg.partiu.profile;

import javax.inject.Inject;

/**
 * Created by lucas on 06/08/17.
 */

public class ProfilePresenter implements ProfileContract.Presenter {

    private final ProfileContract.View view;

    @Inject
    public ProfilePresenter(ProfileContract.View view) {
        this.view = view;
    }

    @Inject
    public void setupListeners() {
        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void searchProfiles() {
        view.launchSearchActivity();
    }
}
