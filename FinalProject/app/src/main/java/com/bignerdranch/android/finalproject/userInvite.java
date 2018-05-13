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

public class userInvite extends AppCompatActivity {
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference usersRef = database.getReference("users");
    DatabaseReference eventRef = database.getReference("event");

    private StorageReference mStorageRef;


    public List<User> userList= new ArrayList<>();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private UsersListAdapter mAdapter;

    Button addButton;

    Event event;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_invite);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        Bundle extras = getIntent().getExtras();

        event = (Event) getIntent().getSerializableExtra("newEvent");
        //The key argument here must match that used in the other activity
        // Log.i("+++ Event Title ++ ",event.getDescription());
        // Log.i("+++ Event Location ++ ",event.getLocation());




        //String key = eventRef.child("TEsting").push().getKey();
        // Log.i("--- TEsting Key --- ",key);
        //User joe = new User("","joe", "lastName", "username", "email", "password");
//        Event testEvent = new Event();

//        eventRef.child("TEsting").child("guestList").child("joe").setValue(joe);
        eventRef.child(event.getName()).setValue(event);


        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {


                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    User user = child.getValue(User.class);

                    userList.add(user);

                    Log.i("GetData", user.getUsername());


                }
                mAdapter = new UsersListAdapter(userList, userInvite.this);
                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
                recyclerView.setHasFixedSize(true);
                layoutManager = new LinearLayoutManager(userInvite.this);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(mAdapter);

                List<User> users = ((UsersListAdapter) mAdapter).getUserList();



                eventRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                addButton = (Button) findViewById(R.id.addbutton);
                addButton.setOnClickListener(new View.OnClickListener() {


                    @Override
                    public void onClick(View v) {
                        List<User> users = ((UsersListAdapter) mAdapter).getUserList();
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
                                 eventRef.child(event.getName()).child("pendingList").child(user.getUsername()).setValue(userInfo);
                            }

                        }

                        User gestUser = new User();
                        eventRef.child(event.getName()).child("gestList").setValue(gestUser);
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

