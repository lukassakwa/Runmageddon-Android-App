package com.olivier.jasmedapp.ui.fragments;

import android.content.SharedPreferences;
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
import org.jetbrains.annotations.NotNull;

public class ContactFragment extends Fragment {

    private EditText directionMail;
    private EditText sourceMail;
    private EditText title;
    private EditText body;
    private Button send;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.contact_fragment, container, false);

        directionMail = rootView.findViewById(R.id.directionMail);
        sourceMail = rootView.findViewById(R.id.sourceMail);
        title = rootView.findViewById(R.id.titleEditText);
        body = rootView.findViewById(R.id.bodyEditText);
        send = rootView.findViewById(R.id.sendButton);

        return rootView;
    }
}
