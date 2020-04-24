package com.ernesto.charmapp.presentation.fragments.patientFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ernesto.charmapp.R;
import com.ernesto.charmapp.domain.retrofitEntities.Patient;
import com.ernesto.charmapp.presentation.dialogs.ErrorDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class HistoryFragment extends Fragment {

    private Patient patient;

    private Date date;

    private String dateString;

    private String dateLbl;

    public static HistoryFragment create(Patient patient) {
        Bundle args = new Bundle();
        args.putSerializable("patient", patient);
        HistoryFragment f = new HistoryFragment();
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        patient = (Patient) arguments.getSerializable("patient");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_history_v_2, container, false);
        this.dateString = "";
        CalendarView calenderEvent = v.findViewById(R.id.calendarView);
        calenderEvent.setMaxDate(Calendar.getInstance().getTimeInMillis());
        calenderEvent.setMinDate(calenderEvent.getMaxDate() - 30l * 24 * 60 * 60 * 1000);
        calenderEvent.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
                dateString = year + "-" + twoDigits(month + 1) + "-" + twoDigits(day);
                dateLbl = twoDigits(day) + "-" + twoDigits(month + 1) + "-" + year;
                if (isValidDate(dateString)) {
                    getActivity().getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                            .replace(R.id.fragmentContainer_patient, HistoryCrisisAndDiaryFragment.create(patient, dateString, dateLbl), "HISTORY_CRISIS_AND_DIARY_FRAGMENT")
                            .addToBackStack(null)
                            .commit();
                } else {
                    showErrorDialog();
                }
            }
        });

        return v;
    }


    public void showErrorDialog() {
        ErrorDialog errorDialog = new ErrorDialog("Error", "Solo puedes consultar las fechas entre hoy y hace 30 días");
        errorDialog.show(getActivity().getSupportFragmentManager(), "ERROR_DIALOG");
    }


    private boolean isValidDate(String selectedDate) {
        boolean ok = false;
        try {
            Date today = new Date();
            Date userDate = new SimpleDateFormat("yyyy-MM-dd").parse(selectedDate);

            long ago30days = 30l * 24 * 60 * 60 * 1000;
            ok = today.before(new Date((userDate.getTime() + ago30days))) && today.after(userDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ok;
    }

    private String twoDigits(int n) {
        return (n <= 9) ? ("0" + n) : String.valueOf(n);
    }

}