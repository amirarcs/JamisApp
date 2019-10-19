package ir.bolive.app.jamisapp.activiy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import java.io.IOException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.bolive.app.jamisapp.R;
import ir.bolive.app.jamisapp.app.Preferences;
import ir.bolive.app.jamisapp.models.LoginResponse;
import ir.bolive.app.jamisapp.network.Auth;
import ir.bolive.app.jamisapp.network.Network;
import ir.bolive.app.jamisapp.network.NetworkChecker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.login_btnLogin)
    AppCompatButton btnLogin;
    @BindView(R.id.login_btnSignUp)
    AppCompatButton btnSignUp;
    @BindView(R.id.login_btnForget)
    AppCompatButton btnForget;
    @BindView(R.id.login_txtUsername)
    AppCompatEditText txtUsername;
    @BindView(R.id.login_txtPassword)
    AppCompatEditText txtPassword;


    Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        preferences = new Preferences(this);
    }

    @OnClick(R.id.login_btnLogin)
    public void onLoginClick() {
        if(NetworkChecker.isConnected(LoginActivity.this)){
            Call<LoginResponse> call = Network.getInstance().authService.login(txtUsername.getText().toString(), txtPassword.getText().toString());
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    switch (response.code()) {
                        case 200:
                            onloginSuccess();
                            break;
                        default:
                            Toast.makeText(LoginActivity.this,response.body().getMessage(),Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    Toast.makeText(LoginActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            Toast.makeText(LoginActivity.this,R.string.noNetwork,Toast.LENGTH_LONG).show();
        }
    }

    void onloginSuccess() {
//        , txtPassword.getText().toString()
        preferences.setKeyIsloggedin(true);
        preferences.setKeyUsername(txtUsername.getText().toString());
//        preferences.setKeyPass("1234");
        Intent intent=new Intent(LoginActivity.this,MainActivity.class);
        this.finish();
        startActivity(intent);
    }

    @OnClick(R.id.login_btnSignUp)
    public void onSignUpClick() {
        Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
        startActivity(intent);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    boolean isDoubleBackPressed = false;

    @Override
    public void onBackPressed() {
        if (isDoubleBackPressed) {
            super.onBackPressed();
        }
        this.isDoubleBackPressed = true;
        Toast.makeText(this, R.string.tapToClose, Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isDoubleBackPressed = false;
            }
        }, 2000);

    }
}
