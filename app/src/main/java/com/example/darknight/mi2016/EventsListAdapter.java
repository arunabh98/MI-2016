package com.example.darknight.mi2016;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.darknight.mi2016.ServerConnection.GsonModels;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by sajalnarang on 28/11/16.
 */

public class EventsListAdapter extends RecyclerView.Adapter<EventsListAdapter.ViewHolder> {

    private List<GsonModels.Event> eventList;
    private Context context;
    private ItemCLickListener itemCLickListener;
    private SharedPreferences.Editor goingSharedPreferencesEditor;
    private SharedPreferences goingPreferences;

    public EventsListAdapter(List<GsonModels.Event> eventList, ItemCLickListener itemCLickListener) {
        this.eventList = eventList;
        this.itemCLickListener = itemCLickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View userView = inflater.inflate(R.layout.event_list_row, parent, false);
        final EventsListAdapter.ViewHolder userViewHolder = new EventsListAdapter.ViewHolder(userView);
        userView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCLickListener.onItemClick(v, userViewHolder.getAdapterPosition());
            }
        });
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        goingSharedPreferencesEditor = context.getSharedPreferences("GOING", Context.MODE_PRIVATE).edit();
        final GsonModels.Event selectedEvent = eventList.get(position);
        holder.eventName.setText(selectedEvent.getTitle());
        holder.eventVenue.setText(selectedEvent.getLocation());
        String time = String.valueOf(selectedEvent.getTime());
        if (time.length() == 3)
            time = "0" + time;
        holder.eventTime.setText(time.substring(0, 2) + ":" + time.substring(2));
        holder.bookmarkIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (holder.eventName.getCurrentTextColor() == Color.parseColor("#FFFFFF")) {
                    holder.eventName.setTextColor(Color.parseColor("#FFC107"));
                    holder.eventVenue.setTextColor(Color.parseColor("#FFC107"));
                    holder.eventTime.setTextColor(Color.parseColor("#FFC107"));
                    holder.venueIcon.setColorFilter(Color.parseColor("#FFC107"));
                    holder.timeIcon.setColorFilter(Color.parseColor("#FFC107"));
                    holder.bookmarkIcon.setColorFilter(Color.parseColor("#FFC107"));
                    holder.bookmarkIcon.setImageResource(R.drawable.ic_notifications_black_48dp);
                    BookmarkedEvents.addToGoingList(selectedEvent);
                } else {
                    holder.eventName.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.eventVenue.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.eventTime.setTextColor(Color.parseColor("#FFFFFF"));
                    holder.venueIcon.setColorFilter(Color.parseColor("#FFFFFF"));
                    holder.timeIcon.setColorFilter(Color.parseColor("#FFFFFF"));
                    holder.bookmarkIcon.setColorFilter(Color.parseColor("#FFFFFF"));
                    holder.bookmarkIcon.setImageResource(R.drawable.ic_notifications_none_black_48dp);
                    BookmarkedEvents.removeFromGoingList(selectedEvent);
                }
                String goingEventsListJson = (new Gson()).toJson(BookmarkedEvents.getGoingEventsList());
                Log.d("TAG", goingEventsListJson);
                goingSharedPreferencesEditor.putString("GOING_LIST", goingEventsListJson);
                goingSharedPreferencesEditor.apply();
            }
        });
        goingPreferences = context.getSharedPreferences("GOING", Context.MODE_PRIVATE);
        String goingList = goingPreferences.getString("GOING_LIST", null);
        Type type = new TypeToken<List<GsonModels.Event>>(){}.getType();
        List<GsonModels.Event> goingListGson = (new Gson()).fromJson(goingList, type);
        if (goingListGson == null) {
            ;
        } else {
            if (goingListGson.contains(selectedEvent)) {
                holder.eventName.setTextColor(Color.parseColor("#FFC107"));
                holder.eventVenue.setTextColor(Color.parseColor("#FFC107"));
                holder.eventTime.setTextColor(Color.parseColor("#FFC107"));
                holder.venueIcon.setColorFilter(Color.parseColor("#FFC107"));
                holder.timeIcon.setColorFilter(Color.parseColor("#FFC107"));
                holder.bookmarkIcon.setColorFilter(Color.parseColor("#FFC107"));
                holder.bookmarkIcon.setImageResource(R.drawable.ic_notifications_black_48dp);
            }
        }
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView eventName;
        private TextView eventVenue;
        private TextView eventTime;
        private ImageView venueIcon;
        private ImageView timeIcon;
        private ImageView bookmarkIcon;

        public ViewHolder(View itemView) {
            super(itemView);

            eventName = (TextView) itemView.findViewById(R.id.event_name);
            eventVenue = (TextView) itemView.findViewById(R.id.event_venue);
            eventTime = (TextView) itemView.findViewById(R.id.event_time);
            venueIcon = (ImageView) itemView.findViewById(R.id.venue_icon);
            timeIcon = (ImageView) itemView.findViewById(R.id.time_icon);
            bookmarkIcon = (ImageView) itemView.findViewById(R.id.bookmark);
        }
    }
}
