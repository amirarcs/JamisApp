package ir.bolive.app.jamisapp.activiy;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bolive.app.jamisapp.R;
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

    //progress button
    @BindView(R.id.signup_coordinator)
    CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    boolean isDoubleBackPressed=false;
    @Override
    public void onBackPressed() {
        if(isDoubleBackPressed){
            super.onBackPressed();
        }
        this.isDoubleBackPressed=true;
        Toast.makeText(this,R.string.tapToClose,Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isDoubleBackPressed=false;
            }
        },2000);

    }
}
