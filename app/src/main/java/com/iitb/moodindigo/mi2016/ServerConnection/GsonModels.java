package com.iitb.moodindigo.mi2016.ServerConnection;

import com.google.gson.annotations.SerializedName;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        private Date date;
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

        public Date getDate() {
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = String.valueOf(getTime());
            if (time.length() == 3)
                time = "0" + time;
            try {
                date = dateFormat.parse("2016-12-" + String.valueOf(22 + getActualDay()) + " " + time.substring(0,2) + ":" + time.substring(2,4) + ":00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
        }

        public Integer getActualDay() { return day.getSaneDateBecauseWebValonNeHagDiya(); }

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

        public boolean compareByTime(Event event) {
            if(time < event.time)
                return true;
            return false;
        }
    }
    public class Day {
        @SerializedName("0")
        private Boolean _0;
        @SerializedName("1")
        private Boolean _1;
        @SerializedName("2")
        private Boolean _2;
        @SerializedName("3")
        private Boolean _3;
        @SerializedName("4")
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

        public Integer getSaneDateBecauseWebValonNeHagDiya() {
            if (get_0() != null && get_0()){
                return 0;
            } else if (get_1() != null && get_1()) {
                return 1;
            } else if (get_2() != null && get_2()) {
                return 2;
            } else if (get_3() != null && get_3()) {
                return 3;
            } else if (get_4() != null && get_4()) {
                return 4;
            } else
                return -1;
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
