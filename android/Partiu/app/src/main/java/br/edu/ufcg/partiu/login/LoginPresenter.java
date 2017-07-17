package br.edu.ufcg.partiu.login;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import javax.inject.Inject;

import br.edu.ufcg.partiu.base.ServiceCallback;
import br.edu.ufcg.partiu.service.UserService;
import br.edu.ufcg.partiu.util.Constants;

public class LoginPresenter implements LoginContract.LoginPresenter {

    private final UserService userService;
    private LoginContract.LoginView view;

    @Inject
    public LoginPresenter(LoginContract.LoginView view, UserService userService) {
        this.view = view;
        this.userService = userService;
    }

    @Inject
    public void setupListeners() {
        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void onSuccessfulLogin(LoginResult loginResult) {
        userService.createUser(Profile.getCurrentProfile(), new ServiceCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                final int mStatusCode = response.statusCode;

                if (mStatusCode == 202) {
                    Log.i(Constants.LOG, "User found");
                    // user.saveInPreferences(getApplicationContext());
                } else if (mStatusCode == 204) {
                    Log.i(Constants.LOG, "User not found");
                } else {
                    view.showLoginErrorDialog();
                }
            }

            @Override
            public void onError(VolleyError error) {
                view.showLoginErrorDialog();
                try {
                    LoginManager.getInstance().logOut();
                } catch (Exception e){}
            }
        });
    }
}
