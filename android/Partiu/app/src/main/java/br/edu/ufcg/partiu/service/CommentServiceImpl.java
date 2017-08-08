package br.edu.ufcg.partiu.service;

import android.content.Context;

import br.edu.ufcg.partiu.base.ServiceCallback;
import br.edu.ufcg.partiu.model.Comment;
import br.edu.ufcg.partiu.model.Event;
import br.edu.ufcg.partiu.service.repository.CommentRepository;
import br.edu.ufcg.partiu.util.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lucas on 08/08/17.
 */

public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final Context context;

    public CommentServiceImpl(Context context, CommentRepository commentRepository) {
        this.context = context;
        this.commentRepository = commentRepository;
    }

    @Override
    public Void createComment(Event event, Comment comment, final ServiceCallback<Comment> callback) {
        commentRepository.createComment(comment, event.getId(), Util.getSessionToken(context))
                .enqueue(new Callback<Comment>() {
                    @Override
                    public void onResponse(Call<Comment> call, Response<Comment> response) {
                        if (response.code() == 201) {
                            callback.onResponse(response.body(), response);
                        } else {
                            callback.onError(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<Comment> call, Throwable t) {
                        callback.onError(t);
                    }
                });

        return null;
    }

    @Override
    public Void deleteComment(Comment comment, final ServiceCallback<Void> callback) {
        commentRepository.deleteComment(comment.getId(), Util.getSessionToken(context))
                .enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        if (response.code() == 204) {
                            callback.onResponse(null, response);
                        } else {
                            callback.onError(null);
                        }
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        callback.onError(t);
                    }
                });

        return null;
    }
}
