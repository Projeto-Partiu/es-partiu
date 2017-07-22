package br.edu.ufcg.partiu.login;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;
import com.facebook.FacebookException;
import com.facebook.Profile;
import com.facebook.ProfileTracker;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONObject;

import javax.inject.Inject;

import br.edu.ufcg.partiu.base.ServiceCallback;
import br.edu.ufcg.partiu.model.User;
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
    public void onSuccessfulLogin(JSONObject profile) {
        final User user = new User(profile);

        userService.createUser(user, new ServiceCallback() {
            @Override
            public void onSuccess(NetworkResponse response) {
                final int mStatusCode = response.statusCode;

                if (mStatusCode == 202) {
                    Log.i(Constants.LOG, "User found");
                } else {
                    view.showLoginErrorDialog();
                }
            }

            @Override
            public void onError(VolleyError error) {
                view.showLoginErrorDialog();
            }
        });
    }

    @Override
    public void onCancelLogin() {
        view.showLoginErrorDialog();
    }

    @Override
    public void onLoginError(FacebookException e) {
        view.showLoginErrorDialog();
    }
}
