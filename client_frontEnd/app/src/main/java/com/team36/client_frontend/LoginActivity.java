package com.team36.client_frontend;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private final String attemptsRemaining = "Number of attempts remaining: %d";

    private EditText username;
    private EditText password;
    private TextView attempts;
    private Button login;
    private Button register;
    private int counter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getSupportActionBar().hide();
        new RemoveStatus(this);

        setContentView(R.layout.activity_login);

        username = findViewById(R.id.editText_username); // Assigning variables
        password = findViewById(R.id.editText_password);
        attempts = findViewById(R.id.textView_attempts);
        login = findViewById(R.id.button_login);
        attempts.setText(String.format(attemptsRemaining, 5));
        register = findViewById(R.id.button_register);

        login.setOnClickListener(this::onLogin);
        register.setOnClickListener(this::onRegister);
    }

    public void onLogin(View view){
        validate(username.getText().toString(), password.getText().toString());
        // Gets the username and password from what the user typed into the boxes
    }

    public void onRegister(View view){
        registerUser();
        // Register new user
    }

    private void validate (String userName, String userPassword) {// Sets the username and password
        if ((userName.equals("")) && (userPassword.equals(""))) {
            // Opens base fragment is what links to the rest of the app
            SaveLogInState();
            Intent intent = new Intent(LoginActivity.this, BaseActivity.class);
            startActivity(intent);
        }else {
            counter--;

            attempts.setText(String.format(attemptsRemaining, counter));
            // Converts int to string to show how many remaining attempts you have

            if (counter == 0) {//after 5 attempts button disabled
                finish();
                System.exit(0);
            }
        }
    }
    // TODO: Save the users Log In State on successful Log In
    private void SaveLogInState(){
        SharedPreferences sharedPreferences;
        int mode = Activity.MODE_PRIVATE;
    }

    private void registerUser () {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            // Second activity is what links to the rest of the app
            startActivity(intent);
    }
}
