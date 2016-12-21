package com.iitb.moodindigo.mi2016;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.login.LoginManager;

import static android.content.Context.MODE_PRIVATE;

public class MainFragment extends Fragment {


    public MainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        getActivity().setTitle("Home");
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SharedPreferences prefs = getActivity().getSharedPreferences("userDetails", MODE_PRIVATE);
        String miNumberStored = prefs.getString("MI_NUMBER", null);

        if (miNumberStored == null) {
            LinearLayout mi_number_present = (LinearLayout) view.findViewById(R.id.mi_number_present);
            mi_number_present.setVisibility(View.INVISIBLE);
            ImageButton register_now = (ImageButton) view.findViewById(R.id.register_now);
            register_now.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getActivity().finish();
                    Uri webpage = Uri.parse("https://moodi.org/#/registration");
                    Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
                    if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                        startActivity(intent);
                    }
                }
            });
        } else {
            LinearLayout mi_number_absent = (LinearLayout) view.findViewById(R.id.mi_number_absent);
            mi_number_absent.setVisibility(View.INVISIBLE);
            TextView mi_number = (TextView) view.findViewById(R.id.mi_number);
            mi_number.setText(miNumberStored);
        }

        GridView gridview = (GridView) view.findViewById(R.id.main_menu);
        gridview.setAdapter(new GridAdapter(getContext()));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {
                switch (position) {
                    case 0:
                        openSchedule();
                        break;
                    case 1:
                        openGoing();
                        break;
                    case 2:
                        openMap();
                        break;
                    case 3:
                        openQRCode();
                        break;
                    case 4:
                        openFaq();
                        break;
                    case 5:
                        openMIDarshan();
                        break;
                    case 6:
                        openContactUs();
                        break;
                    case 7:
                        openDev();
                        break;
                    case 8:
                        logout();

                }
            }
        });
    }

    private void openMIDarshan() {
        MIDarshanFragment miDarshanFragment = new MIDarshanFragment();
        openFragment(miDarshanFragment);
    }

    private void openDev() {
        ((MainActivity) getActivity()).openDevelopersFromFragment();
    }

    public void openQRCode() {
        QrCodeFragment qrCodeFragment = new QrCodeFragment();
        openFragment(qrCodeFragment);

    }

    public void openGoing() {
        GoingFragment goingFragment = new GoingFragment();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack("goingmain");
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.relativelayout_for_fragment, goingFragment, goingFragment.getTag());
        transaction.commit();
    }

    public void openSchedule() {
        DaysFragment daysFragment = new DaysFragment();
        openFragment(daysFragment);
    }

    public void openFaq() {
        Fragment faqsFragment = new FaqsFragment();
        openFragment(faqsFragment);
    }

    public void openContactUs() {
        ((MainActivity) getActivity()).openContactUsFromFragment();
    }

    public void openMap() {
        Fragment mapFragment = new MapFragment();
        openFragment(mapFragment);
    }

    public void openFragment(Fragment fragment) {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        transaction.replace(R.id.relativelayout_for_fragment, fragment, fragment.getTag());
        transaction.commit();
    }

    public void logout() {
        new AlertDialog.Builder(getContext())
                .setTitle("Logout")
                .setMessage("Are you sure you want to Logout?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        LoginManager.getInstance().logOut();
                        SharedPreferences prefs = getActivity().getSharedPreferences("userDetails", MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.clear();
                        editor.apply();
                        Intent intent = new Intent(getContext(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.pull_in_left, R.anim.push_out_right);
                        getActivity().finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }
}
