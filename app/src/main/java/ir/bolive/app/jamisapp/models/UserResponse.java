package ir.bolive.app.jamisapp.models;

import androidx.annotation.Nullable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Map;

public class UserResponse {

    @SerializedName("success")
    @Expose
    boolean success;

    @SerializedName("message")
    @Expose
    String message;

    @SerializedName("user")
    @Expose
    User user;

    public UserResponse() {
    }

    public UserResponse(boolean success, String message, User user) {
        this.success = success;
        this.message = message;
        this.user = user;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
