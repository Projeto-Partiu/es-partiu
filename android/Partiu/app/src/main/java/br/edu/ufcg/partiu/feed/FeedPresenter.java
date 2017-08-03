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
        view.hideEmptyMessage();
        view.showLoadingSpinner();

        actionService.findAll(userService.loggedUser(), new ServiceCallback<List<Action>>() {
            @Override
            public void onResponse(List<Action> actionList, Response<List<Action>> response) {
                view.hideLoadingSpinner();
                handleActionListResponse(actionList);
            }

            @Override
            public void onError(Throwable error) {
                view.hideLoadingSpinner();
                handleActionListError(error);
            }
        });
    }

    @Override
    public void onRefreshList() {
        view.setRefreshing(true);

        actionService.findAll(userService.loggedUser(), new ServiceCallback<List<Action>>() {
            @Override
            public void onResponse(List<Action> actionList, Response<List<Action>> response) {
                view.setRefreshing(false);
                handleActionListResponse(actionList);
            }

            @Override
            public void onError(Throwable error) {
                view.setRefreshing(false);
                handleActionListError(error);
            }
        });
    }

    private void handleActionListResponse(List<Action> actionList) {
        if (actionList != null && !actionList.isEmpty()) {
            view.hideEmptyMessage();
            view.setActionList(actionList);
        } else {
            view.showEmptyMessage();
        }
    }

    private void handleActionListError(Throwable error) {
        view.showEmptyMessage();
        view.showToast("Não foi possível atualizar o feed");
    }
}
