package com.iitb.moodindigo.mi2016;


import android.app.ProgressDialog;
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
import android.widget.Toast;

import com.iitb.moodindigo.mi2016.ServerConnection.GsonModels;
import com.iitb.moodindigo.mi2016.ServerConnection.RetrofitInterface;
import com.iitb.moodindigo.mi2016.ServerConnection.ServiceGenerator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class ScheduleFragment extends Fragment implements Callback<List<GsonModels.Event>> {

    private ProgressDialog scheduleProgressDialog;
    private View inflatedView;
    private List<GsonModels.Event> day1List = new ArrayList<>();
    private List<GsonModels.Event> day2List = new ArrayList<>();
    private List<GsonModels.Event> day3List = new ArrayList<>();
    private List<GsonModels.Event> day4List = new ArrayList<>();

    public ScheduleFragment() {
        // Required empty public constructor

    }

    public static List<GsonModels.Event> mergeSort(List<GsonModels.Event> whole) {
        List<GsonModels.Event> left = new ArrayList<>();
        List<GsonModels.Event> right = new ArrayList<>();
        int center;

        if (whole.size() == 1)
            return whole;
        else {
            center = whole.size() / 2;
            // copy the left half of whole into the left.
            for (int i = 0; i < center; i++) {
                left.add(whole.get(i));
            }

            //copy the right half of whole into the new arraylist.
            for (int i = center; i < whole.size(); i++) {
                right.add(whole.get(i));
            }

            // Sort the left and right halves of the arraylist.
            left = mergeSort(left);
            right = mergeSort(right);


            // Merge the results back together.
            merge(left, right, whole);

        }
        return whole;
    }

    private static void merge(List<GsonModels.Event> left, List<GsonModels.Event> right,
                              List<GsonModels.Event> whole) {

        int leftIndex = 0;
        int rightIndex = 0;
        int wholeIndex = 0;


        // As long as neither the left nor the right arraylist has
        // been used up, keep taking the smaller of left.get(leftIndex)
        // or right.get(rightIndex) and adding it at both.get(bothIndex).
        while (leftIndex < left.size() && rightIndex < right.size()) {
            if ((left.get(leftIndex).compareByTime(right.get(rightIndex)))) {
                whole.set(wholeIndex, left.get(leftIndex));
                leftIndex++;
            } else {
                whole.set(wholeIndex, right.get(rightIndex));
                rightIndex++;
            }
            wholeIndex++;
        }

        List<GsonModels.Event> rest;
        int restIndex;
        if (leftIndex >= left.size()) {
            // The left arraylist has been use up...
            rest = right;
            restIndex = rightIndex;
        } else {
            // The right arraylist has been used up...
            rest = left;
            restIndex = leftIndex;
        }

        // Copy the rest of whichever arraylist (left or right) was
        // not used up.
        for (int i = restIndex; i < rest.size(); i++) {
            whole.set(wholeIndex, rest.get(i));
            wholeIndex++;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Schedule");
        // Inflate the layout for this fragment
        inflatedView = inflater.inflate(R.layout.fragment_schedule, container, false);

        if (Cache.isSendEventRequest()) {
            scheduleProgressDialog = new ProgressDialog(getContext());
            scheduleProgressDialog.setIndeterminate(true);
            scheduleProgressDialog.setCancelable(false);
            scheduleProgressDialog.setMessage("Requesting Details");
            RetrofitInterface scheduleretrofitInterface = ServiceGenerator.createService(RetrofitInterface.class);
            scheduleretrofitInterface.getEvents().enqueue(this);
            scheduleProgressDialog.show();
        } else {
            inflateTabs(Cache.getEventList());
        }
        return inflatedView;
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onResponse(Call<List<GsonModels.Event>> call, Response<List<GsonModels.Event>> response) {
        if (response.isSuccessful()) {
            Cache.setEventList(response.body());
            inflateTabs(Cache.getEventList());
            Cache.setSendEventRequest(false);
        } else {
            Toast.makeText(getContext(), "Response code " + response.code(), Toast.LENGTH_SHORT).show();
        }
        scheduleProgressDialog.dismiss();
    }

    @Override
    public void onFailure(Call<List<GsonModels.Event>> call, Throwable t) {
        Toast.makeText(getContext(), "Network error occurred", Toast.LENGTH_LONG).show();
        Log.d("TAG", "onFailure: " + t.toString());
        scheduleProgressDialog.dismiss();
    }

    public void inflateTabs(List<GsonModels.Event> eventList) {
        for (GsonModels.Event event : eventList) {
            if (event.getDay().get_1()) {
                day1List.add(event);
            }
            if (event.getDay().get_2()) {
                day2List.add(event);
            }
            if (event.getDay().get_3()) {
                day3List.add(event);
            }
            if (event.getDay().get_4()) {
                day4List.add(event);
            }
        }
        day1List = mergeSort(day1List);
        day2List = mergeSort(day2List);
        day3List = mergeSort(day3List);
        day4List = mergeSort(day4List);

        TabLayout tabLayout = (TabLayout) inflatedView.findViewById(R.id.scheduleTabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("Day1"));
        tabLayout.addTab(tabLayout.newTab().setText("Day2"));
        tabLayout.addTab(tabLayout.newTab().setText("Day3"));
        tabLayout.addTab(tabLayout.newTab().setText("Day4"));
        final ViewPager viewPager = (ViewPager) inflatedView.findViewById(R.id.scheduleViewPager);

        viewPager.setAdapter(new ScheduleFragment.PagerAdapter
                (getFragmentManager(), tabLayout.getTabCount()));
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
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
                    return new SingleDayFragment(getContext(), day1List);
                case 1:
                    return new SingleDayFragment(getContext(), day2List);
                case 2:
                    return new SingleDayFragment(getContext(), day3List);
                case 3:
                    return new SingleDayFragment(getContext(), day4List);
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
