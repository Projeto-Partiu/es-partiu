package br.edu.ufcg.partiu.show_events;

import org.json.JSONObject;

import java.util.List;

import br.edu.ufcg.partiu.base.BasePresenter;
import br.edu.ufcg.partiu.base.BaseView;
import br.edu.ufcg.partiu.model.Event;
import br.edu.ufcg.partiu.model.FilterType;

/**
 * Created by ordan on 06/08/17.
 */

public interface ShowEventsContract {

    interface Presenter extends BasePresenter {

        void getEvents(FilterType filterType);

    }

    interface View extends BaseView<Presenter> {
        void showEvents(List<Event> events);

        void showToast(String message);
    }

}
