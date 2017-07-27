package br.edu.ufcg.partiu.login;

import com.facebook.FacebookException;

import org.json.JSONObject;

import br.edu.ufcg.partiu.base.BasePresenter;
import br.edu.ufcg.partiu.base.BaseView;

public interface LoginContract {

    interface Presenter extends BasePresenter {

        void onSuccessfulLogin(JSONObject profile);

        void onCancelLogin();

        void onLoginError(FacebookException e);
    }

    interface View extends BaseView<Presenter> {
        void showLoginErrorDialog();

        void goToMain();
    }
}
