package com.olivier.jasmedapp.model;

import android.net.Uri;
import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

@IgnoreExtraProperties
public class Register implements Serializable {

    private String email;
    private String password;
    private String fullName;
    private String imageUrl;
    private int phoneNumber;

    public Register() {
    }

    public Register(String email, String password, String fullName, int phoneNumber) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
    }

    public Register(String email, String password, String fullName, String imageUrl, int phoneNumber) {
        this.email = email;
        this.password = password;
        this.fullName = fullName;
        this.imageUrl = imageUrl;
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isRegisterValid(){
        return email != "" && email != null && password != "" && password != null && fullName != "" && fullName != null;
    }

}
