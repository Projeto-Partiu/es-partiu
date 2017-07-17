package br.edu.ufcg.partiu.login;

import com.facebook.FacebookException;
import com.facebook.login.LoginResult;

import br.edu.ufcg.partiu.base.BasePresenter;
import br.edu.ufcg.partiu.base.BaseView;

public interface LoginContract {

    interface LoginPresenter extends BasePresenter {

        void onSuccessfulLogin(LoginResult loginResult);

        void onCancelLogin();

        void onLoginError(FacebookException e);
    }

    interface LoginView extends BaseView<LoginPresenter> {
        void showLoginErrorDialog();
    }
}
