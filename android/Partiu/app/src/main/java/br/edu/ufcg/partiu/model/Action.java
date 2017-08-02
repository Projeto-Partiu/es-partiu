package br.edu.ufcg.partiu.model;

import com.google.gson.annotations.SerializedName;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * Representa uma atividade no feed do usuário
 */
public class Action {

    @SerializedName("_id")
    private String id;
    private Calendar date;
    private ActionType type;
    private User user;
    private List<Comment> comments;
    private HashMap<String, Object> arguments;

    public Action() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Calendar getDate() {
        return date;
    }

    public void setDate(Calendar date) {
        this.date = date;
    }

    public ActionType getType() {
        return type;
    }

    public void setType(ActionType type) {
        this.type = type;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public HashMap<String, Object> getArguments() {
        return arguments;
    }

    public void setArguments(HashMap<String, Object> arguments) {
        this.arguments = arguments;
    }
}
