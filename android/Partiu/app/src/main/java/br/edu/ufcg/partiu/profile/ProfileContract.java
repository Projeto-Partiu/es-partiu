package br.edu.ufcg.partiu.profile;

import android.os.Bundle;

import java.util.List;

import br.edu.ufcg.partiu.base.BasePresenter;
import br.edu.ufcg.partiu.base.BaseView;
import br.edu.ufcg.partiu.model.User;

/**
 * Created by lucas on 06/08/17.
 */

public interface ProfileContract {

    interface Presenter extends BasePresenter {
        Bundle provideBundle(User user);

        void searchProfiles(String query);
    }

    interface View extends BaseView<Presenter> {

        void showUsers(List<User> users);

        void showToast(String message);
    }
}
