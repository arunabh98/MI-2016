package com.iitb.moodindigo.mi2016;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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


/**
 * A simple {@link Fragment} subclass.
 */
public class EventPageFragment extends Fragment implements View.OnClickListener {

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
        final ImageView eventImage = (ImageView) rootView.findViewById(R.id.event_image);
        final TextView title = (TextView) rootView.findViewById(R.id.event_name);
        final TextView genre = (TextView) rootView.findViewById(R.id.genre_name);
        goingSharedPreferencesEditor = getContext().getSharedPreferences("GOING", Context.MODE_PRIVATE).edit();
        title.setText(event.getTitle());
        String category = event.getCategory();
        if (category.equalsIgnoreCase("proshows")) {
            eventImage.setImageResource(R.drawable.proshows);
        } else if (category.equalsIgnoreCase("workshops")) {
            eventImage.setImageResource(R.drawable.workshops);
        } else if (category.equalsIgnoreCase("competitions")) {
            eventImage.setImageResource(R.drawable.compi);
        } else if (category.equalsIgnoreCase("informals")) {
            eventImage.setImageResource(R.drawable.informals);
        } else if (category.equalsIgnoreCase("concerts")) {
            eventImage.setImageResource(R.drawable.concerts);
        }
        String timeString = String.valueOf(event.getTime());
        if (timeString.length() == 3 || timeString.length() == 7)
            timeString = "0" + timeString;
        if (timeString.length() == 4)
            time.setText(timeString.substring(0, 2) + ":" + timeString.substring(2, 4));
        if (timeString.length() == 8)
            time.setText(timeString.substring(0, 2) + ":" + timeString.substring(2, 4) + "-" + timeString.substring(4, 6) + ":" + timeString.substring(6, 8));
        description.setText(event.getDescription());
        eventVenue.setText(event.getLocation());
        genre.setText(event.getCategory());
        getActivity().setTitle("Event");
        Cache.setDay(event.getDay());
        Cache.setCategoryPosition(event.getCategory());
        Cache.setGoingdayPosition(event.getDay());
        if (goingListGson == null) {
            ;
        } else if (goingListGson.contains(event)) {
            eventVenue.setTextColor(Color.parseColor("#DEB951"));
            time.setTextColor(Color.parseColor("#DEB951"));
            clockIcon.setColorFilter(Color.parseColor("#DEB951"));
            locationIcon.setColorFilter(Color.parseColor("#DEB951"));
            notification.setColorFilter(Color.parseColor("#DEB951"));
            notification.setImageResource(R.drawable.ic_notifications_black_48dp);
        }
        eventVenue.setOnClickListener(this);
        locationIcon.setOnClickListener(this);
        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (eventVenue.getCurrentTextColor() != Color.parseColor("#DEB951")) {
                    eventVenue.setTextColor(Color.parseColor("#DEB951"));
                    time.setTextColor(Color.parseColor("#DEB951"));
                    clockIcon.setColorFilter(Color.parseColor("#DEB951"));
                    locationIcon.setColorFilter(Color.parseColor("#DEB951"));
                    notification.setColorFilter(Color.parseColor("#DEB951"));
                    notification.setImageResource(R.drawable.ic_notifications_black_48dp);
                    Cache.addToGoingList(event);
                } else {
                    eventVenue.setTextColor(Color.parseColor("#FFFFFF"));
                    time.setTextColor(Color.parseColor("#FFFFFF"));
                    clockIcon.setColorFilter(Color.parseColor("#FFFFFF"));
                    locationIcon.setColorFilter(Color.parseColor("#FFFFFF"));
                    notification.setColorFilter(Color.parseColor("#FFFFFF"));
                    notification.setImageResource(R.drawable.ic_notifications_none_black_48dp);
                    Cache.removeFromGoingList(event);
                }
                String goingEventsListJson = (new Gson()).toJson(Cache.getGoingEventsList());
                goingSharedPreferencesEditor.putString("GOING_LIST", goingEventsListJson);
                goingSharedPreferencesEditor.apply();
            }
        });
        return rootView;

    }

    @Override
    public void onClick(View v) {
        MapFragment mapFragment = new MapFragment(event);
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack("singleevent");
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.relativelayout_for_fragment, mapFragment, mapFragment.getTag());
        transaction.commit();
    }
}
