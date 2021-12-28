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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.datingconsent.R;

public class SurveyDatingFragment extends Fragment {

    private RadioGroup PhyTou, Pay, Sex;
    private RadioButton PhyTouYes, PhyTouNo, PayYes, PayNo, SexYes, SexNo;
    private CheckBox date1, date2, date3, date4, date5, date6;
    private TextView PhyTouWarn;

    public SurveyDatingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_survey_sex, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        /**
         * Instantiate java parameters with UI parameters
         */
        PhyTou = view.findViewById(R.id.dating_PhyTou_RadioGroup);
        Pay = view.findViewById(R.id.dating_Pay_RadioGroup);
        Sex = view.findViewById(R.id.dating_Sex_RadioGroup);
        PhyTouYes = view.findViewById(R.id.dating_PhyTouYes_radiobtn);
        PhyTouNo = view.findViewById(R.id.dating_PhyTouNo_radiobtn);
        PayYes = view.findViewById(R.id.dating_PayYes_radiobtn);
        PayNo = view.findViewById(R.id.dating_PayNo_radiobtn);
        SexYes = view.findViewById(R.id.dating_SexYes_radiobtn);
        SexNo = view.findViewById(R.id.dating_SexNo_radiobtn);
        PhyTouWarn = view.findViewById(R.id.dating_PhyTouWarn_text);
        date1 = view.findViewById(R.id.dating_Date1_checkbox);
        date2 = view.findViewById(R.id.dating_Date2_checkbox);
        date3 = view.findViewById(R.id.dating_Date3_checkbox);
        date4 = view.findViewById(R.id.dating_Date4_checkbox);
        date5 = view.findViewById(R.id.dating_Date5_checkbox);
        date6 = view.findViewById(R.id.dating_Date6_checkbox);
    }
}