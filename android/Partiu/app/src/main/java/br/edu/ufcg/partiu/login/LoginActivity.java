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

        fragment = (LoginFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_container);


        if (fragment == null) {
            fragment = new LoginFragment();


            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.fragment_container, fragment)
                    .commit();
        }

        ((MainApplication) getApplication())
                .getComponent()
                .newLoginComponent()
                .loginModule(new LoginModule(fragment))
                .build()
                .inject(this);
    }

}