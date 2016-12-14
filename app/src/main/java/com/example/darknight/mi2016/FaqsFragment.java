package com.example.darknight.mi2016;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class FaqsFragment extends Fragment implements View.OnClickListener {


    public FaqsFragment() {
        // Required empty public constructor
    }

    View rootView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
<<<<<<< HEAD
        rootView = inflater.inflate(R.layout.fragment_faqs, container, false);
        Button buttonGeneral = (Button) rootView.findViewById(R.id.general_button);
        Button buttonCompetitions = (Button) rootView.findViewById(R.id.competitions_button);
        Button buttonConcerts = (Button) rootView.findViewById(R.id.concerts_button);
        Button buttonHospitality = (Button) rootView.findViewById(R.id.hospitality_button);
        Button buttonClInfo = (Button) rootView.findViewById(R.id.cl_info_button);
        Button button = (Button) rootView.findViewById(R.id.button);
        buttonGeneral.setOnClickListener(this);
        buttonCompetitions.setOnClickListener(this);
        buttonConcerts.setOnClickListener(this);
        buttonHospitality.setOnClickListener(this);
        buttonClInfo.setOnClickListener(this);
        button.setOnClickListener(this);
        return rootView;
||||||| merged common ancestors
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_faqs, container, false);
=======
        // Inflate the layout for this fragment
        getActivity().setTitle("FAQ");
        return inflater.inflate(R.layout.fragment_faqs, container, false);
