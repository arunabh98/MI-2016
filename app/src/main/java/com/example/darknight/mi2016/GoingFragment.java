package com.example.darknight.mi2016;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class GoingFragment extends Fragment {

    private RecyclerView goingRecyclerView;
    private BookmarkedEventsListAdapter bookmarkedEventsListAdapter;

    public GoingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_going, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        goingRecyclerView = (RecyclerView) getActivity().findViewById(R.id.going_events_list);
        bookmarkedEventsListAdapter = new BookmarkedEventsListAdapter(BookmarkedEvents.getGoingEventsList(), new ItemCLickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //TODO: Launch event description page
            }
        });
        goingRecyclerView.setAdapter(bookmarkedEventsListAdapter);
        goingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
