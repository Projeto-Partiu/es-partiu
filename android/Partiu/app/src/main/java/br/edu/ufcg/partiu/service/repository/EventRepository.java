package br.edu.ufcg.partiu.service.repository;

import br.edu.ufcg.partiu.model.Event;
import br.edu.ufcg.partiu.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by lucas on 26/07/17.
 */

public interface EventRepository {
    @POST("event/new")
        Call<Event> createEvent(@Body Event event, @Header("Authorization") String token);
}
