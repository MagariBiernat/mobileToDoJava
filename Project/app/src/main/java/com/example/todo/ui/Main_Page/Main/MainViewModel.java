package com.example.todo.ui.Main_Page.Main;

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
import java.util.ArrayList;

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

    void setTasks(){

        myTasks.clear();
        String tasksTable = "tasks"+_username;

        try(Cursor cursor = myDatabase.rawQuery("SELECT * FROM "+tasksTable, new String [] {})){
            if(cursor!=null)
            {
                if(cursor.getCount() > 0){
                    cursor.moveToFirst();
                    for(int i = 1; i <= cursor.getCount() ; i++)
                    {
                        myTasks.add(new Task(cursor.getInt(cursor.getColumnIndex("task_ID")), cursor.getString(cursor.getColumnIndex("title"))) );
                        //TODO: check if date is past if is then send a message to make background..red?
                        cursor.moveToNext();
                    }
                    myTasksLive.setValue(myTasks);
                }
                if(cursor.getCount() == 0){
                    myTasksLive.setValue(myTasks);
                }
            }
        }

    }

    LiveData<ArrayList<Task>> getTasks() { return myTasksLive;}
}