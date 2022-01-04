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
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.datingconsent.R;


public class SurveySexFragment extends Fragment {

    private CheckBox kiss, tongue;
    private CheckBox bc1, bc2, bc3, bc4, bc5, bc6;
    private CheckBox bc7, bc8, bc9, bc10, bc11, bc12;
    private TextView bcNote, bcSubnote;
    private RadioGroup analGroup, oralGroup;

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
        //Additional information prompted if kissing Checkbox is checked
        CheckBox kissingCheck = rootView.findViewById(R.id.sex_KissingCheck_checkbox);
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
        //Additional information prompted if Birth Control Checkbox is checked
        CheckBox birthControlCheck = rootView.findViewById(R.id.sex_BirthControlCheck_checkbox);
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
        //Additional Radio Group will appear if Anal Checkbox is checked
        CheckBox analCheck = rootView.findViewById(R.id.sex_AnalCheck_checkbox);
        analCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    analGroup.setVisibility(View.VISIBLE);
                }
                else{
                    analGroup.setVisibility(View.GONE);
                }
            }
        });
        //Additional Radio Group will appear if Oral Checkbox is checked
        CheckBox oralCheck = rootView.findViewById(R.id.sex_OralCheck_checkbox);
        oralCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    oralGroup.setVisibility(View.VISIBLE);
                }
                else{
                    oralGroup.setVisibility(View.GONE);
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
        kiss = view.findViewById(R.id.sex_KissingCheck_checkbox);
        tongue = view.findViewById(R.id.sex_TongueCheck_checkbox);
        bc1 = view.findViewById(R.id.sex_BirthControl1_checkbox);
        bc2 = view.findViewById(R.id.sex_BirthControl2_checkbox);
        bc3 = view.findViewById(R.id.sex_BirthControl3_checkbox);
        bc4 = view.findViewById(R.id.sex_BirthControl4_checkbox);
        bc5 = view.findViewById(R.id.sex_BirthControl5_checkbox);
        bc6 = view.findViewById(R.id.sex_BirthControl6_checkbox);
        bc7 = view.findViewById(R.id.sex_BirthControl7_checkbox);
        bc8 = view.findViewById(R.id.sex_BirthControl8_checkbox);
        bc9 = view.findViewById(R.id.sex_BirthControl9_checkbox);
        bc10 = view.findViewById(R.id.sex_BirthControl10_checkbox);
        bc11 = view.findViewById(R.id.sex_BirthControl11_checkbox);
        bc12 = view.findViewById(R.id.sex_BirthControl12_checkbox);
        bcNote = view.findViewById(R.id.sex_BirthControlNote_text);
        bcSubnote = view.findViewById(R.id.sex_BirthControlSubnote_text);
        analGroup = view.findViewById(R.id.sex_Anal_radioGroup);
        oralGroup = view.findViewById(R.id.sex_Oral_radioGroup);
    }
}
