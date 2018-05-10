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
import com.google.gson.JsonObject;

public class Signup extends AppCompatActivity {
private EditText fname;
private EditText lname;
private EditText username;
private EditText email;
private EditText password;
private Button signup;
private TextView error;
private User user;

private DatabaseReference usersRef;
private FirebaseDatabase database;
private static final String FILENAME = "UserFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("users");
        fname = (EditText) findViewById(R.id.firstname);
        lname = (EditText) findViewById(R.id.lastname);
        username = (EditText) findViewById(R.id.usernameSignup);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.passwordSignup);
        signup = (Button) findViewById(R.id.signupButton);
        error = (TextView) findViewById(R.id.errorSignup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //DatabaseReference myRef = database.getReference("message");
                if(validate()) {
                    //DatabaseReference myRef = usersRef.child(username.getText().toString());
                    //user = new User("", fname.getText().toString(), lname.getText().toString(), username.getText().toString(), email.getText().toString(), password.getText().toString());
                    checkUsernameAndEmail();
                    //saveUserInfoToPhone(user);
                    //myRef.setValue(user);
                    //goToHomepage(user);
                }
                // Map<String, User> users = new HashMap<>();
                //users.put(username.getText().toString(), );
                // myRef.setValue(users);
                //startActivity(intent);
            }
        });
    }
        public void goToHomepage(User user){
        Intent intent = new Intent(this, Homepage.class);
        //intent.putExtra("USER",user);
        startActivity(intent);
    }

    public void saveUserInfoToPhone(User user){
        SharedPreferences settings = getSharedPreferences(FILENAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        Gson gson = new Gson();
        String savedStudent = gson.toJson(user);
        editor.putString("SavedUser", savedStudent);
        editor.commit();
    }

    public void checkUsernameAndEmail(){
        error.setText("");
        usersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                error.setText("");
                Boolean foundUsername = false;
                Boolean foundEmail = false;
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                // Log.e("Count " ,"Count:"+dataSnapshot.getChildrenCount());
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    User userTemp = child.getValue(User.class);
                    if(username.getText().toString().equals(userTemp.getUsername())){
                       foundUsername = true;
                    }
                    if(email.getText().toString().equals(userTemp.getEmail())){
                        foundEmail = true;
                    }

                    Log.e("GetData", userTemp.getUsername());
                }
                if(foundUsername ==true){
                    error.setText(getResources().getString(R.string.usernameexist));

                }
                else if(foundEmail ==true){
                    error.setText(getResources().getString(R.string.emailexist));

                }
                else{
                        DatabaseReference myRef = usersRef.child(username.getText().toString());
                        user = new User("", fname.getText().toString(), lname.getText().toString(), username.getText().toString(), email.getText().toString(), password.getText().toString());
                        saveUserInfoToPhone(user);
                        myRef.setValue(user);
                        goToHomepage(user);

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERRORMESSAGE", "Failed to read value.", error.toException());
            }
        });
    }

    public boolean validate(){
        error.setText("");
        if(fname.getText().toString().equals("")){
            error.setText(getResources().getString(R.string.blankFirstName));
            return false;
        }
        if(lname.getText().toString().equals("")){
            error.setText(getResources().getString(R.string.blankLastName));
            return false;
        }
        if(username.getText().toString().equals("")){
            error.setText(getResources().getString(R.string.blankUsername));
            return false;
        }
        if(email.getText().toString().equals("")) {
            error.setText(getResources().getString(R.string.blankEmail));
            return false;
        }
        if(password.getText().toString().equals("")){
            error.setText(getResources().getString(R.string.blankPassword));
            return false;
        }
        return true;
    }
}
