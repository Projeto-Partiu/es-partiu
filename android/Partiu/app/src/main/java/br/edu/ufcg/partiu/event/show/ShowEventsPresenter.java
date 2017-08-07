package br.edu.ufcg.partiu.event.show;

import br.edu.ufcg.partiu.service.EventService;
import br.edu.ufcg.partiu.service.UserService;

/**
 * Created by ordan on 06/08/17.
 */

import javax.inject.Inject;


public class ShowEventsPresenter implements ShowEventsContract.Presenter{

    private final ShowEventsContract.View view;
    private final EventService eventService;
    public final UserService userService;

    @Inject
    public ShowEventsPresenter(ShowEventsContract.View view, EventService eventService, UserService userService) {
        this.view = view;
        this.eventService = eventService;
        this.userService = userService;
    }

    @Inject
    public void setupListeners() {
        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

}
