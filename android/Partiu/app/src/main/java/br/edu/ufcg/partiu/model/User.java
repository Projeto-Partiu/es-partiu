package br.edu.ufcg.partiu.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.Profile;

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
    private String name;
    private String urlPhoto;
    private List<User> following;
    private List<User> followers;
    private String token;

    public User() {
    }

    public User(JSONObject account) {
        try {
            this.id = account.getString("id");
            this.name = account.getString("name");
            this.token = account.getString("token");
            this.urlPhoto = "";
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

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
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
        SharedPreferences.Editor editor = Util.getPreferences(context).edit();
        editor.putString(Constants.NAME_USER, getName());
        editor.putString(Constants.ID_SOCIAL_NETWORK, getId());
        editor.putString(Constants.URL_PHOTO_USER, getUrlPhoto());
        editor.putBoolean(Constants.LOGGED_USER, true);
        editor.putString(Constants.TOKEN, getToken());
        editor.apply();
        return true;
    }
}
