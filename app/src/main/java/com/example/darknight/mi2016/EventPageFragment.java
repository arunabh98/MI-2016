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
        final ImageButton ib = (ImageButton) rootView.findViewById(R.id.myIconButton);
        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) rootView.findViewById(R.id.myTextView1);
                TextView textView2 = (TextView) rootView.findViewById(R.id.myTextView2);
                ImageView imageView = (ImageView) rootView.findViewById(R.id.myClockIcon);
                ImageView imageView2 = (ImageView) rootView.findViewById(R.id.myLocationIcon);
                if(textView.getCurrentTextColor()==getResources().getColor(R.color.yellow))
                {
                    ib.setImageResource(R.drawable.ic_notifications_none_white_24px);
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
                    ib.setImageResource(R.drawable.ic_notifications_white_24px);
                }

            }
        });
     return rootView;

    }
}
