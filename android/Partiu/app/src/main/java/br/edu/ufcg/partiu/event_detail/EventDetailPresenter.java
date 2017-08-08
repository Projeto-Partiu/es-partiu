package br.edu.ufcg.partiu.event_detail;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.inject.Inject;

import br.edu.ufcg.partiu.base.ServiceCallback;
import br.edu.ufcg.partiu.model.Comment;
import br.edu.ufcg.partiu.model.Event;
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

    @Inject
    public EventDetailPresenter(EventDetailContract.View view, EventService eventService, UserService userService, CommentService commentService) {
        this.view = view;
        this.eventService = eventService;
        this.commentService = commentService;
        this.userService = userService;
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
        if (event != null)
            return;

        view.hideDetailLayout();
        view.showLoader();

        eventService.find(eventId, new ServiceCallback<Event>() {
            @Override
            public void onResponse(Event event, Response<Event> response) {
                EventDetailPresenter.this.event = event;

                DateFormat formatter = SimpleDateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT);

                view.setEventName(event.getName());
                view.setEventDescription(event.getDescription());
                view.setStartDate(formatter.format(event.getStartDate()));
                view.setPlace(event.getAddress());

                if (event.getEndDate() != null) {
                    view.showEndDate();
                    view.setEndDate(formatter.format(event.getEndDate()));
                }

                if (!event.getComments().isEmpty()) {
                    view.hideEmptyCommentsMessage();
                    view.setComments(event.getComments());
                } else {
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
}
