package com.example.todo.ui.Main_Page.Main;

public class Task {
    private int id;
    private String title;
    private boolean past;

    public Task(int _id, String _title, boolean _past){
        this.id = _id;
        this.title = _title;
        this.past = _past;
    }

    public int getID(){return id;}
    public String getTitle() {
        return title;
    }
    public boolean isPast() {return past;}
}
