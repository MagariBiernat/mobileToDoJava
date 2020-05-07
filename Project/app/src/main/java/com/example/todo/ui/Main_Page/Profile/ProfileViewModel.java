package com.example.todo.ui.Main_Page.Profile;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.text.DateFormatSymbols;


public class ProfileViewModel extends ViewModel {

    private MutableLiveData<String> DateCreated; // dd/MM/yyyy
    private SQLiteDatabase myDatabase;
    private String username;


    public ProfileViewModel() {
        DateCreated = new MutableLiveData<>();


    }


    void setUsername(String username) {
        this.username = username;
    }

    void setDatabase(SQLiteDatabase db){
        this.myDatabase = db;

        // query database
        Cursor cursor = myDatabase.rawQuery("SELECT * FROM users WHERE username=?", new String[] {username});

        if(cursor.getCount() > 0) {
            if(cursor.moveToFirst())
            {
                String[] data = (cursor.getString(cursor.getColumnIndex("date_created"))).split("/");
                DateCreated.setValue(data[0] + " " + getMonth(Integer.parseInt(data[1])) + " " + data[2]);

            }
        }
    }

    LiveData<String> getDate() {return DateCreated;}

    private static String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }
}