package com.example.todo.ui.Main_Page.Main;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.lang.reflect.Field;
import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getContext(),(DatePickerDialog.OnDateSetListener) getActivity(), year, month, day);
        Field mDatePickerField;
        try {
            mDatePickerField = dialog.getClass().getDeclaredField("mDatePicker");
            mDatePickerField.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        return dialog;

//        DatePickerDialog dialog = new DatePickerDialog()
//;
//        return new DatePickerDialog(getActivity(), , year, month, day);
    }
}
