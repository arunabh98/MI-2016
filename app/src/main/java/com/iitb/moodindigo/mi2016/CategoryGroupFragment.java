package com.iitb.moodindigo.mi2016;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.iitb.moodindigo.mi2016.ServerConnection.GsonModels;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class CategoryGroupFragment extends Fragment {

    View categorygroupview;
    private List<GsonModels.Event> dayList = new ArrayList<>();
    private Integer day;
    private List<GsonModels.Event> compi = new ArrayList<>();
    private List<GsonModels.Event> workshops = new ArrayList<>();
    private List<GsonModels.Event> proshows = new ArrayList<>();
    private List<GsonModels.Event> informals = new ArrayList<>();
    private List<GsonModels.Event> concerts = new ArrayList<>();

    public CategoryGroupFragment() {
        // Required empty public constructor
    }

    public CategoryGroupFragment(List<GsonModels.Event> dayList, Integer day) {
        Log.d("Category Group", "Constructor");
        this.dayList = dayList;
        this.day = day;
        generatecategories(dayList);
        Log.d("Category Group", "generated");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Day " + Integer.toString(day));
        // Inflate the layout for this fragment
        categorygroupview = (View) inflater.inflate(R.layout.fragment_category_group, container, false);
        TabLayout tabLayout = (TabLayout) categorygroupview.findViewById(R.id.categoryTabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Proshows"));
        tabLayout.addTab(tabLayout.newTab().setText("Workshops"));
        tabLayout.addTab(tabLayout.newTab().setText("Competitions"));
        tabLayout.addTab(tabLayout.newTab().setText("Informals"));
        tabLayout.addTab(tabLayout.newTab().setText("Concerts"));
        final ViewPager viewPager = (ViewPager) categorygroupview.findViewById(R.id.categoryViewPager);

        Log.d("Category Group", " OnCreate");

        viewPager.setAdapter(new CategoryGroupFragment.PagerAdapter
                (getFragmentManager(), tabLayout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        viewPager.setCurrentItem(Cache.getCategoryPosition(), true);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        return categorygroupview;
    }

    public void generatecategories(List<GsonModels.Event> eventList) {
        for (GsonModels.Event event : eventList) {
            if (event.getCategory().equals("Proshows")) {
                proshows.add(event);
            }
            if (event.getCategory().equals("Workshops")) {
                workshops.add(event);
            }
            if (event.getCategory().equals("Competitions")) {
                compi.add(event);
            }
            if (event.getCategory().equals("Informals")) {
                informals.add(event);
            }
            if (event.getCategory().equals("Concerts")) {
                concerts.add(event);
            }
        }
        Log.d("Category Group", eventList.toString());
        Log.d("Category Group", proshows.toString());
        Log.d("Category Group", workshops.toString());
        Log.d("Category Group", compi.toString());
        Log.d("Category Group", informals.toString());
        Log.d("Category Group", concerts.toString());
    }

    public class PagerAdapter extends FragmentStatePagerAdapter {
        int mNumOfTabs;

        public PagerAdapter(FragmentManager fm, int NumOfTabs) {
            super(fm);
            this.mNumOfTabs = NumOfTabs;
        }

        @Override
        public Fragment getItem(int position) {

            switch (position) {
                case 0:
                    return new CategoryFragment(getContext(), proshows);
                case 1:
                    return new CategoryFragment(getContext(), workshops);
                case 2:
                    return new CategoryFragment(getContext(), compi);
                case 3:
                    return new CategoryFragment(getContext(), informals);
                case 4:
                    return new CategoryFragment(getContext(), concerts);
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            return mNumOfTabs;
        }
    }
}
