package ir.bolive.app.jamisapp.network;

import java.security.PublicKey;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Network {
    private static final Network ourInstance = new Network();
    Retrofit retrofit;
    public Auth authService;

    public static Network getInstance() {
        return ourInstance;
    }

    private Network() {
        retrofit = new Retrofit.Builder()
                .baseUrl("https://faceapp.liara.run/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        authService = retrofit.create(Auth.class);
    }
}
