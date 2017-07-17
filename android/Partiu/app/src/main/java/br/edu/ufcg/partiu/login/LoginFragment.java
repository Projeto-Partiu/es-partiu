package br.edu.ufcg.partiu.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import java.util.Arrays;

import br.edu.ufcg.partiu.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginFragment extends Fragment implements LoginContract.LoginView {
    private LoginContract.LoginPresenter presenter;

    @BindView(R.id.login_button)
    LoginButton loginButton;
    private CallbackManager callbackManager;

    public LoginFragment() {
    }

    @Override
    public void setPresenter(LoginContract.LoginPresenter presenter) {
        this.presenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        ButterKnife.bind(this, view);

        setUpLoginButton();

        return view;
    }

    private void setUpLoginButton() {
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        // If using in a fragment
        loginButton.setFragment(this);
    }

    @Override
    public void onStart() {
        super.onStart();

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                presenter.onSuccessfulLogin(loginResult);
            }

            @Override
            public void onCancel() {
                presenter.onCancelLogin();
            }

            @Override
            public void onError(FacebookException e) {
                presenter.onLoginError(e);
            }
        });

        presenter.start();
    }

    @Override
    public void showLoginErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Erro");
        builder.setMessage("Ocorreu um erro ao tentar realizar o login");
        builder.setNeutralButton("OK", null);
        builder.show();
    }

    public void setCallbackManager(CallbackManager callbackManager) {
        this.callbackManager = callbackManager;
    }
}
