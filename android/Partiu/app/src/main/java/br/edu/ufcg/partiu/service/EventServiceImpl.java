package br.edu.ufcg.partiu.service;

import android.content.Context;

import java.util.List;

import br.edu.ufcg.partiu.base.ServiceCallback;
import br.edu.ufcg.partiu.model.Comment;
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

    @Override
    public Void getEvents(final ServiceCallback<List<Event>> callback) {
        eventRepository.getEvents(Util.getSessionToken(context)).enqueue(new Callback<List<Event>>() {

            @Override
            public void onResponse(Call<List<Event>> call, Response<List<Event>> response) {
                if (response.code() == 200)
                    callback.onResponse(response.body(), response);
                else
                    callback.onError(null);
            }

            @Override
            public void onFailure(Call<List<Event>> call, Throwable t) {
                callback.onError(t);
            }

        });

        return null;
    }

    @Override
    public Void find(String eventId, final ServiceCallback<Event> callback) {
        eventRepository.findEvent(Util.getSessionToken(context), eventId)
                .enqueue(new Callback<Event>() {
                    @Override
                    public void onResponse(Call<Event> call, Response<Event> response) {
                        if (response.code() == 201) {
                            callback.onResponse(response.body(), response);
                        } else {
                            callback.onError(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<Event> call, Throwable t) {
                        callback.onError(t);
                    }
                });

        return null;
    }

    @Override
    public Void disconfirmPresence(Event event, final ServiceCallback<Void> callback) {
        eventRepository.disconfirmPresence(event.getId(), Util.getSessionToken(context))
            .enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    callback.onResponse(null, response);
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    callback.onError(t);
                }
            });

        return null;
    }

    @Override
    public Void confirmPresence(Event event, final ServiceCallback<Void> callback) {
        eventRepository.confirmPresence(event.getId(), Util.getSessionToken(context))
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        callback.onResponse(null, response);
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
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
