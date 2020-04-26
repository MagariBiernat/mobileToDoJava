package com.example.todo.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.example.todo.R;
import com.example.todo.ui.Main_Page.Main_page;
import com.example.todo.ui.register.Register;

public class MainActivity extends AppCompatActivity  {






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // variables with access to texts, button, clickable textview.

        EditText login = (EditText) findViewById(R.id.Login_Login_Text);
        EditText password = (EditText) findViewById(R.id.Login_Password_Text);
        Button logInButton = (Button) findViewById(R.id.Login_Log_In_Button);
        TextView register = (TextView) findViewById(R.id.Login_Register_Text);

//        Move to register activity after clicking register textview

        register.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(v.getContext(), Register.class);
                startActivityForResult(myIntent, 0);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

//            Login button

        logInButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent myIntent = new Intent(v.getContext(), Main_page.class);
                startActivityForResult(myIntent,0);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

//                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
//                builder.setCancelable(true);
//                builder.setTitle("Hello");
//                builder.setMessage("Hi!");
//                builder.setNegativeButton("Wyjdz", new DialogInterface.OnClickListener() {
//
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dialog.cancel();
//                    }
//                });
//                builder.show();

            }
        });



    }


    @Override
    public void onBackPressed(){
        finish();
    }





}
