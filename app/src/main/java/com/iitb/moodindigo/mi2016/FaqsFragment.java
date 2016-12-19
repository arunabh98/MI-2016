package com.iitb.moodindigo.mi2016;


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

    private FragmentTabHost tabHost;

    public FaqsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //populating items1
        items1.add(new Faq("Is the entry free?", "The entry has been and will always be free. All you need is a valid college ID."));
        items1.add(new Faq("How do we reach the campus?", "The campus is located in Powai, near Hiranandani. The nearest local station is Kanjur Marg, and the campus is easily accessible by taxis or autos."));
        items1.add(new Faq("What are the food and beverage facilities?", "There will be 3 food courts during the fest - SAC Food Court, PPL Food Court(Physics Parking Lot) and Gymkhana Grounds Food Court. The PPL Food Court will be open till 5 PM while the  SAC & Gymkhana Grounds’ Food Court will be operational during the concerts."));
        items1.add(new Faq("What if I don’t have my MI Number before coming to the fest?", "You can register and get your MI Number for the festival at the registration desk at the main gate."));
        items1.add(new Faq("Are we allowed to get vehicles inside the campus?", "Personal vehicles won’t be allowed to enter the campus, and there is no parking facility near the campus so it is advised to use public transport."));
        items1.add(new Faq("Who can we contact in case of need of help/emergency?", "You can contact the helpline at : 022-25765562."));
        //populating items2
        items2.add(new Faq("How can I attend the concerts?", "All the concerts except the Livewire Nite are free for all. You can collect the passes from the Hospitality Desk on the day before the concert 10 AM onwards. For Livewire Nite, register at BookMyShow."));
        items2.add(new Faq("Am I eligible to attend a concert?", "Yes! Anyone having an MI number."));
        items2.add(new Faq("What about passes?", "You can collect them from the Hospi Desk in the morning at 7:30am on the day of the concert."));
        items2.add(new Faq("Can I leave and again enter?", "No."));
        items2.add(new Faq("What all should I bring along?", "Nothing other than your friends. Cameras, cosmetics, pointed objects like scissors, knives etc are not allowed."));
        items2.add(new Faq("Can I take photos?", "Preferably not. You just stood in a huge queue to see it live, why ruin it for just showing it to others!"));
        items2.add(new Faq("Can I carry water?", "It's not required, there are arrangements inside. Moreover there is the food court."));
        //populating items3
        items3.add(new Faq("What type of accommodation will be provided?", "Accommodation will be provided to boys and girls in well secured separate residential complexes on the campus of IIT Bombay."));
        items3.add(new Faq("What is the procedure to be followed after we reach IIT Bombay?", "You need to come to the Accommodation Desk, Students Activity Centre (SAC) along with your college identity card. You will be allotted your place of stay on campus and given a registration booklet. In case of a big contingent, contingent leader needs to present the ID cards of all the people in his contingent at the accommodation desk along with a list of all the people. Accommodation would be strictly on shared."));
        items3.add(new Faq("What is the band and booklet system?", "There are two types of bands that will be distributed along with the registration booklets - blue and red. Blue band will be given to the people who have accommodation while the red band will be given to the people who have don’t have accommodation. People with red bands need to vacate the campus after 10 PM."));
        items3.add(new Faq("Does accommodation fee include the food facility as well?", "No. The accommodation charges don’t include food. However, there will be food courts operational during Mood Indigo to cater to the food requirements."));

        //set title
        getActivity().setTitle("FAQ");

        // Inflate the layout for this fragment
        View inflatedView = inflater.inflate(R.layout.fragment_faqs, container, false);

        TabLayout tabLayout = (TabLayout) inflatedView.findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText("General"));
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
