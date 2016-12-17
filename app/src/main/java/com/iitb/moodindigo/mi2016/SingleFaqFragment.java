package com.iitb.moodindigo.mi2016;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class SingleFaqFragment extends Fragment {

    private faqAdapter itemsAdapter;

    public SingleFaqFragment() {
        // Required empty public constructor
    }

    public SingleFaqFragment(Context context,ArrayList<Faq> items){

        itemsAdapter = new faqAdapter(context, items);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View single_faq_page = (View) inflater.inflate(R.layout.fragment_singlefaq, container, false);

        ListView faqlist= (ListView) single_faq_page.findViewById(R.id.singlefaqlist);
        faqlist.setAdapter(itemsAdapter);

        return  single_faq_page;
    }

    public class faqAdapter extends ArrayAdapter<Faq>{
        public faqAdapter(Context context, ArrayList<Faq> faq_items) {
            super(context, 0, faq_items);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            Faq faq = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_faq, parent, false);
            }
            // Lookup view for data population
            TextView Question = (TextView) convertView.findViewById(R.id.qn);
            TextView Answer = (TextView) convertView.findViewById(R.id.ans);
            // Populate the data into the template view using the data object
            Question.setText(faq.getQuestion());
            Answer.setText(faq.getAnswer());
            // Return the completed view to render on screen
            return convertView;
        }
    }
}

