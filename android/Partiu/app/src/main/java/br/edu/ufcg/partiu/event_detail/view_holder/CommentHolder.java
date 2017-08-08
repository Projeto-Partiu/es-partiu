package br.edu.ufcg.partiu.event_detail.view_holder;

import br.edu.ufcg.partiu.model.Comment;
import br.edu.ufcg.partiu.util.ItemAdapter;

/**
 * Created by lucas on 08/08/17.
 */

public class CommentHolder implements ItemAdapter.ItemHolder {

    private final Comment comment;

    private CommentHolder(Comment comment) {
        this.comment = comment;
    }

    public static CommentHolder from(Comment comment) {
        return new CommentHolder(comment);
    }

    public Comment getComment() {
        return comment;
    }

    @Override
    public int getItemViewType() {
        return CommentViewHolder.VIEW_TYPE;
    }
}