>>>>>>> 66b461cf04167ca7c03d6bbb3f242955598fbbe5
    }

    @Override
    public void onClick(View v) {
        Button buttonGeneral = (Button) rootView.findViewById(R.id.general_button);
        Button buttonCompetitions = (Button) rootView.findViewById(R.id.competitions_button);
        Button buttonConcerts = (Button) rootView.findViewById(R.id.concerts_button);
        Button buttonHospitality = (Button) rootView.findViewById(R.id.hospitality_button);
        Button buttonClInfo = (Button) rootView.findViewById(R.id.cl_info_button);
        Button button = (Button) rootView.findViewById(R.id.button);
        TextView textView1 = (TextView) rootView.findViewById(R.id.question1);
        TextView textView2 = (TextView) rootView.findViewById(R.id.question2);
        TextView textView3 = (TextView) rootView.findViewById(R.id.question3);
        TextView textViewA1 = (TextView) rootView.findViewById(R.id.answer1);
        TextView textViewA2 = (TextView) rootView.findViewById(R.id.answer2);
        TextView textViewA3 = (TextView) rootView.findViewById(R.id.answer3);
        TextView textView4 = (TextView) rootView.findViewById(R.id.question4);
        TextView textView5 = (TextView) rootView.findViewById(R.id.question5);
        TextView textView6 = (TextView) rootView.findViewById(R.id.question6);
        TextView textView7 = (TextView) rootView.findViewById(R.id.question7);
        TextView textViewA4 = (TextView) rootView.findViewById(R.id.answer4);
        TextView textViewA5 = (TextView) rootView.findViewById(R.id.answer5);
        TextView textViewA6 = (TextView) rootView.findViewById(R.id.answer6);
        TextView textViewA7 = (TextView) rootView.findViewById(R.id.answer7);
        TextView textView8 = (TextView) rootView.findViewById(R.id.question8);
        TextView textView9 = (TextView) rootView.findViewById(R.id.question9);
        TextView textView10 = (TextView) rootView.findViewById(R.id.question10);
        TextView textView11 = (TextView) rootView.findViewById(R.id.question11);
        TextView textView12 = (TextView) rootView.findViewById(R.id.question12);
        TextView textView13 = (TextView) rootView.findViewById(R.id.question13);
        TextView textView14 = (TextView) rootView.findViewById(R.id.question14);
        TextView textViewA8 = (TextView) rootView.findViewById(R.id.answer8);
        TextView textViewA9 = (TextView) rootView.findViewById(R.id.answer9);
        TextView textViewA10 = (TextView) rootView.findViewById(R.id.answer10);
        TextView textViewA11 = (TextView) rootView.findViewById(R.id.answer11);
        TextView textViewA12 = (TextView) rootView.findViewById(R.id.answer12);
        TextView textViewA13 = (TextView) rootView.findViewById(R.id.answer13);
        TextView textViewA14 = (TextView) rootView.findViewById(R.id.answer14);
        TextView textView15 = (TextView) rootView.findViewById(R.id.question15);
        TextView textView16 = (TextView) rootView.findViewById(R.id.question16);
        TextView textView17 = (TextView) rootView.findViewById(R.id.question17);
        TextView textView18 = (TextView) rootView.findViewById(R.id.question18);
        TextView textView19 = (TextView) rootView.findViewById(R.id.question19);
        TextView textView20 = (TextView) rootView.findViewById(R.id.question20);
        TextView textView21 = (TextView) rootView.findViewById(R.id.question21);
        TextView textViewA15 = (TextView) rootView.findViewById(R.id.answer15);
        TextView textViewA16 = (TextView) rootView.findViewById(R.id.answer16);
        TextView textViewA17 = (TextView) rootView.findViewById(R.id.answer17);
        TextView textViewA18 = (TextView) rootView.findViewById(R.id.answer18);
        TextView textViewA19 = (TextView) rootView.findViewById(R.id.answer19);
        TextView textViewA20 = (TextView) rootView.findViewById(R.id.answer20);
        TextView textViewA21 = (TextView) rootView.findViewById(R.id.answer21);
        TextView textView22 = (TextView) rootView.findViewById(R.id.question22);
        TextView textView23 = (TextView) rootView.findViewById(R.id.question23);
        TextView textView24 = (TextView) rootView.findViewById(R.id.question24);
        TextView textView25 = (TextView) rootView.findViewById(R.id.question25);
        TextView textViewA22 = (TextView) rootView.findViewById(R.id.answer22);
        TextView textViewA23 = (TextView) rootView.findViewById(R.id.answer23);
        TextView textViewA24 = (TextView) rootView.findViewById(R.id.answer24);
        TextView textViewA25 = (TextView) rootView.findViewById(R.id.answer25);
        switch (v.getId()) {
            case R.id.general_button:
                buttonGeneral.setVisibility(View.GONE);
                buttonCompetitions.setVisibility(View.GONE);
                buttonConcerts.setVisibility(View.GONE);
                buttonHospitality.setVisibility(View.GONE);
                buttonClInfo.setVisibility(View.GONE);
                button.setText("back");
                textView1.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.VISIBLE);
                textView3.setVisibility(View.VISIBLE);
                textViewA1.setVisibility(View.VISIBLE);
                textViewA2.setVisibility(View.VISIBLE);
                textViewA3.setVisibility(View.VISIBLE);
                break;
            case R.id.competitions_button:
                buttonGeneral.setVisibility(View.GONE);
                buttonCompetitions.setVisibility(View.GONE);
                buttonConcerts.setVisibility(View.GONE);
                buttonHospitality.setVisibility(View.GONE);
                buttonClInfo.setVisibility(View.GONE);
                button.setText("back");
                textView4.setVisibility(View.VISIBLE);
                textView5.setVisibility(View.VISIBLE);
                textView6.setVisibility(View.VISIBLE);
                textView7.setVisibility(View.VISIBLE);
                textViewA4.setVisibility(View.VISIBLE);
                textViewA5.setVisibility(View.VISIBLE);
                textViewA6.setVisibility(View.VISIBLE);
                textViewA7.setVisibility(View.VISIBLE);
                break;
            case R.id.concerts_button:
                buttonGeneral.setVisibility(View.GONE);
                buttonCompetitions.setVisibility(View.GONE);
                buttonConcerts.setVisibility(View.GONE);
                buttonHospitality.setVisibility(View.GONE);
                buttonClInfo.setVisibility(View.GONE);
                button.setText("back");
                textView8.setVisibility(View.VISIBLE);
                textView9.setVisibility(View.VISIBLE);
                textView10.setVisibility(View.VISIBLE);
                textView11.setVisibility(View.VISIBLE);
                textView12.setVisibility(View.VISIBLE);
                textView13.setVisibility(View.VISIBLE);
                textView14.setVisibility(View.VISIBLE);
                textViewA8.setVisibility(View.VISIBLE);
                textViewA9.setVisibility(View.VISIBLE);
                textViewA10.setVisibility(View.VISIBLE);
                textViewA11.setVisibility(View.VISIBLE);
                textViewA12.setVisibility(View.VISIBLE);
                textViewA13.setVisibility(View.VISIBLE);
                textViewA14.setVisibility(View.VISIBLE);
                break;
            case R.id.hospitality_button:
                buttonGeneral.setVisibility(View.GONE);
                buttonCompetitions.setVisibility(View.GONE);
                buttonConcerts.setVisibility(View.GONE);
                buttonHospitality.setVisibility(View.GONE);
                buttonClInfo.setVisibility(View.GONE);
                button.setText("back");
                textView15.setVisibility(View.VISIBLE);
                textView16.setVisibility(View.VISIBLE);
                textView17.setVisibility(View.VISIBLE);
                textView18.setVisibility(View.VISIBLE);
                textView19.setVisibility(View.VISIBLE);
                textView20.setVisibility(View.VISIBLE);
                textView21.setVisibility(View.VISIBLE);
                textViewA15.setVisibility(View.VISIBLE);
                textViewA16.setVisibility(View.VISIBLE);
                textViewA17.setVisibility(View.VISIBLE);
                textViewA18.setVisibility(View.VISIBLE);
                textViewA19.setVisibility(View.VISIBLE);
                textViewA20.setVisibility(View.VISIBLE);
                textViewA21.setVisibility(View.VISIBLE);
                break;
            case R.id.cl_info_button:
                buttonGeneral.setVisibility(View.GONE);
                buttonCompetitions.setVisibility(View.GONE);
                buttonConcerts.setVisibility(View.GONE);
                buttonHospitality.setVisibility(View.GONE);
                buttonClInfo.setVisibility(View.GONE);
                button.setText("back");
                textView22.setVisibility(View.VISIBLE);
                textView23.setVisibility(View.VISIBLE);
                textView24.setVisibility(View.VISIBLE);
                textView25.setVisibility(View.VISIBLE);
                textViewA22.setVisibility(View.VISIBLE);
                textViewA23.setVisibility(View.VISIBLE);
                textViewA24.setVisibility(View.VISIBLE);
                textViewA25.setVisibility(View.VISIBLE);
                break;
            case R.id.button:
                if (buttonGeneral.getVisibility() == View.GONE) {
                    textView1.setVisibility(View.GONE);
                    textView2.setVisibility(View.GONE);
                    textView3.setVisibility(View.GONE);
                    textViewA1.setVisibility(View.GONE);
                    textViewA2.setVisibility(View.GONE);
                    textViewA3.setVisibility(View.GONE);
                    textView4.setVisibility(View.GONE);
                    textView5.setVisibility(View.GONE);
                    textView6.setVisibility(View.GONE);
                    textView7.setVisibility(View.GONE);
                    textViewA4.setVisibility(View.GONE);
                    textViewA5.setVisibility(View.GONE);
                    textViewA6.setVisibility(View.GONE);
                    textViewA7.setVisibility(View.GONE);
                    textView8.setVisibility(View.GONE);
                    textView9.setVisibility(View.GONE);
                    textView10.setVisibility(View.GONE);
                    textView11.setVisibility(View.GONE);
                    textView12.setVisibility(View.GONE);
                    textView13.setVisibility(View.GONE);
                    textView14.setVisibility(View.GONE);
                    textViewA8.setVisibility(View.GONE);
                    textViewA9.setVisibility(View.GONE);
                    textViewA10.setVisibility(View.GONE);
                    textViewA11.setVisibility(View.GONE);
                    textViewA12.setVisibility(View.GONE);
                    textViewA13.setVisibility(View.GONE);
                    textViewA14.setVisibility(View.GONE);
                    textView15.setVisibility(View.GONE);
                    textView16.setVisibility(View.GONE);
                    textView17.setVisibility(View.GONE);
                    textView18.setVisibility(View.GONE);
                    textView19.setVisibility(View.GONE);
                    textView20.setVisibility(View.GONE);
                    textView21.setVisibility(View.GONE);
                    textViewA15.setVisibility(View.GONE);
                    textViewA16.setVisibility(View.GONE);
                    textViewA17.setVisibility(View.GONE);
                    textViewA18.setVisibility(View.GONE);
                    textViewA19.setVisibility(View.GONE);
                    textViewA20.setVisibility(View.GONE);
                    textViewA21.setVisibility(View.GONE);
                    textView22.setVisibility(View.GONE);
                    textView23.setVisibility(View.GONE);
                    textView24.setVisibility(View.GONE);
                    textView25.setVisibility(View.GONE);
                    textViewA22.setVisibility(View.GONE);
                    textViewA23.setVisibility(View.GONE);
                    textViewA24.setVisibility(View.GONE);
                    textViewA25.setVisibility(View.GONE);
                    buttonGeneral.setVisibility(View.VISIBLE);
                    buttonCompetitions.setVisibility(View.VISIBLE);
                    buttonConcerts.setVisibility(View.VISIBLE);
                    buttonHospitality.setVisibility(View.VISIBLE);
                    buttonClInfo.setVisibility(View.VISIBLE);
                    button.setText("FAQS");
                }


        }

    }
}