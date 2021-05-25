package com.olivier.jasmedapp.presenter;

import android.content.Intent;
import com.olivier.jasmedapp.contracts.ContactFragmentContract;
import com.olivier.jasmedapp.model.Mail;

public class ContactFragmentPresenter extends BasePresenter<ContactFragmentContract.View> implements ContactFragmentContract.Presenter{

    private Intent mailIntent;
    private final String mailTo = "test@gmail.com";

    public ContactFragmentPresenter() {
    }

    @Override
    public void sendMail(Mail mail) {
        mailIntent = new Intent(Intent.ACTION_SEND);
        mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{ mailTo });
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, mail.getTitle());
        mailIntent.putExtra(Intent.EXTRA_TEXT, mail.getBody());
        mailIntent.setType("message/rfc822");
        view.startIntent(mailIntent);
    }
}
