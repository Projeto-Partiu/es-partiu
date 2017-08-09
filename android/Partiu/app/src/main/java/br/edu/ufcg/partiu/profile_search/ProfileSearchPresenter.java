package br.edu.ufcg.partiu.profile_search;

import javax.inject.Inject;

/**
 * Created by lucas on 07/08/17.
 */

public class ProfileSearchPresenter implements ProfileSearchContract.Presenter {

    private final ProfileSearchContract.View view;

    @Inject
    public ProfileSearchPresenter(ProfileSearchContract.View view) {
        this.view = view;
    }

    @Inject
    public void setupListeners() {
        view.setPresenter(this);
    }

    @Override
    public void start() {

    }
}
