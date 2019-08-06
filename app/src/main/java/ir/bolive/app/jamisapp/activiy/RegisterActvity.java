package ir.bolive.app.jamisapp.activiy;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;


import com.github.rubensousa.bottomsheetbuilder.BottomSheetBuilder;
import com.github.rubensousa.bottomsheetbuilder.BottomSheetMenuDialog;
import com.github.rubensousa.bottomsheetbuilder.adapter.BottomSheetItemClickListener;
import com.google.android.material.snackbar.Snackbar;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.bolive.app.jamisapp.R;
import ir.bolive.app.jamisapp.app.PermissionCheck;
import ir.bolive.app.jamisapp.database.DatabaseClient;
import ir.bolive.app.jamisapp.models.FaceArgs;
import ir.bolive.app.jamisapp.models.Gallery;
import ir.bolive.app.jamisapp.models.Patient;
import ir.bolive.app.jamisapp.util.Tools;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterActvity extends AppCompatActivity {

//    @BindView(R.id.btn_p_face)
//    AppCompatButton btnPatientFace;
//    @BindView(R.id.btn_p_info)
//    AppCompatButton btnPatient;
//    @BindView(R.id.btn_p_images)
//    AppCompatButton btnImages;

    @BindView(R.id.reg_expandButtonPatient)
    Button btnExpandPatient;
    @BindView(R.id.reg_expandButtonArg)
    Button btnExpandArg;
    @BindView(R.id.reg_expandButtonImage)
    Button btnExpandImage;

    @BindView(R.id.reg_expand_layout_patient)
    ExpandableLayout layoutPatient;
    @BindView(R.id.reg_expand_layout_args)
    ExpandableLayout layoutArg;
    @BindView(R.id.reg_expand_layout_img)
    ExpandableLayout layoutImage;

//    @BindView(R.id.reg_personal_info)
//    LinearLayout layoutPersonalInfo;
//    @BindView(R.id.reg_face_info)
//    ScrollView layoutFaceInfo;
//    @BindView(R.id.reg_face_img)
//    LinearLayout layoutImageInfo;
    @BindView(R.id.reg_coordiantor)
    CoordinatorLayout coordinatorLayout;

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

//    @BindView(R.id.reg_btn_submit)
//    Button btnSubmit;
    @BindView(R.id.reg_btn_store)
    Button btnSubmit;

    @BindView(R.id.toolbar_top)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    int req_code = 0;
    int CAMERA_REQUEST=200;
    List<String> chinModes=new ArrayList<String>();

    int chinmode,step;
    int mYear,mMonth,mDay;
    long patientId;
    Patient patient=new Patient();
    FaceArgs faceArgs=new FaceArgs();
    Gallery galleryBefore=new Gallery();
    Gallery galleryMask=new Gallery();
    Gallery galleryAfter=new Gallery();

    Bitmap bitmap1=null,bitmap2=null,bitmap3=null;
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for(int i=0;i<grantResults.length;i++){
            if(grantResults[i] == -1){
                Toast.makeText(RegisterActvity.this,R.string.givePermissions, Toast.LENGTH_LONG).show();
                finish();
                return;
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==CAMERA_REQUEST && resultCode== Activity.RESULT_OK){
            Bitmap photo=null;
            photo=(Bitmap)data.getExtras().get("data");
            photo=Tools.image_resize(photo);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            photo.compress(Bitmap.CompressFormat.PNG, 100, stream);
            byte[] byteArray = stream.toByteArray();
            photo.recycle();
            switch (req_code){
                case 1:
                    img_before.setImageBitmap(photo);
                    galleryBefore.setImage(byteArray);
                    break;
                case 2:
                    img_mask.setImageBitmap(photo);
                    galleryMask.setImage(byteArray);
                    break;
                case 3:
                    img_after.setImageBitmap(photo);
                    galleryAfter.setImage(byteArray);
                    break;
            }
        }
    }

    //endregion
    //region Events
    @OnClick(R.id.reg_expandButtonPatient)
    public void onExpandPatient(){
        showPanel(1);
    }
    @OnClick(R.id.reg_expandButtonArg)
    public void onExpandArg(){
        showPanel(2);
    }
    @OnClick(R.id.reg_expandButtonImage)
    public void onExpandImage(){
        showPanel(3);
    }
    @OnClick(R.id.reg_img_before)
    public void onImgBeforeClick(){
        req_code=1;
        bottomSheet();
    }
    @OnClick(R.id.reg_img_mask)
    public void onImgMaskClick(){
        req_code=2;
        bottomSheet();
    }
    @OnClick(R.id.reg_img_after)
    public void onImgAfterClick(){
        req_code=3;
        bottomSheet();
    }
    @OnClick(R.id.reg_btn_store)
    public void onFaceSubmit(){
        if(checkPatientData()){
            if(checkArgData()){
                SaveData();
            }
            else{
                Snackbar.make(coordinatorLayout,R.string.enterArgfields,Snackbar.LENGTH_SHORT).show();
            }
        }
        else{
            Snackbar.make(coordinatorLayout,R.string.enterPatientFields,Snackbar.LENGTH_SHORT).show();
        }

    }

    @OnClick(R.id.reg_rdate)
    public void onRefDateClick(){
        final Calendar calendar=Calendar.getInstance();
        mYear=calendar.get(Calendar.YEAR);
        mMonth=calendar.get(Calendar.MONTH);
        mDay=calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog pickerDialog=new DatePickerDialog(RegisterActvity.this, R.style.AlertDialogStyle,new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                txtRdate.setText(String.format("%s/%s/%s",year,monthOfYear,dayOfMonth));
            }
        },mYear,mMonth,mDay);
        pickerDialog.show();
    }
    //endregion
    //region DBMethods
    private void SaveData(){
        try{
            Executor executor= Executors.newSingleThreadExecutor();
            executor.execute(() -> {
                DatabaseClient databaseClient=DatabaseClient.getInstance(getApplicationContext());
                patientId=databaseClient.getAppDatabase().patientDAO().insertPatient(patient);
                faceArgs.setPid_fk(patientId);
                databaseClient.getAppDatabase().faceArgDAO().insertFaceArgs(faceArgs);
                if(galleryBefore.getImage()!=null){
                    galleryBefore.setPid_fk(patientId);
                    databaseClient.getAppDatabase().galleryDAO().insertGallery(galleryBefore);
                }
                if(galleryAfter.getImage()!=null){
                    galleryAfter.setPid_fk(patientId);
                    databaseClient.getAppDatabase().galleryDAO().insertGallery(galleryMask);
                }
                if(galleryMask.getImage()!=null){
                    galleryMask.setPid_fk(patientId);
                    databaseClient.getAppDatabase().galleryDAO().insertGallery(galleryAfter);
                }
            });
            Snackbar.make(coordinatorLayout,R.string.successMessage,Snackbar.LENGTH_SHORT).show();
            clearAll();
            hideAll();
            showPanel(1);
        }
        catch (Exception ex){
            Snackbar.make(coordinatorLayout,R.string.failureMessage,Snackbar.LENGTH_SHORT).show();
        }
    }
    private void loadData(){

    }
    //endregion
    //region Methods
    void init(){
        setSupportActionBar(toolbar);
        toolbarTitle.setText(getString(R.string.menuRegister));
        showPanel(1);
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
        checkPermission();

    }
    void checkPermission(){
        final  List<String> permission_needed = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(PermissionCheck.isCameraAvailable(RegisterActvity.this)){
                if(!PermissionCheck.isCameraGranted(RegisterActvity.this)){
                    permission_needed.add(Manifest.permission.CAMERA);
                }
                if(permission_needed.size()>0){
                    String [] permissions =new String[permission_needed.size()];
                    permission_needed.toArray(permissions);
                    ActivityCompat.requestPermissions(RegisterActvity.this,permissions,100);
                }
            }
            else{
                Toast.makeText(RegisterActvity.this,R.string.noCameraAvailable,Toast.LENGTH_SHORT).show();
                RegisterActvity.this.finish();
            }

        }
    }

    void hideAll(){
        layoutPatient.collapse();
        layoutImage.collapse();
        layoutArg.collapse();
    }

    void showPanel(int which){
        switch (which){
            case 1:
                hideAll();
                layoutPatient.expand(true);
                break;
            case 2:
                hideAll();
                layoutArg.expand(true);
                break;
            case 3:
                hideAll();
                layoutImage.expand(true);
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
        showPanel(1);
        txtPname.requestFocus();
    }
    void bottomSheet(){
        BottomSheetMenuDialog dialog=new BottomSheetBuilder(RegisterActvity.this,R.style.AppTheme_BottomSheetDialog)
                .setMode(BottomSheetBuilder.MODE_GRID)
                .setMenu(R.menu.menu_bottomsheet)
                .setItemClickListener(new BottomSheetItemClickListener() {
                    @Override
                    public void onBottomSheetItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.sheet_del:
                                delImage();
                                break;
                            case R.id.sheet_pick:
                                startCamera();
                                break;
                            case R.id.sheet_show:
                                showImage();
                                break;
                        }
                    }
                })
                .createDialog();
        dialog.show();
    }
    void showImage(){
        Drawable drawable =null;
        boolean show=false;
        switch (req_code){
            case 1:
                drawable=img_before.getDrawable();
                if(bitmap1 != null)
                    show=true;
                break;

            case 2:
                drawable =img_mask.getDrawable();
                if(bitmap2 != null)
                    show =true;
                break;

            case 3:
                drawable =img_after.getDrawable();
                if(bitmap3!=null)
                    show=true;
                break;
        }

        if(!show){
            Snackbar.make(coordinatorLayout,R.string.noImagetoShow,Snackbar.LENGTH_SHORT).show();
            return;
        }

        Dialog dialog=new Dialog(RegisterActvity.this);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.dialog_show);
        ImageView imageView =(ImageView)dialog.findViewById(R.id.dialog_img);
        imageView.setImageDrawable(drawable);
        dialog.show();
    }
    void delImage(){
        switch (req_code){
            case 1:
                bitmap1=null;
                img_before.setImageResource(R.drawable.ic_image);
                break;

            case 2:
                bitmap2=null;
                img_mask.setImageResource(R.drawable.ic_image);
                break;

            case 3:
                bitmap3=null;
                img_after.setImageResource(R.drawable.ic_image);
                break;

        }
    }
    void startCamera(){
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
    }
    boolean checkPatientData(){
        if(!txtPname.getText().toString().isEmpty()&&
                !txtNcode.getText().toString().isEmpty()&&
                !txtPhone.getText().toString().isEmpty()&&
                !txtRdate.getText().toString().isEmpty()){
            String pname=txtPname.getText().toString();
            String ncode=txtNcode.getText().toString();
            String phone=txtPhone.getText().toString();
            String refDate=txtRdate.getText().toString();
            patient.setFullname(pname);
            patient.setNationalcode(ncode);
            patient.setPhone(phone);
            patient.setRefdate(refDate);
            return true;
        }
        else{
            return false;
        }
    }
    boolean checkArgData(){
        if(!txtUpInc.getText().toString().isEmpty()&&
                !txtLowerInc.getText().toString().isEmpty()&&
                !txtUpging.getText().toString().isEmpty()&&
                !txtLowerging.getText().toString().isEmpty()&&
                !txtEye_x.getText().toString().isEmpty()&&
                !txtEye_y.getText().toString().isEmpty()&&
                !txtEyebrow_y.getText().toString().isEmpty()&&
                !txtEyebrow_x.getText().toString().isEmpty()&&
                !txtRamus_y.getText().toString().isEmpty()&&
                !txtRamus_x.getText().toString().isEmpty()&&
                !txtEar_y.getText().toString().isEmpty()&&
                !txtEar_x.getText().toString().isEmpty()&&
                chinmode!=0){
            faceArgs.setUpper_central_ans(Float.parseFloat(txtUpInc.getText().toString()));
            faceArgs.setLower_central_ans(Float.parseFloat(txtLowerInc.getText().toString()));
            faceArgs.setUpper_ging(Float.parseFloat(txtUpging.getText().toString()));
            faceArgs.setLower_ging(Float.parseFloat(txtLowerging.getText().toString()));
            faceArgs.setX_eye(Float.parseFloat(txtEye_x.getText().toString()));
            faceArgs.setY_eye(Float.parseFloat(txtEye_y.getText().toString()));
            faceArgs.setX_ear(Float.parseFloat(txtEar_x.getText().toString()));
            faceArgs.setY_ear(Float.parseFloat(txtEar_y.getText().toString()));
            faceArgs.setX_eyebrow(Float.parseFloat(txtEyebrow_x.getText().toString()));
            faceArgs.setY_eyebrow(Float.parseFloat(txtEyebrow_y.getText().toString()));
            faceArgs.setX_ramus(Float.parseFloat(txtRamus_x.getText().toString()));
            faceArgs.setY_ramus(Float.parseFloat(txtRamus_y.getText().toString()));
            faceArgs.setMidLine(0f);
            faceArgs.setChinMode(chinmode);
            return false;
        }
        else{
            return false;
        }
    }
    //endregion
}
