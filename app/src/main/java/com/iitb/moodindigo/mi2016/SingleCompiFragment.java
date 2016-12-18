package com.iitb.moodindigo.mi2016;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class SingleCompiFragment extends Fragment {

    private RecyclerView compiRecyclerView;
    private EventsListAdapter compiListAdapter;
    private List<GsonModels.Event> compiResponse;

    public SingleCompiFragment() {
        // Required empty public constructor
    }

    public SingleCompiFragment(Context context, List<GsonModels.Event> compiResponse) {
        this.compiResponse = compiResponse;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View singledayview = (View) inflater.inflate(R.layout.fragment_single_compi, container, false);
        compiRecyclerView = (RecyclerView) singledayview.findViewById(R.id.compi_list);
        compiListAdapter = new EventsListAdapter(compiResponse, new ItemCLickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //TODO: Launch Event Page
            }
        });
        compiRecyclerView.setAdapter(compiListAdapter);
        compiRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return singledayview;
    }

}
