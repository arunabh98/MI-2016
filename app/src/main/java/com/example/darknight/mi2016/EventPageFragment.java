package com.example.darknight.mi2016;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class EventPageFragment extends Fragment {


    public EventPageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        final View rootView = inflater.inflate(R.layout.fragment_event_page, container, false);
        final ImageButton imageButton = (ImageButton) rootView.findViewById(R.id.myIconButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) rootView.findViewById(R.id.Event_venue);
                TextView textView2 = (TextView) rootView.findViewById(R.id.Time);
                ImageView imageView = (ImageView) rootView.findViewById(R.id.Clock_icon);
                ImageView imageView2 = (ImageView) rootView.findViewById(R.id.Location_icon);
                if(textView.getCurrentTextColor()==getResources().getColor(R.color.yellow))
                {
                    imageButton.setImageResource(R.drawable.ic_notifications_none_white_24px);
                    textView.setTextColor(getResources().getColor(R.color.white));
                    textView2.setTextColor(getResources().getColor(R.color.white));
                    imageView.setImageResource(R.drawable.ic_access_time_white_24px);
                    imageView2.setImageResource(R.drawable.ic_place_white_24px);
                }
                else
                {
                    textView.setTextColor(getResources().getColor(R.color.yellow));
                    textView2.setTextColor(getResources().getColor(R.color.yellow));
                    imageView.setImageResource(R.drawable.ic_access_time_yellow_24px);
                    imageView2.setImageResource(R.drawable.ic_place_yellow_24px);
                    imageButton.setImageResource(R.drawable.ic_notifications_white_24px);
                }

            }
        });
     return rootView;

    }
}
