package com.team36.client_frontend;
// Javier Ballester

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity implements ServerResponded {

    private Snackbar snackbar;
    private EditText newFirst;
    private EditText newLast;
    private EditText newUsername;
    private EditText newPassword;
    private EditText passwordConfirmation;
    private EditText email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        newFirst = findViewById(R.id.editText_firstName); //assigning variables
        newLast = findViewById(R.id.editText_lastName);
        newUsername = findViewById(R.id.editText_usernameRegister);
        newPassword = findViewById(R.id.editText_passwordRegister);
        passwordConfirmation = findViewById(R.id.editText_passwordConfirm);
        email = findViewById(R.id.editText_emailRegister);
       // snackbar = Snackbar.make((findViewById(R.id.constrainLayoutRegisterActivity)), R.string.register_snackbar_fieldError, Snackbar.LENGTH_SHORT); //**** David Parrish ****//
        Button newRegister = findViewById(R.id.button_registerRegister);

        newRegister.setOnClickListener(this::onRegister);
    }

    public void onRegister(View view){
        //**** David Parrish ****//
        hideKeyboard(); // Hides the keyboard so that the snackbar can be seen by the user
       // if (checkBlank() ){
            //snackbar.show(); // Shows the snackbar if fields are left blank and the snackbar is not already shown

        // }
        //********//

        String toSend = ("username="+newUsername.getText().toString()+"&password=" + newPassword.getText().toString()+"&firstname="
                + newFirst.getText().toString() + "&surname="+newLast + "&email="+ email);
        new ServerSender(RegisterActivity.this).execute(toSend, "http://social-box.xyz/api/register", "");
    }

    //**** David Parrish ****//
    private boolean checkBlank(){
        // Checks whether any of the fields are left blank
        return (newFirst.getText().toString().equals("") || newLast.getText().toString().equals("") ||
                newUsername.getText().toString().equals("") || newPassword.getText().toString().equals("") ||
                passwordConfirmation.getText().toString().equals("") || email.getText().toString().equals(""));
    }
    //********//

    //**** David Parrish ****//
    private void hideKeyboard(){
        // The below hides the keyboard if open
        View view = this.getCurrentFocus();
        if(view == null){
            view = new View(this);
        }
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
    //********//

    private void CheckRegistered(String result) throws JSONException {

        JSONObject serverResponce = new JSONObject(result);
        Boolean isVaid = serverResponce.getBoolean("ok");
        if (isVaid) {
            Toast.makeText(getApplicationContext(),"Register successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //**** David Parrish ****//
            startActivity(intent);
        } else {
            String errorCode = serverResponce.getString("message");
            Toast.makeText(getApplicationContext(),errorCode, Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public void onTaskComplete(String result) {
        try {
            CheckRegistered(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
}}
