package br.edu.ufcg.partiu.show_events;

import java.util.List;

import br.edu.ufcg.partiu.base.ServiceCallback;
import br.edu.ufcg.partiu.model.Event;
import br.edu.ufcg.partiu.model.FilterType;
import br.edu.ufcg.partiu.service.EventService;
import br.edu.ufcg.partiu.service.UserService;
import retrofit2.Response;

/**
 * Created by ordan on 06/08/17.
 */

import javax.inject.Inject;


public class ShowEventsPresenter implements ShowEventsContract.Presenter {

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

    @Override
    public void getEvents(FilterType filterType) {
        eventService.getEvents(filterType, new ServiceCallback<List<Event>>() {
            @Override
            public void onResponse(List<Event> events, Response<List<Event>> response) {
                view.showEvents(events);
            }

            @Override
            public void onError(Throwable error) {
                view.showToast("Não foi possível atualizar eventos");
            }
        });
    }

    @Override
    public void onEventClicked(Event event) {
        view.goToEventDetailActivity(event.getId());
    }
}
