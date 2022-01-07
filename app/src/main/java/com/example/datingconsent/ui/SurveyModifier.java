package com.example.datingconsent.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.datingconsent.R;
import com.example.datingconsent.surveyresources.*;


/**
 * Opens up the already filled out survey.
 * Displays the first survey form fragment (class SurveyDatingFragment) and allows the user to modify
 * their responses.
 * Called if the user clicks the "Change preferences" button on the profile tab of the
 * app (ProfileFragment).
 */
public class SurveyModifier extends AppCompatActivity {

    /** Fragment manager for this activity */
    private FragmentManager fm;
    /** The main Fragment to be displayed by this activity */
    private Fragment mainFragment;


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
        mainFragment = new SurveyDatingFragment();
        fm.beginTransaction().replace(R.id.surveymodifier_content_container, mainFragment).commit();
    }

}