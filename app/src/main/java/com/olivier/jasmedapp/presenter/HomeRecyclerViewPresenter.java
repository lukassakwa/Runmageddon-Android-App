package com.olivier.jasmedapp.presenter;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.olivier.jasmedapp.contracts.HomeRecyclerViewContract;
import com.olivier.jasmedapp.model.Event;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

import static android.content.Context.MODE_PRIVATE;

public class HomeRecyclerViewPresenter implements HomeRecyclerViewContract.Presenter {

    private ArrayList<Event> userEvents;

    public HomeRecyclerViewPresenter() {
        userEvents = new ArrayList<>();
    }

    @Override
    public void getUserEvent(HomeRecyclerViewContract.View view, int position) {
        Event event = userEvents.get(position);

        //get date from json string date
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

        view.setDate(dateString);
        view.setTitle(event.getTitle());

        Uri imageUri = Uri.parse(event.getImage());
        view.setImage(imageUri);
    }

    @Override
    public void setUserEventsArray(HomeRecyclerViewContract.View view, int position) {
        userEvents.remove(position);
        view.setItems(position);
    }

    public ArrayList<Event> getUserEvents() {
        return userEvents;
    }

    public void setUserEvents(ArrayList<Event> userEvents) {
        this.userEvents = userEvents;
    }

    public int getSize(){
        return userEvents.size();
    }
}
