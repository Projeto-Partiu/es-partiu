package br.edu.ufcg.partiu.model;

import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.Profile;

import org.json.JSONException;
import org.json.JSONObject;

import br.edu.ufcg.partiu.util.Constants;
import br.edu.ufcg.partiu.util.Util;

/**
 * Created by caiovidal on 12/07/17.
 */

public class User {
    private String id;
    private String name;
    private String urlPhoto;

    public User(JSONObject account) {
        try {
            this.id = account.getString("id");
            this.name = account.getString("name");
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

    public boolean saveInPreferences(Context context) {
        SharedPreferences.Editor editor = Util.getPreferences(context).edit();
        editor.putString(Constants.NAME_USER, getName());
        editor.putString(Constants.ID_SOCIAL_NETWORK, getId());
        editor.putString(Constants.URL_PHOTO_USER, getUrlPhoto());
        editor.putBoolean(Constants.LOGGED_USER, true);
        editor.apply();
        return true;
    }

    public JSONObject toJSON(){
        JSONObject jsonObject = new JSONObject();
        try {
            if (getId() != null && !getId().trim().isEmpty())
                jsonObject.put("id", getId());
            jsonObject.put("name", getName());
            if (getUrlPhoto() != null && !getUrlPhoto().trim().isEmpty())
                jsonObject.put("photo", getUrlPhoto());
            return jsonObject;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
}
