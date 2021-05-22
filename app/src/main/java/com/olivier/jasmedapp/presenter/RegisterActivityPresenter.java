package com.olivier.jasmedapp.presenter;

import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.olivier.jasmedapp.contracts.RegisterActivityContract;
import com.olivier.jasmedapp.model.Register;

public class RegisterActivityPresenter extends BasePresenter<RegisterActivityContract.View> implements RegisterActivityContract.Presenter {

    private FirebaseAuth auth;
    private DatabaseReference database;

    private Register register;

    public RegisterActivityPresenter() {
        auth = FirebaseAuth.getInstance();
        register = new Register();
    }

    @Override
    public void createAccount() {
        auth.createUserWithEmailAndPassword(register.getEmail(), register.getPassword())
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            saveUserData();
                            view.registerSuccess();
                        }else{
                            view.registerFail();
                        }
                    }
                });
    }

    private void saveUserData() {
        database = FirebaseDatabase.getInstance().getReference();

        String userUID = auth.getCurrentUser().getUid();
        database.child("users").child(userUID).setValue(register);
    }

    public void setRegister(Register register) {
        this.register = register;
    }
}
