package com.iitb.moodindigo.mi2016;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.iitb.moodindigo.mi2016.ServerConnection.GsonModels;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryFragment extends Fragment {

    private RecyclerView categoryRecyclerView;
    private EventsListAdapter categoryListAdapter;
    private List<GsonModels.Event> eventResponse = new ArrayList<>();

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
        TextView noEvents = (TextView) singledayview.findViewById(R.id.no_events);
        FragmentManager.BackStackEntry fragment = getActivity().getSupportFragmentManager().getBackStackEntryAt(getActivity().getSupportFragmentManager().getBackStackEntryCount() - 1);
        if (fragment != null) {
            if (!fragment.getName().equals("back")) {
                int date = (int) getActivity().getTitle().charAt(4);
                date -= 26;
                if ((int) Calendar.getInstance().get(Calendar.DATE) == date) {
                    int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                    int pos = -1;
                    Log.d("current hour", Integer.toString(hour));
                    hour = hour * 100;
                    for (GsonModels.Event event : eventResponse) {
                        pos++;
                        if (event.getTime() >= hour) {
                            break;
                        }
                    }
                    Cache.setListPosition(pos);
                } else {
                    Cache.setListPosition(0);
                }
            }
        }
        categoryListAdapter = new EventsListAdapter(eventResponse, new ItemCLickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Cache.setListPosition(position);
                Fragment eventPageFragment = new EventPageFragment(getContext(), eventResponse.get(position));
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.addToBackStack("category");
                transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                transaction.replace(R.id.relativelayout_for_fragment, eventPageFragment, eventPageFragment.getTag());
                transaction.commit();
            }
        });

        if (categoryListAdapter.getItemCount() == 0) {
            noEvents.setVisibility(View.VISIBLE);
            categoryRecyclerView.setVisibility(View.GONE);
        } else {
            noEvents.setVisibility(View.GONE);
            categoryRecyclerView.setVisibility(View.VISIBLE);
            categoryRecyclerView.setAdapter(categoryListAdapter);
            LinearLayoutManager llm = (LinearLayoutManager) new LinearLayoutManager(getContext());
            categoryRecyclerView.setLayoutManager(llm);
            llm.scrollToPosition(Cache.getListPosition());
        }

        return singledayview;
    }

}
