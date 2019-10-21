package ir.bolive.app.jamisapp.models;

public class User {
    private String username,fullname;
    private boolean activated;


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
