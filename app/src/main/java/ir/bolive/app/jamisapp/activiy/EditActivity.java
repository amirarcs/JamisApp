package ir.bolive.app.jamisapp.activiy;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.bolive.app.jamisapp.R;
import ir.bolive.app.jamisapp.app.Preferences;
import ir.bolive.app.jamisapp.models.Response;
import ir.bolive.app.jamisapp.network.Network;
import ir.bolive.app.jamisapp.network.NetworkChecker;
import retrofit2.Call;
import retrofit2.Callback;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EditActivity extends AppCompatActivity {

    //#region define views and variables
    @BindView(R.id.toolbar_top)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.edit_coordinator)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.edit_name)
    EditText txtname;
    @BindView(R.id.edit_nationalCode)
    EditText txtNcode;
    @BindView(R.id.edit_password)
    EditText txtPass;
    @BindView(R.id.edit_passwordconfirm)
    EditText txtConfirmPass;

    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.prg_button)
    ProgressBar progressBar;

    Preferences preferences;


    //endregion
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @OnClick(R.id.btnSubmit)
    public void onUpdateClick(){
        if(!txtPass.getText().toString().isEmpty() && !txtConfirmPass.getText().toString().isEmpty() && !txtNcode.getText().toString().isEmpty()){
            if(txtPass.getText().toString().equals(txtConfirmPass.getText().toString())){
                if(NetworkChecker.isConnected(EditActivity.this)){
                    doUpdate();
                }
                else{
                    Snackbar.make(coordinatorLayout,R.string.noNetwork,Snackbar.LENGTH_SHORT).show();
                }
            }
            else{
                Snackbar.make(coordinatorLayout,R.string.passwordnotMatch,Snackbar.LENGTH_SHORT).show();
            }

        }
    }
    //region Methods
    void init(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbarTitle.setText(getString(R.string.menuEdit));
        preferences=new Preferences(EditActivity.this);
        toolbarTitle.setText(getString(R.string.menuEdit));
        txtname.setText(preferences.getKeyFullname());
        txtNcode.setText(preferences.getKeyUsername());
        txtPass.setText(preferences.getKeyPass());
    }
    private void showProgress(boolean shouldshow){
        if (shouldshow){
            btnSubmit.setEnabled(false);
            btnSubmit.setText("");
            progressBar.setVisibility(View.VISIBLE);
        }
        else{
            btnSubmit.setEnabled(true);
            btnSubmit.setText(R.string.update);
            progressBar.setVisibility(View.GONE);
        }
    }
    private void doUpdate(){

        Call<Response> changeInfoCall= Network.getInstance().authService.changeInfo(txtNcode.getText().toString(),txtPass.getText().toString(),txtname.getText().toString());
        changeInfoCall.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                showProgress(false);
                Response rp=response.body();
                if(rp!=null){
                    if(rp.getSuccess()){
                        preferences.setKeyUsername(txtNcode.getText().toString().trim());
                        preferences.setKeyFullname(txtname.getText().toString().trim());
                        preferences.setKeyPass(txtPass.getText().toString().trim());
                        Snackbar.make(coordinatorLayout,R.string.successMessage,Snackbar.LENGTH_LONG).show();
                    }
                    else{
                        String msg=rp.getMessage();
                        Snackbar.make(coordinatorLayout,msg,Snackbar.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                showProgress(false);
                Snackbar.make(coordinatorLayout,R.string.noResponse,Snackbar.LENGTH_LONG).show();
            }
        });
    }
    //endregion
}
