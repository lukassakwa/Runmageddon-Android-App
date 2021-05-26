package com.olivier.jasmedapp.ui.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.olivier.jasmedapp.R;
import com.olivier.jasmedapp.adapters.GalleryRVAdapter;
import com.olivier.jasmedapp.contracts.GalleryFragmentContract;
import com.olivier.jasmedapp.presenter.GalleryFragmentPresenter;
import com.olivier.jasmedapp.presenter.GalleryRecyclerViewPresenter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class GalleryFragment extends Fragment implements GalleryFragmentContract.View {

    private GalleryFragmentPresenter mGalleryFragmentPresenter;
    private GalleryRecyclerViewPresenter mGalleryRecyclerViewPresenter;

    private RecyclerView mGalleryRV;
    private GalleryRVAdapter mGalleryAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mGalleryFragmentPresenter = new GalleryFragmentPresenter();
        mGalleryFragmentPresenter.attach(this);

        mLayoutManager = new GridLayoutManager(getContext(), 2);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.gallery_fragment_layout, container, false);

        mGalleryRV = rootView.findViewById(R.id.galleryRecyclerView);
        mGalleryRV.setHasFixedSize(true);
        mGalleryRV.setLayoutManager(mLayoutManager);

        mGalleryFragmentPresenter.init();

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void setFragment(ArrayList<Uri> imagesUri) {
        mGalleryRecyclerViewPresenter = new GalleryRecyclerViewPresenter(imagesUri);

        mGalleryAdapter = new GalleryRVAdapter(mGalleryRecyclerViewPresenter);
        mGalleryRV.setAdapter(mGalleryAdapter);
    }
}
