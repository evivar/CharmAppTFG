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
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.ernesto.charmapp.R;
import com.ernesto.charmapp.data.RetrofitClient;
import com.ernesto.charmapp.domain.Diary;
import com.ernesto.charmapp.domain.Patient;
import com.ernesto.charmapp.interactors.responses.CreateDiaryResponse;
import com.ernesto.charmapp.interactors.validators.DiaryValidator;
import com.ernesto.charmapp.presentation.dialogs.ErrorDialog;
import com.skyhope.eventcalenderlibrary.CalenderEvent;
import com.skyhope.eventcalenderlibrary.listener.CalenderDayClickListener;
import com.skyhope.eventcalenderlibrary.model.DayContainerModel;
import com.skyhope.eventcalenderlibrary.model.Event;

import java.sql.Date;
import java.util.Calendar;
import java.util.GregorianCalendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HistoryFragment extends Fragment {

    private Patient patient;

    private Date date;

    private String dateString;

    private String maxDateBack;

    public static HistoryFragment create(Patient patient, Diary diary){
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
        View v = inflater.inflate(R.layout.fragment_history, container, false);

        CalenderEvent calenderEvent = v.findViewById(R.id.calender_event);

        Calendar calendar = new GregorianCalendar();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Event event = new Event(calendar.getTimeInMillis(), "Test");
        calenderEvent.addEvent(event);

        calenderEvent.initCalderItemClickCallback(new CalenderDayClickListener() {
            @Override
            public void onGetDay(DayContainerModel dayContainerModel) {
                dateString = dayContainerModel.getDate();
                /* TODO: Restringir el acceso de eventos hasta un máximo de 30 días atras */
                // No hace falta comprobar que el calendario tenga eventos, ya que siempre tiene que tener diarios y puede tener crisis
                // Cuando hace click en un dia -> Lo lleva al fragment donde estan los expandables layouts
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                        .addToBackStack(null)
                        .replace(R.id.fragmentContainer_patient, HistoryCrisisAndDiaryFragment.create(patient, dateString), "HISTORY_CRISIS_AND_DIARY_FRAGMENT")
                        .commit();
            }
        });

        return v;
    }

    public void showErrorDialog() {
        ErrorDialog errorDialog = new ErrorDialog("Error", "Solo puedes consultar los últimos 30 días");
        errorDialog.show(getActivity().getSupportFragmentManager(), "ERROR_DIALOG");
    }

}
