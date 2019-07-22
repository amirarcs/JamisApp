package ir.bolive.app.jamisapp.activiy;

import android.content.Context;
import android.os.Bundle;
import android.widget.EditText;
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
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class EditActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_top)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.export_coordinator)
    CoordinatorLayout coordinatorLayout;

    @BindView(R.id.edit_name)
    EditText txtname;
    @BindView(R.id.edit_nationalCode)
    EditText txtNcode;
    @BindView(R.id.edit_password)
    EditText txtPass;
    @BindView(R.id.edit_passwordconfirm)
    EditText txtConfirmPass;
    Preferences preferences;
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
    @OnClick(R.id.edit_btnChange)
    public void onChangeClick(){
        if(!txtPass.getText().toString().isEmpty() && !txtConfirmPass.getText().toString().isEmpty() && !txtNcode.getText().toString().isEmpty()){
            if(txtPass.getText().toString().equals(txtConfirmPass.getText().toString())){
                preferences.setKeyUsername(txtNcode.getText().toString().trim());
                preferences.setKeyFullname(txtname.getText().toString().trim());
                preferences.setKeyPass(txtPass.getText().toString().trim());
                Snackbar.make(coordinatorLayout,R.string.successMessage,Snackbar.LENGTH_SHORT).show();
            }
            else{
                Snackbar.make(coordinatorLayout,R.string.passwordnotMatch,Snackbar.LENGTH_SHORT).show();
            }
        }
    }
    //region Methods
    void init(){
        preferences=new Preferences(EditActivity.this);
        setSupportActionBar(toolbar);
        toolbarTitle.setText(getString(R.string.menuEdit));
        txtname.setText(preferences.getKeyFullname());
        txtNcode.setText(preferences.getKeyUsername());
        txtPass.setText(preferences.getKeyPass());
    }
    //endregion
}
