package com.example.todo.ui.Main_Page.Main;

public class Task {
    private int id;
    private String title;
    private String date;
    private String time;
    private boolean alarm = false;
    private int alarm_time;
    private String description;

    public Task(int _id, String _title){
        this.id = _id;
        this.title = _title;
    }

    public String getTitle() {
        return title;
    }
}
