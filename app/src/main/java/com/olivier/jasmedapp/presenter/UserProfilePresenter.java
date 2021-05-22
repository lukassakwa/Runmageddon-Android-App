package com.olivier.jasmedapp.presenter;

import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.*;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.olivier.jasmedapp.contracts.UserProfileContract;
import com.olivier.jasmedapp.model.Register;

import java.io.UnsupportedEncodingException;

public class UserProfilePresenter extends BasePresenter<UserProfileContract.View> implements UserProfileContract.Presenter{

    private FirebaseDatabase firebaseDatabase;
    private FirebaseUser firebaseUser;
    private StorageReference storageReference;

    private Register register;

    public UserProfilePresenter() {
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference();
        register = new Register();
    }

    @Override
    public void getUserData() {
        ValueEventListener registerListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                Register register = dataSnapshot.getValue(Register.class);
                view.setUserView(register.getFullName(), register.getEmail(), register.getPhoneNumber());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("DatabaseFirebase", "loadPost:onCancelled", databaseError.toException());
            }
        };
        firebaseDatabase.getReference().child("users").child(firebaseUser.getUid()).addValueEventListener(registerListener);
    }

    @Override
    public void getUserPicture() {
        StorageReference storagePath = storageReference.child("profile/" + firebaseUser.getUid() + "/profile.jpg");

        //getting picture from firebase storage
        storagePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                view.setUserPicture(uri);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }
}
