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

import com.example.profileresources.*;


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
        mainFragment = new SurveyFormFragment();
        fm.beginTransaction().replace(R.id.survey_content_container, mainFragment).commit();
    }
}