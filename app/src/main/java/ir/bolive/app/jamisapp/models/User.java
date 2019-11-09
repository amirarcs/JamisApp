package ir.bolive.app.jamisapp.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    @SerializedName("username")
    @Expose
    private String username;

    @SerializedName("fullname")
    @Expose
    private String fullname;

    @SerializedName("activated")
    @Expose
    private boolean activated;

    @SerializedName("createdAt")
    @Expose
    private String createdAt;

    @SerializedName("isLoggedIn")
    @Expose
    private boolean isLoggedIn;



    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public boolean isLoggedIn() {
        return isLoggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        isLoggedIn = loggedIn;
    }

    public User(String username, String fullname, boolean activated) {
        this.username = username;
        this.fullname = fullname;
        this.activated = activated;
    }

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
