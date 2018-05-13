package com.bignerdranch.android.finalproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class SummaryUsersListAdapter extends RecyclerView.Adapter<SummaryUsersListAdapter.ViewHolder> {

    List<User> userList;
    private Context context;



    public SummaryUsersListAdapter(List<User> user, Context context){
        this.userList = user;
        this.context = context;


    }
    @NonNull
    @Override
    public SummaryUsersListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new SummaryUsersListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SummaryUsersListAdapter.ViewHolder holder, int position) {

        User user = userList.get(position);

        Log.i("-- Processing -- ",user.getUsername());
        Log.i("-- Image Url --",user.getImage());


        Picasso.get().load(user.getImage())
                .into(holder.imageView);

        holder.username.setText(user.getUsername());



    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView username;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.username);

            imageView = (ImageView) itemView.findViewById((R.id.image_view));

        }
    }
}
