package com.iitb.moodindigo.mi2016.ServerConnection;

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
        private Day day;
        private int time;

        public Event(String _id, String category, String title, String short_des, String description, String location, String map_loc, Day day, int time) {
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

        public Day getDay() {
            return day;
        }

        public void setDay(Day day) {
            this.day = day;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Event) {
                Event event = (Event) obj;
                if (title.equals(event.title) && day.equals(event.day) && time == event.time)
                    return true;
            }
            return false;
        }
    }
    public class Day {
        private Boolean _0;
        private Boolean _1;
        private Boolean _2;
        private Boolean _3;
        private Boolean _4;

        public Day(Boolean _0, Boolean _1, Boolean _2, Boolean _3, Boolean _4) {
            this._0 = _0;
            this._1 = _1;
            this._2 = _2;
            this._3 = _3;
            this._4 = _4;
        }

        public Boolean get_0() {
            return _0;
        }

        public void set_0(Boolean _0) {
            this._0 = _0;
        }

        public Boolean get_1() {
            return _1;
        }

        public void set_1(Boolean _1) {
            this._1 = _1;
        }

        public Boolean get_2() {
            return _2;
        }

        public void set_2(Boolean _2) {
            this._2 = _2;
        }

        public Boolean get_3() {
            return _3;
        }

        public void set_3(Boolean _3) {
            this._3 = _3;
        }

        public Boolean get_4() {
            return _4;
        }

        public void set_4(Boolean _4) {
            this._4 = _4;
        }

        @Override
        public boolean equals(Object obj) {
            if(obj instanceof Day) {
                Day day = (Day) obj;
                if(_0 == day._0 && _1 == day._1 && _2 == day._2 && _3 == day._3 && _4 == day._4)
                    return true;
            }
            return false;
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
