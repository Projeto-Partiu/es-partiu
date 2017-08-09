package br.edu.ufcg.partiu.event_detail;

import java.util.List;

import br.edu.ufcg.partiu.base.BasePresenter;
import br.edu.ufcg.partiu.base.BaseView;
import br.edu.ufcg.partiu.model.Comment;

/**
 * Created by lucas on 07/08/17.
 */

public interface EventDetailContract {

    interface Presenter extends BasePresenter {

        void fetchEvent(String eventId);

        void onCommentClicked(Comment comment);

        void onDeleteComment(Comment comment);

        void onCommentChanged(CharSequence text);

        void onComment();

        void togglePresence();
    }

    interface View extends BaseView<Presenter> {

        void showLoader();

        void hideLoader();

        void showDetailLayout();

        void hideDetailLayout();

        void showToast(String text);

        void close();

        void setPresence(boolean presenca);

        void setComments(List<Comment> comments);

        void showEmptyCommentsMessage();

        void hideEmptyCommentsMessage();

        void setEventName(String eventName);

        void setEventDescription(String eventDescription);

        void setStartDate(String startDate);

        void setPlace(String address);

        void showEndDate();

        void setEndDate(String endDate);

        void showDeleteCommentPopup(Comment comment);

        void removeCommentFromList(Comment comment);

        void addCommentToList(Comment comment);

        void setCommentInputText(String text);

        void hideEventDescription();

        void showEventDescription();

        void showCommentsList();

        void hideCommentsList();
    }
}
