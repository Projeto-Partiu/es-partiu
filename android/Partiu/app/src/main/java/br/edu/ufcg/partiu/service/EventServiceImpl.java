package br.edu.ufcg.partiu.service;

import android.content.Context;

import br.edu.ufcg.partiu.base.ServiceCallback;
import br.edu.ufcg.partiu.model.Event;
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
