package com.example.myapplication.API;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.ContactWIthMessages;
import com.example.myapplication.entities.Message;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactsViewModel extends AndroidViewModel {
    private final ContactsRepository repository;
    private final MutableLiveData<List<Contact>> contacts;
    private final Map<String, ContactWIthMessages> contactWIthMessagesMap;
    private final Map<String, MutableLiveData<List<Message>>> messagesMap = new HashMap<>();

    public ContactsViewModel(Application application) {
        super(application);
        repository = new ContactsRepository(application.getApplicationContext());
        contacts = repository.getAllContacts();
        contactWIthMessagesMap = repository.getAllMessages();
        updateMessageMap();
    }

    private void updateMessageMap() {
        // create a map of names to mutable message lists
        for (String s : contactWIthMessagesMap.keySet()
        ) {
            if (messagesMap.containsKey(s)) {
                messagesMap.get(s).setValue(contactWIthMessagesMap.get(s).messages);
            } else {
                messagesMap.put(s, new MutableLiveData<>(contactWIthMessagesMap.get(s).messages));
            }
        }
    }

    public MutableLiveData<List<Contact>> getContactsList() {
        return contacts;
    }

    public MutableLiveData<List<Message>> getMessagesWith(String name) {
        updateMessageMap();
        if (contactWIthMessagesMap.get(name) == null) {
            // if there is no CotactWithMessages for this name, create a new empty one
            contactWIthMessagesMap.put(name, (new ContactWIthMessages(getContactByName(name))));
        }
        return messagesMap.get(name);
    }

    public Contact getContactByName(String name) {
        if (contacts.getValue() != null) {
            for (Contact contact : contacts.getValue()) {
                if (contact.getId().equals(name)) {
                    return contact;
                }
            }
        }
        return null;
    }


    public void add(Contact contact) {
        repository.addContact(contact);
    }

    public void addMessage(Message message) {
        repository.addMessage(message);
        updateMessageMap();
    }

}