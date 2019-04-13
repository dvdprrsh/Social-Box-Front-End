package com.team36.client_frontend;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
    private final String attemptsRemaining = "Number of attempts remaining: %d";
    private Snackbar snackbar;
    private boolean loggedIn = false;
    private String username_text = null;
    private EditText username;
    private EditText password;
    private TextView attempts;
    private Button login;
    private Button register;
    private int counter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean checkLogin = true;

        try {
            checkLogin = getIntent().getBooleanExtra("checkLogin", true);

            SharedPreferences sharedPreferences = getSharedPreferences("Logged_In", Context.MODE_PRIVATE);
            username_text = sharedPreferences.getString("Username",null);
        }catch (NullPointerException e){
            System.out.print("First Open");
        }

        if (checkLogin) checkLogInState();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        snackbar = Snackbar.make((findViewById(R.id.constraintLayoutLoginActivity)), R.string.login_snackbar, Snackbar.LENGTH_LONG);

        username = findViewById(R.id.editText_username); // Assigning variables
        password = findViewById(R.id.editText_password);
        attempts = findViewById(R.id.textView_attempts);
        attempts.setText(String.format(attemptsRemaining, 5));

        login = findViewById(R.id.button_login);
        register = findViewById(R.id.button_register);
        login.setOnClickListener(this::onLogin);
        register.setOnClickListener(this::onRegister);

        setUsername();
    }

    // Checks if the user has logged in previously and not logged out
    private void checkLogInState(){
        try {
            SharedPreferences sharedPreferences = getSharedPreferences("Logged_In", Context.MODE_PRIVATE);
            loggedIn = sharedPreferences.getBoolean("logged_in", false);
            username_text = sharedPreferences.getString("Username",null);
        }catch (NullPointerException e){
            System.out.print("User not previously logged in.");
        }

        if (loggedIn){
            Intent intent = new Intent(LoginActivity.this, BaseActivity.class);
            startActivity(intent);
            finish();
        }else{
            setUsername();
        }
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
        if ((userName.equals("Dave")) && (userPassword.equals(""))) {
            // Opens base fragment is what links to the rest of the app
            SaveLogInState();
            Intent intent = new Intent(LoginActivity.this, BaseActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }else {
            View view = this.getCurrentFocus();
            if(view == null){
                view = new View(this);
            }

            InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

            if (!snackbar.isShown()){
                snackbar.show();
            }

            counter--;
            attempts.setText(String.format(attemptsRemaining, counter));
            // Converts int to string to show how many remaining attempts you have

            if (counter == 0) {//after 5 attempts button disabled
                Snackbar snackbar_disabledLogIn = Snackbar.make(findViewById(R.id.constraintLayoutLoginActivity), R.string.login_snackbar_loginDisabled, Snackbar.LENGTH_LONG);
                snackbar_disabledLogIn.show();

                login.setEnabled(false);
            }
        }
    }

    // If username has previously been entered, sets the username field to this value
    private void setUsername(){
        if (username_text != null){
            username.setText(username_text);
        }
    }

    // Saves the users log in status so they do not have to log in each time
    private void SaveLogInState(){
        SharedPreferences sharedPreferences = getSharedPreferences("Logged_In", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("logged_in", true);
        editor.putString("Username", username.getText().toString());
        editor.apply();
    }

    private void registerUser () {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            // Second activity is what links to the rest of the app
            startActivity(intent);
    }
}
