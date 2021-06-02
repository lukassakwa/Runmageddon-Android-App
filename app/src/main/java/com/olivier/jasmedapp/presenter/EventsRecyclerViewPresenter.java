package com.olivier.jasmedapp.presenter;

import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;
import androidx.annotation.NonNull;
import com.facebook.share.Share;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.olivier.jasmedapp.contracts.EventsRecyclerViewContract;
import com.olivier.jasmedapp.model.Event;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class EventsRecyclerViewPresenter implements EventsRecyclerViewContract.Presenter {

    private final int SIZE = 3;

    private DatabaseReference databaseReference;
    private FirebaseUser user;

    private ArrayList<Event> events;
    private ArrayList<Event> userEvents;

    public EventsRecyclerViewPresenter() {
        init();
    }

    private void init(){
        databaseReference = FirebaseDatabase.getInstance().getReference();
        user = FirebaseAuth.getInstance().getCurrentUser();
        events = new ArrayList<>();
        userEvents = new ArrayList<>();
    }

    @Override
    public void getEvent(int position, EventsRecyclerViewContract.View holder) {
        String pos = position + "";
        DatabaseReference eventReference = databaseReference.child("allEvents").child(pos);

        eventReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Event event = snapshot.getValue(Event.class);
                events.add(event);

                //get data from json
                SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
                SimpleDateFormat outputDate = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                Date outDate;
                String dateString = "";
                try {
                    outDate = date.parse(event.getDate());
                    dateString = outputDate.format(outDate);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Uri imageUri = Uri.parse(event.getImage());

                holder.setTitle(event.getTitle());
                holder.setDate(dateString);
                holder.setImage(imageUri);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void setUserEvent(int position) {
        //TODO:: Make it better
        boolean ifExist = false;
        Event event = events.get(position);

        for(Event userEvent : userEvents){
            if(userEvent.getDate().equals(event.getDate()))
                ifExist = true;
        }

        if(!ifExist) {
            userEvents.add(event);
            Collections.sort(userEvents);
        }
    }

    public ArrayList<Event> getUserEvents() {
        return userEvents;
    }

    public void setUserEvents(ArrayList<Event> userEvents) {
        this.userEvents = userEvents;
    }
}
