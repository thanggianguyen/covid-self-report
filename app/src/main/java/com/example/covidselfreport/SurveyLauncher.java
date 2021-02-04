package com.example.covidselfreport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.util.List;

import com.example.profile.*;


/**
 * Launches the preferences survey (the initial survey users fill out) by displaying the appropriate
 * fragment for each question, and handling all click events that go to the "Next" button.
 */
public class SurveyLauncher extends AppCompatActivity {

    /** The current question being answered for the survey (current question fragment being displayed) */
    private int currentQuestion;
    /** Fragment manager for this activity */
    private FragmentManager fm;
    /** The fragments for each question */
    private Fragment q1, q2, q3, q4;


    /**
     * Called upon Activity creation
     * Initializes each fragment and loads the Fragment Container with the question 1 fragment.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_launcher);

        //Initialize the fragment manager and all fragments used in the container:
        currentQuestion = 1; //initialize current question to 1 (the starting question)
        fm = getSupportFragmentManager();
        q1 = new Q1Fragment();
        q2 = new Q2Fragment();
        q3 = new Q3Fragment();
        q4 = new Q4Fragment();

        //Display the fragment for Q1 initially:
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.survey_question_container, q1, "QUESTION_1");
        transaction.commit();
    }


    /**
     * Event handler for the "Next" button at the bottom of this activity.
     * If user is not at the final question: Goes to the next question fragment.
     * If user is at the final question: Ends the activity, execution returns to MainActivity.
     */
    public void nextButtonHandler(View view) {
        //Begin the fragment transaction (transitioning fragments) and get the next fragment to be displayed:
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment nextFragment = getNextFragment();

        //if the next fragment to be displayed is null (nothing was returned), go to the final survey screen:
        if (nextFragment == null) {
            MainActivity.profile.setPreferences(MainActivity.preferenceSurvey);
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
        //If the next fragment is not null, go to the next fragment and increment the current question number:
        else {
            currentQuestion++;
            transaction.replace(R.id.survey_question_container, nextFragment);
            transaction.commit();
        }
    }


    /**
     * Saves the response of the current survey question being displayed to the survey object in
     * MainActivity.
     */
    private void saveResponse() {
        //Initialize the question, response, and optionalResponse strings to empty strings:
        String question = "", response = "", optionalResponse = "";

        //These int variables represent the View IDs for the question TextView, response RadioGroup, etc.:
        int textID, responsesID, optionalRespID;

        //For questions 1-3:
        if (currentQuestion >= 1 && currentQuestion <= 3) {
            if (currentQuestion == 1) {
                textID = R.id.preference_q1_text_textview; responsesID = R.id.preference_q1_responses_radiogroup; optionalRespID = R.id.preference_q1_optionalresponse_edittext;
            }
            else if (currentQuestion == 2) {
                textID = R.id.preference_q2_text_textview; responsesID = R.id.preference_q2_responses_radiogroup; optionalRespID = R.id.preference_q2_optionalresponse_edittext;
            }
            else {
                textID = R.id.preference_q3_text_textview; responsesID = R.id.preference_q3_responses_radiogroup; optionalRespID = 0;
            }

            //Assign question to the content of thequestion box of the appropriate question fragment:
            question = ((TextView)findViewById(textID)).getText().toString();

            //Locate the selected response RadioButton and save its index value to response String:
            RadioGroup responses = (RadioGroup)findViewById(responsesID);
            View selected = findViewById(responses.getCheckedRadioButtonId());
            response += responses.indexOfChild(selected);

            //If the current question is 1 or 2, save the optional response to optionalResponse String:
            if (currentQuestion == 1 || currentQuestion == 2)
                optionalResponse = ((EditText)findViewById(optionalRespID)).getText().toString();
        }
        //For question 4:
        else if (currentQuestion == 4) {
            question = ((TextView)findViewById(R.id.preference_q4_text_textview)).getText().toString();
            //Index the various CheckBox ID numbers in array checkIDs:
            int[] checkIDs = {R.id.preference_q4_cb0_checkbox, R.id.preference_q4_cb1_checkbox, R.id.preference_q4_cb2_checkbox, R.id.preference_q4_cb3_checkbox, R.id.preference_q4_cb4_checkbox, R.id.preference_q4_cb5_checkbox, R.id.preference_q4_cb6_checkbox,
                    R.id.preference_q4_cb7_checkbox, R.id.preference_q4_cb8_checkbox};
            CheckBox[] checkboxes = new CheckBox[checkIDs.length];

            //Assign each CheckBox element in checkboxes using its corresponding ID num in checkIDs.
            for (int i = 0; i < checkIDs.length; i++)
                checkboxes[i] = findViewById(checkIDs[i]);

            //For each checkbox that is checked, add its text to the response String.
            for (int i = 0; i < checkboxes.length; i++) {
                if (checkboxes[i].isChecked()) {
                    response += checkboxes[i].getText().toString() + ", ";
                }
            }

            //Cut out the final two characters in response, which are an unnecessary comma and space:
            response = response.substring(0, response.length() - 2);
        }

        //Set the survey question's question text, response, and optional response:
        MainActivity.preferenceSurvey.setQuestion(currentQuestion - 1, question);
        MainActivity.preferenceSurvey.setResponse(currentQuestion - 1, response);
        MainActivity.preferenceSurvey.setTextboxResponse(currentQuestion - 1, optionalResponse);
    }


    /**
     * Returns the current active question fragment within the fragment display:
     */
    private Fragment getActiveFragment() {
        List<Fragment> fragments = fm.getFragments();
        for (Fragment frag : fragments) {
            if (frag != null && frag.isVisible())
                return frag;
        }
        return null;
    }


    /**
     * Returns the next question fragment to be displayed, if there is one, and calls for the
     * response for the current fragment's question to be saved.
     * If the currently displayed question fragment is Q4 (the final question), return null.
     * If the currently displayed question fragment is not Q4, return the next question fragment.
     */
    private Fragment getNextFragment() {
        Fragment current = getActiveFragment();
        saveResponse();
        if (current instanceof Q1Fragment)
            return q2;
        else if (current instanceof Q2Fragment)
            return q3;
        else if (current instanceof Q3Fragment)
            return q4;
        else
            return null;
    }
}