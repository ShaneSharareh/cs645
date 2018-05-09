package com.bignerdranch.android.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;

public class CreateEventTest extends AppCompatActivity {
    private DatabaseReference eventRef;
    private FirebaseDatabase database;
    private EditText eventNameView;
    private EditText eventDescriptionView;
    private EditText eventDateView;
    private EditText eventLocationView;
    private EditText guest1View;
    private EditText guest2View;
    private Button add;
    private User user;
    private String username;
    private static final String FILENAME = "UserFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event_test);
        database = FirebaseDatabase.getInstance();
        eventRef = database.getReference("event");
        eventNameView = (EditText) findViewById(R.id.eventName);
        eventDescriptionView = (EditText) findViewById(R.id.eventDescription);
        eventDateView = (EditText) findViewById(R.id.date);
        eventLocationView = (EditText) findViewById(R.id.location);
        guest1View = (EditText) findViewById(R.id.guest1);
        guest2View = (EditText) findViewById(R.id.guest2);
        add = (Button) findViewById(R.id.add);
        loadData();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Event event = new Event(username, eventNameView.getText().toString(), eventDescriptionView.getText().toString(), eventDateView.getText().toString(), eventLocationView.getText().toString());

                event.addToPendingList(guest1View.getText().toString());
                event.addToPendingList(guest2View.getText().toString());
                DatabaseReference myRef = eventRef.child(event.getName());
                myRef.setValue(event);

                goToHomepage();

            }
        });
    }
    public void loadData(){
        // Capture the layout's TextView and set the string as its text
        SharedPreferences settings = getSharedPreferences(FILENAME, 0);
        Gson gson = new Gson();
        String savedUser = settings.getString("SavedUser", "");
        // if(!savedStudent.equals("")) {//if student info is saved
        user = gson.fromJson(savedUser, User.class);
        username = user.getUsername();


    }
    public void goToHomepage(){
        finish();
    }
}
