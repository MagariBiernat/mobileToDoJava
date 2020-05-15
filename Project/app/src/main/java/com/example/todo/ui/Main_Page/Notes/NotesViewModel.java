package com.example.todo.ui.Main_Page.Notes;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class NotesViewModel extends ViewModel {

    private String _username;
    private SQLiteDatabase myDatabase;

    private ArrayList<Note> myNotes = new ArrayList<Note>();
    private MutableLiveData<ArrayList<Note>> myNotesLive;


    public NotesViewModel() {
        myNotesLive = new MutableLiveData<>();
    }

    void setNotes(){
        myNotes.clear();

        String notesTable = "notes"+_username;

        try(Cursor cursor = myDatabase.rawQuery("SELECT * FROM "+notesTable, new String[]{})){
            if(cursor!=null){
                if(cursor.getCount()>0){
                    cursor.moveToFirst();
                    for(int i = 1; i<= cursor.getCount() ; i++){
                        myNotes.add(new Note(cursor.getInt(cursor.getColumnIndex("note_ID")), cursor.getString(cursor.getColumnIndex("title"))));
                        cursor.moveToNext();
                    }
                    myNotesLive.setValue(myNotes);
                }
                else if(cursor.getCount() == 0)
                    myNotesLive.setValue(myNotes);
            }
        }
    }

    LiveData<ArrayList<Note>> getNotes() {return myNotesLive;}



    void setMyDatabase(SQLiteDatabase db){this.myDatabase = db;}
    void setUsername(String username){
        this._username = username;
    }

}