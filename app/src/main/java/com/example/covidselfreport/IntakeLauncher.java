package com.example.covidselfreport;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.List;

import com.example.intakesurvey.*;
import com.example.profileresources.Survey;


/**
 * Launches the intake survey (the daily survey users fill out) by displaying the appropriate
 * fragment for each question, and handling all click events that go to the "Next" button.
 */
public class IntakeLauncher extends AppCompatActivity {

    /** The current question being answered for the survey (current question fragment being displayed) */
    private int currentQuestion;
    /** The number of questions that exist in the survey */
    public static final int NUM_INTAKE_QUESTIONS = 2;
    /** Fragment manager for this activity */
    private FragmentManager fm;
    /** The fragments for each question */
    private Fragment q1, q2;
    /** The name of the intake survey file for today's date */
    private String intakeJsonFileName;
    /** The intake survey being filled out today */
    private Survey dailyIntake;


    /**
     * Called when the activity is created.
     * Today's intake file name is initialized.
     * The question fragments are initialized.
     * Displays the first question fragment.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intake_launcher);

        //Find today's date and generate the intake file name ("intake_yyyy_MM_dd.json"):
        intakeJsonFileName = getIntakeFileNameToday();

        //If the intake survey was already taken today, end the activity.
        if (intakeTakenToday()) {
            //Intent toCompleted = new Intent(this, IntakeCompleted.class);
            //startActivity(toCompleted);
            finish();
        }

        //Initialize all appropriate fields:
        currentQuestion = 1;
        dailyIntake = new Survey(NUM_INTAKE_QUESTIONS);
        fm = getSupportFragmentManager();
        q1 = new IntakeQ1Fragment();
        q2 = new IntakeQ2Fragment();

        //Display question 1's fragment:
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.intake_question_container, q1);
        transaction.commit();
    }


    /**
     * Event handler for the "Next" button at the bottom of this activity.
     * If user is not at the final question: Goes to the next question fragment.
     * If the user is at the final question: Saves the dailyIntake Survey object to a json file,
     * execution returns to the MainScreen activity.
     * @param view
     */
    public void intakeNextButtonHandler(View view) {
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment nextFragment = getNextFragment();

        if (nextFragment == null) {
            dailyIntake.saveToJson(getFilesDir().toString(), intakeJsonFileName);
            //Intent toCompletedScreen = new Intent(this, IntakeCompleted.class);
            //startActivity(toCompletedScreen);

            finish();
        } else {
            currentQuestion++;
            if (currentQuestion == 2)
                ((Button) view).setText(R.string.finish);
            transaction.replace(R.id.intake_question_container, nextFragment);
            transaction.commit();
        }
    }


    /**
     * Returns the next question fragment to be displayed, if there is one, and calls for the
     * response for the current fragment's question to be saved.
     * If the currently displayed question fragment is Q2 (the final question), return null.
     * If the currently displayed question fragment is not Q2, return the next question fragment.
     * @return
     */
    private Fragment getNextFragment() {
        Fragment current = getActiveFragment();
        saveResponse();
        if (current instanceof IntakeQ1Fragment)
            return q2;
        else
            return null;
    }


    /**
     * Returns the current active question fragment within the fragment display:
     * @return
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
     * Saves the response of the current survey question being displayed to the survey object in
     * MainActivity.
     */
    private void saveResponse() {
        String question = "", response = "", textResponse = "";

        if (currentQuestion == 1) {
            question = ((TextView) findViewById(R.id.intake_q1_text_textview)).getText().toString();
            RadioGroup responses = (RadioGroup) findViewById(R.id.intake_q1_responses_radiogroup);
            View selected = findViewById(responses.getCheckedRadioButtonId());
            response += responses.indexOfChild(selected);
        } else if (currentQuestion == 2) {
            question = ((TextView) findViewById(R.id.intake_q2_text_textview)).getText().toString();
            RadioGroup responses = (RadioGroup) findViewById(R.id.intake_q2_responses_radiogroup);
            View selected = findViewById(responses.getCheckedRadioButtonId());
            if (responses.indexOfChild(selected) == 0)
                response += "Yes";
            else
                response += "No";

            String textRespPart1 = "Where: " + ((EditText) findViewById(R.id.intake_q2_where_edittext)).getText().toString();
            String textRespPart2 = ",   With whom: " + ((EditText) findViewById(R.id.intake_q2a_who_edittext)).getText().toString();
            textResponse = textRespPart1 + textRespPart2;
        }

        dailyIntake.setQuestion(currentQuestion - 1, question);
        dailyIntake.setResponse(currentQuestion - 1, response);
        dailyIntake.setTextboxResponse(currentQuestion - 1, textResponse);
    }


    /**
     * States whether the intake survey was taken today or not.
     * @return True if the intake was taken today, false if it was not taken today.
     */
    private boolean intakeTakenToday() {
        File intakeJsonFile = new File(getFilesDir(), intakeJsonFileName);
        return intakeJsonFile.exists();
    }


    /**
     * @return The intake file name with today's date.
     */
    public static String getIntakeFileNameToday() {
        //Find today's date and generate the intake file name ("intake_yyyy_MM_dd.json"):
        Date today = Calendar.getInstance().getTime();
        return getIntakeFileNameByDate(today);
    }


    /**
     * @param date The date of the intake file being searched for.
     * @return The name of the intake file with the specified date.
     */
    public static String getIntakeFileNameByDate(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd", Locale.getDefault());
        String formattedDate = df.format(date);
        return "intake_" + formattedDate.toString() + ".json";
    }
}
