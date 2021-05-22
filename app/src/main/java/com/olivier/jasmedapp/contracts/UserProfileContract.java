package com.olivier.jasmedapp.contracts;

import android.net.Uri;

public interface UserProfileContract {

    interface View{
        void setUserView(String fullName, String email, int phoneNumber);
        void setUserPicture(Uri image);
    }

    interface Presenter{
        void getUserData();
        void getUserPicture();
    }

}
