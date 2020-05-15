package com.example.todo.ui.Main_Page.Notes;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todo.R;
import com.google.android.material.textfield.TextInputEditText;

public class NewNoteActivity extends AppCompatActivity {

    Button goBack, submit;
    TextInputEditText title, description;
    SQLiteDatabase myDatabase;
    String username;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_note);

        username = getIntent().getStringExtra("USERNAME");

        final String notesTable = "notes"+username;

        //hooks
        goBack = findViewById(R.id.buttonNewNoteBack);
        submit = findViewById(R.id.newNoteSubmit);

        title = findViewById(R.id.newNoteTitle);
        description = findViewById(R.id.newNoteDescription);

        // database
        myDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);


        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!title.getText().toString().equals("")){
                    ContentValues values = new ContentValues();
                    values.put("title", title.getText().toString());
                    if(!description.getText().toString().equals(""))
                        values.put("description", description.getText().toString());
                    if( myDatabase.insert(notesTable, null,  values) > -1){

                        myDatabase.close();
                        finish();
                    }
                }
            }
        });
    }



    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
