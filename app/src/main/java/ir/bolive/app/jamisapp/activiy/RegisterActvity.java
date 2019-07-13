package ir.bolive.app.jamisapp.activiy;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SpinnerAdapter;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatSpinner;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bolive.app.jamisapp.R;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterActvity extends AppCompatActivity {

    @BindView(R.id.btn_p_face)
    AppCompatButton btnPatientFace;
    @BindView(R.id.btn_p_info)
    AppCompatButton btnPatient;
    @BindView(R.id.btn_p_images)
    AppCompatButton btnImages;

    @BindView(R.id.reg_personal_info)
    LinearLayout layoutPersonalInfo;
    @BindView(R.id.reg_face_info)
    LinearLayout layoutFaceInfo;
    @BindView(R.id.reg_face_img)
    LinearLayout layoutImageInfo;

    @BindView(R.id.reg_pname)
    EditText txtPname;
    @BindView(R.id.reg_ncode)
    EditText txtNcode;
    @BindView(R.id.reg_phone)
    EditText txtPhone;
    @BindView(R.id.reg_rdate)
    EditText txtRdate;

    @BindView(R.id.reg_upper_inc)
    EditText txtUpInc;
    @BindView(R.id.reg_lower_inc)
    EditText txtLowerInc;
    @BindView(R.id.reg_upper_ging)
    EditText txtUpging;
    @BindView(R.id.reg_lower_ging)
    EditText txtLowerging;
    @BindView(R.id.reg_ear_x)
    EditText txtEar_x;
    @BindView(R.id.reg_ear_y)
    EditText txtEar_y;
    @BindView(R.id.reg_eye_x)
    EditText txtEye_x;
    @BindView(R.id.reg_eye_y)
    EditText txtEye_y;
    @BindView(R.id.reg_eyebrow_x)
    EditText txtEyebrow_x;
    @BindView(R.id.reg_eyebrow_y)
    EditText txtEyebrow_y;
    @BindView(R.id.reg_ramus_x)
    EditText txtRamus_x;
    @BindView(R.id.reg_ramus_y)
    EditText txtRamus_y;
    @BindView(R.id.reg_chinmode)
    AppCompatSpinner sp_chinmode;

    @BindView(R.id.reg_btn_submit)
    Button btnSubmit;
    @BindView(R.id.reg_btn_face_submit)
    Button btnFaceSubmit;

    List<String> chinModes=new ArrayList<String>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_patient);
        ButterKnife.bind(this);
        init();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    //region Methods
    void init(){
        chinModes.add("M");
        chinModes.add("Swallow");
        chinModes.add("Simple");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,chinModes);
        sp_chinmode.setAdapter(adapter);
    }
    void hideAll(){
        layoutFaceInfo.setVisibility(View.GONE);
        layoutImageInfo.setVisibility(View.GONE);
        layoutPersonalInfo.setVisibility(View.GONE);
    }
    void showPanel(int which){
        switch (which){
            case 1:
                hideAll();
                layoutPersonalInfo.setVisibility(View.VISIBLE);
                break;
            case 2:
                hideAll();
                layoutFaceInfo.setVisibility(View.VISIBLE);
                break;
            case 3:
                hideAll();
                layoutImageInfo.setVisibility(View.VISIBLE);
                break;
        }
    }
    //endregion
}
