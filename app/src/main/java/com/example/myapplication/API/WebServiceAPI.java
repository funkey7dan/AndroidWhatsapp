package com.example.myapplication.API;

import com.example.myapplication.entities.Contact;
import com.example.myapplication.entities.LoginRequest;
import com.example.myapplication.entities.Message;
import com.example.myapplication.entities.TransferRequest;
import com.example.myapplication.entities.addContactRequest;
import com.example.myapplication.entities.inviteContact;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;


public interface WebServiceAPI {
    @GET("contacts")
    Call<List<Contact>> getContacts();

    @GET("contacts/{id}")
    Call<Contact> getContact(@Path("id") String id);

    @POST("login")
    Call<Void> login(@Body LoginRequest request);

    @POST("register")
    Call<Void> register(@Body LoginRequest request);

    @POST("contacts")
    Call<Void> addContact(@Body addContactRequest request);

    @POST("invitations")
    Call<Void> inviteContact(@Body inviteContact request);

    @POST("transfer")
    Call<Void> transferMessage(@Body TransferRequest request);

    @GET("contacts/{id}/messages")
    Call<List<Message>> getMessagesWith(@Path("id") String id);

    @POST("contacts/{id}/messages")
    Call<Void> sendMessage(@Path("id") String id, @Body MessageRequest request);

    @POST("login/token")
    Call<Void> sendToken(@Body MessageRequest request);

    @GET("Logout")
    Call<Void> logout();
}
