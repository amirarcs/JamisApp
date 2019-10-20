package ir.bolive.app.jamisapp.network;

import ir.bolive.app.jamisapp.models.LoginResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Auth {

    @FormUrlEncoded
    @POST("/user/login")
    Call<LoginResponse> login(@Field("username")String username,@Field("password")String password);

    @FormUrlEncoded
    @POST("/user/create")
    Call<LoginResponse> create(@Field("username")String username,@Field("password")String password,@Field("fullname")String fullname);

}
