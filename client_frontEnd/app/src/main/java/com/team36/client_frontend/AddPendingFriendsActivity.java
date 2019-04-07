package com.team36.client_frontend;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class AddPendingFriendsActivity extends AppCompatActivity {
    private EditText friendsUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        new RemoveStatus(this);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_add_pending_friends);

        friendsUsername = findViewById(R.id.editText_friendsUsername);
    }

    public void addFriend(View view){
        // Check username exists and send request here
    }
}
