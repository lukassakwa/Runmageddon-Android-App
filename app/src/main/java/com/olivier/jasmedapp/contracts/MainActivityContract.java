package com.olivier.jasmedapp.contracts;

import com.olivier.jasmedapp.model.Event;

import java.util.ArrayList;

public interface MainActivityContract {

    interface View{
        void setMainFragment(ArrayList<Event> events);
    }

    interface Presenter{
        void signOut();
        void setUserEventsDatabase();
    }

}
