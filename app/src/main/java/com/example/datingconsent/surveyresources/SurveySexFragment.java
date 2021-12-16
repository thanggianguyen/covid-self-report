package com.example.datingconsent.surveyresources;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment\
        View rootView = inflater.inflate(R.layout.fragment_survey_sex, container, false);
        CheckBox kissingCheck = rootView.findViewById(R.id.KissingCheck);
        kissingCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tongue.setVisibility(View.VISIBLE);
                } else {
                    tongue.setVisibility(View.GONE);
                }
            }
        });
        CheckBox birthControlCheck = rootView.findViewById(R.id.BirthControlCheck);
        birthControlCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
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
                else{
                    bc1.setVisibility(View.GONE);
                    bc2.setVisibility(View.GONE);
                    bc3.setVisibility(View.GONE);
                    bc4.setVisibility(View.GONE);
                    bc5.setVisibility(View.GONE);
                    bc6.setVisibility(View.GONE);
                    bc7.setVisibility(View.GONE);
                    bc8.setVisibility(View.GONE);
                    bc9.setVisibility(View.GONE);
                    bc10.setVisibility(View.GONE);
                    bc11.setVisibility(View.GONE);
                    bc12.setVisibility(View.GONE);
                    bcNote.setVisibility(View.GONE);
                    bcSubnote.setVisibility(View.GONE);
                }
            }
        });


        return rootView;

    }
    

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        /**
         * Instantiate java parameters with UI parameters
         */
        kiss = view.findViewById(R.id.KissingCheck);
        tongue = view.findViewById(R.id.TongueCheck);
        bc = view.findViewById(R.id.BirthControlCheck);
        bc1 = view.findViewById(R.id.BirthControl1);
        bc2 = view.findViewById(R.id.BirthControl2);
        bc3 = view.findViewById(R.id.BirthControl3);
        bc4 = view.findViewById(R.id.BirthControl4);
        bc5 = view.findViewById(R.id.BirthControl5);
        bc6 = view.findViewById(R.id.BirthControl6);
        bc7 = view.findViewById(R.id.BirthControl7);
        bc8 = view.findViewById(R.id.BirthControl8);
        bc9 = view.findViewById(R.id.BirthControl9);
        bc10 = view.findViewById(R.id.BirthControl10);
        bc11 = view.findViewById(R.id.BirthControl11);
        bc12 = view.findViewById(R.id.BirthControl12);
        bcNote = view.findViewById(R.id.BirthControlNote);
        bcSubnote = view.findViewById(R.id.BirthControlSubnote);
    }
}
