package com.example.todo.ui.login;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;

import android.util.Pair;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ActivityNavigator;

import com.example.todo.R;
import com.example.todo.ui.Main_Page.Main_page;
import com.example.todo.ui.register.RegisterActivity;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    TextView helloThere, logInToContinue;
    ImageView image;
    TextInputEditText username, password;
    Button forgetPassword, okLogIn, newSignUp;
    CheckBox rememberMe;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        //TODO: check if shared preferences set (user clicked keep logged in checkbox)
        // if yes move to main part of app.
        // else stay

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Hooks

        //Image and ViewTexts
        image = findViewById(R.id.imageView2);
        helloThere = findViewById(R.id.textHello);
        logInToContinue = findViewById(R.id.textlogintocontinue);
        // EditTexts
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        // Buttons
        forgetPassword = findViewById(R.id.forgetButton);
        okLogIn = findViewById(R.id.login_loginbutton);
        newSignUp = findViewById(R.id.login_signupbutton);
        // CheckBox
        rememberMe = findViewById(R.id.checkBoxRemember);

        //editor for password field, closing soft keyboard
        password.setOnEditorActionListener(editorListener);

        okLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String _username = username.getText().toString();
                final String _password = password.getText().toString();
                if(_username.equals(""))
                    AlertDialog("Error!", "Please fill username field!");
                else if (_password.equals(""))
                    AlertDialog("Error!", "Please fill password field!");
                else
                    login(_username, _password);
            }
        });
        
        newSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(myIntent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

    }

    private void login(final String _username, final String _password){

        // open Database
        SQLiteDatabase myDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);

        // query looking for any rows with user's username
        Cursor cursor = myDatabase.rawQuery("SELECT * FROM users WHERE username=?", new String [] {_username});

        //if there are any rows received, move to first, and compare passwords.
        if(cursor.getCount() > 0){
            if(cursor.moveToFirst()){
                final String pwd = cursor.getString(cursor.getColumnIndex("password"));
                if(_password.equals(pwd)){
                    Intent myIntent = new Intent(LoginActivity.this, Main_page.class);
                    myIntent.putExtra("USERNAME", _username);
                    startActivity(myIntent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    cursor.close();
                }
                else{
                    AlertDialog("Error", "Wrong login or password");
                }
            }
        }
        else
            AlertDialog("Error", "Wrong login or password");

        //close cursor for no data leak.
        cursor.close();
    };


    private TextView.OnEditorActionListener editorListener = new TextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            if(actionId == EditorInfo.IME_ACTION_SEND)
            {
                closeKeyboard();
            }
            return false;
        }
    };

    private void closeKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private void AlertDialog(final String title, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(msg);
        builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
                if(title.equals("Successful"))
                {
                    finish();
                }
            }
        });
        builder.show();
    }

    @Override
    public void finish(){
        super.finish();
    }
    @Override
    public void onBackPressed(){
        finish();
        //TODO: add another activity with some kind of byebye logo \o\
    }
    protected void onResume() {
        super.onResume();
        username.setText("");
        password.setText("");
    }

}
