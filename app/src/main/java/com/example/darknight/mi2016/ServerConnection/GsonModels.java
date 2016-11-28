package com.example.darknight.mi2016.ServerConnection;

import java.util.List;

/**
 * Created by sajalnarang on 26/11/16.
 */

public class GsonModels {
    public class GenreResponse {
        private int count;
        private List<Genre> genreList;

        public GenreResponse(int count, List<Genre> genreList) {

            this.count = count;
            this.genreList = genreList;
        }

        public int getCount() {
            return count;
        }

        public List<Genre> getGenreList() {
            return genreList;
        }
    }

    public class Genre {
        private String name;
        private String iconUrl;

        public Genre(String name, String iconUrl) {

            this.name = name;
            this.iconUrl = iconUrl;
        }

        public String getName() {
            return name;
        }

        public String getIconUrl() {
            return iconUrl;
        }
    }

    public class EventResponse {
        private int count;
        private List<Event> eventList;

        public EventResponse(int count, List<Event> eventList) {
            this.count = count;
            this.eventList = eventList;
        }

        public int getCount() {
            return count;
        }

        public List<Event> getEventList() {
            return eventList;
        }
    }

    public class Event {

    }
}
