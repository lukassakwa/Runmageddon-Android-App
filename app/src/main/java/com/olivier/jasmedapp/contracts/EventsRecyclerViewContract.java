package com.olivier.jasmedapp.contracts;

import android.net.Uri;

public interface EventsRecyclerViewContract {
    interface View{
        void setTitle(String title);
        void setDate(String date);
        void setImage(Uri imageUri);
    }

    interface Presenter{
        void getEvent(int position, EventsRecyclerViewContract.View holder);
        void setUserEvent(int position);
    }
}
