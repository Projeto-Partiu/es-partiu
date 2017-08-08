package br.edu.ufcg.partiu.service;

import br.edu.ufcg.partiu.base.ServiceCallback;
import br.edu.ufcg.partiu.model.Comment;

/**
 * Created by lucas on 08/08/17.
 */

public interface CommentService {

    Void createComment(Comment comment, ServiceCallback<Comment> callback);

    Void deleteComment(Comment comment, ServiceCallback<Void> callback);
}
