package com.olivier.jasmedapp.presenter;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import androidx.annotation.NonNull;
import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.google.android.gms.auth.api.signin.internal.Storage;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.*;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.olivier.jasmedapp.contracts.LoginActivityContract;
import com.olivier.jasmedapp.model.Login;
import com.olivier.jasmedapp.model.Register;
import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class LoginActivityPresenter extends BasePresenter<LoginActivityContract.View> implements LoginActivityContract.Presenter{

    private FirebaseAuth auth;

    private FirebaseUser user;

    private Login login;
    private Register register;

    public LoginActivityPresenter() {
        auth = FirebaseAuth.getInstance();
        login = new Login();
    }

    @Override
    public void isUserLoggedIn() {
        user = auth.getCurrentUser();
        if(user != null){
            view.updateUI();
        }
    }

    @Override
    public void signIn() {
        auth.signInWithEmailAndPassword(login.getEmail(), login.getPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    view.updateUI();
                }else{
                    view.loginFail();
                }
            }
        });
    }

    public void handleFacebookAccessToken(AccessToken accessToken) {
        Log.d("Login Presenter: ", "handleFacebookAccessToken:" + accessToken);

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            view.updateUI();
                            // Sign in success, update UI with the signed-in user's information
                            user = auth.getCurrentUser();

                            String imageUrl = "https://graph.facebook.com/" + accessToken.getUserId() + "/picture?type=large";
                            String fullName = user.getDisplayName();
                            String email = user.getEmail();

                            register = new Register(email, "", fullName, imageUrl,999999999);
                            saveUserData();
                        } else {
                            // If sign in fails, display a message to the user.
                        }
                    }
                });
    }

    //TODO:: fix facebok login and setting user parametrs
    private void saveUserData() {
        DatabaseReference registerReference = FirebaseDatabase.getInstance().getReference();
        StorageReference pictureReference = FirebaseStorage.getInstance().getReference();
        registerReference.child("users").child(user.getUid()).setValue(register);

        pictureReference.child("profile/" + user.getUid() + "/profile.jpg");
        pictureReference.putFile(Uri.parse(register.getImageUrl()));
    }

    public void setLogin(Login login) {
        this.login = login;
    }

}
