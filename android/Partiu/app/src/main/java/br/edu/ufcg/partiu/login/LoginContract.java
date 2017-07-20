package br.edu.ufcg.partiu.login;

import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import br.edu.ufcg.partiu.base.BasePresenter;
import br.edu.ufcg.partiu.base.BaseView;

public interface LoginContract {

    interface LoginPresenter extends BasePresenter {

        void onSuccessfulLogin(JSONObject profile);

        void onCancelLogin();

        void onLoginError(FacebookException e);
    }

    interface LoginView extends BaseView<LoginPresenter> {
        void showLoginErrorDialog();
    }
}
