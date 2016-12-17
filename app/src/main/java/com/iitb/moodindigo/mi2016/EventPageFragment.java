package com.iitb.moodindigo.mi2016;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iitb.moodindigo.mi2016.ServerConnection.GsonModels;

import java.lang.reflect.Type;
import java.util.List;

import static com.iitb.moodindigo.mi2016.BookmarkedEvents.removeFromGoingList;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventPageFragment extends Fragment {

    GsonModels.Event event;
    private SharedPreferences.Editor goingSharedPreferencesEditor;
    private SharedPreferences goingPreferences;

    public EventPageFragment() {
        // Required empty public constructor
    }

    public EventPageFragment(Context context, GsonModels.Event event) {
        this.event = event;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        goingPreferences = getContext().getSharedPreferences("GOING", Context.MODE_PRIVATE);
        final String goingList = goingPreferences.getString("GOING_LIST", null);
        Type type = new TypeToken<List<GsonModels.Event>>() {
        }.getType();
        final List<GsonModels.Event> goingListGson = (new Gson()).fromJson(goingList, type);
        final View rootView = inflater.inflate(R.layout.fragment_event_page, container, false);
        final ImageButton notification = (ImageButton) rootView.findViewById(R.id.icon_button);
        final TextView eventVenue = (TextView) rootView.findViewById(R.id.event_venue);
        final TextView time = (TextView) rootView.findViewById(R.id.time);
        final ImageView clockIcon = (ImageView) rootView.findViewById(R.id.clock_icon);
        final ImageView locationIcon = (ImageView) rootView.findViewById(R.id.location_icon);
        final TextView description = (TextView) rootView.findViewById(R.id.description);
        final TextView title = (TextView) rootView.findViewById(R.id.event_name);
        final TextView genre = (TextView) rootView.findViewById(R.id.genre_name);
        title.setText(event.getTitle());
        String timeString = String.valueOf(event.getTime());
        if (timeString.length() == 3)
            timeString = "0" + timeString;
        time.setText(timeString.substring(0, 2) + ":" + timeString.substring(2));
        description.setText(event.getDescription());
        eventVenue.setText(event.getLocation());
        genre.setText(event.getCategory());
        if (goingListGson == null) {
            ;
        } else if (goingListGson.contains(event)) {
            eventVenue.setTextColor(getResources().getColor(R.color.yellow));
            time.setTextColor(getResources().getColor(R.color.yellow));
            clockIcon.setImageResource(R.drawable.ic_access_time_yellow_24px);
            locationIcon.setImageResource(R.drawable.ic_place_yellow_24px);
            notification.setImageResource(R.drawable.ic_notifications_white_24px);
        }
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (goingListGson == null) {
                    ;
                } else {
                    if (goingListGson.contains(event)) {
                        notification.setImageResource(R.drawable.ic_notifications_none_white_24px);
                        eventVenue.setTextColor(getResources().getColor(R.color.white));
                        time.setTextColor(getResources().getColor(R.color.white));
                        clockIcon.setImageResource(R.drawable.ic_access_time_white_24px);
                        locationIcon.setImageResource(R.drawable.ic_place_white_24px);
                        BookmarkedEvents.removeFromGoingList(event);
                    } else {
                        eventVenue.setTextColor(getResources().getColor(R.color.yellow));
                        time.setTextColor(getResources().getColor(R.color.yellow));
                        clockIcon.setImageResource(R.drawable.ic_access_time_yellow_24px);
                        locationIcon.setImageResource(R.drawable.ic_place_yellow_24px);
                        notification.setImageResource(R.drawable.ic_notifications_white_24px);
                        BookmarkedEvents.addToGoingList(event);
                    }
                }
                goingSharedPreferencesEditor = getContext().getSharedPreferences("GOING", Context.MODE_PRIVATE).edit();
                String goingEventsListJson = (new Gson()).toJson(BookmarkedEvents.getGoingEventsList());
                goingSharedPreferencesEditor.putString("GOING_LIST", goingEventsListJson);
                goingSharedPreferencesEditor.apply();
            }
        });
        return rootView;

    }
}
