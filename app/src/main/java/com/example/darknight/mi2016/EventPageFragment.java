package com.example.darknight.mi2016;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventPageFragment extends Fragment {


    public EventPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Events");
        final View rootView = inflater.inflate(R.layout.fragment_event_page, container, false);
        final ImageButton notification = (ImageButton) rootView.findViewById(R.id.icon_button);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView eventVenue = (TextView) rootView.findViewById(R.id.event_venue);
                TextView time = (TextView) rootView.findViewById(R.id.time);
                ImageView clockIcon = (ImageView) rootView.findViewById(R.id.clock_icon);
                ImageView locationIcon = (ImageView) rootView.findViewById(R.id.location_icon);
                if(eventVenue.getCurrentTextColor()==getResources().getColor(R.color.yellow))
                {
                    notification.setImageResource(R.drawable.ic_notifications_none_white_24px);
                    eventVenue.setTextColor(getResources().getColor(R.color.white));
                    time.setTextColor(getResources().getColor(R.color.white));
                    clockIcon.setImageResource(R.drawable.ic_access_time_white_24px);
                    locationIcon.setImageResource(R.drawable.ic_place_white_24px);
                }
                else
                {
                    eventVenue.setTextColor(getResources().getColor(R.color.yellow));
                    time.setTextColor(getResources().getColor(R.color.yellow));
                    clockIcon.setImageResource(R.drawable.ic_access_time_yellow_24px);
                    locationIcon.setImageResource(R.drawable.ic_place_yellow_24px);
                    notification.setImageResource(R.drawable.ic_notifications_white_24px);
                }

            }
        });
     return rootView;

    }
}
