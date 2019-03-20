package com.team36.comp208_implementation;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ListView myListView;
    ArrayList<listview_item> myRows = new ArrayList<>(); // Initialises the variable which will store each row of the friend listview

    String users_name = "David"; // Stores the active user's name
    final String welcome_text = "Welcome %s, see your rating below!";

    int[] friend_pp = {R.drawable.baseline_android_black_48dp}; // Stores the profile pictures of each friend

    String[] friend_names = {"Cam Mackay", "David Parrish", "Cybil Laobena", "Josh Houghton",
            "George Quantrell", "Javier Ballester"}; // Stores the names of all online friends

    double[] friend_ratings = {4, 4.2, 3.8, 5, 3.9, 4}; // Stores the ratings of each of the user's friends

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        myMainMethod(); // Calls my 'main' method that I created to differentiate between automatically created code and my own
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void myMainMethod(){
        myListView = findViewById(R.id.listView_friends); // Stores the listView
        myFillList(); // Creates all the rows that will fill the listView

        TextView textView_welcome = findViewById(R.id.textView_welcome);
        textView_welcome.setText(String.format(welcome_text, users_name));

        // The two lines below initialise an adapter that will fill out the listView and does so
        myAdapter myAdapter = new myAdapter(getApplicationContext(), myRows);
        myListView.setAdapter(myAdapter);
    }

    private void myFillList() {
        for (int i=0; i < friend_names.length; i++){
            // Creates each individual row dor the listView
            listview_item aRow = new listview_item();
            aRow.setImage_pp(friend_pp[0]);
            aRow.setText_name(friend_names[i]);
            aRow.setRating_stars(friend_ratings[i]);

            myRows.add(aRow); // Saves each row once necessary details are assigned out
        }
    }
}
