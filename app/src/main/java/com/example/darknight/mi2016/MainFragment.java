package com.example.darknight.mi2016;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
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
        getActivity().setTitle("HOME");
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
                switch (position){
                    case 0:
                        openEvents();
                        break;
                    case 1:
                        openHospitality();
                        break;
                    case 2:
                        openMap();
                        break;
                    case 3:
                        openGoing();
                        break;
                    case 4:
                        openSchedule();
                        break;
                    case 5:
                        openFaq();
                        break;
                    case 6:
                        openContactUs();
                        break;
                    case 7:
                        logout();

                }
            }
        });
    }

    public void openEvents() {

    }

    public void openHospitality() {

    }

    public void openGoing() {

    }

    public void openSchedule() {

    }

    public void openFaq() {
        Fragment faqsFragment = new FaqsFragment();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.relativelayout_for_fragment, faqsFragment, faqsFragment.getTag());
        transaction.commit();
    }

    public void openContactUs() {
        Fragment contactUsFragment = new ContactUsFragment();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.relativelayout_for_fragment, contactUsFragment, contactUsFragment.getTag());
        transaction.commit();
    }

    public void openMap() {
        Fragment mapFragment = new MapFragment();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.relativelayout_for_fragment, mapFragment, mapFragment.getTag());
        transaction.commit();
    }

    private void openQrCode() {
        Fragment qrCodeFragment = new QrCodeFragment();
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.addToBackStack(null);
        transaction.replace(R.id.relativelayout_for_fragment, qrCodeFragment, qrCodeFragment.getTag());
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
