package com.olivier.jasmedapp.model;

import android.provider.ContactsContract;
import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

@IgnoreExtraProperties
public class Event implements Serializable, Comparable<Event>{

    private String title;
    private String date;
    private String image;
    private String body;

    public Event() {
    }

    @Exclude
    public HashMap<String, Object> toMap(){
        HashMap<String, Object> result = new HashMap<>();
        result.put("title", title);
        result.put("date", date);
        result.put("image", image);
        result.put("body", body);

        return result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String data) {
        this.date = data;
    }

    @Override
    public int compareTo(Event o) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US);
        Date thisDate = null;
        Date eventDate = null;
        try {
            eventDate = inputFormat.parse(o.getDate());
            thisDate = inputFormat.parse(getDate());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return thisDate.compareTo(eventDate);
    }
}
