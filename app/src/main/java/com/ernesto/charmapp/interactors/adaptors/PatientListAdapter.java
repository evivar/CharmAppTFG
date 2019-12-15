package com.ernesto.charmapp.interactors.adaptors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ernesto.charmapp.R;
import com.ernesto.charmapp.domain.Patient;

import java.util.List;

public class PatientListAdapter extends RecyclerView.Adapter<PatientListAdapter.PatientViewHolder> {

    private Context context;

    private List<Patient> patientList;

    public PatientListAdapter(Context context, List<Patient> patientList) {
        this.context = context;
        this.patientList = patientList;
    }

    @NonNull
    @Override
    public PatientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.card_view_patient, parent, false);
        return new PatientViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PatientViewHolder holder, int position) {
        Patient patient = patientList.get(position);

        holder.nameLbl.setText(patient.getName() + " " + patient.getSurname1() + " " + patient.getSurname2());
        holder.emailLbl.setText(patient.getEmail());
        holder.phoneLbl.setText(patient.getPhone().toString());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Click en la cardView", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return patientList.size();
    }

    class PatientViewHolder extends RecyclerView.ViewHolder {

        private CardView cardView;

        private TextView nameLbl;

        private TextView emailLbl;

        private TextView phoneLbl;

        public PatientViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.patientCard_cardView);

            nameLbl = itemView.findViewById(R.id.nameLbl_cardView);
            emailLbl = itemView.findViewById(R.id.emailLbl_cardView);
            phoneLbl = itemView.findViewById(R.id.phoneLbl_cardView);

        }
    }

}
