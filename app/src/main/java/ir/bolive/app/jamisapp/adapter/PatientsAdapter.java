package ir.bolive.app.jamisapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ir.bolive.app.jamisapp.R;
import ir.bolive.app.jamisapp.models.Patient;

public class PatientsAdapter extends RecyclerView.Adapter<PatientsAdapter.ViewHolder> {
    ArrayList<Patient> patientList=new ArrayList<Patient>();
    Context context;
    public PatientsAdapter(ArrayList<Patient> patients,Context context) {
        this.patientList=patients;
        this.context=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.card_patient,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.card_cardview)
        CardView cardView;
        @BindView(R.id.card_pname)
        TextView txtPname;
        @BindView(R.id.card_nationalcode)
        TextView txtNationalCode;
        @BindView(R.id.card_refDate)
        TextView txtRefDate;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
        public void clearAnimation(){
            cardView.clearAnimation();
        }
    }
}
