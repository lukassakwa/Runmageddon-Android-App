package com.olivier.jasmedapp.presenter;

import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.olivier.jasmedapp.contracts.GalleryRecyclerViewContract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

//TODO:: change
public class GalleryRecyclerViewPresenter implements GalleryRecyclerViewContract.Presenter {

    private ArrayList<Uri> imagesUri;

    public GalleryRecyclerViewPresenter() {
        imagesUri = new ArrayList<>();
    }

    public GalleryRecyclerViewPresenter(ArrayList<Uri> imagesUri) {
        this.imagesUri = imagesUri;
    }

    @Override
    public void getPicture(GalleryRecyclerViewContract.View view, int position){
        Uri imageUri = imagesUri.get(position);
        view.setPicture(imageUri);
    }

    @Override
    public void getFullSizePicture(GalleryRecyclerViewContract.View view, int position) {
        view.setFullSizePicture(imagesUri.get(position));
    }

    @Override
    public int getSize() {
        return imagesUri.size();
    }
}
