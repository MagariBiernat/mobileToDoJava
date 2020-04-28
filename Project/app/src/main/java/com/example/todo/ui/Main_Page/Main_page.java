package com.example.todo.ui.Main_Page;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.todo.R;
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

    private String LoginValue = "", PasswordValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);

        LoginValue = getIntent().getStringExtra("LOGIN_VALUE");
        PasswordValue = getIntent().getStringExtra("PASSWORD_VALUE");

        //menu hamburger
        ImageView hamburger = findViewById(R.id.hamburger_menu);
        registerForContextMenu(hamburger);

        BottomNavigationView navView = findViewById(R.id.nav_view);
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_main, R.id.navigation_stats, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


        // Welcome TOAST
        Toast.makeText(getApplicationContext(),"Welcome "+LoginValue, Toast.LENGTH_SHORT).show();

    }

    // create ContextMenu on click

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Choose your option");
        getMenuInflater().inflate(R.menu.main, menu);
    }

    // context menu switch

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()) {
            case R.id.main_menu_settings:
                Toast.makeText(this, "settings", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.main_menu_logout:
                Toast.makeText(this, "logout", Toast.LENGTH_SHORT).show();
                return true;

        }
        return super.onContextItemSelected(item);
    }



    @Override
    public void onBackPressed() {
//        AlertDialog.Builder builder = new AlertDialog.Builder(Main_page.this,);
//        builder.setCancelable(true);
//        builder.setTitle("Log out");
//        builder.setMessage("Are You sure You want to log out ?!");
//        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//                finish();
//            }
//        });
//        builder.setPositiveButton( "No!", new DialogInterface.OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.cancel();
//            }
//        });
//        builder.show();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    public String getLogin() { return LoginValue;}
}
