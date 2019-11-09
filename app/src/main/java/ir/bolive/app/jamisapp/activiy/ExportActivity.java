package ir.bolive.app.jamisapp.activiy;

import android.content.Context;
import android.media.FaceDetector;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.snackbar.Snackbar;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.bolive.app.jamisapp.R;
import ir.bolive.app.jamisapp.database.FacesDatabase;
import ir.bolive.app.jamisapp.models.FaceArgs;
import ir.bolive.app.jamisapp.models.Patient;
import ir.bolive.app.jamisapp.util.CSVWriter;
import ir.bolive.app.jamisapp.util.DialogUtil;
import ir.bolive.app.jamisapp.util.Tools;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ExportActivity extends AppCompatActivity {
    @BindView(R.id.toolbar_top)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.btnSubmit)
    Button btnSend;
    @BindView(R.id.prg_button)
    ProgressBar progressBar;

    @BindView(R.id.export_coordinator)
    CoordinatorLayout coordinatorLayout;
    DialogUtil dialogUtil;

    List<Patient> patients;
    List<FaceArgs> faceArgs;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        ButterKnife.bind(this);
        init();
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @OnClick(R.id.btnSubmit)
    public void onExportClick(){
        final AlertDialog.Builder builder = dialogUtil.createAlert(getResources().getString(R.string.askToUpdate), getResources().getString(R.string.yes),
                getResources().getString(R.string.no), new DialogUtil.CallbackAlertDialog() {
                    @Override
                    public void OnAlertPositiveClick(AlertDialog.Builder builder) {
                        showProgress(true);
                        getAllData();

                    }

                    @Override
                    public void OnAlertNegativeClick(AlertDialog.Builder builder) {
                    }
                });
        builder.show();
    }
    //region Methods
    void init(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        toolbarTitle.setText(getString(R.string.menuBackUp));
        dialogUtil=new DialogUtil(ExportActivity.this,R.style.AlertDialogStyle);
        showProgress(false);
        Tools.loadBackgroundAnimation(coordinatorLayout);
    }
    private void showProgress(boolean shouldshow){
        if (shouldshow){
            btnSend.setEnabled(false);
            btnSend.setText("");
            progressBar.setVisibility(View.VISIBLE);
        }
        else{
            btnSend.setEnabled(true);
            btnSend.setText(R.string.export);
            progressBar.setVisibility(View.GONE);
        }
    }
    private void getAllData(){
        Executor executor= Executors.newSingleThreadExecutor();
        executor.execute(()->{
            FacesDatabase db=FacesDatabase.getdatabase(ExportActivity.this);
            patients = db.getInstance().patientDAO().getAll();
            faceArgs=db.getInstance().faceArgDAO().getAll();
            setData(patients,faceArgs);
        });
    }
    private void setData(List<Patient> patientsList,List<FaceArgs> faceArgs){
        this.patients=patientsList;
        this.faceArgs=faceArgs;
        if(patients!=null && patients.size()>0) {
            exportData();
        }
        else{
            Snackbar.make(coordinatorLayout, R.string.exportNoData, Snackbar.LENGTH_LONG).show();
            showProgress(false);
        }
    }
    void exportData(){
        new ExportDatabaseCSVTask().execute();
    }
    void callReset(){
        showProgress(false);
    }
    List<FaceArgs> getPatientFaceArgs(long pid){
        List<FaceArgs> list=null;
        for (int i=0;i<faceArgs.size();i++){
            if(pid==faceArgs.get(i).getPid_fk()){
                list.add(faceArgs.get(i));
            }
        }
        return list;
    }
    public class ExportDatabaseCSVTask extends AsyncTask<String, Void, Boolean>{

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            callReset();
            if (aBoolean) {
                Snackbar.make(coordinatorLayout, R.string.successMessage, Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(coordinatorLayout, R.string.failureMessage, Snackbar.LENGTH_SHORT).show();
            }
        }

        @Override
        protected Boolean doInBackground(String... strings) {
            File exportDir = new File(Environment.getExternalStorageDirectory(), "/jamiExport/");
            if (!exportDir.exists()) { exportDir.mkdirs(); }

            File file = new File(exportDir, "Patients.csv");
            try {
                file.createNewFile();
                CSVWriter csvWrite = new CSVWriter(new FileWriter(file));
                if(patients!=null && patients.size()>0){
                    int i=0;
                    while(i<patients.size()){
                        List<FaceArgs> pface=getPatientFaceArgs(patients.get(i).getPid());
                        String[] firstColumn = {"Name","NationalCode","Phone","Reference Date"};
                        csvWrite.writeNext(firstColumn);
                        //
                        String[] mySecondStringArray ={String.valueOf(patients.get(i).getFullname()), patients.get(i).getNationalcode(),patients.get(i).getPhone(),patients.get(i).getRefdate()};
                        csvWrite.writeNext(mySecondStringArray);
                        //
                        if(pface!=null){
                            for (int j=0;j<pface.size();j++){
                                String[] thirdColumn={"upper_central_ans","lower_central_ans","upper_ging","lower_ging",
                                        "chinMode","x_ear","y_ear","x_eye","y_eye","x_eyebrow","y_eyebrow","x_ramus","y_ramus"};
                                csvWrite.writeNext(thirdColumn);
                                //
                                String[] fourthColumn={String.valueOf(pface.get(j).getUpper_central_ans()),String.valueOf(pface.get(j).getLower_central_ans()),String.valueOf(pface.get(j).getUpper_ging()),
                                        String.valueOf(pface.get(j).getLower_ging()),(pface.get(j).getChinMode()==1?"Simple":pface.get(j).getChinMode()==2?"M":"Swallow"),String.valueOf(pface.get(j).getX_ear()),
                                                String.valueOf(pface.get(j).getY_ear()),String.valueOf(pface.get(j).getX_eye()),String.valueOf(pface.get(j).getY_eye()),
                                                        String.valueOf(pface.get(j).getX_eyebrow()),String.valueOf(pface.get(j).getY_eyebrow()),String.valueOf(pface.get(j).getX_ramus()),String.valueOf(pface.get(j).getY_ramus())};
                            }
                        }
                        i++;
                    }
                    csvWrite.close();
                    return true;
                }
                else{
                    return false;
                }

            } catch (IOException e) {
                return false;
            }
        }
    }
    //endregion
}
