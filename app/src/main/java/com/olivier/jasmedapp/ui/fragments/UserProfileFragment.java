package com.olivier.jasmedapp.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.olivier.jasmedapp.R;
import com.olivier.jasmedapp.contracts.UserProfileContract;
import com.olivier.jasmedapp.presenter.UserProfilePresenter;
import com.squareup.picasso.Picasso;

public class UserProfileFragment extends Fragment implements UserProfileContract.View {

    private UserProfilePresenter mUserProfilePresenter;

    private ImageView profilePictureView;
    private TextView fullNameTextView;
    private TextView emailTextView;
    private TextView phoneNumberTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserProfilePresenter = new UserProfilePresenter();
        mUserProfilePresenter.attach(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.profile_user_layout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        profilePictureView = view.findViewById(R.id.userImage);
        fullNameTextView = view.findViewById(R.id.fullNameTextView);
        emailTextView = view.findViewById(R.id.emailTextView);
        phoneNumberTextView = view.findViewById(R.id.phoneNumberTextView);

        mUserProfilePresenter.getUserData();
        mUserProfilePresenter.getUserPicture();
    }

    @Override
    public void setUserView(String fullName, String email, int phoneNumber) {
        fullNameTextView.setText(fullName);
        emailTextView.setText(email);
        phoneNumberTextView.setText(phoneNumber + "");
    }

    @Override
    public void setUserPicture(Uri image) {
        Picasso.get().load(image).into(profilePictureView);
    }
}
