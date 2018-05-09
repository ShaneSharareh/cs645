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

public class Login extends AppCompatActivity {
private EditText username;
private EditText password;
private Button signup;
private Button login;
private TextView error;
private DatabaseReference usersRef;
private FirebaseDatabase database;

private static final String FILENAME = "UserFile";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        username = (EditText) findViewById(R.id.usernameLogin);
        password = (EditText) findViewById(R.id.passwordLogin);
        signup = (Button) findViewById(R.id.enterSignup);
        login = (Button) findViewById(R.id.loginButton);
        error = (TextView) findViewById(R.id.errorLogin);
        database = FirebaseDatabase.getInstance();
        usersRef = database.getReference("users");
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               if(validate()) {
                   authenticate(username.getText().toString(), password.getText().toString());
               }

            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToSignup();
            }
        });
    }

    public void goToSignup(){
        Intent intent = new Intent(this, Signup.class);
        startActivity(intent);

    }

    public void authenticate(final String newUsername, final String newPassword){
        error.setText("");
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
               // Log.e("Count " ,"Count:"+dataSnapshot.getChildrenCount());
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    User user = child.getValue(User.class);
                    if(newUsername.equals(user.getUsername()) && newPassword.equals(user.getPassword())){
                        saveUserLogin(user);
                        goToHomepage();
                    }
                    else{
                        error.setText(getResources().getString(R.string.invalidlogin));
                    }
                    Log.e("GetData", user.getUsername());
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("ERRORMESSAGE", "Failed to read value.", error.toException());
            }
        });
    }
    public void saveUserLogin(User user){
        SharedPreferences settings = getSharedPreferences(FILENAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        Gson gson = new Gson();
        String savedStudent = gson.toJson(user);
        editor.putString("SavedUser", savedStudent);
        editor.commit();

    }
    public void goToHomepage(){
        Intent intent = new Intent(this, Homepage.class);
        //intent.putExtra("USER",user);
        startActivity(intent);
    }

    public Boolean validate(){
        error.setText("");
        if(username.getText().toString().equals("")){
            error.setText(getResources().getString(R.string.blankUsername));
            return false;
        }

        if(password.getText().toString().equals("")){
            error.setText(getResources().getString(R.string.blankPassword));
            return false;
        }
        return true;
    }
}
