package com.example.terceirotrabalho.fragments;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.TimePicker;
import androidx.fragment.app.DialogFragment;

import com.example.terceirotrabalho.activities.CreateHomeworkActivity;
import com.example.terceirotrabalho.activities.EditHomeworkActivity;


import java.util.Calendar;

public class TimeFragment extends DialogFragment
        implements TimePickerDialog.OnTimeSetListener {
    String classOrigin;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the current time as the default values for the picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        // Create a new instance of TimePickerDialog and return it
        return new TimePickerDialog(getActivity(), this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Bundle args = getArguments();

        if (args != null) {
            classOrigin  = args.getString("classOrigin", "");
        }

        if (classOrigin.equals("CreateHomeworkActivity")) {
            ((CreateHomeworkActivity)getActivity()).setTime(hourOfDay, minute);
        } else {
            ((EditHomeworkActivity)getActivity()).setTime(hourOfDay, minute);
        }
    }
}