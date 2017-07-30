package br.edu.ufcg.partiu.service;

import android.content.Context;

import com.facebook.login.LoginManager;

import br.edu.ufcg.partiu.base.ServiceCallback;
import br.edu.ufcg.partiu.model.Event;
import br.edu.ufcg.partiu.model.User;
import br.edu.ufcg.partiu.service.repository.EventRepository;
import br.edu.ufcg.partiu.service.repository.UserRepository;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by ordan on 29/07/17.
 */

public class EventServiceImpl implements EventService {

    private final Context context;
    private final EventRepository repository;

    public EventServiceImpl(Context context, EventRepository repository) {
        this.context = context;
        this.repository = repository;
    }

    @Override
    public Void createEvent(Event event, final ServiceCallback<Event> callback) {
        repository.createEvent(event).enqueue(new Callback<Event>() {
            @Override
            public void onResponse(Call<Event> call, Response<Event> response) {
                if (response.isSuccessful()) {

                } else {
                    try {

                    } catch (Exception e) {
                    }
                }

                callback.onResponse(response.body(), response);
            }

            @Override
            public void onFailure(Call<Event> call, Throwable t) {
                try {

                } catch (Exception e) {
                }

                callback.onError(t);
            }
        });

        return null;
    }

}
