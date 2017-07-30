package br.edu.ufcg.partiu.event;

import br.edu.ufcg.partiu.base.BasePresenter;

/**
 * Created by ordan on 30/07/17.
 */

public interface CreateEventContract {

    interface Presenter extends BasePresenter{
        void onCreateEvent();
    }

}
