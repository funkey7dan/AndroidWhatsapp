package com.example.myapplication.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.myapplication.R;

public class ContactsDialog extends AppCompatDialogFragment {

    private EditText etUsername;
    private EditText etNickname;
    private EditText etServer;
    private DialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog_contact, null);

        etUsername = view.findViewById(R.id.edit_contact_name);
        etNickname = view.findViewById(R.id.edit_contact_nickname);
        etServer = view.findViewById(R.id.edit_contact_server);

        builder.setView(view)
                .setTitle("Add Contact")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String server = etServer.getText().toString();
                        String uname = etUsername.getText().toString();
                        String nickname = etNickname.getText().toString();
                        listener.apply(uname, nickname, server);
                    }

                });

//        etUsername = view.findViewById(R.id.edit_contact_name);
//        etNickname = view.findViewById(R.id.edit_contact_nickname);
//        etServer = view.findViewById(R.id.edit_contact_server);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {

        super.onAttach(context);
        try {
            listener = (DialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"must implement");
        }
    }

    public interface DialogListener {
        void apply(String uname, String nickname, String server);
    }



}
