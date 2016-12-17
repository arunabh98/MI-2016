package com.iitb.moodindigo.mi2016;

import com.iitb.moodindigo.mi2016.ServerConnection.GsonModels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sajalnarang on 16/12/16.
 */

public class BookmarkedEvents {
    private static List<GsonModels.Event> goingEventsList = new ArrayList<>();

    public static List<GsonModels.Event> getGoingEventsList() {
        return goingEventsList;
    }

    public static void setGoingEventsList(List<GsonModels.Event> goingEventsList) {
        BookmarkedEvents.goingEventsList = goingEventsList;
    }

    public static void addToGoingList(GsonModels.Event event) {
        goingEventsList.add(event);
    }

    public static void removeFromGoingList(GsonModels.Event event) {
        goingEventsList.remove(event);
    }
}
