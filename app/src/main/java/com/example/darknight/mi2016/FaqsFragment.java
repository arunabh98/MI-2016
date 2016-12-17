package com.example.darknight.mi2016;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FaqsFragment extends Fragment {

    ArrayList<Faq> items1 = new ArrayList<>();
    ArrayList<Faq> items2 = new ArrayList<>();
    ArrayList<Faq> items3 = new ArrayList<>();
    ArrayList<Faq> items4 = new ArrayList<>();

    private FragmentTabHost tabHost;

    public FaqsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //populating items1
        items1.add(new Faq("Qn11", "Ans11"));
        items1.add(new Faq("Qn12", "Ans12"));
        items1.add(new Faq("Qn13", "Ans13"));
        //populating items2
        items2.add(new Faq("Qn4", "Ans4"));
        items2.add(new Faq("Qn11", "Ans11"));
        items2.add(new Faq("Qn11", "Ans11"));
        //populating items3
        items3.add(new Faq("Qn4", "Ans4"));
        items3.add(new Faq("Qn11", "Ans11"));
        items3.add(new Faq("Qn11", "Ans11"));
        //populating items4
        items4.add(new Faq("Qn4", "Ans4"));
        items4.add(new Faq("Qn11", "Ans11"));
        items4.add(new Faq("Qn11", "Ans11"));

        //set title
        getActivity().setTitle("FAQ");

        // Inflate the layout for this fragment
        View inflatedView = inflater.inflate(R.layout.fragment_faqs, container, false);

        TabLayout tabLayout = (TabLayout) inflatedView.findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("General"));
        tabLayout.addTab(tabLayout.newTab().setText("Competitions"));
        tabLayout.addTab(tabLayout.newTab().setText("Concerts"));
        tabLayout.addTab(tabLayout.newTab().setText("Hospitality"));
        final ViewPager viewPager = (ViewPager) inflatedView.findViewById(R.id.viewpager);

        viewPager.setAdapter(new PagerAdapter
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

        return inflatedView;
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
                    SingleFaqFragment tab1 = new SingleFaqFragment(getContext(), items1);
                    return tab1;
                case 1:
                    SingleFaqFragment tab2 = new SingleFaqFragment(getContext(), items2);
                    return tab2;
                case 2:
                    SingleFaqFragment tab3 = new SingleFaqFragment(getContext(), items3);
                    return tab3;
                case 3:
                    SingleFaqFragment tab4 = new SingleFaqFragment(getContext(), items4);
                    return tab4;
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
