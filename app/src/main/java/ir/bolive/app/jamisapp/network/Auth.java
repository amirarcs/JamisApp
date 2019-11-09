package ir.bolive.app.jamisapp.network;

import ir.bolive.app.jamisapp.models.UserResponse;
import ir.bolive.app.jamisapp.models.Response;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Auth {

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("/user/login")
    Call<UserResponse> login(@Field("username")String username, @Field("password")String password);

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("/user/create")
    Call<UserResponse> create(@Field("username")String username, @Field("password")String password, @Field("fullname")String fullname);

    @Headers("Content-Type:application/x-www-form-urlencoded")
    @FormUrlEncoded
    @POST("/user/changePass")
    Call<Response> changeInfo(@Field("username")String username,@Field("password")String password,@Field("fullname")String fullname);
}
