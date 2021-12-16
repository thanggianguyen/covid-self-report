package com.example.datingconsent.ui;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.datingconsent.R;
import com.example.datingconsent.profileresources.Profile;
import com.example.datingconsent.profileresources.Survey;
import com.google.gson.Gson;

//Todo: change question count
//Todo: add stuff to MainActivity
public class MainActivity extends AppCompatActivity {
    /** Number of questions in the preference survey */
    public static final int PREFERENCE_QUESTION_COUNT = 5;
    /** The name of the preference survey JSON file stored in the app */
    public static final String PREFERENCE_SURVEY_FILE_NAME = "preference_survey.json";
    /** The name of the profile JSON file stored in the app */
    public static final String PROFILE_FILE_NAME = "profile.json";
    /** Stores the user's preference survey responses */
    static Survey preferenceSurvey;
    /** Stores the user's profile information */
    static Profile profile;

    /**
     * Called upon Activity creation.
     * Checks to see if there is a profile JSON file (for the field profile).
     *      If so, it builds the JSON string into a Profile object.
     *      If not, it launches the ProfileCreator Activity.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Necessary Android method calls within onCreate():
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize local variables gson (JSON file reader), toProfileCreator (takes user to the ProfileCreator activity), and profileJsonStr (String of the user's profile, saved as a JSON file):
        Gson gson = new Gson();
        Intent toProfileCreator = new Intent(this, ProfileCreator.class);
        startActivityForResult(toProfileCreator, 123);
        //deprecated

        //String profileJsonStr = checkForFile(PROFILE_FILE_NAME);
    }
}