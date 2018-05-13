package com.bignerdranch.android.finalproject;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;

public class EventSummary extends AppCompatActivity {

    Event event;

    EditText evenSummary;


    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private SummaryUsersListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_summary);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        Bundle extras = getIntent().getExtras();
        event = (Event) getIntent().getSerializableExtra("event");

        evenSummary = (EditText) findViewById(R.id.eventsummary);


        String evenSummaryStr = "Event: "+event.getName()+"\n\n" +
                "Hosted by: "+"\n\n"+
                "Description: "+event.getDescription()+"\n\n" +
                "Time: "+event.getDate() + "\n\n" +
                "Location: "+event.getLocation();
        evenSummary.setText(evenSummaryStr);
        evenSummary.setEnabled(false);


        mAdapter = new SummaryUsersListAdapter(event.getPendingList(), EventSummary.this);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.summary_list_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(EventSummary.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

    }

}
