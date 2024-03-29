package com.team36.client_frontend;
// Javier Ballester

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity implements ServerResponded {
    private final String attemptsRemaining = "Number of attempts remaining: %d";
    private NetworkAvailable networkAvailable;
    private Snackbar snackbar;
    private boolean loggedIn = false;
    private String username_text = null;
    private String password_text = null;
    private EditText username;
    private EditText password;
    private TextView attempts;
    private Button login;
    private Button register;
    private int counter = 5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        boolean checkLogin = true;

        //**** David Parrish ****//
        try {
            checkLogin = getIntent().getBooleanExtra("checkLogin", true);

            SharedPreferences sharedPreferences = getSharedPreferences("Logged_In", Context.MODE_PRIVATE);
            username_text = sharedPreferences.getString("Username",null);
        }catch (NullPointerException e){
            System.out.print("First Open");
        }
        //********//

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        snackbar = Snackbar.make(findViewById(R.id.scroll), R.string.login_snackbar, Snackbar.LENGTH_LONG); //**** David Parrish ****//

        username = findViewById(R.id.editText_username); // Assigning variables
        password = findViewById(R.id.editText_password);
        attempts = findViewById(R.id.textView_attempts);
        attempts.setText(String.format(attemptsRemaining, 5));

        login = findViewById(R.id.button_login);
        register = findViewById(R.id.button_register);

        //**** David Parrish ****//
        login.setOnClickListener(this::onLogin);
        register.setOnClickListener(this::onRegister);

        networkAvailable = new NetworkAvailable(this);

        if (networkAvailable.netAvailable()){
            if (checkLogin) checkLogInState();
        }
        setUsername();
        //********//
    }

    //**** David Parrish ****//
    // Checks if the user has logged in previously and not logged out
    private void checkLogInState(){
        if (networkAvailable.netAvailable()) {
            try {
                SharedPreferences sharedPreferences = getSharedPreferences("Logged_In", Context.MODE_PRIVATE);
                loggedIn = sharedPreferences.getBoolean("logged_in", false);
                username_text = sharedPreferences.getString("username", null);
            } catch (NullPointerException e) {
                System.out.print("User not previously logged in.");
            }
                setUsername();
        }
    }
    //********//

    public void onLogin(View view){
        if (networkAvailable.netAvailable()) { //**** David Parrish ****//
            login.setEnabled(false);
            //**** Cameron MacKay ****//
            // Gets the username and password from what the user typed into the boxes
            String toSend = ("username=" + username.getText().toString() + "&password=" + password.getText().toString());
            //Send to the server
            new ServerSender(LoginActivity.this).execute(toSend, "http://social-box.xyz/api/login", "");
            //********//
        }
    }

    public void onRegister(View view){
        if (networkAvailable.netAvailable()) { //**** David Parrish ****//
            registerUser();
            // Register new user
        }
    }

    public void validate (String responseJSON) throws JSONException {// Sets the username and password

        //Get responce from server check if login was successful
        JSONObject serverResponce = new JSONObject(responseJSON);
        Boolean isVaid = serverResponce.getBoolean("ok");

        if (isVaid) {
            // Opens base activity which links to the rest of the app
            SaveLogInState(); //**** David Parrish ****//
            Intent intent = new Intent(LoginActivity.this, BaseActivity.class);
            intent.putExtra("json",responseJSON);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //**** David Parrish ****//
            startActivity(intent);
        }else {
            View view = this.getCurrentFocus();
            if(view == null){
                view = new View(this);
            }

            //**** David Parrish ****//
            InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);

            if (!snackbar.isShown()){
                snackbar.show();
            }
            //********//

            counter--;
            attempts.setText(String.format(attemptsRemaining, counter));
            // Converts int to string to show how many remaining attempts you have

            if (counter == 0) {//after 5 attempts button disabled
                Snackbar.make(findViewById(R.id.constraintLayoutLoginActivity), R.string.login_snackbar_loginDisabled,
                        Snackbar.LENGTH_INDEFINITE).show(); //**** David Parrish ****//

                login.setEnabled(false);
            }
        }
    }

    //**** David Parrish ****//

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
        editor.putString("username", username.getText().toString());
        editor.apply();
    }

    //********//

    private void registerUser () {
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        // Second activity is what links to the rest of the app
        startActivity(intent);
    }


    //When the server gets response it comes here
    @Override
    public void onTaskComplete(String result) {
        login.setEnabled(true);
        try {
            validate(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
