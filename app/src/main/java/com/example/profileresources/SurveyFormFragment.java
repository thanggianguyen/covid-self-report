package com.example.profileresources;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import com.example.covidselfreport.MainActivity;
import com.example.covidselfreport.R;


/**
 * This Fragment contains the survey questions form that the user can fill out.
 * Used when the user initially fills out the survey (called by MainActivity).
 * Used when the user wants to go back and change their preferences (called by the ProfileFragment
 * of activity MainScreen)
 * Handles all click events.
 */
public class SurveyFormFragment extends Fragment {

    /** The preferences survey, taken from MainActivity */
    private final Survey preferences = MainActivity.getPreferenceSurvey();
    /** The group of RadioButton choices for the first question */
    private RadioGroup q1Responses;
    /** The group of RadioButton choices for the second question */
    private RadioGroup q2Responses;
    /** The group of RadioButton choices for the third question */
    private RadioGroup q3Responses;
    /** The group of CheckBoxes for the fourth question */
    private CheckBox[] q4Responses;
    /** The textbox for the optional text response for the first question */
    private EditText q1TextResponse;
    /** The textbox for the optional text response for the second question */
    private EditText q2TextResponse;
    /** The back button (ends the fragment and its host activity) */
    private Button backButton;
    /** The update button (saves the user's responses) */
    private Button updateButton;


