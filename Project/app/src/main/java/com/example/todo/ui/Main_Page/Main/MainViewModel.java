package com.example.todo.ui.Main_Page.Main;

import android.annotation.SuppressLint;
import android.app.Application;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static java.lang.Integer.parseInt;

public class MainViewModel extends ViewModel {

    private String _username;
    private SQLiteDatabase myDatabase;

    private ArrayList<Task> myTasks = new ArrayList<Task>();
    private MutableLiveData<ArrayList<Task>> myTasksLive;

    public MainViewModel() {
        myTasksLive = new MutableLiveData<>();
    }

    void setMyDatabase(SQLiteDatabase db){
        this.myDatabase = db;
    }

    void setUsername(String username){
        this._username = username;
    }

    void setTasks() throws ParseException {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("d/MM/yyyy");
        String nowDate = format.format(new Date());

        Date now = format.parse(nowDate);

        myTasks.clear();
        String tasksTable = "tasks"+_username;

        try(Cursor cursor = myDatabase.rawQuery("SELECT * FROM "+tasksTable, new String [] {})){
            if(cursor!=null)
            {
                if(cursor.getCount() > 0){
                    cursor.moveToFirst();
                    for(int i = 1; i <= cursor.getCount() ; i++)
                    {
                        boolean past = false;

                        // I dont know what those lines of code are doing.
                        if(cursor.getString(cursor.getColumnIndex("date")) != null){
                            Date toCompare = format.parse(cursor.getString(cursor.getColumnIndex("date")));

                            if(now.compareTo(toCompare) > 0){
                                past = true;

                            } else if (now.compareTo(toCompare) == 0){
                                // 0 -> hour, 1 -> minutes of time set in db;
                                String[] timeToCompare = cursor.getString(cursor.getColumnIndex("time")).split("/");
                                Calendar calendar = Calendar.getInstance();
                                @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
                                // 0 -> hour, 1 -> minutes of time right now;
                                String[] time_now = formatter.format(calendar.getTime()).split(":");

                                if(parseInt(time_now[0]) > parseInt(timeToCompare[0])){
                                    past = true;
                                } else if (parseInt(time_now[0]) == parseInt(timeToCompare[0])){
                                    if(parseInt(time_now[1]) >= parseInt(timeToCompare[1]))
                                        past = true;
                                }
                            }
                        }

                        myTasks.add(new Task(cursor.getInt(cursor.getColumnIndex("task_ID")), cursor.getString(cursor.getColumnIndex("title")), past) );
                        cursor.moveToNext();
                    }
                    myTasksLive.setValue(myTasks);
                }
                else if(cursor.getCount() == 0){
                    myTasksLive.setValue(myTasks);
                }
            }
        }

    }

    LiveData<ArrayList<Task>> getTasks() { return myTasksLive;}
}