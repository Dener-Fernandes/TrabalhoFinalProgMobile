package com.example.terceirotrabalho.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import com.example.terceirotrabalho.activities.CreateHomeworkActivity;
import com.example.terceirotrabalho.activities.EditHomeworkActivity;

import java.util.Calendar;

public class DateFragment extends DialogFragment
        implements DatePickerDialog.OnDateSetListener {
    String classOrigin;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // Create a new instance of DatePickerDialog and return it
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day) {
        Bundle args = getArguments();

        if (args != null) {
            classOrigin  = args.getString("classOrigin", "");
        }

        if (classOrigin.equals("CreateHomeworkActivity")) {
            ((CreateHomeworkActivity)getActivity()).setDate(year, month, day);
        } else {
            ((EditHomeworkActivity)getActivity()).setDate(year, month, day);
        }
    }
}