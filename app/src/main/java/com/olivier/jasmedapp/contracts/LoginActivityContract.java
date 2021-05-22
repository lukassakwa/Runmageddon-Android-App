package com.olivier.jasmedapp.contracts;

public interface LoginActivityContract {

    interface View{
        void loginFail();
        void updateUI();
    }

    interface Presenter{
        void isUserLoggedIn();
        void signIn();
    }

}
