package com.bignerdranch.android.finalproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

/*
*
* Created by Ali Minaei --  May 13,2014
*
* */

public class EventSummary extends AppCompatActivity {

    private Event event;

    private EditText evenSummary;

    private Button doneBtn;

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
        doneBtn = (Button) findViewById(R.id.doneBtn);

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EventSummary.this, Homepage.class);
                startActivity(intent);
                finish();
            }
        });


        String evenSummaryStr = "Event: "+event.getName()+"\n\n" +
                "Hosted by: "+event.getHostname()+"\n\n"+
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
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, Homepage.class);
        startActivity(intent);
        finish();
    }

}
