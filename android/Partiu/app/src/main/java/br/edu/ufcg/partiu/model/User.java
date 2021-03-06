package br.edu.ufcg.partiu.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.Profile;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import br.edu.ufcg.partiu.util.Constants;
import br.edu.ufcg.partiu.util.Util;

/**
 * Created by caiovidal on 12/07/17.
 */

public class User {

    private String id;
    private String _id;
    private String name;
    private String urlPhoto;
    private List<String> following;
    private String token;

    public User() {
    }

    public User(JSONObject account) {
        try {
            this.id = account.getString("id");
            this._id = account.getString("_id");
            this.name = account.getString("name");
            this.urlPhoto = account.getJSONObject("picture").getJSONObject("data").getString("url");
            this.token = account.getString("token");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String get_Id() {
        return this._id;
    }

    public void set_Id(String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public List<String> getFollowing() {
        return following;
    }

    public void setFollowing(List<String> following) {
        this.following = following;
    }

    public String getToken() {
        if (token == null)
            return "";
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean saveInPreferences(Context context) {
        Gson gson = new GsonBuilder()
                .create();

        SharedPreferences.Editor editor = Util.getPreferences(context).edit();
        editor.putString(Constants.NAME_USER, getName());
        editor.putString(Constants.ID_SOCIAL_NETWORK, getId());
        editor.putString(Constants.ID_USER, get_Id());
        editor.putString(Constants.URL_PHOTO_USER, getUrlPhoto());
        editor.putBoolean(Constants.LOGGED_USER, true);
        editor.putString(Constants.FOLLOWING_USER, gson.toJson(getFollowing()));
        editor.putString(Constants.TOKEN, getToken());
        editor.apply();
        return true;
    }
}
