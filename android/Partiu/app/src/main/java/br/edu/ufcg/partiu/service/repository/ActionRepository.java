package br.edu.ufcg.partiu.service.repository;

import java.util.List;

import br.edu.ufcg.partiu.model.Action;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by lucas on 31/07/17.
 */

public interface ActionRepository {

    @GET("/action/{userId}")
    Call<List<Action>> findAll(@Path("userId") String userId);

    @POST("/action/{userId}")
    Call<Action> insert(@Path("userId") String userId, @Body Action action);
}
