package com.olivier.jasmedapp.presenter;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.olivier.jasmedapp.contracts.LoginActivityContract;
import com.olivier.jasmedapp.model.Login;

public class LoginActivityPresenter extends BasePresenter<LoginActivityContract.View> implements LoginActivityContract.Presenter{

    private FirebaseAuth auth;

    private FirebaseUser user;
    private Login login;

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

    public void setLogin(Login login) {
        this.login = login;
    }
}
