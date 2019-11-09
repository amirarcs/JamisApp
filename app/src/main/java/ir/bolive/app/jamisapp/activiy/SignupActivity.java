package ir.bolive.app.jamisapp.activiy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

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
import ir.bolive.app.jamisapp.models.Response;
import ir.bolive.app.jamisapp.models.UserResponse;
import ir.bolive.app.jamisapp.network.Network;
import ir.bolive.app.jamisapp.network.NetworkChecker;
import retrofit2.Call;
import retrofit2.Callback;
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
    public static final String TAG=SignupActivity.class.getSimpleName();
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
            /*Call<UserResponse> call= Network.getInstance().authService.create(txtNationalCode.getText().toString()
                    ,txtPassword.getText().toString(),txtFullname.getText().toString());
            call.enqueue(new Callback<UserResponse>() {
                @Override
                public void onResponse(Call<UserResponse> call, Response<UserResponse> response) {

                    switch (response.code()) {
                        case 200:

                            break;
                        default:
                            UserResponse rp=response.body();
                            Snackbar.make(coordinatorLayout,rp.getMessage(),Snackbar.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<UserResponse> call, Throwable t) {

                }
            });*/
            AndroidNetworking
                    .post(NetworkChecker.BASE_URL+"/user/create")
                    .addBodyParameter("username",txtNationalCode.getText().toString())
                    .addBodyParameter("password",txtPassword.getText().toString().trim())
                    .addBodyParameter("fullname",txtFullname.getText().toString().trim())
                    .setTag("Signup Request")
                    .setContentType("Content-Type:application/x-www-form-urlencoded")
                    .setPriority(Priority.MEDIUM)
                    .build()
                    .getAsObject(Response.class, new ParsedRequestListener<Response>() {
                        @Override
                        public void onResponse(Response response) {
                            showProgress(false);
                            onCreateSuccess();
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
            Snackbar.make(coordinatorLayout,R.string.noNetwork,Snackbar.LENGTH_LONG).show();
        }
    }
    private void onCreateSuccess(){
        Intent intent=new Intent(SignupActivity.this,LoginActivity.class);
        this.finish();
        startActivity(intent);

    }
}
