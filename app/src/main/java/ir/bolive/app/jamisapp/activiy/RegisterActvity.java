package ir.bolive.app.jamisapp.activiy;

import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.bolive.app.jamisapp.R;
import ir.bolive.app.jamisapp.database.DatabaseClient;
import ir.bolive.app.jamisapp.models.FaceArgs;
import ir.bolive.app.jamisapp.models.Gallery;
import ir.bolive.app.jamisapp.models.Patient;
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

    @BindView(R.id.reg_img_before)
    ImageView img_before;
    @BindView(R.id.reg_img_after)
    ImageView img_after;
    @BindView(R.id.reg_img_mask)
    ImageView img_mask;

    @BindView(R.id.reg_btn_submit)
    Button btnSubmit;
    @BindView(R.id.reg_btn_face_submit)
    Button btnFaceSubmit;

    @BindView(R.id.toolbar_top)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    List<String> chinModes=new ArrayList<String>();

    boolean editmode;
    int chinmode,step;
    int mYear,mMonth,mDay;
    long patientId;
    Patient patient=new Patient();
    FaceArgs faceArgs=new FaceArgs();
    Gallery galleryBefore=new Gallery();
    Gallery galleryMask=new Gallery();
    Gallery galleryAfter=new Gallery();
    //region Override Methods
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

    //endregion
    //region Events
    @OnClick(R.id.reg_btn_face_submit)
    public void onFaceSubmit(){

    }
    @OnClick(R.id.reg_btn_submit)
    public void onSubmitClick(){
        if(!txtPname.getText().toString().isEmpty()&&
                !txtNcode.getText().toString().isEmpty()&&
                !txtPhone.getText().toString().isEmpty()&&
                txtRdate.getText().toString().isEmpty()&&
                chinmode!=0){
            String pname=txtPname.getText().toString();
            String ncode=txtNcode.getText().toString();
            String phone=txtPhone.getText().toString();
            String refDate=txtRdate.getText().toString();
            patient.setFullname(pname);
            patient.setNationalcode(ncode);
            patient.setPhone(phone);
            patient.setRefdate(refDate);
            showPanel(2);
        }
    }
    @OnClick(R.id.btn_p_face)
    public void onPFaceClick(){
        showPanel(2);
    }
    @OnClick(R.id.btn_p_info)
    public void onPInfoClick(){
        showPanel(1);
    }
    @OnClick(R.id.btn_p_images)
    public void onPImageClick(){
        showPanel(3);
    }
    @OnClick(R.id.reg_rdate)
    public void onRefDateClick(){
        final Calendar calendar=Calendar.getInstance();
        mYear=calendar.get(Calendar.YEAR);
        mMonth=calendar.get(Calendar.MONTH);
        mDay=calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog pickerDialog=new DatePickerDialog(getApplicationContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                txtRdate.setText(year+"/"+(monthOfYear+1)+"/"+dayOfMonth);
            }
        },mYear,mMonth,mDay);
        pickerDialog.show();
    }
    //endregion
    //region DBMethods
    private void SaveData(){
        Executor executor= Executors.newSingleThreadExecutor();
        executor.execute(() -> {
            DatabaseClient databaseClient=DatabaseClient.getInstance(getApplicationContext());
            patientId=databaseClient.getAppDatabase().patientDAO().insertPatient(patient);
            databaseClient.getAppDatabase().faceArgDAO().insertFaceArgs(faceArgs);
            databaseClient.getAppDatabase().galleryDAO().insertGallery(galleryBefore);
            databaseClient.getAppDatabase().galleryDAO().insertGallery(galleryMask);
            databaseClient.getAppDatabase().galleryDAO().insertGallery(galleryAfter);
        });
    }
    //endregion
    //region Methods
    void init(){
        setSupportActionBar(toolbar);
        toolbarTitle.setText(getString(R.string.menuRegister));
        // *********setup spinner**************
        chinModes.add("-Select Chin Mode -");
        chinModes.add("M");
        chinModes.add("Swallow");
        chinModes.add("Simple");
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,R.layout.row,chinModes);
        sp_chinmode.setAdapter(adapter);
        sp_chinmode.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i!=0){
                    chinmode=i+1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                chinmode=0;
            }
        });
        //*****************************

    }
    void disbleAll(){
        btnPatient.setEnabled(false);
        btnPatientFace.setEnabled(false);
        btnImages.setEnabled(false);
    }
    void hideAll(){
        layoutFaceInfo.setVisibility(View.GONE);
        layoutImageInfo.setVisibility(View.GONE);
        layoutPersonalInfo.setVisibility(View.GONE);
    }
    void enableTab(int which){
        switch (which){
            case 1:
                disbleAll();
                btnPatient.setEnabled(true);
                break;
            case 2:
                disbleAll();
                btnPatientFace.setEnabled(true);
                break;
            case 3:
                disbleAll();
                btnImages.setEnabled(true);
                break;
        }
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
    void clearAll(){
        txtRdate.setText("");
        txtPhone.setText("");
        txtNcode.setText("");
        txtPname.setText("");

        txtEar_x.setText("");
        txtEar_y.setText("");
        txtEye_y.setText("");
        txtEye_x.setText("");
        txtEyebrow_x.setText("");
        txtEyebrow_y.setText("");
        txtLowerging.setText("");
        txtUpging.setText("");
        txtUpInc.setText("");
        txtLowerInc.setText("");
        txtRamus_x.setText("");
        txtRamus_y.setText("");

        img_after.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_image));
        img_before.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_image));
        img_mask.setImageDrawable(ContextCompat.getDrawable(getApplicationContext(),R.drawable.ic_image));
    }
    //endregion
}
