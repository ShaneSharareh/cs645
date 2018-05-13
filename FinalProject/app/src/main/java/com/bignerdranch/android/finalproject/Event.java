package com.bignerdranch.android.finalproject;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by shanesharareh on 5/6/18.
 *
 * Updated by Ali Minaei on 5/11/2018.
 * Added Serializable to the class Event
 * Changed pendingList, guestList & removeFromGuestList data type from String to User, to add user information to the
 * list instead of just username. -AM
 */

public class Event implements Serializable {
    private String hostname;
    private String name;
    private String description;
    private String date;
    private String location;
    private ArrayList<User> pendingList;
    private ArrayList<User> guestList;
    public Event(){
        guestList = new ArrayList<User>();
        pendingList = new ArrayList<User>();
    }

    public Event(String hostname,  String name, String description, String date, String location) {
        guestList = new ArrayList<User>();
        pendingList = new ArrayList<User>();
        this.hostname = hostname;
        this.name = name;
        this.description = description;
        this.date = date;
        this.location = location;

    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void addToPendingList(User user) {pendingList.add(user);}

    public void addToGuestList(User user){
        guestList.add(user);
    }

    public void removeFromGuestList(User user){ guestList.remove(user);}

    public void removeFromPendingList(User user){ pendingList.remove(user);}


    public String getHostname() {
        return hostname;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public String getLocation() {
        return location;
    }

    public ArrayList<User> getGuestList() {
        return guestList;
    }

    public ArrayList<User> getPendingList() { return pendingList; }
}
