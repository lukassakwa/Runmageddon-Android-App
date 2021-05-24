package com.olivier.jasmedapp.presenter;

import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.olivier.jasmedapp.contracts.UserSettingsFragmentContract;
import com.olivier.jasmedapp.model.Register;

public class UserSettingsFragmentPresenter extends BasePresenter<UserSettingsFragmentContract.View> implements UserSettingsFragmentContract.Presenter{

    private FirebaseAuth auth;
    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;

    private Register register;

    public UserSettingsFragmentPresenter() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        register = new Register();
    }

    @Override
    public void updateEmail(String newEmail, String password) {
        if(user.getEmail() == newEmail){
            view.onUpdateEmailFailure();
            return;
        }

        userAuthorization(password);

        user.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    updateUserEmail(newEmail);
                    Log.d("Profile Fragment", "User email updated.");
                    view.onSuccessEmailChange();
                }else{
                    view.onUpdateEmailFailure();
                }
            }
        });
    }

    @Override
    public void updatePassword(String newPassword, String password) {
        if(password == newPassword){
            view.onUpdatePasswordFailure();
            return;
        }

        userAuthorization(password);

        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("Profile Fragment", "User password updated.");
                            updatePassword(newPassword);
                            view.onSuccessPasswordChange();
                        }else{
                            view.onUpdatePasswordFailure();
                        }
                    }
                });
    }

    private void userAuthorization(String password){
        AuthCredential credential = EmailAuthProvider.getCredential(user.getEmail(), password);

        // Prompt the user to re-provide their sign-in credentials
        user.reauthenticate(credential)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Log.d("Profile Fragment", "User re-authenticated.");
                    }
        });
    }

    @Override
    public void otherUserData(int phoneNumber, String fullName){
        databaseReference.child("users").child(user.getUid()).child("phoneNumber").setValue(phoneNumber);
        databaseReference.child("users").child(user.getUid()).child("fullName").setValue(fullName);
        view.onSuccessChange();
    }

    @Override
    public void updatePicture(Uri profileImage) {
        String userUID = user.getUid();
        StorageReference imageReference = storageReference.child("profile/" + userUID + "/profile.jpg");

        imageReference.putFile(profileImage).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                //TODO:: handle failure
                //TODO::get both to one function
                view.onPictureUpdateFailure();
            }
        }).addOnSuccessListener(new OnSuccessListener() {
            @Override
            public void onSuccess(Object o) {
                //TODO:: Hadnle success
                view.onPictureUpdateSuccess();
            }
        });

    }

    private void updateUserEmail(String email){
        databaseReference.child("users").child(user.getUid()).child("email").setValue(email);
    }

    private void updatePassword(String password){
        databaseReference.child("users").child(user.getUid()).child("password").setValue(password);
    }
}
