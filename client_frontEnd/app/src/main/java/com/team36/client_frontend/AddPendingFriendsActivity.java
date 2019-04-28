package com.team36.client_frontend;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class AddPendingFriendsActivity extends AppCompatActivity implements ServerResponded {
    // David Parrish - 201232252

    private EditText friendsUsername;
    private boolean friendRequests = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pending_friends);

        checkPendingFriendRequests();
        friendsUsername = findViewById(R.id.editText_friendsUsername);
    }

    public void addFriend(View view){
        // Check username exists and send request here
        String username = friendsUsername.toString();
        /*
        String toSend = ("api_key=" + need to put in api key + "friend_username=" + friendUsername);
        new ServerSender(getActivity()).execute(toSend, "http://social-box.xyz/api/login", "");
        */
    }

    public void handleAddFriend(String result) throws JSONException {
        JSONObject json = new JSONObject(result);
    }


    private void checkPendingFriendRequests(){
        // Check for friend requests
        if (friendRequests){
            // Load friend requests here

            /*
            String[] friends_keys = new String[friends.size()];
            friends.keySet().toArray(friends_keys);

            myListView.setOnItemClickListener(myClickListenerFriend);
            OverallCalculator overallCalculator;
            for (int i = 0; i < friends.size(); i++) {
                overallCalculator = new OverallCalculator(friends.get(friends_keys[i]));
                double overall = overallCalculator.overallRating;

                ListView_ItemNormal oneRow = new ListView_ItemNormal(); // Creates a new row
                // The below assigns each of the values to their corresponding components
                oneRow.setImage_ratingImage(new ImageCalculator(overall).image);
                oneRow.setText_nameDay(friends_keys[i]);
                oneRow.setRating_ratingStars((float) overall);
                oneRow.setRating_ratingString(overall);

                allRows.add(oneRow); // Adds each row to the list of rows to be added to the listView below
            }
            */
        }else{
            // Displays message to user indicating that they have no friend requests at that time
            TextView errorText = findViewById(R.id.textView_noPending);
            errorText.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onTaskComplete(String result) {

        try {
            handleAddFriend(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
