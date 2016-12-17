package com.example.darknight.mi2016;


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

import com.example.darknight.mi2016.ServerConnection.GsonModels;
import com.example.darknight.mi2016.ServerConnection.RetrofitInterface;
import com.example.darknight.mi2016.ServerConnection.ServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 */
public class CompiFragment extends Fragment implements Callback<List<GsonModels.Event>> {


    private ProgressDialog compiProgressDialog;
    private List<GsonModels.Event> compiResponse;
    private View inflatedView;

    public CompiFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Competitions");
        // Inflate the layout for this fragment
        inflatedView = inflater.inflate(R.layout.fragment_compi, container, false);

        compiProgressDialog = new ProgressDialog(getContext());
        compiProgressDialog.setIndeterminate(true);
        compiProgressDialog.setCancelable(false);
        compiProgressDialog.setMessage("Requesting Details");
        RetrofitInterface compiretrofitInterface = ServiceGenerator.createService(RetrofitInterface.class);
        compiretrofitInterface.getEvents().enqueue(this);
        compiProgressDialog.show();

        return inflatedView;
    }

    @Override
    public void onResponse(Call<List<GsonModels.Event>> call, Response<List<GsonModels.Event>> response) {
        if (response.isSuccessful()) {
            compiResponse = response.body();
            TabLayout tabLayout = (TabLayout) inflatedView.findViewById(R.id.compiTabLayout);
            tabLayout.addTab(tabLayout.newTab().setText("Dance"));
            tabLayout.addTab(tabLayout.newTab().setText("Dramatics"));
            tabLayout.addTab(tabLayout.newTab().setText("Music"));
            tabLayout.addTab(tabLayout.newTab().setText("Speaking Arts"));
            tabLayout.addTab(tabLayout.newTab().setText("Literary Arts"));
            tabLayout.addTab(tabLayout.newTab().setText("Fine Arts"));
            tabLayout.addTab(tabLayout.newTab().setText("Miscellaneous"));
            final ViewPager viewPager = (ViewPager) inflatedView.findViewById(R.id.compiViewPager);

            viewPager.setAdapter(new CompiFragment.PagerAdapter
                    (getFragmentManager(), tabLayout.getTabCount()));
            viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
            tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        } else {
            Toast.makeText(getContext(), "Response code " + response.code(), Toast.LENGTH_SHORT).show();
        }
        compiProgressDialog.dismiss();
    }

    @Override
    public void onFailure(Call<List<GsonModels.Event>> call, Throwable t) {
        Toast.makeText(getContext(), "Network error occurred", Toast.LENGTH_LONG).show();
        Log.d("TAG", "onFailure: " + t.toString());
        compiProgressDialog.dismiss();
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
                    SingleCompiFragment tab1 = new SingleCompiFragment(getContext(), compiResponse);
                    return tab1;
                case 1:
                    SingleCompiFragment tab2 = new SingleCompiFragment(getContext(), compiResponse);
                    return tab2;
                case 2:
                    SingleCompiFragment tab3 = new SingleCompiFragment(getContext(), compiResponse);
                    return tab3;
                case 3:
                    SingleCompiFragment tab4 = new SingleCompiFragment(getContext(), compiResponse);
                    return tab4;
                case 4:
                    SingleCompiFragment tab5 = new SingleCompiFragment(getContext(), compiResponse);
                    return tab5;
                case 5:
                    SingleCompiFragment tab6 = new SingleCompiFragment(getContext(), compiResponse);
                    return tab6;
                case 6:
                    SingleCompiFragment tab7 = new SingleCompiFragment(getContext(), compiResponse);
                    return tab7;
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
