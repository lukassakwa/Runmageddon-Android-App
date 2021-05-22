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
import com.olivier.jasmedapp.R;
import com.olivier.jasmedapp.contracts.EventsRecyclerViewContract;
import com.olivier.jasmedapp.presenter.EventsRecyclerViewPresenter;
import com.squareup.picasso.Picasso;

public class EventsRVAdapter extends RecyclerView.Adapter<EventsRVAdapter.ViewHolder> {

    private EventsRecyclerViewPresenter mEventsRecyclerViewPresenter;

    public EventsRVAdapter(EventsRecyclerViewPresenter mEventsRecyclerViewPresenter) {
        this.mEventsRecyclerViewPresenter = mEventsRecyclerViewPresenter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.events_adapter, null);
        return new ViewHolder(viewHolder);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        mEventsRecyclerViewPresenter.getEvent(position, holder);

        holder.takePart.setOnClickListener((v) -> {
            mEventsRecyclerViewPresenter.setUserEvent(position);
        });
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements EventsRecyclerViewContract.View {

        private ImageView image;
        private TextView title;
        private TextView date;
        private Button takePart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.titleTextView);
            date = itemView.findViewById(R.id.dateTextView);
            takePart = itemView.findViewById(R.id.takePartButton);
            image = itemView.findViewById(R.id.userEventImageView);
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
    }

}
