package com.olivier.jasmedapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.olivier.jasmedapp.R;
import com.olivier.jasmedapp.contracts.RegisterActivityContract;
import com.olivier.jasmedapp.model.Register;
import com.olivier.jasmedapp.presenter.RegisterActivityPresenter;

public class RegisterActivity extends AppCompatActivity implements RegisterActivityContract.View {

    private RegisterActivityPresenter mRegisterActivityPresenter;

    private Intent mainActivityIntent;

    private Register register;

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mFullNameEditText;
    private EditText mPhoneNumberEditText;
    private Button mRegisterButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        mainActivityIntent = new Intent(this, MainActivity.class);

        mRegisterActivityPresenter = new RegisterActivityPresenter();
        mRegisterActivityPresenter.attach(this);

        mEmailEditText = findViewById(R.id.editTextEmailAddress);
        mPasswordEditText = findViewById(R.id.editTextPassword);
        mFullNameEditText = findViewById(R.id.editTextFullName);
        mPhoneNumberEditText = findViewById(R.id.editTextPhoneNumber);
        mRegisterButton = findViewById(R.id.registerButton);

        mRegisterButton.setOnClickListener((v) -> {

            if(TextUtils.isEmpty(mFullNameEditText.getText().toString())){
                mFullNameEditText.setError("Full name is requaired");
                return;
            }

            if(TextUtils.isEmpty(mEmailEditText.getText().toString())){
                mEmailEditText.setError("Email is requaired");
                return;
            }

            if(TextUtils.isEmpty(mPasswordEditText.getText().toString())){
                mPasswordEditText.setError("Password is requaired");
                return;
            }

            if(TextUtils.isEmpty(mPhoneNumberEditText.getText().toString())){
                mPhoneNumberEditText.setError("Phone number is requaired");
                return;
            }

            String email = mEmailEditText.getText().toString();
            String password = mPasswordEditText.getText().toString();
            String fullName = mFullNameEditText.getText().toString();
            int phoneNumer = Integer.parseInt(mPhoneNumberEditText.getText().toString());

            register = new Register(email, password, fullName, phoneNumer);
            if(register.isRegisterValid()){
                mRegisterActivityPresenter.setRegister(register);
                mRegisterActivityPresenter.createAccount();
            }
        });
    }

    @Override
    public void registerFail() {
        Toast.makeText(this, "Register user failed.", Toast.LENGTH_LONG).show();
    }

    @Override
    public void registerSuccess() {
        startActivity(mainActivityIntent);
    }
}
