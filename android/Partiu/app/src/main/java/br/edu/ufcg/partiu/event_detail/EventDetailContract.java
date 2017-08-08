package br.edu.ufcg.partiu.event_detail;

import br.edu.ufcg.partiu.base.BasePresenter;
import br.edu.ufcg.partiu.base.BaseView;
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

        void setEventFields(Event event);

        void close();
    }
}
