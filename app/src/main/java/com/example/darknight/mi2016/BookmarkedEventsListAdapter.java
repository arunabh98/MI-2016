package com.example.darknight.mi2016;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.darknight.mi2016.ServerConnection.GsonModels;

import java.util.List;

/**
 * Created by sajalnarang on 16/12/16.
 */

public class BookmarkedEventsListAdapter extends RecyclerView.Adapter<BookmarkedEventsListAdapter.ViewHolder> {
    private List<GsonModels.Event> eventList;
    private Context context;
    private ItemCLickListener itemCLickListener;

    public BookmarkedEventsListAdapter(List<GsonModels.Event> eventList, ItemCLickListener itemCLickListener) {
        this.eventList = eventList;
        this.itemCLickListener = itemCLickListener;
    }

    @Override
    public BookmarkedEventsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View userView = inflater.inflate(R.layout.bookmarked_event_list_row, parent, false);
        final BookmarkedEventsListAdapter.ViewHolder userViewHolder = new BookmarkedEventsListAdapter.ViewHolder(userView);
        userView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemCLickListener.onItemClick(v, userViewHolder.getAdapterPosition());
            }
        });
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(final BookmarkedEventsListAdapter.ViewHolder holder, int position) {
        GsonModels.Event selectedEvent = eventList.get(position);
        holder.eventName.setText(selectedEvent.getTitle());
        holder.eventVenue.setText(selectedEvent.getLocation());
        String time = String.valueOf(selectedEvent.getTime());
        if (time.length() == 3)
            time = "0" + time;
        holder.eventTime.setText(time.substring(0, 2) + ":" + time.substring(2));
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

        public ViewHolder(View itemView) {
            super(itemView);

            eventName = (TextView) itemView.findViewById(R.id.event_name);
            eventVenue = (TextView) itemView.findViewById(R.id.event_venue);
            eventTime = (TextView) itemView.findViewById(R.id.event_time);
            venueIcon = (ImageView) itemView.findViewById(R.id.venue_icon);
            timeIcon = (ImageView) itemView.findViewById(R.id.time_icon);
        }
    }
}
