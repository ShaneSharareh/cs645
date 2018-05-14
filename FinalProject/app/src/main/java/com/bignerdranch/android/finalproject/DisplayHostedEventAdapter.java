package com.bignerdranch.android.finalproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

/*
 *
 * Created by Ali Minaei --  May 13,2014
 *
 * */

public class DisplayHostedEventAdapter extends RecyclerView.Adapter<DisplayHostedEventAdapter.ViewHolder> {

    private ArrayList<Event> eventList = new ArrayList<Event>();
    private Context context;
    private RecyclerviewInterface mCommunicator;

    public DisplayHostedEventAdapter(ArrayList<Event> eventList, Context context, RecyclerviewInterface communication){
        this.eventList = eventList;
        this.context = context;

        mCommunicator = communication;

    }

    @NonNull
    @Override
    public DisplayHostedEventAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_event_list, parent, false);
        return new DisplayHostedEventAdapter.ViewHolder(view,mCommunicator);
    }

    @Override
    public void onBindViewHolder(@NonNull DisplayHostedEventAdapter.ViewHolder holder, int position) {

        String eventdescription = "Event Name:\t"+eventList.get(position).getName()+"\n"+
                "Hosted by:\t"+eventList.get(position).getHostname()+"\n"+
                "Event Desc:\t"+eventList.get(position).getDescription()+"\n";

        holder.eventTitle.setText(eventdescription);

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        RecyclerviewInterface mComminication;
        String action;

        TextView eventTitle;
        Button delButton;
        Button editButton;

        public ViewHolder(View itemView, RecyclerviewInterface Communicator) {
            super(itemView);

            eventTitle = (TextView) itemView.findViewById(R.id.eventdesc);
            editButton = (Button) itemView.findViewById(R.id.editbtn);
            delButton = (Button) itemView.findViewById(R.id.delbtn);

            mComminication = Communicator;
            editButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    mComminication.DisplayHostEvent(getAdapterPosition(),eventList.get(getAdapterPosition()),"EDIT");
                }
            });

            delButton.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    mComminication.DisplayHostEvent(getAdapterPosition(),eventList.get(getAdapterPosition()),"DELETE");
                }
            });


        }


    }
}
