package com.example.todo.ui.Main_Page.Main;

public class Task {
    private int id;
    private String title;

    public Task(int _id, String _title){
        this.id = _id;
        this.title = _title;
    }

    public int getID(){return id;}
    public String getTitle() {
        return title;
    }
}
