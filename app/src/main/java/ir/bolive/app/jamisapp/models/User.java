package ir.bolive.app.jamisapp.models;

public class User {
    private String username,fullname;

    public User(String username, String fullname) {

        this.fullname = fullname;
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
