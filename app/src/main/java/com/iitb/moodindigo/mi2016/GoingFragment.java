package com.iitb.moodindigo.mi2016;


import android.content.Context;
import android.content.SharedPreferences;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.iitb.moodindigo.mi2016.ServerConnection.GsonModels;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class GoingFragment extends Fragment {

    private SharedPreferences goingSharedPreferences;
    private View goingview;
    private List<GsonModels.Event> goingday1list = new ArrayList<>();
    private List<GsonModels.Event> goingday2list = new ArrayList<>();
    private List<GsonModels.Event> goingday3list = new ArrayList<>();
    private List<GsonModels.Event> goingday4list = new ArrayList<>();

    public GoingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Going");
        goingview = (View) inflater.inflate(R.layout.fragment_going, container, false);
        return goingview;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        goingSharedPreferences = getContext().getSharedPreferences("GOING", Context.MODE_PRIVATE);
        final String goingList = goingSharedPreferences.getString("GOING_LIST", null);
        Type type = new TypeToken<List<GsonModels.Event>>(){}.getType();
        List<GsonModels.Event> goingListGson = (new Gson()).fromJson(goingList, type);
        Cache.setGoingEventsList(goingListGson);
        generateDays(Cache.getGoingEventsList());

        TabLayout tabLayout = (TabLayout) goingview.findViewById(R.id.goingTabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Day1"));
        tabLayout.addTab(tabLayout.newTab().setText("Day2"));
        tabLayout.addTab(tabLayout.newTab().setText("Day3"));
        tabLayout.addTab(tabLayout.newTab().setText("Day4"));
        final ViewPager viewPager = (ViewPager) goingview.findViewById(R.id.goingViewPager);
        viewPager.setAdapter(new GoingFragment.PagerAdapter
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
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    public void generateDays(List<GsonModels.Event> eventList){
        for (GsonModels.Event event : eventList) {
            if (event.getDay().get_1()) {
                goingday1list.add(event);
            }
            else if (event.getDay().get_2()) {
                goingday2list.add(event);
            }
            else if (event.getDay().get_3()) {
                goingday3list.add(event);
            }
            else if (event.getDay().get_4()) {
                goingday4list.add(event);
            }
        }
        goingday1list = Utils.mergeSort(goingday1list);
        goingday2list = Utils.mergeSort(goingday2list);
        goingday3list = Utils.mergeSort(goingday3list);
        goingday4list = Utils.mergeSort(goingday4list);

        Log.d("Going Fragment",goingday1list.toString());
        Log.d("Going Fragment",goingday2list.toString());
        Log.d("Going Fragment",goingday3list.toString());
        Log.d("Going Fragment",goingday4list.toString());
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
                    return new GoingDayFragment(goingday1list);
                case 1:
                    return new GoingDayFragment(goingday2list);
                case 2:
                    return new GoingDayFragment(goingday3list);
                case 3:
                    return new GoingDayFragment(goingday4list);
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