package ir.bolive.app.jamisapp.activiy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bolive.app.jamisapp.R;
import ir.bolive.app.jamisapp.adapter.PatientsAdapter;
import ir.bolive.app.jamisapp.database.FacesDatabase;
import ir.bolive.app.jamisapp.models.Patient;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class DisplayPatientsActivity extends AppCompatActivity {

    @BindView(R.id.toolbar_top)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;


    @BindView(R.id.display_img_nothing)
    ImageView imgNothing;
    @BindView(R.id.display_coordiantor)
    CoordinatorLayout coordinatorLayout;
    @BindView(R.id.display_recycler)
    RecyclerView recyclerView;

    PatientsAdapter adapter;
    List<Patient> patientList;

    static final String TAG=DisplayPatientsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_patients);
        ButterKnife.bind(this);
        init();
        loadAllData();
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
                    if(patientList!=null && patientList.size()>0){
                        if(query!=""){
                            searchData(query);
                        }
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
            searchView.setOnSearchClickListener(view -> {
                toolbarTitle.setVisibility(View.GONE);
            });
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    toolbarTitle.setVisibility(View.VISIBLE);
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
        getSupportActionBar().setTitle("");
        toolbarTitle.setText(getString(R.string.menuDisplay));
        recyclerView.setLayoutManager(new LinearLayoutManager(DisplayPatientsActivity.this,RecyclerView.VERTICAL,false));
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            GestureDetector gestureDetector=new GestureDetector(DisplayPatientsActivity.this,new GestureDetector.SimpleOnGestureListener(){
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);
                }

            });
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
                View child=rv.findChildViewUnder(e.getX(),e.getY());
                if(child!=null && gestureDetector.onTouchEvent(e)){
                    int position=rv.getChildAdapterPosition(child);
                    goToRegActivity(patientList.get(position).getPid());
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }
        });
    }
    private void showNothing(boolean shouldNothing){
        if(shouldNothing){
            imgNothing.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }else{
            imgNothing.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
    private void loadAllData(){
        Executor executor=Executors.newSingleThreadExecutor();
        executor.execute(()->{
            //DatabaseClient client=DatabaseClient.getInstance(DisplayPatientsActivity.this);
            //patientList=client.getAppDatabase().patientDAO().getAll();
            FacesDatabase db=FacesDatabase.getdatabase(DisplayPatientsActivity.this);
            patientList=db.getInstance().patientDAO().getAll();
            Log.i(TAG,"patient size:"+patientList.size());
            setList(patientList);
        });

    }
    private void searchData(String key){
        Executor executor=Executors.newSingleThreadExecutor();
        executor.execute(()->{
            //DatabaseClient client=DatabaseClient.getInstance(DisplayPatientsActivity.this);
            if(Character.isDigit(key.charAt(0))){
                //search by national code
                //patientList=client.getAppDatabase().patientDAO().getbyNationalCode(key);
            }
            else{
                //search by fullname
                //patientList=client.getAppDatabase().patientDAO().getbyName(key);
            }
            if(patientList!=null){
                adapter.notifyDataSetChanged();
                showNothing(false);
            }
            else{
                showNothing(true);
            }

        });
    }
    private void goToRegActivity(long pid){
        Intent intent=new Intent(DisplayPatientsActivity.this,RegisterActvity.class);
        intent.putExtra("pid",pid);
        startActivity(intent);
    }
    private void setList(List<Patient> patients){
        patientList=patients;
        loadToAdapter();
    }
    private void loadToAdapter(){
        Log.i(TAG,"(load adaptor)patient size:"+patientList.size());
        if(patientList!=null && patientList.size()>0){
            adapter=new PatientsAdapter(patientList,DisplayPatientsActivity.this);
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            showNothing(false);
        }
        else{
            showNothing(true);
        }
    }
    //endregion
}
