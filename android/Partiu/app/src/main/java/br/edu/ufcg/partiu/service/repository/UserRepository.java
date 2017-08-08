package br.edu.ufcg.partiu.service.repository;

import java.util.List;

import br.edu.ufcg.partiu.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by lucas on 25/07/17.
 */

public interface UserRepository {

    @POST("user/")
    Call<User> createUser(@Body User user);

    @POST("user/logout")
    Call<Void> logout(@Header("Authorization") String token);

    @POST("find_users/")
    Call<List<User>> findUsers(@Query ("query") String query, @Header("Authorization") String token);
}

