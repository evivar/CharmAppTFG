package com.ernesto.charmapp.presentation.fragments.patientFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ernesto.charmapp.R;
import com.ernesto.charmapp.domain.Patient;
import com.ernesto.charmapp.presentation.dialogs.ErrorDialog;
import com.skyhope.eventcalenderlibrary.CalenderEvent;
import com.skyhope.eventcalenderlibrary.listener.CalenderDayClickListener;
import com.skyhope.eventcalenderlibrary.model.DayContainerModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class HistoryFragment extends Fragment {

    private Patient patient;

    private Date date;

    private String dateString;

    private String dateLbl;

    private String maxDateBack;

    // Pasar la fecha tambien
    public static HistoryFragment create(Patient patient){
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
        this.dateString = "";
        CalenderEvent calenderEvent = v.findViewById(R.id.calender_event);

        calenderEvent.initCalderItemClickCallback(new CalenderDayClickListener() {

            @Override
            public void onGetDay(DayContainerModel dayContainerModel) {
                dateString = dayContainerModel.getYear() + "-" + (dayContainerModel.getMonthNumber() + 1) + "-" + dayContainerModel.getDay();
                dateLbl = dayContainerModel.getDay() + "-" + (dayContainerModel.getMonthNumber() + 1) + "-" + dayContainerModel.getYear();
                //isValidDate(dateString);
                /* TODO: Restringir el acceso de eventos hasta un máximo de 30 días atras */
                // No hace falta comprobar que el calendario tenga eventos, ya que siempre tiene que tener diarios y puede tener crisis
                // Cuando hace click en un dia -> Lo lleva al fragment donde estan los expandables layouts
                getActivity().getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_right, R.anim.enter_from_right, R.anim.exit_to_right)
                        .replace(R.id.fragmentContainer_patient, HistoryCrisisAndDiaryFragment.create(patient, dateString, dateLbl), "HISTORY_CRISIS_AND_DIARY_FRAGMENT")
                        .addToBackStack(null)
                        .commit();
            }
        });

        return v;
    }



    public void showErrorDialog() {
        ErrorDialog errorDialog = new ErrorDialog("Error", "Solo puedes consultar los últimos 30 días");
        errorDialog.show(getActivity().getSupportFragmentManager(), "ERROR_DIALOG");
    }

    /* TODO: Tengo que coger la fecha del dia, la fecha que selecciona el usuario y si al restarlas la diferencia es mayor de 30 salta el error, sino sigue al siguiente fragmento */
    private void isValidDate(String selectedDate){
        boolean ok = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date selected = sdf.parse(selectedDate);
            //long diffInMillies = Math.abs(selected.getTime() - todayString.getTime());
            //long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
            //System.out.println(diff);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        //return ok;
    }

}