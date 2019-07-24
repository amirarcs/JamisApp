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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ir.bolive.app.jamisapp.R;
import ir.bolive.app.jamisapp.database.DatabaseClient;
import ir.bolive.app.jamisapp.models.Patient;
import ir.bolive.app.jamisapp.util.CSVWriter;
import ir.bolive.app.jamisapp.util.DialogUtil;
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
                        exportData();
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
        toolbarTitle.setText(getString(R.string.menuBackUp));
        dialogUtil=new DialogUtil(ExportActivity.this,R.style.AlertDialogStyle);
        showProgress(false);
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
    void exportData(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            new ExportDatabaseCSVTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            new ExportDatabaseCSVTask().execute();
        }
    }
    public class ExportDatabaseCSVTask extends AsyncTask<String, Void, Boolean>{
        private DatabaseClient databaseClient;

        @Override
        protected void onPreExecute() {
            showProgress(true);
           databaseClient =DatabaseClient.getInstance(ExportActivity.this);
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean) {
                Snackbar.make(coordinatorLayout, R.string.successMessage, Snackbar.LENGTH_SHORT).show();
            } else {
                Snackbar.make(coordinatorLayout, R.string.failureMessage, Snackbar.LENGTH_SHORT).show();
            }
            showProgress(false);
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
                List<Patient> patients = databaseClient.getAppDatabase().patientDAO().getAll();
                for(int i=0; i<patients.size(); i++){
                    String[] mySecondStringArray ={String.valueOf(patients.get(i).getFullname()), patients.get(i).getNationalcode(),patients.get(i).getPhone(),patients.get(i).getRefdate()};
                    csvWrite.writeNext(mySecondStringArray);
                }
                csvWrite.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        }
    }
    //endregion
}
