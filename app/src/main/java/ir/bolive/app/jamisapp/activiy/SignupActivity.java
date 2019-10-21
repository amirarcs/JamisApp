package ir.bolive.app.jamisapp.activiy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.bolive.app.jamisapp.R;
import ir.bolive.app.jamisapp.models.LoginResponse;
import ir.bolive.app.jamisapp.network.Network;
import ir.bolive.app.jamisapp.network.NetworkChecker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class SignupActivity extends AppCompatActivity {

    @BindView(R.id.signup_btnLogin)
    AppCompatButton btnLogin;

    @BindView(R.id.signup_txtFullname)
    AppCompatEditText txtFullname;
    @BindView(R.id.signup_txtNationalCode)
    AppCompatEditText txtNationalCode;
    @BindView(R.id.signup_txtPassword)
    AppCompatEditText txtPassword;
    @BindView(R.id.signup_txtConfirmPass)
    AppCompatEditText txtConfirmPass;

    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.prg_button)
    ProgressBar progressBar;


    @BindView(R.id.signup_coordinator)
    CoordinatorLayout coordinatorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
        showProgress(false);
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
    @OnClick(R.id.signup_btnLogin)
    public void onLoginClick(){
        this.finish();
    }
    @OnClick(R.id.btnSubmit)
    public void onButtonClick(View view){
        if(checkFields()){
            if(txtPassword.getText().toString().equals(txtConfirmPass.getText().toString())){
                showProgress(true);
                doSignUp();
            }
            else{
                Snackbar.make(coordinatorLayout,R.string.passwordnotMatch,Snackbar.LENGTH_LONG).show();
            }
        }
        else{
            Snackbar.make(coordinatorLayout,R.string.enterAllFields,Snackbar.LENGTH_LONG).show();
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
            btnSubmit.setText(R.string.signup);
            progressBar.setVisibility(View.GONE);
        }
    }
    private boolean checkFields(){
        if(!txtNationalCode.getText().toString().isEmpty() && !txtFullname.getText().toString().isEmpty()
        && !txtPassword.getText().toString().isEmpty() && !txtConfirmPass.getText().toString().isEmpty()){
            return true;
        }
        else{
            return false;
        }
    }
    private void doSignUp(){
        if(NetworkChecker.isConnected(SignupActivity.this)){
            Call<LoginResponse> call= Network.getInstance().authService.create(txtNationalCode.getText().toString()
                    ,txtPassword.getText().toString(),txtFullname.getText().toString());
            call.enqueue(new Callback<LoginResponse>() {
                @Override
                public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                    showProgress(false);
                    switch (response.code()) {
                        case 200:
                            onCreateSuccess();
                            break;
                        default:
                            Snackbar.make(coordinatorLayout,response.body().getMessage(),Snackbar.LENGTH_LONG).show();
                    }
                }
                @Override
                public void onFailure(Call<LoginResponse> call, Throwable t) {
                    showProgress(false);
                    Snackbar.make(coordinatorLayout,R.string.noResponse,Snackbar.LENGTH_LONG).show();
                }
            });
        }
        else{
            Snackbar.make(coordinatorLayout,R.string.noNetwork,Snackbar.LENGTH_LONG).show();
        }
    }
    private void onCreateSuccess(){
        Intent intent=new Intent(SignupActivity.this,LoginActivity.class);
        this.finish();
        startActivity(intent);

    }
}
