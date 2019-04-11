package com.team36.client_frontend;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    private Snackbar snackbar;
    private EditText newUsername;
    private EditText newPassword;
    private EditText passwordConfirmation;
    private EditText email;
    private Button newRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        newUsername = findViewById(R.id.editText_usernameRegister); //assigning variables
        newPassword = findViewById(R.id.editText_passwordRegister);
        passwordConfirmation = findViewById(R.id.editText_passwordConfirm);
        email = findViewById(R.id.editText_emailRegister);
        snackbar = Snackbar.make((findViewById(R.id.constrainLayoutRegisterActivity)), R.string.register_snackbar_fieldError, Snackbar.LENGTH_SHORT);
        newRegister = findViewById(R.id.button_registerRegister);

        newRegister.setOnClickListener(this::onRegister);
    }

    public void onRegister(View view){
        hideKeyboard();
        if (checkBlank() && !(snackbar.isShown())){
            snackbar.show();
        }
    }

    private boolean checkBlank(){
        return (newUsername.getText().toString().equals("") || newPassword.getText().toString().equals("") ||
                passwordConfirmation.getText().toString().equals("") || email.getText().toString().equals(""));
    }

    private void hideKeyboard(){
        View view = this.getCurrentFocus();
        if(view == null){
            view = new View(this);
        }
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
