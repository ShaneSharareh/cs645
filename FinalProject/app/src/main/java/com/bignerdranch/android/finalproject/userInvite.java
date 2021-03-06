package com.bignerdranch.android.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

/*
 *
 * Created by Ali Minaei --  May 13,2014
 *
 * */

public class userInvite extends AppCompatActivity {
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usersRef = database.getReference("users");
    DatabaseReference eventRef = database.getReference("event");

    private StorageReference mStorageRef;


    public List<User> userList= new ArrayList<>();
    public List<User> userListFromAdapter= new ArrayList<>();
    public List<User> users = new ArrayList<>();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private UsersListAdapter mAdapter;
    public boolean EditMode = false;

    Button addButton;

    Event event,editEvent;
    List<User> eventAttendees;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_invite);

        addButton = (Button) findViewById(R.id.addbutton);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        Intent receivingIntent = getIntent();

        //Bundle extras = getIntent().getExtras();

        if(receivingIntent.hasExtra("newEvent")){
            event = (Event) getIntent().getSerializableExtra("newEvent");
            EditMode = false;
            addButton.setText("Add");
        }
        else{
            event = (Event) getIntent().getSerializableExtra("editEvent");

            eventAttendees = event.getPendingList();
            EditMode = true;
            addButton.setText("Update");
        }


        //The key argument here must match that used in the other activity
        // Log.i("+++ Event Title ++ ",event.getDescription());
        // Log.i("+++ Event Location ++ ",event.getLocation());


        //String key = eventRef.child("TEsting").push().getKey();
        // Log.i("--- TEsting Key --- ",key);
        //User joe = new User("","joe", "lastName", "username", "email", "password");




        eventRef.child(event.getName()).setValue(event);
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                userList.clear();


                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    User user = child.getValue(User.class);

                    if(EditMode){

                        for (int i=0;i<eventAttendees.size();i++){
                            if(eventAttendees.get(i).getUsername().equals(user.getUsername()))
                                user.setSelected(true);
                        }

                    }


                    userList.add(user);

                    Log.i("GetData", user.getUsername());


                }
                mAdapter = new UsersListAdapter(userList, userInvite.this);
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                recyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(userInvite.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(mAdapter);

//                List<User> users = ((UsersListAdapter) mAdapter).getUserList();
//                userListFromAdapter.clear();
//                userListFromAdapter = ((UsersListAdapter) mAdapter).getUserList();
//                Log.i("-- userListFromAdapter -- ", userListFromAdapter.toString());


                eventRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

                addButton.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {
                        users.clear();
                        users = ((UsersListAdapter) mAdapter).getUserList();
                        event.removeAllFromPendingList();

                        for (int i = 0; i < users.size(); i++) {
                            User userInfo = new User();
                            User user = users.get(i);
                            if (user.isSelected() == true) {

                                userInfo.setUsername(user.getUsername());
                                userInfo.setFirstName(user.getFirstName());
                                userInfo.setLastName(user.getLastName());
                                userInfo.setEmail(user.getEmail());
                                userInfo.setImage(user.getImage());
                                userInfo.setSelected(true);

                                event.addToPendingList(userInfo);

                                Log.i("---- USER: ----", user.getUsername() + " is selected!!! ");
//                                eventRef.child(event.getName()).child("pendingList").child(user.getUsername()).setValue(userInfo);
                                eventRef.child(event.getName()).setValue(event);

                            }

                        }

                        User guestUser = new User();
//                        eventRef.child(event.getName()).child("guestList").setValue(guestUser);
//                        eventRef.child(event.getName()).setValue(event);

                        Intent intent = new Intent(userInvite.this, EventSummary.class);
                        intent.putExtra("event", event);
                        startActivity(intent);


                    }
                });


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }


}

