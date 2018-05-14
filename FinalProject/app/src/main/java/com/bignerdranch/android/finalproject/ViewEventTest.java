package com.bignerdranch.android.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ViewEventTest extends AppCompatActivity {
    private DatabaseReference eventRef;
    private FirebaseDatabase database;
    private User user;
    private static final String FILENAME = "UserFile";
    private String username;
    private String eventName;
    private TextView eventTitle;
    private TextView eventHost;
    private TextView eventDescription;
    private Button acceptButton;
    private Button rejectButton;
    private Button editButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_event_test);
        editButton = findViewById(R.id.editButton);
        database = FirebaseDatabase.getInstance();
        eventRef = database.getReference("event");
        eventTitle = (TextView) findViewById(R.id.eventTitle);
        eventHost = (TextView) findViewById(R.id.eventHost);
        eventDescription = (TextView) findViewById(R.id.eventDescription);
        acceptButton = (Button) findViewById(R.id.acceptButton);
        editButton = (Button) findViewById(R.id.editButton);
        rejectButton = (Button) findViewById(R.id.rejectButton);
        loadData();
        Intent intent = getIntent();
        if(intent.hasExtra("EVENTNAME")){
            eventName = intent.getStringExtra("EVENTNAME");
            acceptButton.setVisibility(View.VISIBLE);
            rejectButton.setVisibility(View.VISIBLE);
            editButton.setVisibility(View.GONE);

        }
        else{
            eventName = intent.getStringExtra("HOSTEVENTNAME");
            Log.d("EventName", "Eventname:"+eventName);
            editButton.setVisibility(View.VISIBLE);
            acceptButton.setVisibility(View.GONE);
            rejectButton.setVisibility(View.GONE);
        }
        populateEventInfo();
        rejectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteFromPendingList();

            }
        });

        acceptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addToGuestList();

            }
        });

        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               editEvent();
            }
        });
    }
    public interface MyCallback {
        void onCallback(String value);
    }


    public void populateEventInfo(){
        eventRef.addValueEventListener(new ValueEventListener() {

            ArrayList<String> eventList = new ArrayList<String>();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                // Log.e("Count " ,"Count:"+dataSnapshot.getChildrenCount());
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Event event = child.getValue(Event.class);
                    if(event.getName().equals(eventName)){
                        eventTitle.setText("Title: "+event.getName());
                        eventHost.setText("Host: "+event.getHostname());
                        eventDescription.setText("Description: "+event.getDescription());
                    }

                    //guestNotification.setText(getResources().getString(R.string.invitedNotification ) +guestCount);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERRORMESSAGE", "Failed to read value.", error.toException());
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

        }

    }

    public void deleteFromPendingList(){
        eventRef.addListenerForSingleValueEvent(new ValueEventListener() {

            ArrayList<String> eventList = new ArrayList<String>();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                // Log.e("Count " ,"Count:"+dataSnapshot.getChildrenCount());
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Event event = child.getValue(Event.class);
                    if(event.getName().equals(eventName)) {
                        ArrayList<User> tempPendingList = event.getPendingList();
                        for (int i = 0; i < tempPendingList.size(); i++) {
                            if (tempPendingList.get(i).getUsername().equals(user.getUsername())) {
                                event.removeFromPendingList(i);
                                break;
                            }
                        }
                        eventRef.child(event.getName()).child("pendingList").setValue(event.getPendingList());
                        goToHomePage();

                        break;

                    }

                    //guestNotification.setText(getResources().getString(R.string.invitedNotification ) +guestCount);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERRORMESSAGE", "Failed to read value.", error.toException());
            }
        });
    }

    public void addToGuestList(){

        eventRef.addListenerForSingleValueEvent(new ValueEventListener() {

            ArrayList<String> eventList = new ArrayList<String>();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                // Log.e("Count " ,"Count:"+dataSnapshot.getChildrenCount());

                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Event event = child.getValue(Event.class);
                    if(event.getName().equals(eventName)){
                        ArrayList<User> tempPendingList = event.getPendingList();
                        for(int i = 0; i<tempPendingList.size(); i++) {
                            if (tempPendingList.get(i).getUsername().equals(user.getUsername())) {
                                event.removeFromPendingList(i);
                                event.addToGuestList(user);
                                break;
                            }
                        }
                        eventRef.child(event.getName()).child("guestList").setValue(event.getGuestList());
                        eventRef.child(event.getName()).child("pendingList").setValue(event.getPendingList());
                        goToHomePage();

                        break;

                    }

                    //guestNotification.setText(getResources().getString(R.string.invitedNotification ) +guestCount);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERRORMESSAGE", "Failed to read value.", error.toException());
            }
        });
    }

    public void goToHomePage(){
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
        finish();
    }

    public void editEvent(){
        Intent classIntent = new Intent(this, CreateEventActivity.class);
        classIntent.putExtra("EDITEVENT",eventName);
        startActivity(classIntent);

    }
}
