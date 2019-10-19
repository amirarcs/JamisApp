package ir.bolive.app.jamisapp.activiy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.bolive.app.jamisapp.R;
import ir.bolive.app.jamisapp.app.Preferences;
import ir.bolive.app.jamisapp.util.DialogUtil;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_btn_backup)
    LinearLayout btnBackup;
    @BindView(R.id.main_btn_edit)
    LinearLayout btnEdit;
    @BindView(R.id.main_btn_reg_patient)
    LinearLayout btnReg;
    @BindView(R.id.main_btn_search)
    LinearLayout btnSearch;

    @BindView(R.id.toolbar_top)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    DialogUtil dialogUtil;

    Preferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }
    boolean isDoublePressed=false;
    @Override
    public void onBackPressed() {
        if (isDoublePressed){
            super.onBackPressed();
            return;
        }
        this.isDoublePressed=true;
        Toast.makeText(this,R.string.tapToClose,Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                isDoublePressed=false;
            }
        },2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnu_logout:
                askToLogout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.main_btn_reg_patient)
    public void onRegClick(){
        Intent intent=new Intent(MainActivity.this,RegisterActvity.class);
        startActivity(intent);
    }
    @OnClick(R.id.main_btn_search)
    public void onSearchClick(){
        Intent intent=new Intent(MainActivity.this,DisplayPatientsActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.main_btn_backup)
    public void onBackupClick(){
        Intent intent=new Intent(MainActivity.this,ExportActivity.class);
        startActivity(intent);
    }
    @OnClick(R.id.main_btn_edit)
    public void onEditClick(){
        Intent intent=new Intent(MainActivity.this,EditActivity.class);
        startActivity(intent);
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    void init(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbarTitle.setText(getString(R.string.app_name));
        dialogUtil=new DialogUtil(MainActivity.this,R.style.AlertDialogStyle);
        preferences=new Preferences(MainActivity.this);
    }
    void askToLogout(){
        final AlertDialog.Builder builder =dialogUtil.createAlert(getResources().getString(R.string.askToLogout), getResources().getString(R.string.yes), getResources().getString(R.string.no),
                new DialogUtil.CallbackAlertDialog() {
                    @Override
                    public void OnAlertPositiveClick(AlertDialog.Builder builder) {
                        preferences.logout();
                        Intent intent=new Intent(MainActivity.this,LoginActivity.class);
                        startActivity(intent);
                        MainActivity.this.finish();
                    }

                    @Override
                    public void OnAlertNegativeClick(AlertDialog.Builder builder) {

                    }
                });
        builder.show();
    }
}
