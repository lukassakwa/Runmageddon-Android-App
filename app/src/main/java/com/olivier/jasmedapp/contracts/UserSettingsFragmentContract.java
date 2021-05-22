package com.olivier.jasmedapp.contracts;

import android.net.Uri;

public interface UserSettingsFragmentContract {

    interface View{
        void onSuccessEmailChange();
        void onSuccessPasswordChange();
        void onUpdateEmailFailure();
        void onUpdatePasswordFailure();
        void onSuccessChange();
        void onPictureUpdateSuccess();
        void onPictureUpdateFailure();
    }

    interface Presenter{
        void updateEmail(String newEmail, String password);
        void updatePassword(String newPassword, String password);
        void otherUserData(int phoneNumber, String fullName);
        void updatePicture(Uri profileImage);
    }
}
