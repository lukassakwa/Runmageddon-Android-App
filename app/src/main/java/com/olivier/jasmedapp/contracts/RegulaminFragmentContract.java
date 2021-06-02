package com.olivier.jasmedapp.contracts;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.widget.ImageView;

public interface RegulaminFragmentContract {
    interface View{
        void setPdfView(String uri);
    }

    interface Presenter{
        void openPdfWithAndroidSDK();
    }
}
