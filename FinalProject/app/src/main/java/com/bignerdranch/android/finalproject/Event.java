package com.bignerdranch.android.finalproject;

import java.util.ArrayList;

/**
 * Created by shanesharareh on 5/6/18.
 */

public class Event {
    private String hostname;
    private String name;
    private String description;
    private String date;
    private String location;
    private ArrayList<String> pendingList;
    private ArrayList<String> guestList;
    public Event(){
        guestList = new ArrayList<String>();
        pendingList = new ArrayList<String>();
    }

    public Event(String hostname,  String name, String description, String date, String location) {
        guestList = new ArrayList<String>();
        pendingList = new ArrayList<String>();
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

    public void addToPendingList(String username) {pendingList.add(username);}

    public void addToGuestList(String username){
        guestList.add(username);
    }

    public void removeFromGuestList(String username){ guestList.remove(username);}

    public void removeFromPendingList(String username){ pendingList.remove(username);}


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

    public ArrayList<String> getGuestList() {
        return guestList;
    }

    public ArrayList<String> getPendingList() { return pendingList; }
}
