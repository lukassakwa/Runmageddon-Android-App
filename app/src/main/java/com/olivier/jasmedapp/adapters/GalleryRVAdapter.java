package com.olivier.jasmedapp.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.olivier.jasmedapp.R;
import com.olivier.jasmedapp.contracts.GalleryRecyclerViewContract;
import com.olivier.jasmedapp.presenter.GalleryRecyclerViewPresenter;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;

public class GalleryRVAdapter extends RecyclerView.Adapter<GalleryRVAdapter.ViewHolder> {

    private GalleryRecyclerViewPresenter mGalleryRecyclerViewPresenter;

    public GalleryRVAdapter(GalleryRecyclerViewPresenter mGalleryRecyclerViewPresenter) {
        this.mGalleryRecyclerViewPresenter = mGalleryRecyclerViewPresenter;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.gallery_adapter, null);
        return new ViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        mGalleryRecyclerViewPresenter.getPicture(holder, position);
    }

    @Override
    public int getItemCount() {
        return mGalleryRecyclerViewPresenter.getSize();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements GalleryRecyclerViewContract.View {

        private ImageView imageView;

        public ViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.pictureImageView);
        }

        @Override
        public void setPicture(Uri imageUri) {
            Picasso.get().load(imageUri).into(imageView);
        }
    }
}
