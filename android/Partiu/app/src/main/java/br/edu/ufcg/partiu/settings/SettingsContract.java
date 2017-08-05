package br.edu.ufcg.partiu.settings;

import br.edu.ufcg.partiu.base.BasePresenter;
import br.edu.ufcg.partiu.base.BaseView;

/**
 * Created by lucas on 05/08/17.
 */

public interface SettingsContract {

    interface Presenter extends BasePresenter {

        void onLogoutButtonClick();
    }

    interface View extends BaseView<Presenter> {

    }
}
