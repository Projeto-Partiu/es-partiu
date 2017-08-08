package br.edu.ufcg.partiu.event_detail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

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

        view.hideDetailLayout();
        view.showLoader();

        eventService.find(eventId, new ServiceCallback<Event>() {
            @Override
            public void onResponse(Event event, Response<Event> response) {
                EventDetailPresenter.this.event = event;

                DateFormat formatter = SimpleDateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT);

                view.setEventName(event.getName());
                view.setEventDescription(event.getDescription());
                view.setStartDate(formatter.format(event.getStartDate()));
                view.setPlace(event.getAddress());

                if (event.getEndDate() != null) {
                    view.showEndDate();
                    view.setEndDate(formatter.format(event.getEndDate()));
                }

                if (!event.getComments().isEmpty()) {
                    view.hideEmptyCommentsMessage();
                    view.setComments(event.getComments());
                } else {
                    view.showEmptyCommentsMessage();
                }

                view.hideLoader();
                view.showDetailLayout();
            }

            @Override
            public void onError(Throwable error) {
                if (error == null) {
                    view.showToast("Evento não encontrado");
                    view.close();
                } else {
                    view.showToast("Ocorreu um erro ao processar a requisição");
                    view.hideLoader();
                }
            }
        });
    }
}
