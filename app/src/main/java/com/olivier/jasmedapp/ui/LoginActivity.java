package com.olivier.jasmedapp.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.olivier.jasmedapp.R;
import com.olivier.jasmedapp.contracts.LoginActivityContract;
import com.olivier.jasmedapp.model.Login;
import com.olivier.jasmedapp.presenter.LoginActivityPresenter;

public class LoginActivity extends AppCompatActivity implements LoginActivityContract.View {

    private LoginActivityPresenter mLoginActivityPresenter;
    private Intent mainActivityIntent;
    private Intent registerActivityIntent;

    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private Button mLoginButton;
    private Button mRegisterButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);



        mLoginActivityPresenter = new LoginActivityPresenter();
        mLoginActivityPresenter.attach(this);

        mEmailEditText = findViewById(R.id.editTextEmailAddress);
        mPasswordEditText = findViewById(R.id.editTextPassword);
        mLoginButton = findViewById(R.id.loginButton);
        mRegisterButton = findViewById(R.id.registerButton);

        mRegisterButton.setOnClickListener((v) -> {
            registerActivityIntent = new Intent(this, RegisterActivity.class);
            startActivity(registerActivityIntent);
        });

        mLoginButton.setOnClickListener((v) -> {
            String email = mEmailEditText.getText().toString();
            String password = mPasswordEditText.getText().toString();

            if(TextUtils.isEmpty(email)){
                mEmailEditText.setError("email is required");
                return;
            }

            if(TextUtils.isEmpty(password)){
                mEmailEditText.setError("password is required");
                return;
            }

            Login login = new Login(email, password);

            if(login.isLoginValid()){
                mLoginActivityPresenter.setLogin(login);
                mLoginActivityPresenter.signIn();
            }else{
                Toast.makeText(this, "email or password is not valid", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (v instanceof EditText) {
                Rect outRect = new Rect();
                v.getGlobalVisibleRect(outRect);
                if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                    v.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                }
            }
        }
        return super.dispatchTouchEvent( event );
    }

    @Override
    protected void onStart() {
        super.onStart();
        mLoginActivityPresenter.isUserLoggedIn();
    }

    @Override
    public void updateUI() {
        mainActivityIntent = new Intent(this, MainActivity.class);
        startActivity(mainActivityIntent);
    }

    @Override
    public void loginFail() {
        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_LONG).show();
    }
}
