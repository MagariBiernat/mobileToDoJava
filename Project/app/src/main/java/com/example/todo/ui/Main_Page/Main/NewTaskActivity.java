package com.example.todo.ui.Main_Page.Main;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.todo.R;
import com.example.todo.ui.Main_Page.Main_page;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NewTaskActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    TimePicker picker;
    TextView date;
    SQLiteDatabase myDatabase;
    TextInputEditText title, description;
    String dateSet = "", username;
    Button clearDate;
    CheckBox checkRemind;
    Button goBack, buttonCalendar, newTask;
    Boolean reminder = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_task);

        username = getIntent().getStringExtra("USERNAME");

        //Hooks
        picker = findViewById(R.id.timePicker1);
        title = findViewById(R.id.newTaskTitle);
        description = findViewById(R.id.newTaskDescription);
        goBack = findViewById(R.id.buttonNewTaskBack);
        buttonCalendar = findViewById(R.id.newTaskCalendar);
        newTask = findViewById(R.id.newTaskSubmit);
        checkRemind = findViewById(R.id.checkBoxRemind);
        clearDate = findViewById(R.id.clear_date);
        date = findViewById(R.id.textDate);

        // database
        myDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);

        //set picker to 24 hour format
        picker.setIs24HourView(true);

        //checkBox
        checkRemind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO : create a notification if isChecked that will remind about task's title an hour before...
                reminder = checkRemind.isChecked();
            }
        });

        // on 'set a date' button click, pops out a calendar
        buttonCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "date picker");
            }
        });

        //simply go back button
        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                finish();
            }
        });

        //clear date button
        clearDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                date.setText("");
                dateSet = "";
                //time picker and this button goes invisible
                picker.setVisibility(View.GONE);
                clearDate.setVisibility(View.GONE);
            }
        });

        //new task submit!
        newTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validationSuccess())
                {
                    //TODO: only title to validate????
                    // on success close activity, on resume in main_page refresh listview

                    // prepare values to insert
                    ContentValues values = new ContentValues();
                    values.put("title", title.getText().toString());
                    if (!dateSet.equals("")) {
                        values.put("date", dateSet);
                        String time = picker.getCurrentHour().toString() + "/" + picker.getCurrentMinute().toString();
                        values.put("time", time);
                    }
                    if(!description.getText().toString().equals(""))
                    {
                        values.put("description", description.getText().toString());
                    }
                    if(myDatabase.insert("tasks"+username, null, values) > -1)
                    {
                        //Log.i("Database", "it works :O");
                        Integer count = getTasksOnGoing();
                        if(count > -1)
                        {
                            ++count;
                            ContentValues val = new ContentValues();
                            val.put("tasks_ongoing", count);
                            myDatabase.update("users",val,"username=?",new String[] {username});
                        }
                        myDatabase.close();
                        finish();
                    }
                    else
                        Log.i("Database", "nope");
                    myDatabase.close();
                    //TODO BONUS: allow an image for insert
                }
            }
        });
    }

    int getTasksOnGoing(){
        Cursor cursor = myDatabase.rawQuery("SELECT tasks_ongoing from users where username=?", new String[] {username});
        if(cursor.getCount() > 0)
        {
            cursor.moveToFirst();
            return cursor.getInt(cursor.getColumnIndex("tasks_ongoing"));
        } else
        return -1;
    }


    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        @SuppressLint("SimpleDateFormat") DateFormat today = new SimpleDateFormat("d/MM/yyyy");
        String[] now = (today.format(new Date())).split("");

        @SuppressLint("SimpleDateFormat") DateFormat test = new SimpleDateFormat("d/MM/yyyy");
        test.setCalendar(calendar);

        // array
        // 0 -> day, 1 -> month, 2 -> year
        dateSet = (test.format(calendar.getTime()));


        String currentDateString = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        date.setText(currentDateString);

        // after setting a date make timepicker visible
        picker.setVisibility(View.VISIBLE);
        clearDate.setVisibility(View.VISIBLE);
    }

    //validation function
    private boolean validationSuccess(){
        if(title.getText().toString().equals(""))
            return false;
        return true;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

}
