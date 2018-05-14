package com.bignerdranch.android.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DisplayInvitedNotifications extends AppCompatActivity {
    private static final String FILENAME = "UserFile";

    private String username;
    private DatabaseReference eventRef;
    private FirebaseDatabase database;
    private User user;
    private ListView invitedList;
    Button cancelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_invited_notifications);
        invitedList = (ListView) findViewById(R.id.invitedEventList);
        cancelButton = (Button) findViewById(R.id.cancel);
        database = FirebaseDatabase.getInstance();
        eventRef = database.getReference("event");
        loadData();
        loadInvited();
        invitedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView, int position, long myLong) {
                /*try {
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/
                //JsonObject selectedFromList =(JsonObject) (classList.getItemAtPosition(myItemInt);
            Log.d("item", "" +invitedList.getItemAtPosition(position));
            viewEvent(invitedList.getItemAtPosition(position).toString());
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
    public void loadInvited(){
        eventRef.addValueEventListener(new ValueEventListener() {

            ArrayList<String> eventList = new ArrayList<String>();
            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
            // This method is called once with the initial value and again
            // whenever data at this location is updated.
            // Log.e("Count " ,"Count:"+dataSnapshot.getChildrenCount());
            for (DataSnapshot child : dataSnapshot.getChildren()) {
                Event event = child.getValue(Event.class);
                ArrayList<User> pendingList = event.getPendingList();
                for (int i = 0; i < pendingList.size(); i++) {
                    if (username.equals(pendingList.get(i).getUsername())) {
                       eventList.add(event.getName());
                    }
                }
                //guestNotification.setText(getResources().getString(R.string.invitedNotification ) +guestCount);
            }
            setListView(eventList);
        }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERRORMESSAGE", "Failed to read value.", error.toException());
            }
        });
    }
    public void setListView(ArrayList<String> eventList){
        ArrayAdapter eventAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, eventList);

        invitedList.setAdapter(eventAdapter);

    }

    public void viewEvent(String eventName){
        Intent intent = new Intent(this, ViewEventTest.class);
        intent.putExtra("EVENTNAME",eventName);
        startActivity(intent);

    }
}
