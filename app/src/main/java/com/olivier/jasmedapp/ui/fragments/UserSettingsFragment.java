package com.olivier.jasmedapp.ui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import com.olivier.jasmedapp.R;
import com.olivier.jasmedapp.contracts.UserSettingsFragmentContract;
import com.olivier.jasmedapp.presenter.UserSettingsFragmentPresenter;
import com.theartofdev.edmodo.cropper.CropImage;

public class UserSettingsFragment extends Fragment implements UserSettingsFragmentContract.View {

    private UserSettingsFragmentPresenter mUserSettingsFragmentPresenter;

    //change user picture
    private Button changePictureButton;

    //change email layout widgets
    private ConstraintLayout emailChangeLayout;
    private EditText emailAddressEditText;
    private EditText userPasswordEditText;
    private Button emailChangeButton;
    private Button saveEmailChangeButton;

    //change password layout widgets
    private ConstraintLayout passwordChangeLayout;
    private EditText currentPasswordEditText;
    private EditText newUserPasswordEditText;
    private Button passwordChangeButton;
    private Button passwordSaveChangesButton;

    //user data
    private EditText fullNameEditText;
    private EditText phoneNumberEditText;
    private Button saveChanges;

    public UserSettingsFragment(){
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mUserSettingsFragmentPresenter = new UserSettingsFragmentPresenter();
        mUserSettingsFragmentPresenter.attach(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.user_settings_fragment, container, false);

        //change picture
        changePictureButton = rootView.findViewById(R.id.pictureChangeButton);

        //email layout widget init
        emailChangeLayout = rootView.findViewById(R.id.emailChangeLayout);
        emailAddressEditText = rootView.findViewById(R.id.newUserEmailEditText);
        userPasswordEditText = rootView.findViewById(R.id.passwordEditText);
        emailChangeButton = rootView.findViewById(R.id.emailChangeButton);
        saveEmailChangeButton = rootView.findViewById(R.id.emailSaveChangesButton);

        //password layout widget Init
        passwordChangeLayout = rootView.findViewById(R.id.passwordChangeLayout);
        currentPasswordEditText = rootView.findViewById(R.id.currentPasswordEditText);
        newUserPasswordEditText = rootView.findViewById(R.id.newUserPasswordEditText);
        passwordChangeButton = rootView.findViewById(R.id.passwordChangeButton);
        passwordSaveChangesButton = rootView.findViewById(R.id.passwordSaveChangesButton);

        //other user data init
        phoneNumberEditText = rootView.findViewById(R.id.newUserPhoneEditText);
        fullNameEditText = rootView.findViewById(R.id.newUserFullNameEditText);
        saveChanges = rootView.findViewById(R.id.otherDataSaveChangesButton);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //picture change
        changePictureButton.setOnClickListener((v) -> {
            Intent openGallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(openGallery, 1000);
        });

        //email layout button functions
        emailChangeButton.setOnClickListener((v) -> {

            if(emailChangeLayout.getVisibility() == View.GONE){
                emailChangeButton.setText("Close");
                emailChangeLayout.setVisibility(View.VISIBLE);
            }else{
                emailChangeButton.setText("Change");
                emailChangeLayout.setVisibility(View.GONE);
            }
        });

        saveEmailChangeButton.setOnClickListener((v) -> {

            if(TextUtils.isEmpty(userPasswordEditText.getText().toString())){
                userPasswordEditText.setError("your password is empty");
                return;
            }

            if(TextUtils.isEmpty(emailAddressEditText.getText().toString())){
                emailAddressEditText.setError("your email adress is empty");
                return;
            }

            String email = emailAddressEditText.getText().toString();
            String password = userPasswordEditText.getText().toString();
            mUserSettingsFragmentPresenter.updateEmail(email, password);

        });

        //password layout button functions
        passwordChangeButton.setOnClickListener((v) -> {
            if(passwordChangeLayout.getVisibility() == View.GONE){
                passwordChangeButton.setText("Close");
                passwordChangeLayout.setVisibility(View.VISIBLE);
            }else{
                passwordChangeButton.setText("Change");
                passwordChangeLayout.setVisibility(View.GONE);
            }
        });

        passwordSaveChangesButton.setOnClickListener((v) -> {
            if(TextUtils.isEmpty(currentPasswordEditText.getText().toString())){
                currentPasswordEditText.setError("your password is empty");
                return;
            }

            if(TextUtils.isEmpty(newUserPasswordEditText.getText().toString())){
                newUserPasswordEditText.setError("your email adress is empty");
                return;
            }

            String newPassword = newUserPasswordEditText.getText().toString();
            String password = currentPasswordEditText.getText().toString();
            mUserSettingsFragmentPresenter.updatePassword(newPassword, password);
        });

        saveChanges.setOnClickListener((v) -> {

            if(TextUtils.isEmpty(fullNameEditText.getText().toString())){
                phoneNumberEditText.setError("full Name is required");
                return;
            }

            if(TextUtils.isEmpty(phoneNumberEditText.getText().toString())){
                phoneNumberEditText.setError("phone number is required");
                return;
            }

            int phoneNumber = Integer.parseInt(phoneNumberEditText.getText().toString());
            String fullName = fullNameEditText.getText().toString();

            mUserSettingsFragmentPresenter.otherUserData(phoneNumber, fullName);
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1000){
            if(resultCode == Activity.RESULT_OK){
                Uri profileImage = data.getData();
                CropImage.activity(profileImage).start(getContext(), this);
            }
        }
        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == Activity.RESULT_OK) {
                Uri profileImage = result.getUri();
                mUserSettingsFragmentPresenter.updatePicture(profileImage);
            }
        }
    }

    @Override
    public void onSuccessEmailChange() {
        Toast.makeText(getActivity(), "profile email updated", Toast.LENGTH_LONG).show();
        setEditTextAfterChange(emailAddressEditText, userPasswordEditText);
    }

    @Override
    public void onSuccessPasswordChange() {
        Toast.makeText(getActivity(), "profile password changed", Toast.LENGTH_LONG).show();
        setEditTextAfterChange(newUserPasswordEditText, currentPasswordEditText);
    }

    @Override
    public void onUpdateEmailFailure() {
        Toast.makeText(getActivity(), "profile email change failure", Toast.LENGTH_LONG).show();
        setEditTextAfterChange(emailAddressEditText, userPasswordEditText);
    }

    @Override
    public void onUpdatePasswordFailure() {
        Toast.makeText(getActivity(), "profile password change failure", Toast.LENGTH_LONG).show();
        setEditTextAfterChange(newUserPasswordEditText, currentPasswordEditText);
    }

    @Override
    public void onSuccessChange() {
        Toast.makeText(getActivity(), "profile change success", Toast.LENGTH_LONG).show();
        setEditTextAfterChange(phoneNumberEditText, fullNameEditText);
    }

    @Override
    public void onPictureUpdateSuccess() {
        Toast.makeText(getActivity(), "profile picture update success", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onPictureUpdateFailure() {
        Toast.makeText(getActivity(), "profile picture update failure", Toast.LENGTH_LONG).show();
    }

    private void setEditTextAfterChange(EditText firstEditText, EditText secondEditText){
        firstEditText.setText("");
        secondEditText.setText("");
    }
}
