package com.iitb.moodindigo.mi2016;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iitb.moodindigo.mi2016.ServerConnection.GsonModels;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GoingDayFragment extends Fragment {

    private RecyclerView goingRecyclerView;
    private BookmarkedEventsListAdapter bookmarkedEventsListAdapter;
    private TextView noEventsSelected;
    private List<GsonModels.Event> goingDayList;

    public GoingDayFragment() {
        // Required empty public constructor
    }

    public GoingDayFragment(List<GsonModels.Event> goingDayList) {
        this.goingDayList = goingDayList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View goingDayView = (View) inflater.inflate(R.layout.fragment_going_day, container, false);
        noEventsSelected = (TextView) goingDayView.findViewById(R.id.no_events_selected);
        goingRecyclerView = (RecyclerView) goingDayView.findViewById(R.id.going_events_list);
        bookmarkedEventsListAdapter = new BookmarkedEventsListAdapter(goingDayList, new ItemCLickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Fragment eventPageFragment = new EventPageFragment(getContext(), goingDayList.get(position));
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack("going");
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.relativelayout_for_fragment, eventPageFragment, eventPageFragment.getTag());
                transaction.commit();
            }
        });
        if (bookmarkedEventsListAdapter.getItemCount() == 0) {
            goingRecyclerView.setVisibility(View.GONE);
            noEventsSelected.setVisibility(View.VISIBLE);
        } else {
            goingRecyclerView.setVisibility(View.VISIBLE);
            noEventsSelected.setVisibility(View.GONE);
            goingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            goingRecyclerView.setAdapter(bookmarkedEventsListAdapter);
        }

        return goingDayView;
    }

}
