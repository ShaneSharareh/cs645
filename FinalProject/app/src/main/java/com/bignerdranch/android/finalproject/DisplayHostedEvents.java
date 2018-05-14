package com.bignerdranch.android.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class DisplayHostedEvents extends AppCompatActivity {
    private static final String FILENAME = "UserFile";

    private String username;
    private DatabaseReference eventRef;
    private FirebaseDatabase database;
    private User user;
    private ListView hostedList;

    private RecyclerView.LayoutManager layoutManager;
    private DisplayHostedEventAdapter adapter;
    public ArrayList<Event> hostedEventListObj = new ArrayList<Event>();
    public Event recyclerviewEvent;
    public String recyclerviewAcrion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_hosted_events);
        //hostedList = (ListView) findViewById(R.id.hostedEventList);
        database = FirebaseDatabase.getInstance();
        eventRef = database.getReference("event");
        loadData();
        loadHosted();
//        hostedList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            public void onItemClick(AdapterView<?> myAdapter, View myView, int position, long myLong) {
//                /*try {
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }*/
//                //JsonObject selectedFromList =(JsonObject) (classList.getItemAtPosition(myItemInt);
//                Log.d("item", "" +hostedList.getItemAtPosition(position));
//                viewEvent(hostedList.getItemAtPosition(position).toString());
//            }
//        });
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
    public void loadHosted(){
        eventRef.addListenerForSingleValueEvent(new ValueEventListener() {

            ArrayList<String> eventList = new ArrayList<String>();

            @Override
            public void onDataChange(DataSnapshot dataSnapshot){
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                // Log.e("Count " ,"Count:"+dataSnapshot.getChildrenCount());
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Event event = child.getValue(Event.class);

                    ArrayList<User> guestList = event.getGuestList();
                    if (username.equals(event.getHostname())) {
                        eventList.add(event.getName());
                        hostedEventListObj.add(event);
                    }
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
//        ArrayAdapter eventAdapter = new ArrayAdapter<String>(this,
//                R.layout.single_event_list, eventList);

        adapter = new DisplayHostedEventAdapter(hostedEventListObj, DisplayHostedEvents.this, recyclerviewInterface);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.hostedEventList_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(DisplayHostedEvents.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        //hostedList.setAdapter(eventAdapter);

    }

    public void viewEvent(String eventName){
        Intent intent = new Intent(this, ViewEventTest.class);
        intent.putExtra("HOSTEVENTNAME",eventName);
        startActivity(intent);

    }
    RecyclerviewInterface recyclerviewInterface = new RecyclerviewInterface() {
        @Override
        public void DisplayHostEvent(int adapterPosition, Event event, String action) {
            recyclerviewEvent = event;
            recyclerviewAcrion = action;
            // Log.i("-- recyclerviewInterface -- ", recyclerviewEvent.getHostname()+" --> is Selected ");
            // Log.i("-- recyclerviewInterface -- ", " Action is : "+recyclerviewAcrion+" --> is Selected ");
        }
    };
}
