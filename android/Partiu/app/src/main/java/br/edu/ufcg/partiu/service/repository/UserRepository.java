package br.edu.ufcg.partiu.service.repository;

import br.edu.ufcg.partiu.model.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by lucas on 25/07/17.
 */

public interface UserRepository {

    @POST("user/")
    Call<User> createUser(@Body User user);


}
