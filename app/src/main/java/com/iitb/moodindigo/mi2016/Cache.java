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
    private static GsonModels.Day day;
    private static int categoryPosition;
    private static int listPosition;
    private static int goingdayPosition;
    private static int goinglistPosition;

    public static List<GsonModels.Event> getGoingEventsList() {
        return goingEventsList;
    }

    public static void setGoingEventsList(List<GsonModels.Event> goingEventsList) {
        Cache.goingEventsList = goingEventsList;
    }

    public static List<GsonModels.Event> getDaysList1() {
        return daysList1;
    }

    public static void setDaysList1(List<GsonModels.Event> daysList1) {
        Cache.daysList1 = daysList1;
    }

    public static List<GsonModels.Event> getDaysList2() {
        return daysList2;
    }

    public static void setDaysList2(List<GsonModels.Event> daysList2) {
        Cache.daysList2 = daysList2;
    }

    public static List<GsonModels.Event> getDaysList3() {
        return daysList3;
    }

    public static void setDaysList3(List<GsonModels.Event> daysList3) {
        Cache.daysList3 = daysList3;
    }

    public static List<GsonModels.Event> getDaysList4() {
        return daysList4;
    }

    public static void setDaysList4(List<GsonModels.Event> daysList4) {
        Cache.daysList4 = daysList4;
    }

    public static GsonModels.Day getDay() {
        return day;
    }

    public static void setDay(GsonModels.Day day) {
        Cache.day = day;
    }

    public static int getCategoryPosition() {
        return categoryPosition;
    }

    public static void setCategoryPosition(String category) {
        if (category.equalsIgnoreCase("proshows")) {
            categoryPosition = 0;
        } else if (category.equalsIgnoreCase("workshops")) {
            categoryPosition = 1;
        } else if (category.equalsIgnoreCase("competitions")) {
            categoryPosition = 2;
        } else if (category.equalsIgnoreCase("informals")) {
            categoryPosition = 3;
        } else if (category.equalsIgnoreCase("concerts")) {
            categoryPosition = 4;
        }
    }

    public static int getGoingdayPosition() {
        return goingdayPosition;
    }

    public static void setGoingdayPosition(GsonModels.Day day) {
        if (day.get_1()) {
            Cache.goingdayPosition = 0;
        } else if (day.get_2()) {
            Cache.goingdayPosition = 1;
        } else if (day.get_3()) {
            Cache.goingdayPosition = 2;
        } else if (day.get_4()) {
            Cache.goingdayPosition = 3;
        }
    }

    public static int getGoinglistPosition() {
        return goinglistPosition;
    }

    public static void setGoinglistPosition(int goinglistPosition) {
        Cache.goinglistPosition = goinglistPosition;
    }

    public static void addToGoingList(GsonModels.Event event) {
        if (goingEventsList == null) {
            goingEventsList = new ArrayList<>();
            goingEventsList.add(event);
        } else {
            goingEventsList.add(event);
        }
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
        if (eventList == null) {
            eventList = new ArrayList<>();
            eventList.add(event);
        } else {
            eventList.add(event);
        }
    }

    public static void removeFromEventList(GsonModels.Event event) {
        eventList.remove(event);
    }

    public static int getListPosition() {
        return listPosition;
    }

    public static void setListPosition(int listPosition) {
        Cache.listPosition = listPosition;
    }
}
