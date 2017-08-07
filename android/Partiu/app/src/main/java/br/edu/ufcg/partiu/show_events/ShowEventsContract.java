package br.edu.ufcg.partiu.show_events;

import java.util.List;

import br.edu.ufcg.partiu.base.BasePresenter;
import br.edu.ufcg.partiu.base.BaseView;
import br.edu.ufcg.partiu.model.Event;

/**
 * Created by ordan on 06/08/17.
 */

public interface ShowEventsContract {

    interface Presenter extends BasePresenter {
    }

    interface View extends BaseView<Presenter> {
        void showEvents(List<Event> events);

        void showToast(String message);
    }

}
