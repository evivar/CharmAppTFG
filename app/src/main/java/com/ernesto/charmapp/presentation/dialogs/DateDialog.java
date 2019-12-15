package com.ernesto.charmapp.presentation.dialogs;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DateDialog extends DialogFragment {

    private android.app.DatePickerDialog.OnDateSetListener listener;

    public static DateDialog newInstance(android.app.DatePickerDialog.OnDateSetListener listener) {
        DateDialog fragment = new DateDialog();
        fragment.setListener(listener);
        return fragment;
    }

    public void setListener(android.app.DatePickerDialog.OnDateSetListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar cal = Calendar.getInstance();

        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int year = cal.get(Calendar.YEAR);

        android.app.DatePickerDialog datePickerDialog = new android.app.DatePickerDialog(getActivity(), listener, year, month, day);

        return datePickerDialog;
    }

}