    public SurveyFormFragment() {
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
        return inflater.inflate(R.layout.fragment_survey_form, container, false);
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

        //Initialize the View and Widget components:
        q1Responses = view.findViewById(R.id.preferencemodifier_q1_responses_radiogroup);
        q1TextResponse = view.findViewById(R.id.preferencemodifier_q1_optionalresponse_edittext);
        q2Responses = view.findViewById(R.id.preferencemodifier_q2_responses_radiogroup);
        q2TextResponse = view.findViewById(R.id.preferencemodifier_q2_optionalresponse_edittext);
        q3Responses = view.findViewById(R.id.preferencemodifier_q3_responses_radiogroup);
        backButton = view.findViewById(R.id.preferencemodifier_back_button);
        updateButton = view.findViewById(R.id.preferencemodifier_update_button);

        //Initialize q4Responses by adding each CheckBox to the array:
        int[] checkIDs = {R.id.preferencemodifier_q4_cb0_checkbox, R.id.preferencemodifier_q4_cb1_checkbox,
                R.id.preferencemodifier_q4_cb2_checkbox, R.id.preferencemodifier_q4_cb3_checkbox,
                R.id.preferencemodifier_q4_cb4_checkbox, R.id.preferencemodifier_q4_cb5_checkbox,
                R.id.preferencemodifier_q4_cb6_checkbox, R.id.preferencemodifier_q4_cb7_checkbox,
                R.id.preferencemodifier_q4_cb8_checkbox};
        q4Responses = new CheckBox[checkIDs.length];
        for (int i = 0; i < checkIDs.length; i++)
            q4Responses[i] = view.findViewById(checkIDs[i]);

        //If the host activity is SurveyModifier, call initializeComponentsForModifier().
        if (requireActivity() instanceof com.example.covidselfreport.SurveyModifier) {
            initializeComponentsForModifier(view);
        }
        //If the host activity is something else (SurveyLauncher), make the back button invisible
        // (user has no choice but to fill out the survey) and change the "Update" button text to
        //"Next", and makte updateButton in the center of the screen.
        else {
            backButton.setVisibility(View.INVISIBLE);
            updateButton.setText("Next");

            //Center the updateButton on the screen.
            ConstraintLayout layout = (ConstraintLayout)view.findViewById(R.id.profilemodifier_constraint_layout);
            ConstraintSet set = new ConstraintSet();
            set.clone(layout);
            set.connect(R.id.preferencemodifier_update_button, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
            set.applyTo(layout);
        }

        //Set the onClick action for the update button:
        updateButton = view.findViewById(R.id.preferencemodifier_update_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButtonHandler(v);
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
        try { selectedIndexQ1 = Integer.parseInt(preferences.getResponse(0)); }
        catch (NumberFormatException e) { selectedIndexQ1 = 0; }
        switch (selectedIndexQ1) {
            case 0: q1Responses.check(R.id.preferencemodifier_q1_rb0_radiobutton); break;
            case 1: q1Responses.check(R.id.preferencemodifier_q1_rb1_radiobutton); break;
            case 2: q1Responses.check(R.id.preferencemodifier_q1_rb2_radiobutton); break;
            case 3: q1Responses.check(R.id.preferencemodifier_q1_rb3_radiobutton); break;
            case 4: q1Responses.check(R.id.preferencemodifier_q1_rb4_radiobutton); break;
        }

        //Set the text response for Q1:
        q1TextResponse.setText(preferences.getTextboxResponse(0));

        //Set the selected RadioButton of RadioGroup q2Responses:
        int selectedIndexQ2;
        try { selectedIndexQ2 = Integer.parseInt(preferences.getResponse(1)); }
        catch (NumberFormatException e) { selectedIndexQ2 = 0; }
        switch (selectedIndexQ2) {
            case 0: q2Responses.check(R.id.preferencemodifier_q2_rb0_radiobutton); break;
            case 1: q2Responses.check(R.id.preferencemodifier_q2_rb1_radiobutton); break;
            case 2: q2Responses.check(R.id.preferencemodifier_q2_rb2_radiobutton); break;
            case 3: q2Responses.check(R.id.preferencemodifier_q2_rb3_radiobutton); break;
            case 4: q2Responses.check(R.id.preferencemodifier_q2_rb4_radiobutton); break;
        }

        //Set the text response for Q2:
        q2TextResponse.setText(preferences.getTextboxResponse(1));

        //Set the selected RadioButton of RadioGroup q3Responses:
        int selectedIndexQ3;
        try { selectedIndexQ3 = Integer.parseInt(preferences.getResponse(2)); }
        catch (NumberFormatException e) { selectedIndexQ3 = 0; }
        switch(selectedIndexQ3) {
            case 0: q3Responses.check(R.id.preferencemodifier_q3_rb0_radiobutton); break;
            case 1: q3Responses.check(R.id.preferencemodifier_q3_rb1_radiobutton); break;
            case 2: q3Responses.check(R.id.preferencemodifier_q3_rb2_radiobutton); break;
            case 3: q3Responses.check(R.id.preferencemodifier_q3_rb3_radiobutton); break;
            case 4: q3Responses.check(R.id.preferencemodifier_q3_rb4_radiobutton); break;
        }

        //Set the selected checkboxes for Q4:
        for (int i = 0; i < q4Responses.length; i++) {
            if (preferences.getResponse(3).contains(q4Responses[i].getText().toString()))
                q4Responses[i].setChecked(true);
        }

        //Set the onClick action for the back button:
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButtonHandler(v);
            }
        });
    }


    /**
     * Handles click events for the updateButton.
     * Saves the radio button response and the text box response for question 1.
     * Saves the radio button response and the text box response for question 2.
     * Saves the radio button response for question 3.
     * Saves the checked checkboxes for question 4.
     * Saves the Survey object to a JSON file.
     * Quits the parent activity.
     * @param view The update button
     */
    private void updateButtonHandler(View view) {
        //Set the response for q1 (index 0) to the selected radiobutton index of q1Responses:
        preferences.setQuestion(0, ((TextView)requireActivity().findViewById(R.id.preferencemodifier_q1_text_textview)).getText().toString());
        preferences.setResponse(0, Integer.toString(q1Responses.indexOfChild(requireView().findViewById(
                q1Responses.getCheckedRadioButtonId()))));
        preferences.setTextboxResponse(0, q1TextResponse.getText().toString());

        //Set the response for q2 (index 1) to the selected radiobutton index of q2Responses:
        preferences.setQuestion(1, ((TextView)requireActivity().findViewById(R.id.preferencemodifier_q2_text_textview)).getText().toString());
        preferences.setResponse(1, Integer.toString(q2Responses.indexOfChild(requireView().findViewById(
                q2Responses.getCheckedRadioButtonId()))));
        preferences.setTextboxResponse(1, q2TextResponse.getText().toString());

        //Set the response for q3 (index 2) to the selected radiobutton index of q3Responses:
        preferences.setQuestion(2, ((TextView)requireActivity().findViewById(R.id.preferencemodifier_q3_text_textview)).getText().toString());
        preferences.setResponse(2, Integer.toString(q3Responses.indexOfChild(requireView().findViewById(
                q3Responses.getCheckedRadioButtonId()))));

        //Set the response for q4 (index 3) to the selected CheckBoxs' text fields:
        preferences.setQuestion(3, ((TextView)requireActivity().findViewById(R.id.preferencemodifier_q4_text_textview)).getText().toString());
        String q4Response = "";
        for (CheckBox current : q4Responses) {
            if (current.isChecked())
                q4Response += current.getText().toString() + ", ";
        }
        preferences.setResponse(3, q4Response.substring(0, q4Response.length() - 2));

        //Save responses and quit the activity
        preferences.saveToJson(requireActivity().getFilesDir().toString(), MainActivity.PREFERENCE_SURVEY_FILE_NAME);
        if (requireActivity() instanceof com.example.covidselfreport.SurveyLauncher) {
            Intent returnIntent = new Intent();
            requireActivity().setResult(Activity.RESULT_OK, returnIntent);
        }
        requireActivity().finish();
    }


    /**
     * Handles click events for the updateButton.
     * Quits the parent activity.
     * @param view the back button
     */
    private void backButtonHandler(View view) {
        requireActivity().finish();
    }

}