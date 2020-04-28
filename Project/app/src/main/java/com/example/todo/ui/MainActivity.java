package com.example.todo.ui;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.todo.R;

public class MainActivity extends AppCompatActivity  {

    private String username = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        @SuppressLint("WrongConstant") SharedPreferences sh = getSharedPreferences("USERNAME", MODE_APPEND);
        username = sh.getString("Name", "");
        TextView textView = findViewById(R.id.textView3);
        final EditText editText = findViewById(R.id.editText);
        textView.setText(username);


        Button button4 = findViewById(R.id.button4);

        button4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                SharedPreferences sh = getSharedPreferences("USERNAME", MODE_PRIVATE);
                SharedPreferences.Editor editor = sh.edit();
                editor.putString("Name", editText.getText().toString());
//                editor.putBoolean("Exists",  username.length() > 0 );
                editor.apply();
            }
        });


    }


    @Override
    public void finish(){
        super.finish();


    }

    @Override
    public void onBackPressed(){
        finish();
    }

    protected void onResume() {
        super.onResume();

    }





}
