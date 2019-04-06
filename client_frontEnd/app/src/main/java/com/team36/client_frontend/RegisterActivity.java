package com.team36.client_frontend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    private EditText newUsername;
    private EditText newPassword;
    private EditText passwordConfirmation;
    private EditText email;
    private Button newRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        newUsername = findViewById(R.id.editText_username); //assigning variables
        newPassword = findViewById(R.id.editText_passwordRegister);
        passwordConfirmation = findViewById(R.id.editText_passwordConfirm);
        email = findViewById(R.id.editText_emailRegister);
        newRegister = findViewById(R.id.button_registerRegister);

        newRegister.setOnClickListener(new View.OnClickListener() {//what happens when you press login btn
            @Override
            public void onClick(View v) {
                //send users info to server here
            }
        });
    }
}
