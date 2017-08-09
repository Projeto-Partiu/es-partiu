package br.edu.ufcg.partiu.service;

import br.edu.ufcg.partiu.base.ServiceCallback;
import br.edu.ufcg.partiu.model.Comment;
import br.edu.ufcg.partiu.model.Event;

/**
 * Created by lucas on 08/08/17.
 */

public interface CommentService {

    Void createComment(Event event, Comment comment, ServiceCallback<Comment> callback);

    Void deleteComment(Comment comment, ServiceCallback<Void> callback);
}
