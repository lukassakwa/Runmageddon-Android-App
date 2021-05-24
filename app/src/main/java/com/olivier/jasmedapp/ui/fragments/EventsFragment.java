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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.firebase.auth.FirebaseAuth;
import com.olivier.jasmedapp.R;
import com.olivier.jasmedapp.adapters.EventsRVAdapter;
import com.olivier.jasmedapp.contracts.EventsFragmentContract;
import com.olivier.jasmedapp.model.Event;
import com.olivier.jasmedapp.presenter.EventsFragmentPresenter;
import com.olivier.jasmedapp.presenter.EventsRecyclerViewPresenter;

import java.util.ArrayList;

public class EventsFragment extends Fragment implements EventsFragmentContract.View {

    //TODO:: repair this code so user could choose and resign of events
    //TODO:: save and get preferences on main activity
    //TODO:: transfer array between fragments while program is working
    private EventsRecyclerViewPresenter mEventsRecyclerViewPresenter;
    private EventsFragmentPresenter mEventsFragmentPresenter;

    private RecyclerView eventsRV;
    private EventsRVAdapter eventsRVAdapter;
    private RecyclerView.LayoutManager eventRVLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEventsRecyclerViewPresenter = new EventsRecyclerViewPresenter();

        mEventsFragmentPresenter = new EventsFragmentPresenter();
        mEventsFragmentPresenter.attach(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //TODO:: work on that
        ArrayList<Event> events = (ArrayList<Event>) getArguments().getSerializable("userEvents");
        mEventsRecyclerViewPresenter.setUserEvents(events);

        return inflater.inflate(R.layout.events_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        eventRVLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        eventsRVAdapter = new EventsRVAdapter(mEventsRecyclerViewPresenter);

        eventsRV = view.findViewById(R.id.eventsRecyclerView);
        eventsRV.setHasFixedSize(true);
        eventsRV.setLayoutManager(eventRVLayoutManager);
        eventsRV.setAdapter(eventsRVAdapter);
    }

    @Override
    public void onPause() {
        super.onPause();

        //TODO:: send array to main activity
        Bundle result = new Bundle();
        result.putSerializable("requestKey", mEventsRecyclerViewPresenter.getUserEvents());
        // The child fragment needs to still set the result on its parent fragment manager
        getParentFragmentManager().setFragmentResult("userEvents", result);
    }
}
