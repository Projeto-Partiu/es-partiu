package br.edu.ufcg.partiu.service.repository;

import java.util.List;

import br.edu.ufcg.partiu.model.Comment;
import br.edu.ufcg.partiu.model.Event;
import br.edu.ufcg.partiu.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by lucas on 26/07/17.
 */

public interface EventRepository {
    @POST("event/new")
    Call<Event> createEvent(@Body Event event, @Header("Authorization") String token);

    @GET("events")
    Call<List<Event>> getEvents(@Header("Authorization") String token);

    @GET("event/{eventId}")
    Call<Event> findEvent(@Header("Authorization") String token, @Path("eventId") String eventId);
}
