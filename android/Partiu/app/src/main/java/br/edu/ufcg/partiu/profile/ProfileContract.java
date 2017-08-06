package br.edu.ufcg.partiu.profile;

import br.edu.ufcg.partiu.base.BasePresenter;
import br.edu.ufcg.partiu.base.BaseView;

/**
 * Created by lucas on 06/08/17.
 */

public interface ProfileContract {

    interface Presenter extends BasePresenter {
        void searchProfiles();
    }

    interface View extends BaseView<Presenter> {

        void launchSearchActivity();
    }
}
