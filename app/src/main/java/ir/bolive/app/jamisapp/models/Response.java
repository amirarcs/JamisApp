package ir.bolive.app.jamisapp.models;

import com.google.gson.annotations.SerializedName;

public class Response {
    @SerializedName("success")
    private boolean success;

    @SerializedName("message")
    private String message;

    public Response() {
    }

    public Response(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public boolean getSuccess() {
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
}
