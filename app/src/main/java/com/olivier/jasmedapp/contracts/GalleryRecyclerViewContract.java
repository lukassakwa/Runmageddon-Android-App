package com.olivier.jasmedapp.contracts;

import android.net.Uri;

public interface GalleryRecyclerViewContract {

    interface View{
        void setPicture(Uri imageUri);
        void setFullSizePicture(Uri imageUri);
    }

    interface Presenter{
        void getPicture(GalleryRecyclerViewContract.View view, int position);
        void getFullSizePicture(GalleryRecyclerViewContract.View view, int position);
        int getSize();
    }
}
