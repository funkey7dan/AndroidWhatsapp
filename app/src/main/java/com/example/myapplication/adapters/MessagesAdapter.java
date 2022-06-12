package com.example.myapplication.adapters;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.R;
import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.Message;
import com.example.myapplication.utils.DataSingleton;

public class MessagesAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final Context context;
    List<Message> list;
    public static final int MESSAGE_TYPE_IN = 1;
    public static final int MESSAGE_TYPE_OUT = 2;
    private DataSingleton data = DataSingleton.getInstance();

    public MessagesAdapter(Context context) { // you can pass other parameters in constructor
        this.context = context;
    }

    public void setData(List<Message> newData){
        list = newData;
        notifyDataSetChanged();
    }

    private class MessageInViewHolder extends RecyclerView.ViewHolder {

        TextView messageTV,dateTV;
        MessageInViewHolder(final View itemView) {
            super(itemView);
            messageTV = itemView.findViewById(R.id.message_text);
            dateTV = itemView.findViewById(R.id.date_text);
        }
        @RequiresApi(api = Build.VERSION_CODES.O)
        void bind(int position) {
            Message messageModel = list.get(position);
            messageTV.setText(messageModel.getContent());
            dateTV.setText(messageModel.getCreated());
            // TODO: convert date to more convenient format
//            LocalDate parsedDate = LocalDate.parse(messageModel.getCreated(), DateTimeFormatter.ofPattern("HH:mm"));
//            String formatted = parsedDate.format(DateTimeFormatter.ofPattern("HH:mm"));
//            dateTV.setText(formatted);
        }
    }

    private class MessageOutViewHolder extends RecyclerView.ViewHolder {

        TextView messageTV,dateTV;
        MessageOutViewHolder(final View itemView) {
            super(itemView);
            messageTV = itemView.findViewById(R.id.message_text);
            dateTV = itemView.findViewById(R.id.date_text);
        }
        @RequiresApi(api = Build.VERSION_CODES.O)
        void bind(int position) {
            Message messageModel = list.get(position);
            messageTV.setText(messageModel.getContent());
            // TODO: change to formatted time
            dateTV.setText(messageModel.getCreated());


        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == MESSAGE_TYPE_IN) {
            return new MessageInViewHolder(LayoutInflater.from(context).inflate(R.layout.item_text_in, parent, false));
        }
        return new MessageOutViewHolder(LayoutInflater.from(context).inflate(R.layout.item_text_out, parent, false));
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (Objects.equals(list.get(position).getSender(), data.getUser())) {
            ((MessageOutViewHolder) holder).bind(position);
        } else {
            ((MessageInViewHolder) holder).bind(position);

        }
    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return Objects.equals(list.get(position).getSender(), data.getUser()) ? MESSAGE_TYPE_OUT:MESSAGE_TYPE_IN;
    }
}