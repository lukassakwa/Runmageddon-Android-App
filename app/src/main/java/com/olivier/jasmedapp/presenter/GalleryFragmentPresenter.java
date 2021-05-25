package com.olivier.jasmedapp.presenter;

import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.ListResult;
import com.google.firebase.storage.StorageReference;
import com.olivier.jasmedapp.contracts.GalleryFragmentContract;
import com.olivier.jasmedapp.ui.fragments.GalleryFragment;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GalleryFragmentPresenter extends BasePresenter<GalleryFragmentContract.View> implements GalleryFragmentContract.Presenter{

    private StorageReference storageReference;

    private ArrayList<Uri> imagesUri;

    public GalleryFragmentPresenter() {
        imagesUri = new ArrayList<>();
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    @Override
    public void init() {
        StorageReference galleryReference = storageReference.child("allPictures");
        galleryReference.listAll().addOnSuccessListener(new OnSuccessListener<ListResult>() {
            @Override
            public void onSuccess(ListResult listResult) {
                for (StorageReference item : listResult.getItems()){

                    //get picture
                    item.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            imagesUri.add(uri);
                            view.setFragment(imagesUri);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull @NotNull Exception e) {
                            Log.d("allPicture", "item problems");
                        }
                    });

                }
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull @NotNull Exception e) {
                Log.d("allPicture", "child problems");
            }
        });
    }
}
