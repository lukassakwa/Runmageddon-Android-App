package com.olivier.jasmedapp.adapters;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.olivier.jasmedapp.R;
import com.olivier.jasmedapp.contracts.HomeRecyclerViewContract;
import com.olivier.jasmedapp.model.Event;
import com.olivier.jasmedapp.presenter.HomeRecyclerViewPresenter;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserHomeRVAdapter extends RecyclerView.Adapter<UserHomeRVAdapter.ViewHolder> {

    private HomeRecyclerViewPresenter mHomeRecyclerView;

    public UserHomeRVAdapter(HomeRecyclerViewPresenter homeRecyclerViewPresenter) {
        mHomeRecyclerView = homeRecyclerViewPresenter;
    }

    @NonNull
    @Override
    public UserHomeRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_adapter, null);
        return new ViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        mHomeRecyclerView.getUserEvent(holder,  position);
        holder.resign.setOnClickListener((v) -> {
            mHomeRecyclerView.setUserEventsArray(holder, position);
        });
    }

    @Override
    public int getItemCount() {
        return mHomeRecyclerView.getSize();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements HomeRecyclerViewContract.View {

        private ImageView image;
        private TextView title;
        private TextView date;
        private Button resign;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titleTextView);
            date = itemView.findViewById(R.id.dateTextView);
            image = itemView.findViewById(R.id.userEventImageView);
            resign = itemView.findViewById(R.id.resignButton);
        }

        @Override
        public void setTitle(String title) {
            this.title.setText(title);
        }

        @Override
        public void setDate(String date) {
            this.date.setText(date);
        }

        @Override
        public void setImage(Uri imageUri) {
            Picasso.get().load(imageUri).into(image);
        }

        @Override
        public void setItems(int position) {
            notifyItemRemoved(position);
        }
    }
}
