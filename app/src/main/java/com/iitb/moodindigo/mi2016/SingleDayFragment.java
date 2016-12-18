package com.iitb.moodindigo.mi2016;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iitb.moodindigo.mi2016.ServerConnection.GsonModels;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class SingleDayFragment extends Fragment {

    private RecyclerView scheduleRecyclerView;
    private EventsListAdapter scheduleListAdapter;
    private List<GsonModels.Event> eventResponse;

    public SingleDayFragment() {
        // Required empty public constructor
    }

    public SingleDayFragment(Context context, List<GsonModels.Event> eventResponse) {
        this.eventResponse = eventResponse;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View singledayview = (View) inflater.inflate(R.layout.fragment_single_day, container, false);
        scheduleRecyclerView = (RecyclerView) singledayview.findViewById(R.id.schedule_list);
        scheduleListAdapter = new EventsListAdapter(eventResponse, new ItemCLickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Fragment eventPageFragment = new EventPageFragment(getContext(), Cache.getEventList().get(position));
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.relativelayout_for_fragment, eventPageFragment, eventPageFragment.getTag());
                transaction.commit();
            }
        });
        scheduleRecyclerView.setAdapter(scheduleListAdapter);
        scheduleRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return singledayview;
    }

}
