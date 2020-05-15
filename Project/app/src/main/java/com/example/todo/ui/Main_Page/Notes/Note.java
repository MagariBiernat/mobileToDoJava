package com.example.todo.ui.Main_Page.Notes;

public class Note {
    private int id;
    private String title;

    public Note(int _id, String _title){
        this.id = _id;
        this.title = _title;

    }

    public int getId(){return id;}
    public String getTitle(){return title;}
}
