package ir.bolive.app.jamisapp.activiy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.bolive.app.jamisapp.R;
import ir.bolive.app.jamisapp.app.Preferences;
import ir.bolive.app.jamisapp.models.User;
import ir.bolive.app.jamisapp.models.UserResponse;
import ir.bolive.app.jamisapp.network.NetworkChecker;
import ir.bolive.app.jamisapp.util.Tools;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_btnSignUp)
    AppCompatButton btnSignUp;
    @BindView(R.id.login_btnForget)
    AppCompatButton btnForget;
    @BindView(R.id.login_txtUsername)
    AppCompatEditText txtUsername;
    @BindView(R.id.login_txtPassword)
    AppCompatEditText txtPassword;


    @BindView(R.id.login_coordinator)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.prg_button)
    ProgressBar progressBar;


    Preferences preferences;
    public static final String TAG=LoginActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        preferences = new Preferences(this);
        showProgress(false);
    }

    @OnClick(R.id.btnSubmit)
    public void onLoginClick() {
        Tools.hideKeyboard(LoginActivity.this);
        if(!txtUsername.getText().toString().isEmpty() && !txtPassword.getText().toString().isEmpty()){
            if(NetworkChecker.isConnected(LoginActivity.this)){
                showProgress(true);
                AndroidNetworking
                        .post(NetworkChecker.BASE_URL+"/user/login")
                        .addBodyParameter("username",txtUsername.getText().toString().trim())
                        .addBodyParameter("password",txtPassword.getText().toString().trim())
                        .setTag("Login Request")
                        .setContentType("Content-Type:application/x-www-form-urlencoded")
                        .setPriority(Priority.MEDIUM)
                        .build()
                        .getAsObject(UserResponse.class, new ParsedRequestListener<UserResponse>(){
                            @Override
                            public void onResponse(UserResponse response) {
                                showProgress(false);
                                Log.i(LoginActivity.class.getSimpleName(),"Response :"+response);
                                if(response!=null && response.isSuccess()){
                                    onloginSuccess(response.getUser());
                                }
                                else{
                                    Snackbar.make(coordinatorLayout,R.string.noResponse,Snackbar.LENGTH_LONG).show();
                                }
                            }
                            @Override
                            public void onError(ANError anError) {
                                showProgress(false);
                                try{
                                    Snackbar.make(coordinatorLayout,anError.getErrorAsObject(UserResponse.class).getMessage(),Snackbar.LENGTH_LONG).show();
                                }catch (Exception ex){
                                    Snackbar.make(coordinatorLayout,R.string.somethingIsWrong,Snackbar.LENGTH_LONG).show();
                                    Log.e(TAG,ex.getMessage());
                                }
                            }
                        });

            }
            else{
                Snackbar.make(coordinatorLayout,R.string.noNetwork,Snackbar.LENGTH_SHORT).show();
            }
        }
        else{
            Snackbar.make(coordinatorLayout,R.string.enterAllFields,Snackbar.LENGTH_SHORT).show();
        }

    }

    void onloginSuccess(User user) {
        if(user.isActivated()){
            if(!user.isLoggedIn()){
                preferences.setKeyIsloggedin(true);
                preferences.setKeyUsername(user.getUsername());
                preferences.setKeyFullname(user.getFullname());
                Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                this.finish();
                startActivity(intent);
            }
            else{
                Snackbar.make(coordinatorLayout,R.string.loggedInAccount,Snackbar.LENGTH_SHORT).show();
            }
        }
        else{
            Snackbar.make(coordinatorLayout,R.string.inactiveAccount,Snackbar.LENGTH_SHORT).show();
        }
    }
    private void showProgress(boolean shouldshow){
        if (shouldshow){
            btnSubmit.setEnabled(false);
            btnSubmit.setText("");
            progressBar.setVisibility(View.VISIBLE);
        }
        else{
            btnSubmit.setEnabled(true);
            btnSubmit.setText(R.string.login);
            progressBar.setVisibility(View.GONE);
        }
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
