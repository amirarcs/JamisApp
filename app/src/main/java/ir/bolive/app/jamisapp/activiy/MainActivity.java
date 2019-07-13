package ir.bolive.app.jamisapp.activiy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.bolive.app.jamisapp.R;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_btn_backup)
    LinearLayout btnBackup;
    @BindView(R.id.main_btn_edit)
    LinearLayout btnEdit;
    @BindView(R.id.main_btn_reg_patient)
    LinearLayout btnReg;
    @BindView(R.id.main_btn_search)
    LinearLayout btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
    }
    @OnClick(R.id.main_btn_reg_patient)
    private void onRegClick(){
        Intent intent=new Intent(MainActivity.this,MainActivity.class);
        startActivity(intent);
    }
    void init(){
        this.setTitle("Main Page");
    }
}
