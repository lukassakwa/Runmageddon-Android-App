package com.olivier.jasmedapp.contracts;

import android.net.Uri;

import java.util.ArrayList;

public interface GalleryFragmentContract {

    interface View{
        void setFragment(ArrayList<Uri> imagesUri);
    }

    interface Presenter{
        void init();
    }
}
