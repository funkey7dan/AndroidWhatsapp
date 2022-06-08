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
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.activities.ChatsActivity;
import com.example.myapplication.entities.Contact;

import java.util.List;



public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {
    DataSingleton data = DataSingleton.getInstance();

    private List<Contact> contacts;
    private final LayoutInflater inflater;
    Context context;

    public MyAdapter(Context ct) {
        context = ct;
        inflater = LayoutInflater.from(ct);
    }


    public void setData(List<Contact> newData){
        // TODO: check if we need it
//        if (contacts!=null){
//            contacts.clear();
//            contacts.addAll(newData);
//            notifyDataSetChanged();
//        }
//        else{
//            contacts = newData;
//        }
        contacts = newData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
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
                data.setActiveContact(contacts.get(holder.getAdapterPosition()).getId());
                Intent i = new Intent(context, ChatsActivity.class);
                i.putExtra("nickname", contacts.get(holder.getAdapterPosition()).getName());
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
