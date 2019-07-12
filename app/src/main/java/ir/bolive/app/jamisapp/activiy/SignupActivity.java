package ir.bolive.app.jamisapp.activiy;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bolive.app.jamisapp.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignupActivity extends AppCompatActivity {

    @BindView(R.id.signup_btnLogin)
    AppCompatButton btnLogin;
    @BindView(R.id.signup_btnSignup)
    AppCompatButton btnSignup;
    @BindView(R.id.signup_txtFullname)
    AppCompatEditText txtFullname;
    @BindView(R.id.signup_txtNationalCode)
    AppCompatEditText txtNationalCode;
    @BindView(R.id.signup_txtPassword)
    AppCompatEditText txtPassword;
    @BindView(R.id.signup_txtConfirmPass)
    AppCompatEditText txtConfirmPass;

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
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
