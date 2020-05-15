package com.example.todo.ui.Main_Page.Notes;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todo.R;
import com.example.todo.ui.Main_Page.Main_page;

public class ShowNoteActivity extends AppCompatActivity {

    int id;
    String title;
    String username;
    SQLiteDatabase myDatabase;
    Button delete,goBack,Edit;

    TextView titleView, descriptionView;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_note);

        id = getIntent().getIntExtra("NOTE_ID", 0);
        title = getIntent().getStringExtra("NOTE_TITLE");
        username = getIntent().getStringExtra("USERNAME");

        // database
        myDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);

        //hooks

        titleView = findViewById(R.id.noteShowTitle);
        descriptionView = findViewById(R.id.noteShowDescription);
        delete = findViewById(R.id.noteDelete);
        goBack = findViewById(R.id.goBackButton);
        Edit = findViewById(R.id.editNote);

        //setters
        titleView.setText(title);
        descriptionView.setText(getDescription());

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogDelete(myDatabase);
            }
        });


    }

    private String getDescription(){
        String notesTable = "notes"+username;
        Cursor cursor = myDatabase.rawQuery("SELECT description FROM "+notesTable+" WHERE note_ID=?", new String[] {String.valueOf(id)});
        if(cursor!=null){
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                return cursor.getString(cursor.getColumnIndex("description"));
            }
        }
        return "";
    }

    private void showDialogDelete(final SQLiteDatabase myDb){
        AlertDialog.Builder builder = new AlertDialog.Builder(ShowNoteActivity.this);
        builder.setCancelable(true);
        builder.setTitle("Delete note");
        builder.setMessage("Are You sure You want to delete Your note ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String notesTable = "notes"+username;
                myDb.delete(notesTable, "note_ID=?", new String[] {String.valueOf(id)});
                dialog.cancel();
                finish();
            }
        });
        builder.setNegativeButton( "No!", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }


    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); }
    @Override
    public void onBackPressed(){ finish(); }
}
