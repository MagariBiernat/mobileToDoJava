package com.example.todo.ui.Main_Page.Profile;

import android.app.Activity;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.todo.R;
import com.example.todo.ui.Main_Page.Main_page;
import com.example.todo.ui.login.LoginActivity;
import com.example.todo.ui.register.RegisterActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity {

    SQLiteDatabase myDatabase;
    ArrayList<String> listsOfUsernames = new ArrayList<String>();
    String username;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        final SharedPreferences sharedPrefs = this.getSharedPreferences("LOGIN_CREDENTIALS",Context.MODE_PRIVATE);
        //final SharedPreferences.Editor editor = sharedPrefs.edit();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        username = getIntent().getStringExtra("USERNAME");
        Button changeUsername = findViewById(R.id.settingsChangeNickname);
        Button deleteAcc = findViewById(R.id.settingsDeleteAccount);
        Button logOut = findViewById(R.id.settingsLogOut);
        myDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);

        changeUsername.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialogWChangeUsername();
            }
        });

        deleteAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog("Deleting account", "Are You sure You want to delete Your account ?", "Delete");
            }
        });


        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Log out");
                Log.i("siemano kolano settings", sharedPrefs.getString("username", "default"));
                builder.setMessage("Are You sure You want to log out ?!");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        sharedPrefs.edit().remove("username").commit();
                        sharedPrefs.edit().remove("loggedin").commit();
                        dialog.cancel();
                        Intent myIntent = new Intent(SettingsActivity.this, LoginActivity.class);
                        startActivity(myIntent);
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                        finishAffinity();

                    }
                });
                builder.setNegativeButton( "No!", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Log.i("siemano kolano settings", "asdfasd");
                        dialog.cancel();
                    }
                });
                builder.show();
            }
        });
    }

    private void fillListWithUsernamesFromDB(){
        listsOfUsernames.clear();
        Cursor cursor = myDatabase.rawQuery("SELECT username FROM users", new String[] {});
        if(cursor!= null) {
            if(cursor.getCount() > 0){
                cursor.moveToFirst();
                for(int i =1 ; i<= cursor.getCount() ; i++){
                    listsOfUsernames.add(cursor.getString(cursor.getColumnIndex("username")));
                    cursor.moveToNext();
                }
            }

        }
    }

    private void ChangeTablesName(String oldUsername, String newUsername){

        String oldNotes = "notes"+oldUsername;
        String oldTasks = "tasks"+oldUsername;
        String newNotes = "notes"+newUsername;
        String newTasks = "tasks"+newUsername;
        //
        myDatabase.execSQL("ALTER TABLE "+oldNotes+" RENAME TO "+newNotes);
        myDatabase.execSQL("ALTER TABLE "+oldTasks+" RENAME TO "+newTasks);

        //TODO: dont change sharef prefs just change username globally//set sp everywhere
        //change sharedprefs
        SharedPreferences sharedPrefs = this.getSharedPreferences("LOGIN_CREDENTIALS",Context.MODE_PRIVATE);
        sharedPrefs.edit().remove("username").commit();
        sharedPrefs.edit().remove("loggedin").commit();

    }


    private void AlertDialogWChangeUsername(){
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        final EditText edittext = new EditText(getApplicationContext());
        alert.setMessage("Please enter Your new username :");
        alert.setTitle("Username change");

        alert.setView(edittext);

        alert.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                String EditTextValue = edittext.getText().toString();
                if(!EditTextValue.equals("")){
                    fillListWithUsernamesFromDB();
                    int temp = listsOfUsernames.contains(EditTextValue) ? 1 : 0;
                    if(temp != 1){
                        ContentValues values = new ContentValues();
                        values.put("username", EditTextValue);
                        myDatabase.update("users", values, "username=?", new String[] {username});
                        ChangeTablesName(username, EditTextValue);
                        username = EditTextValue;
                        dialog.cancel();
                        AlertDialog("Success", "Your username was changed successfully, please log in again now.", "UsernameSuccess");
                    } else{
                        dialog.cancel();
                        AlertDialog("Error", "A user with this username already exists!", "DeleteError");
                    }
                }
            }
        });

        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                dialog.cancel();
            }
        });

        alert.show();
    }


    private void AlertDialog(final String title, final String msg, final String type){
        AlertDialog.Builder builder = new AlertDialog.Builder(SettingsActivity.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        if(type.equals("Delete")){
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.cancel();

                }
            });
        }
        String buttonText = type.equals("Delete") ? "Yes" : "Ok";
        builder.setPositiveButton(buttonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(type.equals("Delete")){
                    myDatabase.delete("users", "username=?", new String[]{username});
                    myDatabase.execSQL("DROP TABLE notes"+username);
                    myDatabase.execSQL("DROP TABLE tasks"+username);

                    dialog.cancel();
                    Intent myIntent = new Intent(SettingsActivity.this, LoginActivity.class);
                    startActivity(myIntent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finishAffinity();
                } else if(type.equals("UsernameSuccess")){
                    dialog.cancel();
                    Intent myIntent = new Intent(SettingsActivity.this, LoginActivity.class);
                    startActivity(myIntent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    finishAffinity();
                }
                dialog.cancel();
            }
        });
        builder.show();
    }

}
