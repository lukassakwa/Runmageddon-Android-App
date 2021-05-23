package com.olivier.jasmedapp.presenter;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.olivier.jasmedapp.contracts.MainActivityContract;
import com.olivier.jasmedapp.model.Event;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivityPresenter extends BasePresenter<MainActivityContract.View> implements MainActivityContract.Presenter{

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;

    private ArrayList<Event> userEvents;

    public MainActivityPresenter() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        userEvents = new ArrayList<>();
        initApp();
    }

    @Override
    public void setUserEventsDatabase() {
        DatabaseReference userEventsRef = databaseReference.child("userEvents").child(user.getUid());
        userEventsRef.removeValue();

        userEventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (int i = 0; i < userEvents.size(); i++){
                    String index = i+"";
                    userEventsRef.child(index).setValue(userEvents.get(i));
                }
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    //This is async so i need to add fragment to function which task was only add elemnts to userEvents
    //Get Main Fragment Init
    //init the userEvents Database
    private void initApp() {
        DatabaseReference userEventsRef = databaseReference.child("userEvents").child(user.getUid());
        userEventsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()){
                    userEvents.add(ds.getValue(Event.class));
                }
                view.setMainFragment(userEvents);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

    @Override
    public void signOut() {
        auth.signOut();
    }

    public ArrayList<Event> getUserEvents() {
        return userEvents;
    }

    public void setUserEvents(ArrayList<Event> userEvents) {
        this.userEvents = userEvents;
    }
}
