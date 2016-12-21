package com.iitb.moodindigo.mi2016.ServerConnection;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;
import com.iitb.moodindigo.mi2016.Place;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class GsonModels {
    public static class Day {
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

        public Day(int date) {
            switch (date) {
                case 0:
                    this._0 = false;
                    this._1 = true;
                    this._2 = false;
                    this._3 = false;
                    this._4 = false;
                    break;
                case 1:
                    this._0 = false;
                    this._1 = false;
                    this._2 = true;
                    this._3 = false;
                    this._4 = false;
                    break;
                case 2:
                    this._0 = false;
                    this._1 = false;
                    this._2 = false;
                    this._3 = true;
                    this._4 = false;
                    break;
                case 3:
                    this._0 = false;
                    this._1 = false;
                    this._2 = false;
                    this._3 = false;
                    this._4 = true;
                    break;
            }
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
            if (get_0() != null && get_0()) {
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
            if (obj instanceof Day) {
                Day day = (Day) obj;
                if (_0 == day._0 && _1 == day._1 && _2 == day._2 && _3 == day._3 && _4 == day._4)
                    return true;
            }
            return false;
        }
    }

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

        public void setDay(Day day) {
            this.day = day;
        }

        public Date getDate() {
            Date date = new Date();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String time = String.valueOf(getTime());
            if (time.length() == 3 || time.length() == 7)
                time = "0" + time;
            time = time.substring(0, 4);
            try {
                date = dateFormat.parse("2016-12-" + String.valueOf(22 + getActualDay()) + " " + time.substring(0, 2) + ":" + time.substring(2, 4) + ":00");
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return date;
        }

        public Integer getActualDay() {
            return day.getSaneDateBecauseWebValonNeHagDiya();
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Event) {
                Event event = (Event) obj;
                if (title.equals(event.title) && day.equals(event.day) && time == event.time)
                    return true;
            }
            return false;
        }

        public boolean compareByTime(Event event) {
            if (time < event.time)
                return true;
            return false;
        }

        public Place getPlace() {
            Place place = null;
            switch (map_loc) {
                case "PCSA":
                    place = new Place(new LatLng(19.132348, 72.915785), "LT PCSA");
                    break;
                case "lch":
                    place = new Place(new LatLng(19.130735, 72.916900), "Lecture Hall Complex (LCH)");
                    break;
                case "Convo":
                    place = new Place(new LatLng(19.131973, 72.914285), "Convocation Hall");
                    break;
                case "SAC parking lot":
                    place = new Place(new LatLng(19.135771, 72.914428), "SAC Parking Lot");
                    break;
                case "fck":
                    place = new Place(new LatLng(19.130480, 72.915724), "FC Kohli Auditorium (FCK)");
                    break;
                case "NCC":
                    place = new Place(new LatLng(19.133572, 72.913399), "NCC Grounds");
                    break;
                case "OSP":
                    place = new Place(new LatLng(19.135572, 72.914017), "Old Swimming Pool");
                    break;
                case "KV":
                    place = new Place(new LatLng(19.129113, 72.918408), "Kendriya Vidyalaya (KV)");
                    break;
                case "SOM":
                    place = new Place(new LatLng(19.131651, 72.915758), "SJM SOM");
                    break;
                case "h 10 t point":
                    place = new Place(new LatLng(19.129574, 72.915394), "H10 T-Point");
                    break;
                case "PCSA back lawns":
                    place = new Place(new LatLng(19.132030, 72.915920), "PCSA Backlawns");
                    break;
                case "MB lawns":
                    place = new Place(new LatLng(19.132635, 72.915716), "MB Lawns");
                    break;
                case "OAT":
                    place = new Place(new LatLng(19.135045, 72.913401), "Open Air Theatre (OAT)");
                    break;
                case "liby road":
                    place = new Place(new LatLng(19.134299, 72.915376), "Library Road");
                    break;
                case "ppl":
                    place = new Place(new LatLng(19.130005, 72.916704), "Physics Parking Lot");
                    break;
                case "Gymkhana":
                    place = new Place(new LatLng(19.134446, 72.912217), "Gymkhana Grounds");
                    break;
            }
            return place;
        }
    }

    public class DistanceMatrix {
        ArrayList<String> destination_addresses;
        ArrayList<String> origin_addresses;
        ArrayList<Rows> rows;
        String status;

        public ArrayList<Rows> getRows() {
            return rows;
        }

        public String getStatus() {
            return status;
        }
    }

    public class Rows {
        ArrayList<Element> elements;

        public ArrayList<Element> getElements() {
            return elements;
        }
    }

    public class Element {
        Distance distance;
        Duration duration;
        String status;

        public Distance getDistance() {
            return distance;
        }

        public Duration getDuration() {
            return duration;
        }

        public String getStatus() {
            return status;
        }
    }

    public class Distance {
        String text;
        int value;

        public String getText() {
            return text;
        }
    }

    public class Duration {
        String text;
        int value;

        public String getText() {
            return text;
        }
    }
}
