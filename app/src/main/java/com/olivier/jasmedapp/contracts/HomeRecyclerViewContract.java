package com.olivier.jasmedapp.contracts;

import android.net.Uri;
import android.os.Bundle;
import com.olivier.jasmedapp.model.Event;

import java.util.ArrayList;

public interface HomeRecyclerViewContract {
    interface View{
        void setTitle(String title);
        void setDate(String date);
        void setImage(Uri imageUri);
        void setItems(int position);
    }

    interface Presenter{
        void getUserEvent(HomeRecyclerViewContract.View view, int position);
        void setUserEventsArray(HomeRecyclerViewContract.View view, int position);
    }
}
