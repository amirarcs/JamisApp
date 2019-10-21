package ir.bolive.app.jamisapp.activiy;

import android.content.Context;
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
            setData(patients);
        });
    }
    private void setData(List<Patient> patientsList){
        this.patients=patientsList;
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
                String[] column = {"Name","NationalCode","Phone","Reference Date"};
                csvWrite.writeNext(column);
                if(patients!=null && patients.size()>0){
                    for(int i=0; i<patients.size(); i++){
                        String[] mySecondStringArray ={String.valueOf(patients.get(i).getFullname()), patients.get(i).getNationalcode(),patients.get(i).getPhone(),patients.get(i).getRefdate()};
                        csvWrite.writeNext(mySecondStringArray);
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
