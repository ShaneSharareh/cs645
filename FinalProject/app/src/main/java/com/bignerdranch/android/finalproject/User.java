package com.bignerdranch.android.finalproject;

import java.io.Serializable;

/**
 * Created by shanesharareh on 5/6/18.
 *
 * Updated by Ali Minaei on 5/11/2018:
 * Added boolean selected variable and setSelected, isSelected methods.
 */

public class User implements Serializable {
    private String firstName;
    private String lastName;
    private String username;
    private String email;
    private String password;
    private String image;
    private boolean selected;
    public User(){

    }

    public User(String image, String firstName, String lastName, String username, String email, String password) {
        this.image = image;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.password = password;
        this.selected=false;


    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getImage() {
        return image;
    }

    public void setSelected(boolean selected){
        this.selected = selected;
    }
    public boolean isSelected(){
        return this.selected;
    }
}
