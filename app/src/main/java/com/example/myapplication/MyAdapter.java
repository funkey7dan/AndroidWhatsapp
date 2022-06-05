package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.MutableLiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.entities.Contact;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    List<Contact> contacts;
    Context context;

    public MyAdapter(Context ct, List<Contact> contacts) {
        this.contacts = contacts;
        this.context = ct;
    }


    public void setData(List<Contact> newData){
        if (contacts!=null){
            contacts.clear();
            contacts.addAll(newData);
            notifyDataSetChanged();
        }
        else{
            contacts = newData;
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.contact_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.t1.setText(this.contacts.get(position).getName());
        holder.t2.setText(this.contacts.get(position).getLast());
        holder.t3.setText(this.contacts.get(position).getLastdate());


        holder.cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, ChatsActivity.class);
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (contacts == null) return 0;
        return contacts.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView t1, t2, t3;
        ConstraintLayout cl;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            t1 = itemView.findViewById(R.id.contactName);
            t2 = itemView.findViewById(R.id.lastMessage);
            t3 = itemView.findViewById(R.id.lastDate);
            cl = itemView.findViewById(R.id.main_layout);
            ImageView iv = itemView.findViewById(R.id.contactImage);
            iv.setClipToOutline(true);

        }
    }
}
