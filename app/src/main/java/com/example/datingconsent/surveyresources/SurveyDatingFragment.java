package com.example.datingconsent.surveyresources;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;


import com.example.datingconsent.ui.MainActivity;
import com.example.datingconsent.R;

public class SurveyDatingFragment extends Fragment {

    private final Survey datingpreferences = MainActivity.getDatingPreferenceSurvey();
    private FragmentManager fm;
    private RadioGroup PhyTou, Pay;
    public RadioGroup Sex;
    private CheckBox[] dates;
    private TextView[] warn;
    private EditText PhyTouWhere, datesOther;
    private Button backButton, doneButton;
    private int count=0;

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_survey_dating, container, false);
    }


    /**
     * Called upon the creation of the Fragment View object.
     * Initializes all fields to the appropriate View/Widget in the fragment xml file.
     * Sets button functionality.
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {

        PhyTou = view.findViewById(R.id.dating_PhyTou_RadioGroup);
        PhyTouWhere = view.findViewById(R.id.PhyTouWhere);
        Pay = view.findViewById(R.id.dating_Pay_RadioGroup);
        datesOther = view.findViewById(R.id.dating_DateOther_edittext);
        Sex = view.findViewById(R.id.dating_Sex_RadioGroup);
        doneButton = view.findViewById(R.id.dating_DatingSurveyDone_button);
        backButton = view.findViewById(R.id.dating_DatingSurveyBack_button);

        //Initialize Dates by adding each CheckBox to the array:
        int[] checkIDs = {R.id.dating_Date1_checkbox, R.id.dating_Date2_checkbox,
                R.id.dating_Date3_checkbox, R.id.dating_Date4_checkbox,
                R.id.dating_Date5_checkbox, R.id.dating_Date6_checkbox};
        dates = new CheckBox[checkIDs.length];
        for (int i = 0; i < checkIDs.length; i++) {
            dates[i] = view.findViewById(checkIDs[i]);
            //Counter used for Checkboxes that is used later in the code
            dates[i].setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
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
        }
        //Initialize Warnings by adding each TextView to the array:
        int[] textIDs = {R.id.dating_PhyTouWarn_text, R.id.dating_PayWarn_text,
                R.id.dating_DateWarn_text, R.id.dating_SexWarn_text};
        warn = new TextView[textIDs.length];
        for (int i = 0; i < textIDs.length; i++)
            warn[i] = view.findViewById(textIDs[i]);
        //If the host activity is SurveyModifier, call initializeComponentsForModifier().
        if (requireActivity() instanceof com.example.datingconsent.ui.SurveyModifier) {
            initializeComponentsForModifier(view);
            doneButton.setText("Update");
        }
        //If the host activity is something else (SurveyLauncher), make the back button invisible
        // (user has no choice but to fill out the survey)
        else {
            backButton.setVisibility(View.INVISIBLE);

            //Center the updateButton on the screen.
            ConstraintLayout layout = (ConstraintLayout)view.findViewById(R.id.datingsurvey_constraint_layout);
            ConstraintSet set = new ConstraintSet();
            set.clone(layout);
            set.connect(R.id.dating_DatingSurveyDone_button, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
            set.applyTo(layout);
        }
        //If user clicks PhyTouYes, PhyTouWhere should appear to answer.
        //If user clicks PhyTouNo, PhyTouWhere should not appear to answer.
        /*
        }*/
        PhyTou.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (PhyTou.getCheckedRadioButtonId() == R.id.dating_PhyTouYes_radiobtn) {
                    PhyTouWhere.setVisibility(View.VISIBLE);

                } else if (PhyTou.getCheckedRadioButtonId() == R.id.dating_PhyTouNo_radiobtn) {
                    PhyTouWhere.setVisibility(View.GONE);
                }
            }
        });


        //Set the onClick action for the update button:
        doneButton = view.findViewById(R.id.dating_DatingSurveyDone_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PhyTou.getCheckedRadioButtonId() != -1 && Pay.getCheckedRadioButtonId() != -1 &&
                        Sex.getCheckedRadioButtonId() != -1 && count>0) {
                    doneButtonHandler(v);
                }
                else {
                    if (PhyTou.getCheckedRadioButtonId() == -1) {
                        // no radio buttons are checked
                        warn[0].setVisibility(View.VISIBLE);
                    } else {
                        // one of the radio buttons is checked
                        warn[0].setVisibility(View.INVISIBLE);
                    }
                    if (Pay.getCheckedRadioButtonId() == -1) {
                        // no radio buttons are checked
                        warn[1].setVisibility(View.VISIBLE);
                    } else {
                        // one of the radio buttons is checked
                        warn[1].setVisibility(View.INVISIBLE);
                    }
                    if (count == 0) {
                        // none of the checkboxes are checked
                        warn[2].setVisibility(View.VISIBLE);
                    } else {
                        // one of the checkboxes is checked
                        warn[2].setVisibility(View.INVISIBLE);
                    }
                    if (Sex.getCheckedRadioButtonId() == -1) {
                        // no radio buttons are checked
                        warn[3].setVisibility(View.VISIBLE);
                    } else {
                        // one of the radio buttons is checked
                        warn[3].setVisibility(View.INVISIBLE);
                    }
                }
            }
        });
    }

    /**
     * Initializes the settings for each component to the user's previous survey responses.
     * Only called if the parent activity is SurveyModifier (only called if the user has already filled out a survey).
     * @param view
     */
    private void initializeComponentsForModifier(View view) {
        //Set the selected RadioButton of RadioGroup q1Responses:
        int selectedIndexQ1;
        try { selectedIndexQ1 = Integer.parseInt(datingpreferences.getResponse(0)); }
        catch (NumberFormatException e) { selectedIndexQ1 = 0; }
        switch (selectedIndexQ1) {
            case 0: PhyTou.check(R.id.dating_PhyTouYes_radiobtn);
                if (PhyTou.getCheckedRadioButtonId() == R.id.dating_PhyTouYes_radiobtn) {
                    PhyTouWhere.setVisibility(View.VISIBLE);

                } else if (PhyTou.getCheckedRadioButtonId() == R.id.dating_PhyTouNo_radiobtn) {
                    PhyTouWhere.setVisibility(View.GONE);
                }
            break;
            case 1: PhyTou.check(R.id.dating_PhyTouNo_radiobtn); break;
        }

        //Set the text response for Q1:
        PhyTouWhere.setText(datingpreferences.getTextboxResponse(0));

        //Set the selected RadioButton of RadioGroup q2Responses:
        int selectedIndexQ2;
        try { selectedIndexQ2 = Integer.parseInt(datingpreferences.getResponse(1)); }
        catch (NumberFormatException e) { selectedIndexQ2 = 0; }
        switch (selectedIndexQ2) {
            case 0: Pay.check(R.id.dating_PayYes_radiobtn); break;
            case 1: Pay.check(R.id.dating_PayNo_radiobtn); break;
        }

        //Set the selected checkboxes for Q3:
        for (int i = 0; i < dates.length; i++) {
            if (datingpreferences.getResponse(2).contains(dates[i].getText().toString()))
                dates[i].setChecked(true);
        }
        //Set the text response for Q3:
        datesOther.setText(datingpreferences.getTextboxResponse(2));

        //Set the selected RadioButton of RadioGroup q3Responses:
        int selectedIndexQ4;
        try { selectedIndexQ4 = Integer.parseInt(datingpreferences.getResponse(3)); }
        catch (NumberFormatException e) { selectedIndexQ4 = 0; }
        switch(selectedIndexQ4) {
            case 0: Sex.check(R.id.dating_SexYes_radiobtn); break;
            case 1: Sex.check(R.id.dating_SexNo_radiobtn); break;
        }

        //Set the onClick action for the back button:
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButtonHandler(v);
            }
        });
    }

    private void doneButtonHandler(View view) {
        //Set the response for Physical Touch (index 0 of the preferenceSurvey questions arrays) to the selected radiobutton index of Physical Touch Response:
        datingpreferences.setQuestion(0, ((TextView)requireActivity().findViewById(R.id.dating_PhyTouTitle_text)).getText().toString());
        datingpreferences.setResponse(0, Integer.toString(PhyTou.indexOfChild(requireView().findViewById(
                PhyTou.getCheckedRadioButtonId()))));
        if(PhyTou.getCheckedRadioButtonId() == R.id.dating_PhyTouNo_radiobtn)
            datingpreferences.setTextboxResponse(0, " ");
        else if(PhyTou.getCheckedRadioButtonId() == R.id.dating_PhyTouYes_radiobtn)
            datingpreferences.setTextboxResponse(0, PhyTouWhere.getText().toString());

        //Set the response for Paying (index 1 of the preferenceSurvey questions arrays) to the selected radiobutton index of Paying Response:
        datingpreferences.setQuestion(1, ((TextView)requireActivity().findViewById(R.id.dating_PayTitle_text)).getText().toString());
        datingpreferences.setResponse(1, Integer.toString(Pay.indexOfChild(requireView().findViewById(
                Pay.getCheckedRadioButtonId()))));

        //Set the response for Dates (index 2 of the preferenceSurvey questions arrays) to the selected CheckBoxes' text fields:
        datingpreferences.setQuestion(2, ((TextView)requireActivity().findViewById(R.id.dating_DateTitle_text)).getText().toString());
        String datesResponse = "";
        for (CheckBox current : dates) {
            if (current.isChecked())
                datesResponse += current.getText().toString() + ", ";
        }
        datingpreferences.setResponse(2, datesResponse.substring(0, datesResponse.length() - 2));
        datingpreferences.setTextboxResponse(1, datesOther.getText().toString());

        //Set the response for Sex (index 3 of the preferenceSurvey questions arrays) to the selected radiobutton index of Sex Response:
        datingpreferences.setQuestion(3, ((TextView)requireActivity().findViewById(R.id.dating_SexTitle_text)).getText().toString());
        datingpreferences.setResponse(3, Integer.toString(Sex.indexOfChild(requireView().findViewById(
                Sex.getCheckedRadioButtonId()))));

        //Save responses and quit the activity
        datingpreferences.saveToJson(requireActivity().getFilesDir().toString(), MainActivity.DATING_PREFERENCE_SURVEY_FILE_NAME);
        if(Sex.getCheckedRadioButtonId()==R.id.dating_SexYes_radiobtn) {
            fm = getParentFragmentManager();
            int container = requireActivity() instanceof com.example.datingconsent.ui.SurveyModifier ? R.id.surveymodifier_content_container: R.id.survey_content_container;
            fm.beginTransaction().replace(container, new SurveySexFragment()).commit();
        }
        else if(Sex.getCheckedRadioButtonId()==R.id.dating_SexNo_radiobtn){
            if (requireActivity() instanceof com.example.datingconsent.ui.SurveyLauncher) {
                Intent returnIntent = new Intent();
                requireActivity().setResult(Activity.RESULT_OK, returnIntent);
            }
            requireActivity().finish();
        }
    }
    /**
     * Handles click events for the backButton.
     * Quits the parent activity.
     * @param view the back button
     */
    private void backButtonHandler(View view) {
        requireActivity().finish();
    }
}