package br.edu.ufcg.partiu.event_detail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.inject.Inject;

import br.edu.ufcg.partiu.base.ServiceCallback;
import br.edu.ufcg.partiu.model.Comment;
import br.edu.ufcg.partiu.model.Event;
import br.edu.ufcg.partiu.model.User;
import br.edu.ufcg.partiu.service.CommentService;
import br.edu.ufcg.partiu.service.EventService;
import br.edu.ufcg.partiu.service.UserService;
import retrofit2.Response;

/**
 * Created by lucas on 07/08/17.
 */

public class EventDetailPresenter implements EventDetailContract.Presenter {

    private final EventDetailContract.View view;
    private final EventService eventService;
    private final UserService userService;
    private final CommentService commentService;

    private Event event;
    private String commentText;

    @Inject
    public EventDetailPresenter(EventDetailContract.View view, EventService eventService, UserService userService, CommentService commentService) {
        this.view = view;
        this.eventService = eventService;
        this.commentService = commentService;
        this.userService = userService;
        this.commentText = "";
    }

    @Inject
    public void setupListeners() {
        view.setPresenter(this);
    }

    @Override
    public void start() {
    }

    @Override
    public void fetchEvent(String eventId) {
        view.hideDetailLayout();
        view.showLoader();

        eventService.find(eventId, new ServiceCallback<Event>() {
            @Override
            public void onResponse(Event event, Response<Event> response) {
                EventDetailPresenter.this.event = event;

                DateFormat formatter = SimpleDateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT);

                view.setEventName(event.getName());

                if (event.getDescription() == null) {
                    view.hideEventDescription();
                } else {
                    view.showEventDescription();
                    view.setEventDescription(event.getDescription());
                }

                view.setStartDate(formatter.format(event.getStartDate()));
                view.setPlace(event.getAddress());

                if (event.getEndDate() != null) {
                    view.showEndDate();
                    view.setEndDate(formatter.format(event.getEndDate()));
                }

                User loggedUser = userService.loggedUser();
                boolean confirmed = false;
                for (int i = 0; i < event.getConfirmedUsers().size(); i++) {
                    if (loggedUser.get_Id().equals(event.getConfirmedUsers().get(i).get_Id()))
                        confirmed = true;
                        break;
                }

                view.setPresence(confirmed);

                if (!event.getComments().isEmpty()) {
                    view.hideEmptyCommentsMessage();
                    view.showCommentsList();
                    view.setComments(event.getComments());
                } else {
                    view.hideCommentsList();
                    view.showEmptyCommentsMessage();
                }

                view.hideLoader();
                view.showDetailLayout();
            }

            @Override
            public void onError(Throwable error) {
                if (error == null) {
                    view.showToast("Evento não encontrado");
                    view.close();
                } else {
                    view.showToast("Ocorreu um erro ao processar a requisição");
                    view.hideLoader();
                }
            }
        });
    }

    @Override
    public void onCommentClicked(Comment comment) {
        if (!comment.getUser().getId().equals(userService.loggedUser().getId()))
            return;

        view.showDeleteCommentPopup(comment);
    }

    @Override
    public void onDeleteComment(final Comment comment) {
        commentService.deleteComment(comment, new ServiceCallback<Void>() {
            @Override
            public void onResponse(Void object, Response<Void> response) {
                view.removeCommentFromList(comment);
            }

            @Override
            public void onError(Throwable error) {
                view.showToast("Ocorreu um erro ao apagar o comentário");
            }
        });
    }

    @Override
    public void onCommentChanged(CharSequence text) {
        commentText = text.toString();
    }

    @Override
    public void onComment() {
        commentService.createComment(event, new Comment(commentText), new ServiceCallback<Comment>() {
            @Override
            public void onResponse(Comment comment, Response<Comment> response) {
                view.addCommentToList(comment);
                view.setCommentInputText(null);
            }

            @Override
            public void onError(Throwable error) {
                view.showToast("Ocorreu um erro ao adicionar comentário");
            }
        });
    }
}
