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
public class CategoryFragment extends Fragment {

    private RecyclerView categoryRecyclerView;
    private EventsListAdapter categoryListAdapter;
    private List<GsonModels.Event> eventResponse;

    public CategoryFragment() {
        // Required empty public constructor
    }

    public CategoryFragment(Context context, List<GsonModels.Event> eventResponse) {
        this.eventResponse = eventResponse;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View singledayview = (View) inflater.inflate(R.layout.fragment_category, container, false);
        categoryRecyclerView = (RecyclerView) singledayview.findViewById(R.id.category_list);
        categoryListAdapter = new EventsListAdapter(eventResponse, new ItemCLickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Fragment eventPageFragment = new EventPageFragment(getContext(), eventResponse.get(position));
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack(null);
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.relativelayout_for_fragment, eventPageFragment, eventPageFragment.getTag());
                transaction.commit();
            }
        });
        categoryRecyclerView.setAdapter(categoryListAdapter);
        categoryRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return singledayview;
    }

}
