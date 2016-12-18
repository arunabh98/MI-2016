package com.iitb.moodindigo.mi2016;

import com.iitb.moodindigo.mi2016.ServerConnection.GsonModels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sajalnarang on 16/12/16.
 */

public class Cache {
    private static List<GsonModels.Event> goingEventsList = new ArrayList<>();
    private static boolean sendEventRequest = true;
    private static List<GsonModels.Event> eventList = new ArrayList<>();
    private static List<GsonModels.Event> daysList1 = new ArrayList<>();
    private static List<GsonModels.Event> daysList2 = new ArrayList<>();
    private static List<GsonModels.Event> daysList3 = new ArrayList<>();
    private static List<GsonModels.Event> daysList4 = new ArrayList<>();

    public static List<GsonModels.Event> getGoingEventsList() {
        return goingEventsList;
    }
    public static List<GsonModels.Event> getDaysList1() {
        return daysList1;
    }
    public static List<GsonModels.Event> getDaysList2() {
        return daysList2;
    }
    public static List<GsonModels.Event> getDaysList3() {
        return daysList3;
    }
    public static List<GsonModels.Event> getDaysList4() {
        return daysList4;
    }

    public static void setGoingEventsList(List<GsonModels.Event> goingEventsList) {
        Cache.goingEventsList = goingEventsList;
    }

    public static void addToGoingList(GsonModels.Event event) {
        goingEventsList.add(event);
    }

    public static void removeFromGoingList(GsonModels.Event event) {
        goingEventsList.remove(event);
    }

    public static boolean isSendEventRequest() {
        return sendEventRequest;
    }

    public static void setSendEventRequest(boolean sendEventRequest) {
        Cache.sendEventRequest = sendEventRequest;
    }

    public static List<GsonModels.Event> getEventList() {

        return eventList;
    }

    public static void setEventList(List<GsonModels.Event> eventList) {
        Cache.eventList = eventList;
    }

    public static void addToEventList(GsonModels.Event event) {
        eventList.add(event);
    }

    public static void removeFromEventList(GsonModels.Event event) {
        eventList.remove(event);
    }

    public static void setDaysList1(List<GsonModels.Event> daysList1) {
        Cache.daysList1 = daysList1;
    }
    public static void setDaysList2(List<GsonModels.Event> daysList2) {
        Cache.daysList2 = daysList2;
    }
    public static void setDaysList3(List<GsonModels.Event> daysList3) {
        Cache.daysList3 = daysList3;
    }
    public static void setDaysList4(List<GsonModels.Event> daysList4) {
        Cache.daysList4 = daysList4;
    }
}
