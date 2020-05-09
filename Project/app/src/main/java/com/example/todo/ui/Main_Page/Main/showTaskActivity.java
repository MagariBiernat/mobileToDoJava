package com.example.todo.ui.Main_Page.Main;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todo.R;

public class showTaskActivity extends AppCompatActivity {

    private int id;
    private String title;

    Button goBack, settings;
    TextView titleView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_task);

        //receive id from intent
        id = getIntent().getIntExtra("TASK_ID", 0);
        title = getIntent().getStringExtra("TASK_TITLE");

        //hooks
        goBack = findViewById(R.id.goBackButton);
        settings = findViewById(R.id.settingsTasks);

        titleView = findViewById(R.id.taskTitle);

        titleView.setText(title);


    }
}
