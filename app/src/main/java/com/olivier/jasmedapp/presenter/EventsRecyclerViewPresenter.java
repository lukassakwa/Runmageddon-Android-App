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

import java.util.ArrayList;

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

                Uri imageUri = Uri.parse(event.getImage());

                holder.setTitle(event.getTitle());
                holder.setDate(event.getDate());
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
            if(userEvent.getTitle().equals(event.getTitle()))
                ifExist = true;
        }

        if(!ifExist)
            userEvents.add(event);
    }

    public ArrayList<Event> getUserEvents() {
        return userEvents;
    }

    public void setUserEvents(ArrayList<Event> userEvents) {
        this.userEvents = userEvents;
    }
}
