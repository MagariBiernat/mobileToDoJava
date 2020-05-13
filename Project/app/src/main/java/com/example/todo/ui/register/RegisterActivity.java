package com.example.todo.ui.register;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ActivityNavigator;

import com.example.todo.R;
import com.example.todo.ui.Main_Page.Main_page;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegisterActivity extends AppCompatActivity {

    // material edittext widgets.
    TextInputEditText username, password, passwordTwo;
    CheckBox checkTerms;


    // SQL Create table
    private static String tableUsers = "CREATE TABLE IF NOT EXISTS users ( " +
            " user_ID INTEGER PRIMARY KEY, " +
            " username VARCHAR NOT NULL, " +
            " password VARCHAR NOT NULL, " +
            " date_created VARCHAR," +
            " tasks_successful INT(10) DEFAULT 0," +
            " tasks_failed INT(10) DEFAULT 0," +
            " tasks_ongoing INT(10) DEFAULT 0 )";


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_register);
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

            // Hooks
            username = findViewById(R.id.username);
            password = findViewById(R.id.password);
            passwordTwo = findViewById(R.id.passwordTwo);
            checkTerms = findViewById(R.id.checkBoxTerms);

            // Buttons
            Button registerUser = findViewById(R.id.login_loginbutton);
            Button goBack = findViewById(R.id.register_goback);

            // TODO: LATER coloring fields on validation

//            username.addTextChangedListener(new TextWatcher() {
//                @Override
//                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//                }
//
//                @Override
//                public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//                }
//
//                @Override
//                public void afterTextChanged(Editable s) {
//
//                }
//            });

            // editorListener for second password. Next on keyboard go straight to SIGN UP button (registerUser)
            passwordTwo.setOnEditorActionListener(editorListener);

            // go back to login page
            goBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onBackPressed();
                }
            });


            // SIGN UP button onClick event.
            registerUser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                        registerFunction();

                }
            });

        }

        // this occurs when user clicks NEXT on keyboard while on third EditText( Confirm Password)
        // same as he clicked SIGN UP  finishing registering.
        private TextView.OnEditorActionListener editorListener = new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    closeKeyboard();
                }
                return false;
            }
        };

        private void registerFunction(){
            if(validationSuccess())
            {
                String _username = username.getText().toString();
                String _password = password.getText().toString();
                String _passwordTwo = passwordTwo.getText().toString();

                // Create/Open Database
                SQLiteDatabase myDatabase = this.openOrCreateDatabase("Users", MODE_PRIVATE, null);

                // create table 'users' if does not exist.
                myDatabase.execSQL(tableUsers);

                //check if user with this login already exists
                // second parameter is a parameter for '?' in a query.
                Cursor cursor = myDatabase.rawQuery("SELECT username FROM users WHERE username=?", new String [] {String.valueOf(_username)});

                // getCount returns a number of columns received from a query. If it is bigger than 0, it means there is already a user with same username
                if(cursor.getCount()>0) {
                    AlertDialog("Error", "Username already exists!");
//                    return false;
                }
                else  // otherwise it means the username can be created with that name.
                {
                    @SuppressLint("SimpleDateFormat") DateFormat date = new SimpleDateFormat("d/MM/yyyy");
                    String now = date.format(new Date());
//                    String[] Data = now.split("/");
//                    now = Data[0] + " " + getMonth(Integer.parseInt(Data[1])) + ""
                    ContentValues values = new ContentValues();
                    values.put("username", _username);
                    values.put("password", _password);
                    values.put("date_created", now);

                    if(myDatabase.insert("users", null, values) > -1)
                    {
                        //create user's tasks table : tasksUSERNAME
                        myDatabase.execSQL(createTasksTable(_username));
                        //create user's notes table : notesUSERNAME
                        myDatabase.execSQL(createNotesTable(_username));
                        AlertDialog("Successful", "User has been created :)");
                    }
                }
                cursor.close();

                myDatabase.close();
            }
        }

    // NotesTable
    private String createNotesTable(final String username) {
        return "CREATE TABLE IF NOT EXISTS notes"+username+" ( " +
                " note_ID INTEGER PRIMARY KEY, " +
                " title VARCHAR NOT NULL, " +
                " description VARCHAR )";
    };


    // TasksTable
    private String createTasksTable(final String username) {
        return "CREATE TABLE IF NOT EXISTS tasks"+username+" ( " +
              " task_ID INTEGER PRIMARY KEY, " +
              " title VARCHAR NOT NULL, " +
              " date VARCHAR , " +
              " time VARCHAR," +
              " description VARCHAR," +
              " image_src VARCHAR )";
    };


    //return name of month
    private static String getMonth(int month) {
        return new DateFormatSymbols().getMonths()[month-1];
    }

    //Validate form

    private Boolean validationSuccess(){
            if(username.getText().toString().equalsIgnoreCase("")){
                AlertDialog("Error", "Please enter username");
                return false;
            }
            if(password.getText().toString().equalsIgnoreCase("")){
                AlertDialog("Error", "Please enter password");
                return false;
            }
            if(passwordTwo.getText().toString().equalsIgnoreCase("")){
                AlertDialog("Error", "Please confirm password");
                return false;
            }
            if(!passwordTwo.getText().toString().equals(password.getText().toString())){
                AlertDialog("Error", "Passwords must be equal");
                return false;
            }
            if(!checkTerms.isChecked())
            {
                AlertDialog("Error", "Please confirm everything :)");
                return false;
            }
            return true;
    };

    private void AlertDialog(final String title, String msg){
        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
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

    private void closeKeyboard(){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right); }
    @Override
    public void onBackPressed(){ finish(); }
}


