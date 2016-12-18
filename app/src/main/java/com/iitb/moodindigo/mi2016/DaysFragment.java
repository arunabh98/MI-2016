package com.iitb.moodindigo.mi2016;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
public class DaysFragment extends Fragment implements Callback<List<GsonModels.Event>>,View.OnClickListener{

    private ProgressDialog scheduleProgressDialog;
    private List<GsonModels.Event> day1List = new ArrayList<>();
    private List<GsonModels.Event> day2List = new ArrayList<>();
    private List<GsonModels.Event> day3List = new ArrayList<>();
    private List<GsonModels.Event> day4List = new ArrayList<>();
    View daysView;
    Button day1;
    Button day2;
    Button day3;
    Button day4;

    public DaysFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        getActivity().setTitle("Schedule");
        // Inflate the layout for this fragment
        daysView = inflater.inflate(R.layout.fragment_days, container, false);
        day1 = (Button) daysView.findViewById(R.id.day1);
        day2 = (Button) daysView.findViewById(R.id.day2);
        day3 = (Button) daysView.findViewById(R.id.day3);
        day4 = (Button) daysView.findViewById(R.id.day4);

        day1.setOnClickListener(this);
        day2.setOnClickListener(this);
        day3.setOnClickListener(this);
        day4.setOnClickListener(this);

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

        return daysView;
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
        day1List = Utils.mergeSort(day1List);
        day2List = Utils.mergeSort(day2List);
        day3List = Utils.mergeSort(day3List);
        day4List = Utils.mergeSort(day4List);

        Cache.setDaysList1(day1List);
        Cache.setDaysList2(day2List);
        Cache.setDaysList3(day3List);
        Cache.setDaysList4(day4List);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.day1:
                CategoryGroupFragment categoryGroupFragment1 = new CategoryGroupFragment(day1List,1);
                openFragment(categoryGroupFragment1);
                break;
            case R.id.day2:
                CategoryGroupFragment categoryGroupFragment2 = new CategoryGroupFragment(day2List,2);
                openFragment(categoryGroupFragment2);
                break;
            case R.id.day3:
                CategoryGroupFragment categoryGroupFragment3 = new CategoryGroupFragment(day3List,3);
                openFragment(categoryGroupFragment3);
                break;
            case R.id.day4:
                CategoryGroupFragment categoryGroupFragment4 = new CategoryGroupFragment(day4List,4);
                openFragment(categoryGroupFragment4);
                break;
        }
    }

    public void openFragment(Fragment fragment) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.relativelayout_for_fragment, fragment, fragment.getTag());
        transaction.commit();
    }
}
