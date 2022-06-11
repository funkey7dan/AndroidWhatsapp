package com.example.myapplication.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.utils.DataSingleton;
import com.example.myapplication.R;
import com.example.myapplication.activities.MessagesActivity;
import com.example.myapplication.entities.Contact;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;


public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.MyViewHolder> {
    DataSingleton data = DataSingleton.getInstance();

    private List<Contact> contacts;
    private final LayoutInflater inflater;
    Context context;

    public ContactsAdapter(Context ct) {
        context = ct;
        inflater = LayoutInflater.from(ct);
    }


    public void setData(List<Contact> newData){
        contacts = newData;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.contact_item, parent, false);
        return new MyViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.t1.setText(this.contacts.get(position).getName());
        holder.t2.setText(this.contacts.get(position).getLast());
        LocalDateTime dateTime = LocalDateTime.parse(this.contacts.get(position).getLastdate());
        String formatted = dateTime.format(DateTimeFormatter.ISO_LOCAL_DATE) + " " + dateTime.format(DateTimeFormatter.ISO_LOCAL_TIME);
        if (!Objects.equals(this.contacts.get(position).getLast(), null)) {
            holder.t3.setText(formatted);
        } else {
            holder.t3.setText("");
        }


        holder.cl.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                data.setActiveContact(contacts.get(holder.getAdapterPosition()).getId());
                Intent i = new Intent(context, MessagesActivity.class);
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
