package com.olivier.jasmedapp.contracts;

public interface RegisterActivityContract {

    interface View{
        void registerFail();
        void registerSuccess();
    }

    interface Presenter{
        void createAccount();
    }

}
