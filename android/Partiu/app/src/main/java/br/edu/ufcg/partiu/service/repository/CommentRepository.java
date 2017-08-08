package br.edu.ufcg.partiu.service.repository;

import br.edu.ufcg.partiu.model.Comment;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by lucas on 08/08/17.
 */

public interface CommentRepository {

    @POST("comment/{eventId}")
    Call<Comment> createComment(@Body Comment comment, @Path("eventId") String eventId, @Header("Authorization") String token);

    @DELETE("comment/{commentId}")
    Call<Void> deleteComment(@Path("commentId") String commentId, @Header("Authorization") String token);
}
