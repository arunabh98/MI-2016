package com.example.darknight.mi2016;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public final class GridAdapter extends BaseAdapter {
    private final List<Item> mItems = new ArrayList<Item>();
    private final LayoutInflater mInflater;

    public GridAdapter(Context context) {
        mInflater = LayoutInflater.from(context);

        mItems.add(new Item("Events", R.drawable.events));
        mItems.add(new Item("Hospitality", R.drawable.hospitality_256));
        mItems.add(new Item("Map", R.drawable.map_256));
        mItems.add(new Item("Going", R.drawable.going_256));
        mItems.add(new Item("Schedule", R.drawable.schedule_256));
        mItems.add(new Item("FAQ", R.drawable.faq_256));
        mItems.add(new Item("Contact Us", R.drawable.contactus_256));
        mItems.add(new Item("Logout", R.drawable.logout_256));
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Item getItem(int i) {
        return mItems.get(i);
    }

    @Override
    public long getItemId(int i) {
        return mItems.get(i).drawableId;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View v = view;
        ImageView picture;
        TextView name;

        if (v == null) {
            v = mInflater.inflate(R.layout.grid_item, viewGroup, false);
            v.setTag(R.id.picture, v.findViewById(R.id.picture));
            v.setTag(R.id.text, v.findViewById(R.id.text));
        }

        picture = (ImageView) v.getTag(R.id.picture);
        name = (TextView) v.getTag(R.id.text);

        Item item = getItem(i);

        picture.setImageResource(item.drawableId);
        name.setText(item.name);

        return v;
    }

    private static class Item {
        public final String name;
        public final int drawableId;

        Item(String name, int drawableId) {
            this.name = name;
            this.drawableId = drawableId;
        }
    }
}