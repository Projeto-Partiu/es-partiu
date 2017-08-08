package br.edu.ufcg.partiu.event_detail;

import java.util.Date;
import java.util.List;

import br.edu.ufcg.partiu.base.BasePresenter;
import br.edu.ufcg.partiu.base.BaseView;
import br.edu.ufcg.partiu.model.Comment;
import br.edu.ufcg.partiu.model.Event;

/**
 * Created by lucas on 07/08/17.
 */

public interface EventDetailContract {

    interface Presenter extends BasePresenter {

        void fetchEvent(String eventId);
    }

    interface View extends BaseView<Presenter> {

        void showToast(String text);

        void close();

        void setPresence(boolean presenca);

        void setComments(List<Comment> comments);

        void setEventName(String eventName);

        void setEventDescription(String eventDescription);

        void setStartDate(String startDate);

        void setPlace(String address);

        void showEndDate();

        void setEndDate(String endDate);
    }
}
