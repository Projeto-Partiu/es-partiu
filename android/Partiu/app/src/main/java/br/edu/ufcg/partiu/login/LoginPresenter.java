package br.edu.ufcg.partiu.login;

import com.facebook.FacebookException;

import org.json.JSONObject;

import javax.inject.Inject;

import br.edu.ufcg.partiu.base.ServiceCallback;
import br.edu.ufcg.partiu.model.User;
import br.edu.ufcg.partiu.service.UserService;
import retrofit2.Response;

public class LoginPresenter implements LoginContract.Presenter {

    private final UserService userService;
    private LoginContract.View view;
    
    @Inject
    public LoginPresenter(LoginContract.View view, UserService userService) {
        this.view = view;
        this.userService = userService;
    }

    @Inject
    public void setupListeners() {
        view.setPresenter(this);
    }

    @Override
    public void start() {
        if (userService.loggedUser() != null) {
            view.goToMain();
        }
    }

    @Override
    public void onSuccessfulLogin(JSONObject profile) {
        final User user = new User(profile);

        userService.createUser(user, new ServiceCallback<User>() {
            @Override
            public void onResponse(User object, Response<User> response) {
                if (response.isSuccessful()) {
                    view.goToMain();
                } else {
                    view.showLoginErrorDialog();
                }
            }

            @Override
            public void onError(Throwable error) {
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
