package br.edu.ufcg.partiu.service;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.Profile;
import com.facebook.login.LoginManager;

import org.json.JSONObject;

import br.edu.ufcg.partiu.R;
import br.edu.ufcg.partiu.base.ServiceCallback;
import br.edu.ufcg.partiu.model.User;
import br.edu.ufcg.partiu.util.Constants;

/**
 * Created by Lucas on 17/07/2017.
 */

public class UserServiceImpl implements UserService {

    private final Context context;

    public UserServiceImpl(Context context) {
        this.context = context;
    }

    @Override
    public void createUser(final User user, final ServiceCallback callback) {
        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST,
                context.getString(R.string.url_server) + context.getString(R.string.request_user_login), user.toJSON(),
                null, null) {
            @Override
            protected VolleyError parseNetworkError(VolleyError volleyError) {
                try {
                    LoginManager.getInstance().logOut();
                } catch (Exception e){}
                callback.onError(volleyError);
                return super.parseNetworkError(volleyError);
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                user.saveInPreferences(context);
                callback.onSuccess(response);
                return super.parseNetworkResponse(response);
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(req);
    }
}
