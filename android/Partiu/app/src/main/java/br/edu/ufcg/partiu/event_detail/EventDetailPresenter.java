package br.edu.ufcg.partiu.event_detail;

import javax.inject.Inject;

import br.edu.ufcg.partiu.base.ServiceCallback;
import br.edu.ufcg.partiu.model.Event;
import br.edu.ufcg.partiu.service.EventService;
import retrofit2.Response;

/**
 * Created by lucas on 07/08/17.
 */

public class EventDetailPresenter implements EventDetailContract.Presenter {

    private final EventDetailContract.View view;
    private final EventService eventService;

    private Event event;

    @Inject
    public EventDetailPresenter(EventDetailContract.View view, EventService eventService) {
        this.view = view;
        this.eventService = eventService;
    }

    @Inject
    public void setupListeners() {
        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void fetchEvent(String eventId) {
        if (event != null)
            return;

        eventService.find(eventId, new ServiceCallback<Event>() {
            @Override
            public void onResponse(Event event, Response<Event> response) {
                EventDetailPresenter.this.event = event;

                view.setEventFields(event);
            }

            @Override
            public void onError(Throwable error) {
                if (error == null) {
                    view.showToast("Evento não encontrado");
                } else {
                    view.showToast("Ocorreu um erro ao processar a requisição");
                }
            }
        });
    }
}
