package com.iitb.moodindigo.mi2016;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iitb.moodindigo.mi2016.ServerConnection.GsonModels;

import java.lang.reflect.Type;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GoingFragment extends Fragment {

    private RecyclerView goingRecyclerView;
    private BookmarkedEventsListAdapter bookmarkedEventsListAdapter;
    private SharedPreferences goingSharedPreferences;

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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        goingSharedPreferences = getContext().getSharedPreferences("GOING", Context.MODE_PRIVATE);
        final String goingList = goingSharedPreferences.getString("GOING_LIST", null);
        Type type = new TypeToken<List<GsonModels.Event>>(){}.getType();
        List<GsonModels.Event> goingListGson = (new Gson()).fromJson(goingList, type);
        Cache.setGoingEventsList(goingListGson);
        goingRecyclerView = (RecyclerView) getActivity().findViewById(R.id.going_events_list);
        bookmarkedEventsListAdapter = new BookmarkedEventsListAdapter(Cache.getGoingEventsList(), new ItemCLickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Fragment eventPageFragment = new EventPageFragment(getContext(), Cache.getGoingEventsList().get(position));
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.relativelayout_for_fragment, eventPageFragment, eventPageFragment.getTag());
                transaction.commit();
            }
        });
        goingRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        goingRecyclerView.setAdapter(bookmarkedEventsListAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
    }
}