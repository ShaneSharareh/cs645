package com.bignerdranch.android.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Homepage extends AppCompatActivity {
private TextView welcomeTextView;
private TextView invitedNotification;
private Button createEvent;
private Button viewHostedEvents;
private Button viewUpcomingEvents;
private static final String FILENAME = "UserFile";

private String username;
private DatabaseReference eventRef;
private FirebaseDatabase database;
private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        database = FirebaseDatabase.getInstance();
        eventRef = database.getReference("event");
        welcomeTextView = (TextView) findViewById(R.id.welcome);
        invitedNotification = (TextView) findViewById(R.id.invitedNotification);
        createEvent = (Button) findViewById(R.id.createEvent);
        viewHostedEvents = (Button) findViewById(R.id.goToHostedButton);
        viewUpcomingEvents = (Button) findViewById(R.id.upcoming);
        loadData();
        //loadHosted();
        loadInvited();

        //      Intent intent = getIntent();
//        User user = (User) intent.getExtras().getSerializable("USER");
        //username = user.username;

        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToCreateEventPage();

            }
        });

        invitedNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayInvitedEvent();
            }
        });

        viewHostedEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayHostedEvent();
            }
        });

        viewUpcomingEvents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                displayUpcomingEvents();
            }
        });
    }

    public void loadData(){
        // Capture the layout's TextView and set the string as its text
        SharedPreferences settings = getSharedPreferences(FILENAME, 0);
        Gson gson = new Gson();
        String savedUser = settings.getString("SavedUser", "");
        // if(!savedStudent.equals("")) {//if student info is saved
        if (!savedUser.equals("")) {
            user = gson.fromJson(savedUser, User.class);
            username = user.getUsername();
            welcomeTextView.setText("Welcome "+user.getFirstName());

        } else {
            sendToLogin();
        }

    }

    public void sendToLogin(){
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

    public void goToCreateEventPage(){
        Intent intent = new Intent(this, CreateEventActivity.class);
        startActivity(intent);
    }

   /* public void loadHosted(){

        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int hostedCount = 0;
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                // Log.e("Count " ,"Count:"+dataSnapshot.getChildrenCount());
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    Event event = child.getValue(Event.class);
                    if(username.equals(event.getHostname())){
                        hostedCount++;
                    }


                }
                hostedNotification.setText(getResources().getString(R.string.hostNotification ) +hostedCount);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERRORMESSAGE", "Failed to read value.", error.toException());
            }
        });
    }*/
    public void loadInvited(){
        eventRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int pendingCount = 0;
                // This method is called once with tthe initial value and again
                // whenever data at this location is updated.
                // Log.e("Count " ,"Count:"+dataSnapshot.getChildrenCount());
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    Event event = child.getValue(Event.class);
//                    eventRef.child(event.getName()).child("pendingList").child(user.getUsername()).setValue(userInfo);
                    ArrayList<User> pendingList= event.getPendingList();

                    for (int i= 0; i<pendingList.size(); i++ ){
                        if(username.equals(pendingList.get(i).getUsername())){
                            pendingCount++;
                        }
                    }


                }
                invitedNotification.setText(getResources().getString(R.string.invitedNotification ) +pendingCount);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERRORMESSAGE", "Failed to read value.", error.toException());
            }
        });
    }

    public void displayInvitedEvent(){
        Intent intent = new Intent(this, DisplayInvitedNotifications.class);
        startActivity(intent);
    }

    public void displayHostedEvent(){
        Intent intent = new Intent(this, DisplayHostedEvents.class);
        startActivity(intent);
    }

    public void displayUpcomingEvents(){
        Intent intent = new Intent(this, DisplayUpcomingEvents.class);
        startActivity(intent);
    }
}
