package com.ernesto.charmapp.presentation.fragments.patientFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ernesto.charmapp.R;
import com.ernesto.charmapp.data.RetrofitClient;
import com.ernesto.charmapp.domain.Diary;
import com.ernesto.charmapp.domain.Headache;
import com.ernesto.charmapp.domain.Patient;
import com.ernesto.charmapp.interactors.responses.ReadDiaryResponse;
import com.ernesto.charmapp.presentation.dialogs.ErrorDialog;

import net.cachapa.expandablelayout.ExpandableLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/*
 * TODO:
 *   Leer el diario de la fecha
 *   Leer la crisis de la fecha
 *   Si existen meter los campos y activar los botones
 *   Si alguno no existe desactivar los botones
 * */
public class HistoryCrisisAndDiaryFragment extends Fragment {

    private Patient patient;

    private String date;

    private String dateString;

    private Diary diary;

    private Button expandDiaryBtn;

    private ExpandableLayout diaryExpandableLayout;

    private Headache headache;

    private TextView dateLbl;


    public static HistoryCrisisAndDiaryFragment create(Patient patient, String dateString, String date) {
        Bundle args = new Bundle();
        args.putSerializable("patient", patient);
        args.putSerializable("date", dateString);
        args.putSerializable("dateLbl", date);
        HistoryCrisisAndDiaryFragment f = new HistoryCrisisAndDiaryFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        patient = (Patient) arguments.getSerializable("patient");
        dateString = (String) arguments.getSerializable("date");
        date = (String) arguments.getSerializable("dateLbl");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history_crisis_and_diary, container, false);

        this.dateLbl = v.findViewById(R.id.dateLbl_History);
        this.dateLbl.setText(date);

        // Leemos el diario y la crisis del dia seleccionado

        final Call<ReadDiaryResponse> readDiaryByDate = RetrofitClient
                .getInstance()
                .getAPI()
                .readDiaryByDate(patient.getPatientId(), dateString);
        readDiaryByDate.enqueue(new Callback<ReadDiaryResponse>() {
            @Override
            public void onResponse(Call<ReadDiaryResponse> call, Response<ReadDiaryResponse> response) {
                ReadDiaryResponse readDiaryResponse = response.body();
                if (!readDiaryResponse.getError()) {
                    if (readDiaryResponse.getDiario().getDate() != null) {
                        diary = readDiaryResponse.getDiario();
                    } else {
                        diary = null;
                    }
                } else {
                    Toast.makeText(getActivity(), "Error con la base de datos", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ReadDiaryResponse> call, Throwable t) {

            }
        });

        expandDiaryBtn = v.findViewById(R.id.expandDiaryBtn_history);
        diaryExpandableLayout = v.findViewById(R.id.ExpandableLayoutDiary);
        expandDiaryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (diaryExpandableLayout.isExpanded()) {
                    expandDiaryBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, getContext().getDrawable(R.drawable.ic_expand), null);
                    diaryExpandableLayout.collapse();
                } else {
                    expandDiaryBtn.setCompoundDrawablesWithIntrinsicBounds(null, null, getContext().getDrawable(R.drawable.ic_collapse), null);
                    diaryExpandableLayout.expand();
                }
            }
        });


        return v;
    }

    public void showErrorDialog() {
        ErrorDialog errorDialog = new ErrorDialog("Error", "Solo puedes consultar los últimos 30 días");
        errorDialog.show(getActivity().getSupportFragmentManager(), "ERROR_DIALOG");
    }
}