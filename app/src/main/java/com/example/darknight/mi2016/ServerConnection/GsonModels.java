package com.example.darknight.mi2016.ServerConnection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sajalnarang on 26/11/16.
 */

public class GsonModels {
    public class Event {
        private String _id;
        private String category;
        private String title;
        private String short_des;
        private String description;
        private String location;
        private String map_loc;
        private ArrayList<Boolean> day;
        private String time;

        public Event(String _id, String category, String title, String short_des, String description, String location, String map_loc, ArrayList<Boolean> day, String time) {
            this._id = _id;
            this.category = category;
            this.title = title;
            this.short_des = short_des;
            this.description = description;
            this.location = location;
            this.map_loc = map_loc;
            this.day = day;
            this.time = time;
        }

        public String get_id() {
            return _id;
        }

        public void set_id(String _id) {
            this._id = _id;
        }

        public String getCategory() {
            return category;
        }

        public void setCategory(String category) {
            this.category = category;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getShort_des() {
            return short_des;
        }

        public void setShort_des(String short_des) {
            this.short_des = short_des;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getMap_loc() {
            return map_loc;
        }

        public void setMap_loc(String map_loc) {
            this.map_loc = map_loc;
        }

        public ArrayList<Boolean> getDay() {
            return day;
        }

        public void setDay(ArrayList<Boolean> day) {
            this.day = day;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
    public class Genre {
        private String name;
        private String iconUrl;

        public String getIconUrl() {
            return iconUrl;
        }

        public String getName() {
            return name;
        }
    }
    public class GenreResponse {
        private int count;
        private List<Genre> genreList;

        public int getCount() {
            return count;
        }

        public List<Genre> getGenreList() {
            return genreList;
        }
    }
}
