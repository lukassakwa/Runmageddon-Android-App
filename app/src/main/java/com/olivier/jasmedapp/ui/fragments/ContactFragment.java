package com.olivier.jasmedapp.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.olivier.jasmedapp.R;
import com.olivier.jasmedapp.contracts.ContactFragmentContract;
import com.olivier.jasmedapp.model.Mail;
import com.olivier.jasmedapp.presenter.ContactFragmentPresenter;
import org.jetbrains.annotations.NotNull;

public class ContactFragment extends Fragment implements ContactFragmentContract.View {

    private ContactFragmentPresenter mContactFragmentPresenter;

    private EditText title;
    private EditText body;
    private Button send;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContactFragmentPresenter = new ContactFragmentPresenter();
        mContactFragmentPresenter.attach(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contact_fragment, container, false);

        title = rootView.findViewById(R.id.titleEditText);
        body = rootView.findViewById(R.id.bodyEditText);
        send = rootView.findViewById(R.id.sendButton);

        send.setOnClickListener(v -> {
            String title = this.title.getText().toString();
            String body = this.body.getText().toString();
            Mail mail = new Mail(title, body);

            mContactFragmentPresenter.sendMail(mail);

        });

        return rootView;
    }

    @Override
    public void startIntent(Intent intent) {
        startActivity(Intent.createChooser(intent, "Choose Mail App"));
    }
}
