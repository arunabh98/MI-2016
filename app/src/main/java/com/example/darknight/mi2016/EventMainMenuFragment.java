package com.example.darknight.mi2016;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventMainMenuFragment extends Fragment {


    public EventMainMenuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_main_menu, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final Button competitions = (Button) getActivity().findViewById(R.id.competitions);
        competitions.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });

        final Button proshows = (Button) getActivity().findViewById(R.id.proshows);
        proshows.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                EventsFragment eventsFragment = new EventsFragment();
                openFragment(eventsFragment);
            }
        });

        final Button concerts = (Button) getActivity().findViewById(R.id.concerts);
        concerts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                EventsFragment eventsFragment = new EventsFragment();
                openFragment(eventsFragment);
            }
        });

        final Button informals = (Button) getActivity().findViewById(R.id.informals);
        informals.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                EventsFragment eventsFragment = new EventsFragment();
                openFragment(eventsFragment);
            }
        });

        final Button workshops = (Button) getActivity().findViewById(R.id.workshops);
        workshops.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Perform action on click
                EventsFragment eventsFragment = new EventsFragment();
                openFragment(eventsFragment);
            }
        });
    }

    public void openFragment(Fragment fragment) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.relativelayout_for_fragment, fragment, fragment.getTag());
        transaction.commit();
    }

}
