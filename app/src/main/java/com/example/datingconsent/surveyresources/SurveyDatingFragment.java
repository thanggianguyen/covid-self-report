package com.example.datingconsent.surveyresources;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.datingconsent.R;

public class SurveyDatingFragment extends Fragment {

    private RadioGroup PhyTou, Pay, Sex;
    private RadioButton PhyTouNo, PayYes, PayNo, SexYes, SexNo;
    private CheckBox date1, date2, date3, date4, date5, date6;
    private TextView PhyTouWarn, PhyTouWhere, PayWarn, SexWarn, dateWarn;
    private int count=0;
    //TODO: Transition from Dating Survey to Sex Survey
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
        View rootView = inflater.inflate(R.layout.fragment_survey_dating, container, false);
        //Counter used to check if the user checked any checkboxes in the date section
        CheckBox date1 = rootView.findViewById(R.id.dating_Date1_checkbox);
        date1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    count++;
                }
                else {
                    count--;
                }
            }
        });
        CheckBox date2 = rootView.findViewById(R.id.dating_Date2_checkbox);
        date2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    count++;
                }
                else {
                    count--;
                }
            }
        });
        CheckBox date3 = rootView.findViewById(R.id.dating_Date3_checkbox);
        date3.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    count++;
                }
                else {
                    count--;
                }
            }
        });
        CheckBox date4 = rootView.findViewById(R.id.dating_Date4_checkbox);
        date4.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    count++;
                }
                else {
                    count--;
                }
            }
        });
        CheckBox date5 = rootView.findViewById(R.id.dating_Date5_checkbox);
        date5.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    count++;
                }
                else {
                    count--;
                }
            }
        });
        CheckBox date6 = rootView.findViewById(R.id.dating_Date6_checkbox);
        date6.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    count++;
                }
                else {
                    count--;
                }
            }
        });
        //The Done button will warn the user if any sections require attention after being clicked
        //Else no warning message will appear
        Button done = rootView.findViewById(R.id.dating_DatingSurveyDone_button);
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (PhyTou.getCheckedRadioButtonId() == -1)
                {
                    // no radio buttons are checked
                    PhyTouWarn.setVisibility(View.VISIBLE);
                }
                else
                {
                    // one of the radio buttons is checked
                    PhyTouWarn.setVisibility(View.INVISIBLE);
                }
                if (Pay.getCheckedRadioButtonId() == -1)
                {
                    // no radio buttons are checked
                    PayWarn.setVisibility(View.VISIBLE);
                }
                else
                {
                    // one of the radio buttons is checked
                    PayWarn.setVisibility(View.INVISIBLE);
                }
                if (Sex.getCheckedRadioButtonId() == -1)
                {
                    // no radio buttons are checked
                    SexWarn.setVisibility(View.VISIBLE);
                }
                else
                {
                    // one of the radio buttons is checked
                    SexWarn.setVisibility(View.INVISIBLE);
                }
                if(count==0)
                {
                    // none of the checkboxes are checked
                    dateWarn.setVisibility(View.VISIBLE);
                }
                else
                {
                    // one of the checkboxes is checked
                    dateWarn.setVisibility(View.INVISIBLE);
                }
            }
        });
        //Additional question asked if PhyTou Checkbox is checked
        RadioButton PhyTouYes = rootView.findViewById(R.id.dating_PhyTouYes_radiobtn);
        PhyTouYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    PhyTouWhere.setVisibility(View.VISIBLE);
                } else {
                    PhyTouWhere.setVisibility(View.GONE);
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
        PhyTou = view.findViewById(R.id.dating_PhyTou_RadioGroup);
        PhyTouWhere = view.findViewById(R.id.PhyTouWhere);
        Pay = view.findViewById(R.id.dating_Pay_RadioGroup);
        Sex = view.findViewById(R.id.dating_Sex_RadioGroup);
        PhyTouNo = view.findViewById(R.id.dating_PhyTouNo_radiobtn);
        PayYes = view.findViewById(R.id.dating_PayYes_radiobtn);
        PayNo = view.findViewById(R.id.dating_PayNo_radiobtn);
        SexYes = view.findViewById(R.id.dating_SexYes_radiobtn);
        SexNo = view.findViewById(R.id.dating_SexNo_radiobtn);
        PhyTouWarn = view.findViewById(R.id.dating_PhyTouWarn_text);
        PayWarn = view.findViewById(R.id.dating_PayWarn_text);
        SexWarn = view.findViewById(R.id.dating_SexWarn_text);
        date1 = view.findViewById(R.id.dating_Date1_checkbox);
        date2 = view.findViewById(R.id.dating_Date2_checkbox);
        date3 = view.findViewById(R.id.dating_Date3_checkbox);
        date4 = view.findViewById(R.id.dating_Date4_checkbox);
        date5 = view.findViewById(R.id.dating_Date5_checkbox);
        date6 = view.findViewById(R.id.dating_Date6_checkbox);
        dateWarn = view.findViewById(R.id.dating_DateWarn_text);
    }
}