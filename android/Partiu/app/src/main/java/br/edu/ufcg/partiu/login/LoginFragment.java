package br.edu.ufcg.partiu.login;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import butterknife.ButterKnife;

public class LoginFragment extends Fragment implements LoginContract.LoginView {
    private LoginContract.LoginPresenter presenter;

    private LoginButton loginButton;

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

        setUpLoginButton(view);

        ButterKnife.bind(this, view);

        return view;
    }

    private void setUpLoginButton(View view) {
        loginButton = (LoginButton) view.findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email"));
        // If using in a fragment
        loginButton.setFragment(this);

    }

    @Override
    public void onStart() {
        super.onStart();
        presenter.start();
    }
}
