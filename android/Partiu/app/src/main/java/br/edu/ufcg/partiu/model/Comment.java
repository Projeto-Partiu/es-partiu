package br.edu.ufcg.partiu.model;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

/**
 * Created by lucas on 31/07/17.
 */

public class Comment {

    @SerializedName("_id")
    private String id;
    private User user;
    private String content;
    private Date date;

    public Comment() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
