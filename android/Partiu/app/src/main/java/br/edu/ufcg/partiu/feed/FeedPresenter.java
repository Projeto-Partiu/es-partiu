package br.edu.ufcg.partiu.feed;

import javax.inject.Inject;

/**
 * Created by lucas on 26/07/17.
 */

public class FeedPresenter implements FeedContract.Presenter {

    private final FeedContract.View view;

    @Inject
    public FeedPresenter(FeedContract.View view) {
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
