package com.example.darknight.mi2016;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.darknight.mi2016.ServerConnection.GsonModels;

import java.util.List;

/**
 * Created by sajalnarang on 28/11/16.
 */

public class EventsListAdapter extends RecyclerView.Adapter<EventsListAdapter.ViewHolder> {

    private List<GsonModels.Event> eventList;
    private Context context;
    private ItemCLickListener itemCLickListener;

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
    public void onBindViewHolder(ViewHolder holder, int position) {
        GsonModels.Event selectedEvent = eventList.get(position);
        //TODO: inflate event_list_row
    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }
    }

}
