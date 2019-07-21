package ir.bolive.app.jamisapp.activiy;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bolive.app.jamisapp.R;
import ir.bolive.app.jamisapp.adapter.PatientsAdapter;
import ir.bolive.app.jamisapp.database.DatabaseClient;
import ir.bolive.app.jamisapp.models.Patient;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DisplayPatientsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_top)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;

    @BindView(R.id.display_coordiantor)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.display_recycler)
    RecyclerView recyclerView;

    PatientsAdapter adapter;
    List<Patient> patientList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_patients);
        ButterKnife.bind(this);
        init();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search,menu);
        MenuItem item=menu.findItem(R.id.mnu_search);
        if(item!=null){
            SearchView searchView=(SearchView) MenuItemCompat.getActionView(item);
            searchView.setQueryHint(getString(R.string.searchPatients));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    if(query!=""){
                        searchData(query);
                    }
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if(newText==""){
                        loadAllData();
                    }
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    //region Methods
    private void init(){
        setSupportActionBar(toolbar);
        toolbarTitle.setText(getString(R.string.menuDisplay));
        adapter=new PatientsAdapter(patientList,DisplayPatientsActivity.this);
        recyclerView.setLayoutManager(new LinearLayoutManager(DisplayPatientsActivity.this,RecyclerView.VERTICAL,false));
        recyclerView.setAdapter(adapter);
        loadAllData();
    }
    private void loadAllData(){
        Executor executor=Executors.newSingleThreadExecutor();
        executor.execute(()->{
            DatabaseClient client=DatabaseClient.getInstance(DisplayPatientsActivity.this);
            patientList=client.getAppDatabase().patientDAO().getAll();
            adapter.notifyDataSetChanged();
        });
    }
    private void searchData(String key){
        Executor executor=Executors.newSingleThreadExecutor();
        executor.execute(()->{
            DatabaseClient client=DatabaseClient.getInstance(DisplayPatientsActivity.this);
            if(Character.isDigit(key.charAt(0))){
                //search by national code
                patientList=client.getAppDatabase().patientDAO().getbyNationalCode(key);
            }
            else{
                //search by fullname
                patientList=client.getAppDatabase().patientDAO().getbyName(key);
            }
            adapter.notifyDataSetChanged();
        });

    }
    //endregion
}
