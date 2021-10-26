package com.example.datingconsent.surveyresources;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.datingconsent.R;


public class SurveySexFragment extends Fragment {

    private CheckBox kiss, tongue;
    private CheckBox bc, bc1, bc2, bc3, bc4, bc5, bc6;
    private CheckBox bc7, bc8, bc9, bc10, bc11, bc12;
    private TextView bcNote, bcSubnote;

    public SurveySexFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * Instantiate java parameters with UI parameters
         */
        kiss.findViewById(R.id.KissingCheck);
        tongue.findViewById(R.id.TongueCheck);
        bc.findViewById(R.id.BirthControlCheck);
        bc1.findViewById(R.id.BirthControl1);
        bc2.findViewById(R.id.BirthControl2);
        bc3.findViewById(R.id.BirthControl3);
        bc4.findViewById(R.id.BirthControl4);
        bc5.findViewById(R.id.BirthControl5);
        bc6.findViewById(R.id.BirthControl6);
        bc7.findViewById(R.id.BirthControl7);
        bc8.findViewById(R.id.BirthControl8);
        bc9.findViewById(R.id.BirthControl9);
        bc10.findViewById(R.id.BirthControl10);
        bc11.findViewById(R.id.BirthControl11);
        bc12.findViewById(R.id.BirthControl12);
        bcNote.findViewById(R.id.BirthControlNote);
        bcSubnote.findViewById(R.id.BirthControlSubnote);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_survey_sex, container, false);

    }

    //Sets visibility of parameters when CheckBox is clicked
    public void KissingClicked(View v)
    {
        tongue.setVisibility(View.VISIBLE);
    }

    //Sets visibility of parameters when CheckBox is clicked
    public void BirthControlClicked(View v)
    {
        bc1.setVisibility(View.VISIBLE);
        bc2.setVisibility(View.VISIBLE);
        bc3.setVisibility(View.VISIBLE);
        bc4.setVisibility(View.VISIBLE);
        bc5.setVisibility(View.VISIBLE);
        bc6.setVisibility(View.VISIBLE);
        bc7.setVisibility(View.VISIBLE);
        bc8.setVisibility(View.VISIBLE);
        bc9.setVisibility(View.VISIBLE);
        bc10.setVisibility(View.VISIBLE);
        bc11.setVisibility(View.VISIBLE);
        bc12.setVisibility(View.VISIBLE);
        bcNote.setVisibility(View.VISIBLE);
        bcSubnote.setVisibility(View.VISIBLE);
    }


}