package br.edu.ufcg.partiu.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.test.mock.MockApplication;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import org.json.JSONException;
import org.json.JSONObject;

import javax.inject.Inject;

import br.edu.ufcg.partiu.MainApplication;
import br.edu.ufcg.partiu.R;
import br.edu.ufcg.partiu.model.User;
import br.edu.ufcg.partiu.util.Constants;
import br.edu.ufcg.partiu.util.Util;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @Inject
    LoginPresenter presenter;

    LoginFragment fragment;

    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        FacebookSdk.sdkInitialize(getApplicationContext());

        fragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);

        if (fragment == null) {
            fragment = new LoginFragment();

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

        callbackManager = CallbackManager.Factory.create();

        fragment.setCallbackManager(callbackManager);

        ((MainApplication) getApplication())
                .getComponent()
                .newLoginComponent()
                .loginModule(new LoginModule(fragment))
                .build()
                .inject(this);

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                sendUserToServer(Profile.getCurrentProfile());
            }

            @Override
            public void onCancel() {
                showLoginErrorDialog();
            }

            @Override
            public void onError(FacebookException e) {
                showLoginErrorDialog();
            }
        });
    }

    private void sendUserToServer(Profile account) {
        final User user = new User(account);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
                getString(R.string.url_server) + getString(R.string.request_user_login), user.toJSON(),
                null, null) {
            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                Log.i(Constants.LOG, "Erro no login");
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showLoginErrorDialog();
                        try {
                            LoginManager.getInstance().logOut();
                        } catch (Exception e){}
                    }
                });
                return super.parseNetworkError(volleyError);
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                final int mStatusCode = response.statusCode;
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mStatusCode == 202) {
                            Log.i(Constants.LOG, "User found");
                            user.saveInPreferences(getApplicationContext());
                        } else if (mStatusCode == 204) {
                            Log.i(Constants.LOG, "User not found");
                        } else {
                            showLoginErrorDialog();
                        }
                    }
                });

                return super.parseNetworkResponse(response);
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(req);
    }


    private void showLoginErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Erro");
        builder.setMessage("Ocorreu um erro ao tentar realizar o login");
        builder.setNeutralButton("OK", null);
        builder.show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
        Profile profile = Profile.getCurrentProfile();
        Log.d("fb_login_sdk", "callback onError");
    }
}
