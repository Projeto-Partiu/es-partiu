package br.edu.ufcg.partiu.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONObject;

import java.util.Arrays;

import br.edu.ufcg.partiu.R;
import br.edu.ufcg.partiu.shared.MainActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginFragment extends android.support.v4.app.Fragment implements LoginContract.View {

    private LoginContract.Presenter presenter;

    @BindView(R.id.login_button)
    LoginButton loginButton;
    private CallbackManager callbackManager;

    public LoginFragment() {
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Nullable
    @Override
    public android.view.View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        android.view.View view = inflater.inflate(R.layout.fragment_login, container, false);

        FacebookSdk.sdkInitialize(view.getContext());

        callbackManager = CallbackManager.Factory.create();

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

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                GraphRequest request = GraphRequest.newMeRequest(
                        loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {

                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                presenter.onSuccessfulLogin(object);
                            }
                        });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
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
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle("Erro");
                builder.setMessage("Ocorreu um erro ao tentar realizar o login");
                builder.setNeutralButton("OK", null);
                builder.show();
            }
        });
    }

    @Override
    public void goToMain() {
        Intent intent = new Intent(getContext(), MainActivity.class);

        startActivity(intent);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
