package com.ernesto.charmapp.presentation.fragments.patientFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ernesto.charmapp.R;
import com.ernesto.charmapp.domain.Diary;
import com.ernesto.charmapp.domain.Headache;
import com.ernesto.charmapp.domain.Patient;
import com.ernesto.charmapp.presentation.dialogs.ErrorDialog;
import com.skyhope.eventcalenderlibrary.CalenderEvent;
import com.skyhope.eventcalenderlibrary.listener.CalenderDayClickListener;
import com.skyhope.eventcalenderlibrary.model.DayContainerModel;
import com.skyhope.eventcalenderlibrary.model.Event;

import java.io.Serializable;
import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;


public class HistoryCrisisAndDiaryFragment extends Fragment {

    private Patient patient;

    private Date date;

    private String dateString;

    private Diary diary;

    private Headache headache;

    public static HistoryFragment create(Patient patient, String dateString){
        Bundle args = new Bundle();
        args.putSerializable("patient", patient);
        args.putSerializable("date", dateString);
        HistoryFragment f = new HistoryFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        patient = (Patient) arguments.getSerializable("patient");
        dateString = (String) arguments.getSerializable("date");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history_crisis_and_diary, container, false);



        return v;
    }

    public void showErrorDialog() {
        ErrorDialog errorDialog = new ErrorDialog("Error", "Solo puedes consultar los últimos 30 días");
        errorDialog.show(getActivity().getSupportFragmentManager(), "ERROR_DIALOG");
    }
}
