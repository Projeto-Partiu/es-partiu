package br.edu.ufcg.partiu.feed;

import java.util.List;

import javax.inject.Inject;

import br.edu.ufcg.partiu.base.ServiceCallback;
import br.edu.ufcg.partiu.model.Action;
import br.edu.ufcg.partiu.service.ActionService;
import br.edu.ufcg.partiu.service.UserService;
import retrofit2.Response;

/**
 * Created by lucas on 26/07/17.
 */

public class FeedPresenter implements FeedContract.Presenter {

    private final FeedContract.View view;
    private final ActionService actionService;
    private final UserService userService;

    @Inject
    public FeedPresenter(FeedContract.View view, ActionService actionService, UserService userService) {
        this.view = view;
        this.actionService = actionService;
        this.userService = userService;
    }

    @Inject
    public void setupListeners() {
        view.setPresenter(this);
    }

    @Override
    public void start() {
        actionService.findAll(userService.loggedUser(), new ServiceCallback<List<Action>>() {
            @Override
            public void onResponse(List<Action> object, Response<List<Action>> response) {

            }

            @Override
            public void onError(Throwable error) {

            }
        });
    }
}
