package com.example.todo.ui.Main_Page;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.todo.R;
import com.example.todo.ui.MainActivity;
import com.example.todo.ui.Main_Page.Main.NewTaskActivity;
import com.example.todo.ui.Main_Page.Main.showTaskActivity;
import com.example.todo.ui.Main_Page.Notes.NewNoteActivity;
import com.example.todo.ui.login.LoginActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class Main_page extends AppCompatActivity {

    private String username;
    public SQLiteDatabase myDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        myDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);

        username = getIntent().getStringExtra("USERNAME");

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_main, R.id.navigation_stats, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupWithNavController(navView, navController);

        // Welcome TOAST
        Toast.makeText(getApplicationContext(),"Welcome ", Toast.LENGTH_SHORT).show();

    }

    public void showTaskActivity(int id, String title){
        Intent myIntent = new Intent(Main_page.this, showTaskActivity.class);
        myIntent.putExtra("TASK_ID", id);
        myIntent.putExtra("TASK_TITLE", title);
        myIntent.putExtra("USERNAME", username);
        startActivity(myIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    public void newTaskAcitivity() {
        Intent myIntent = new Intent(Main_page.this, NewTaskActivity.class);
        myIntent.putExtra("USERNAME", username);
        startActivity(myIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void newNoteActivity(){
        Intent myIntent = new Intent(Main_page.this, NewNoteActivity.class);
        myIntent.putExtra("USERNAME", username);
        startActivity(myIntent);
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public String getUsername(){return username;}

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Main_page.this);
        builder.setCancelable(true);
        builder.setTitle("Log out");
        builder.setMessage("Are You sure You want to log out ?!");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
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
        myDatabase.close();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i("asdfasdf", " krul wielki");
    }
}
