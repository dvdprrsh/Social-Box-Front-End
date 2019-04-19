package com.team36.client_frontend;
// Javier Ballester

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity implements ServerResponded {
    private NetworkAvailable networkAvailable;
    private Snackbar snackbar;
    private Button newRegister;
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
        snackbar = Snackbar.make(findViewById(R.id.scrollView), R.string.register_snackbar_fieldError, Snackbar.LENGTH_SHORT); //**** David Parrish ****//
        newRegister = findViewById(R.id.button_registerRegister);

        newRegister.setOnClickListener(this::onRegister);

        Button back = findViewById(R.id.button_registerBack);
        back.setOnClickListener(this::onBack);

        networkAvailable = new NetworkAvailable(this);
        if (!networkAvailable.netAvailable()){
            newRegister.setEnabled(false);
        }
    }

    public void onRegister(View view){
        //**** David Parrish ****//
        if (networkAvailable.netAvailable()) {
            hideKeyboard(); // Hides the keyboard so that the snackbar can be seen by the user

            if (checkBlank()) {
                snackbar.show(); // Shows the snackbar if fields are left blank and the snackbar is not already shown

            } else if (!checkEmail()) {
                Snackbar.make(findViewById(R.id.scrollView), R.string.register_snackbar_emailError, Snackbar.LENGTH_SHORT).show();
                //********//

            } else {
                newRegister.setEnabled(false);
                //**** Cameron MacKay ****//
                String toSend = ("username=" + newUsername.getText().toString() + "&password=" + newPassword.getText().toString() + "&firstname="
                        + newFirst.getText().toString() + "&surname=" + newLast + "&email=" + email);
                new ServerSender(RegisterActivity.this).execute(toSend, "http://social-box.xyz/api/register", "");
                //********//
            }
        }
    }

    //**** David Parrish ****//

    public void onBack(View view){
        hideKeyboard(); // Hides the keyboard

        onBackPressed();
        finish();
    }

    private boolean checkBlank(){
        // Checks whether any of the fields are left blank
        String elecMail = email.getText().toString();
        return (newFirst.getText().toString().equals("") || newLast.getText().toString().equals("") ||
                newUsername.getText().toString().equals("") || newPassword.getText().toString().equals("") ||
                passwordConfirmation.getText().toString().equals("") || elecMail.equals(""));
    }

    private boolean checkEmail(){
        // Checks if email entered is valid
        return Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches();
    }

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

    //**** Cameron MacKay ****//
    private void CheckRegistered(String result) throws JSONException {

        JSONObject serverResponce = new JSONObject(result);
        boolean isValid = serverResponce.getBoolean("ok");
        if (isValid) {
            hideKeyboard();
            Toast.makeText(getApplicationContext(),"Register successful", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); //**** David Parrish ****//
            startActivity(intent);
            finish();
        } else {
            hideKeyboard();

            String errorCode = serverResponce.getString("message");
            Toast.makeText(getApplicationContext(),errorCode, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onTaskComplete(String result) {
        newRegister.setEnabled(true);
        try {
            CheckRegistered(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    //********//
}
