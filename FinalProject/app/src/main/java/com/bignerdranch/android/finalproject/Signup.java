package com.bignerdranch.android.finalproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
                    DatabaseReference myRef = usersRef.child(username.getText().toString());
                    User user = new User("", fname.getText().toString(), lname.getText().toString(), username.getText().toString(), email.getText().toString(), password.getText().toString());
                    saveUserInfoToPhone(user);
                    myRef.setValue(user);
                    goToHomepage(user);
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
