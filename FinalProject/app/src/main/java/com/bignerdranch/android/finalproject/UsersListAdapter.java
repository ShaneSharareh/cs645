package com.bignerdranch.android.finalproject;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder> {

    private List<User> userList;
    private SparseBooleanArray itemStateArray= new SparseBooleanArray();
    private Context context;
    private StorageReference storageReference;


    public UsersListAdapter(List<User> user, Context context){

        this.userList = user;
        this.context = context;


    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.userinvite_singleview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        String userImgUrl = "https://firebasestorage.googleapis.com/v0/b/cs646-f50aa.appspot.com/o/cs645_UserImg%2Fuser.png?alt=media&token=50d17bc7-5b35-47e6-9f90-da0cc83f3097";
        User user = userList.get(position);


        if(user.getImage().equals(""))
            Picasso.get().load(userImgUrl)
                    .into(holder.imageView);
        else
            Picasso.get().load(user.getImage())
                    .into(holder.imageView);


        holder.username.setText(user.getUsername());

        final int pos = position;

        holder.checkedBox.setChecked(userList.get(position).isSelected());

        holder.checkedBox.setTag(userList.get(position));


        holder.checkedBox.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                User user = (User) checkBox.getTag();

                user.setSelected(checkBox.isChecked());
                userList.get(pos).setSelected(checkBox.isChecked());

                Log.i("-- USER SELECTED ---",user.getUsername());

            }
        });
    }

    @Override
    public int getItemCount() {

        return userList.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView username;
        TextView firstName;
        TextView LastName;
        CheckBox checkedBox;
        ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            username = (TextView) itemView.findViewById(R.id.username);

            checkedBox= (CheckBox) itemView.findViewById(R.id.checkBox);
            imageView = (ImageView) itemView.findViewById((R.id.image_view));

        }


    }
    // method to access in activity after updating selection
    public List<User> getUserList() {
        return userList;
    }
}
