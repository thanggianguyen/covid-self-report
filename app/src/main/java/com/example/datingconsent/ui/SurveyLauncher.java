package com.example.datingconsent.ui;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.datingconsent.R;
import com.example.datingconsent.surveyresources.*;

import java.util.List;


/**
 *  Launches the preferences survey (the initial survey users fill out) by displaying the fragment
 *  containing the questions.
 *  Called by MainActivity for the initial filling out of the preferences survey.
 *  Only called if a preferences survey file is not detected by the app.
 */
public class SurveyLauncher extends AppCompatActivity {

    /** Fragment manager for this activity */
    private FragmentManager fm;
    /** The main Fragment to be displayed by this activity */
    private Fragment datingFragment, sexFragment;


    /**
     * Called upon the creation of the SurveyLauncher activity.
     * Displays the survey form fragment (SurveyFormFragment) on this activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_launcher);

        fm = getSupportFragmentManager();
        datingFragment = new SurveyDatingFragment();
        sexFragment = new SurveySexFragment();

        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.survey_content_container, datingFragment);
        transaction.commit();
    }

    public void datingDone(View view) {
        FragmentTransaction transaction = fm.beginTransaction();
        Fragment nextFragment = getNextFragment();
        transaction.replace(R.id.survey_content_container, nextFragment);
        transaction.commit();}
    /**
     * Returns the next question fragment to be displayed, if there is one, and calls for the
     * response for the current fragment's question to be saved.
     * If the currently displayed question fragment is Q2 (the final question), return null.
     * If the currently displayed question fragment is not Q2, return the next question fragment.
     * @return
     */
    private Fragment getNextFragment() {
        Fragment current = getActiveFragment();
        if (current instanceof SurveyDatingFragment)
            return sexFragment;
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
}

