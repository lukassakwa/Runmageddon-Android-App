package com.olivier.jasmedapp.contracts;

import com.facebook.AccessToken;

public interface LoginActivityContract {

    interface View{
        void loginFail();
        void updateUI();
    }

    interface Presenter{
        void isUserLoggedIn();
        void handleFacebookAccessToken(AccessToken accessToken);
        void signIn();
    }

}
