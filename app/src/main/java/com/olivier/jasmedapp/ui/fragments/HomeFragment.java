package com.olivier.jasmedapp.ui.fragments;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.olivier.jasmedapp.R;
import com.olivier.jasmedapp.adapters.UserHomeRVAdapter;
import com.olivier.jasmedapp.model.Event;
import com.olivier.jasmedapp.presenter.EventsRecyclerViewPresenter;
import com.olivier.jasmedapp.presenter.HomeRecyclerViewPresenter;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    //TODO:: user recycler view with events
    //TODO:: MVP!!!!!!!!!!
    //TODO:: contact etc.......

    private HomeRecyclerViewPresenter mHomeRecyclerViewPresenter;

    private RecyclerView mUserHomeRV;
    private UserHomeRVAdapter mUserHomeRVAdapter;
    private RecyclerView.LayoutManager mUserHomeRVLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mHomeRecyclerViewPresenter = new HomeRecyclerViewPresenter();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.home_layout, container, false);

        //TODO:: work on that
        ArrayList<Event> events = (ArrayList<Event>) getArguments().getSerializable("userEvents");
        mHomeRecyclerViewPresenter.setUserEvents(events);

        mUserHomeRVLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        mUserHomeRVAdapter = new UserHomeRVAdapter(mHomeRecyclerViewPresenter);

        mUserHomeRV = rootView.findViewById(R.id.homeRecyclerView);
        mUserHomeRV.setHasFixedSize(true);
        mUserHomeRV.setLayoutManager(mUserHomeRVLayoutManager);
        mUserHomeRV.setAdapter(mUserHomeRVAdapter);

        return rootView;
    }

    @Override
    public void onPause() {
        super.onPause();

        //TODO:: send array to main activity
        Bundle result = new Bundle();
        result.putSerializable("requestKey", mHomeRecyclerViewPresenter.getUserEvents());
        // The child fragment needs to still set the result on its parent fragment manager
        getParentFragmentManager().setFragmentResult("userEvents", result);
    }
}
