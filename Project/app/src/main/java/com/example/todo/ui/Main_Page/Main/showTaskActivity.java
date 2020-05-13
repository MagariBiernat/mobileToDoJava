package com.example.todo.ui.Main_Page.Main;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todo.R;
import com.example.todo.ui.Main_Page.Main_page;

public class showTaskActivity extends AppCompatActivity {

    private int id;
    private String title;
    private String username;

    Button goBack, taskCompleted, taskDelete;
    ImageView settings;
    TextView titleView, dateTime, description, alarmed;
    String tasksTable;
    SQLiteDatabase myDatabase;


    @SuppressLint("CutPasteId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_task);

        //receive id from intent
        id = getIntent().getIntExtra("TASK_ID", 0);
        title = getIntent().getStringExtra("TASK_TITLE");
        username = getIntent().getStringExtra("USERNAME");

        // database
        myDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);

        //hooks
        goBack = findViewById(R.id.goBackButton);
        taskCompleted = findViewById(R.id.taskCompleted);
        taskDelete = findViewById(R.id.taskDelete);
        settings = findViewById(R.id.settingsTasks);
        titleView = findViewById(R.id.taskTitle);
        dateTime = findViewById(R.id.taskDateTime);
        description = findViewById(R.id.taskDescription);
        //alarmed = findViewById(R.id.taskReminded);

        //read remaining data
        tasksTable = "tasks"+username;
        Cursor cursor = myDatabase.rawQuery("SELECT * FROM "+tasksTable+" WHERE task_ID = ?", new String[] {String.valueOf(id)});

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            if(cursor.getString(cursor.getColumnIndex("date")) != null){
                String date = cursor.getString(cursor.getColumnIndex("date")) + cursor.getString(cursor.getColumnIndex("time"));
                dateTime.setText(date);
            }
            if(cursor.getString(cursor.getColumnIndex("description")) != null)
            {
                description.setText(cursor.getString(cursor.getColumnIndex("description")));
            }
        }

        cursor.close();

        taskCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogTaskComplete("Complete task", "Did You finish Your task successfully?", "COMPLETE");
            }
        });

        taskDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogTaskComplete("Delete task", "Are You sure You want to delete Your task of the list ??", "DELETE");
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        //textView setters
        titleView.setText(title);

    }

    private int getTasksCount(final String type) {
        Cursor cursor = myDatabase.rawQuery("SELECT tasks_successful, tasks_failed FROM users WHERE username=?", new String[]{username});

        if(cursor!=null){
            cursor.moveToFirst();
            if(type.equals("success")){
                return cursor.getInt(cursor.getColumnIndex("tasks_successful"));
            }else{
                return cursor.getInt(cursor.getColumnIndex("tasks_failed"));
            }
        }
        return -1;
    }

    private void AlertDialogTaskComplete(String title, String msg, final String type){
        AlertDialog.Builder builder = new AlertDialog.Builder(showTaskActivity.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(type.equals("COMPLETE")){

                    //COMPLETE -> not completed task
                    int count = getTasksCount("failed");

                    if(count == -1){
                        try {
                            throw new Exception("something went wrong");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        dialog.cancel();
                        finish();
                    }else{
                        ++count;
                        ContentValues values = new ContentValues();
                        values.put("tasks_failed", count);
                        myDatabase.update("users", values , "username=?", new String[] {username});
                        deleteTask();
                    }
                    dialog.cancel();
                    finish();
                }else {
                    dialog.cancel();
                }
            }
        });

        if(type.equals("COMPLETE")){
            builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
        }

        builder.setPositiveButton( "Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(type.equals("DELETE")) {
                    //DELETE
                    deleteTask();

                } else {

                    // COMPLETE -> completed successfully task
                    int count = getTasksCount("success");

                    if(count == -1){
                        try {
                            throw new Exception("something went wrong");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }else{
                        ++count;
                        ContentValues values = new ContentValues();
                        values.put("tasks_successful", count);
                        myDatabase.update("users", values , "username=?", new String[] {username});
                        deleteTask();
                    }
                }
                dialog.cancel();
                finish();
            }
        });
        builder.show();
    }

    //delete task of a tasksUSERNAME table and update users table with new value of ongoing tasks
    private void deleteTask(){
        Integer count = getTasksOnGoing();
        if(count > 0)
        {
            --count;
            ContentValues val = new ContentValues();
            val.put("tasks_ongoing", count);
            myDatabase.update("users",val,"username=?",new String[] {username});
        }
        myDatabase.delete(tasksTable, "task_ID=?", new String[] {String.valueOf(id)});
    }

    //get count of ongoing tasks...
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
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); }
    @Override
    public void onBackPressed(){ finish(); }
}
