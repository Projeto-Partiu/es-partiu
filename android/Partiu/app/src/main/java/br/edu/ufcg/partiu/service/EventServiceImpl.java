package br.edu.ufcg.partiu.service;

import android.content.Context;
import android.location.Location;

import java.util.List;

import br.edu.ufcg.partiu.base.ServiceCallback;
import br.edu.ufcg.partiu.model.Event;
import br.edu.ufcg.partiu.model.FilterType;
import br.edu.ufcg.partiu.model.LocationUser;
import br.edu.ufcg.partiu.service.repository.EventRepository;
import br.edu.ufcg.partiu.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ordan on 29/07/17.
 */

public class EventServiceImpl implements EventService {

    private final Context context;
    private final EventRepository eventRepository;
    private final UserService userService;

    public EventServiceImpl(Context context, EventRepository eventRepository, UserService userService) {
        this.context = context;
        this.eventRepository = eventRepository;
        this.userService = userService;
    }

    @Override
    public Void createEvent(Event event, final ServiceCallback<Event> callback) {
        validateEvent(event);

        event.setOwner(userService.loggedUser());

        eventRepository.createEvent(event, Util.getSessionToken(context)).enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                callback.onResponse(response.body(), response);
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                callback.onError(t);
            }
        });

        return null;
    }

    @Override
    public Void getEvents(FilterType filterType, final ServiceCallback<List<Event>> callback) {

        Callback<List<Event>> callbackEvent = new Callback<List<Event>>() {

            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                callback.onResponse(response.body(), response);
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                callback.onError(t);
            }

        };

        switch (filterType) {
            case BY_DISTANCE:
                LocationUser locationUser = Util.getLastLocation(context);

                eventRepository.getEventsByDistance(Util.getSessionToken(context), locationUser).enqueue(callbackEvent);
                break;
            case BY_TIME:
                eventRepository.getEventsByTime(Util.getSessionToken(context)).enqueue(callbackEvent);
                break;
        }

        return null;
    }

    private void validateEvent(Event event) {
        if (event.getName() == null || event.getName().isEmpty()) {
            throw new IllegalStateException("Nome é obrigatorio");
        }

        if (event.getAddress() == null || event.getAddress().isEmpty()) {
            throw new IllegalStateException("Local é obrigatório");
        }

        if (event.getStartDate() == null) {
            throw new IllegalStateException("Data de início é obrigatória");
        }
    }

}
