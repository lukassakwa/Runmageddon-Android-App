package com.olivier.jasmedapp.contracts;

import android.content.Intent;
import com.olivier.jasmedapp.model.Mail;

public interface ContactFragmentContract {
    interface View{
        void startIntent(Intent intent);
    }

    interface Presenter{
        void sendMail(Mail mail);
    }
}
