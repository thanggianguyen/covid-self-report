package com.example.datingconsent.ui;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.example.datingconsent.R;
import com.example.datingconsent.profileresources.Profile;
import com.example.datingconsent.surveyresources.Survey;
import com.example.datingconsent.surveyresources.SurveyDatingFragment;
import com.example.datingconsent.surveyresources.SurveySexFragment;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

//Todo: more stuff to MainActivity at onCreate
public class MainActivity extends AppCompatActivity {
    /** Number of questions in the preference survey */
    public static final int DATING_PREFERENCE_QUESTION_COUNT = 4;
    /** The name of the preference survey JSON file stored in the app */
    public static final String DATING_PREFERENCE_SURVEY_FILE_NAME = "dating_preference_survey.json";
    /** Number of questions in the preference survey */
    public static final int SEX_PREFERENCE_QUESTION_COUNT = 8;
    /** The name of the preference survey JSON file stored in the app */
    public static final String SEX_PREFERENCE_SURVEY_FILE_NAME = "dating_preference_survey.json";
    /** The name of the profile JSON file stored in the app */
    public static final String PROFILE_FILE_NAME = "profile.json";
    /** Stores the user's preference survey responses */
    static Survey datingpreferenceSurvey;
    /** Stores the user's preference survey responses */
    static Survey sexpreferenceSurvey;
    /** Stores the user's profile information */
    static Profile profile;
    public static final String TAG = "MainActivity";
    public ActivityResultLauncher<Intent> profileCreatorResultLauncher,surveyResultLauncher;


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
         profileCreatorResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Log.d("MainActivity","Result profile callback");
                        profile.saveToJson(getFilesDir().toString(), PROFILE_FILE_NAME);
                        checkForPreferenceSurvey();
                    }
                }
        );
         surveyResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if(result.getResultCode() == Activity.RESULT_OK){
                        Log.d("MainActivity","Result survey callback");
                        preferenceSurvey.saveToJson(getFilesDir().toString(), PREFERENCE_SURVEY_FILE_NAME);
                        profile.saveToJson(getFilesDir().toString(), PROFILE_FILE_NAME);
                        //TODO: Create MainScreen
                        //Intent toMainScreen = new Intent(this, MainScreen.class);
                        //startActivity(toMainScreen);
                    }
                }
        );

        //Initialize local variables gson (JSON file reader), toProfileCreator (takes user to the ProfileCreator activity), and profileJsonStr (String of the user's profile, saved as a JSON file):
<<<<<<< Updated upstream
        preferenceSurvey = new Survey(5);
        profile = new Profile();
=======
        datingpreferenceSurvey = new Survey(4);
        sexpreferenceSurvey = new Survey(8);
>>>>>>> Stashed changes
        Gson gson = new Gson();
        Intent toProfileCreator = new Intent(this, ProfileCreator.class);
        profileCreatorResultLauncher.launch(toProfileCreator);

        //deprecated

        //String profileJsonStr = checkForFile(PROFILE_FILE_NAME);

    }

    /**
<<<<<<< Updated upstream
=======
     * Automatically called when an activity finishes that was started using StartActivityForResult().
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //Request Code 123 is for the ProfileCreator Activity.
        //This if statement is called when ProfileCreator finishes its execution.
        if (requestCode == 123 && resultCode == Activity.RESULT_OK) {
            profile.saveToJson(getFilesDir().toString(), PROFILE_FILE_NAME);
            checkForDatingPreferenceSurvey();
        }
        //Request Code 321 is for the SurveyLauncher Activity.
        //This if statement is called when SurveyLauncher finishes its execution.
        else if (requestCode == 321 && resultCode == Activity.RESULT_OK) {
            datingpreferenceSurvey.saveToJson(getFilesDir().toString(), DATING_PREFERENCE_SURVEY_FILE_NAME);
            profile.saveToJson(getFilesDir().toString(), PROFILE_FILE_NAME);
            checkForSexPreferenceSurvey();
        }
    }
    /**
>>>>>>> Stashed changes
     * Getter for field profile
     * @return The Profile object, profile, generated by this activity
     */
    public static Profile getProfile() {
        return profile;
    }

    /**
     * Checks for a file and returns a string of its contents.
     * Meant to be used on JSON files.
     * @param fileName The name of the file being searched for
     * @return the file's contents, in String format.
     */
    private String checkForFile(String fileName) {
        try {
            File file = new File(getFilesDir(), fileName);
            //If the file exists, return a string containing the file's contents.
            if (file.exists()) {
                FileReader reader = new FileReader(file);
                String jsonStr = "";
                int charIndex;
                while((charIndex = reader.read()) != -1)
                    jsonStr += (char) charIndex;
                reader.close();
                return jsonStr;
            }
            else //Return null if the file does not exist.
                return null;
        }
        catch (IOException e) {
            return null;
        }
    }

    /**
     * Checks to see if the preferenceSurvey JSON file (of the specified name) exists.
     * Launches the SurveyLauncher Activity if it does not exist.
     * Builds a Survey object out of the JSON file if it does exist, then launches Activity MainScreen.
     */
    private void checkForDatingPreferenceSurvey() {
        Gson gson = new Gson();
        String preferenceJsonStr = checkForFile(DATING_PREFERENCE_SURVEY_FILE_NAME);

        //If the preference survey file does not exist, have the user fill out the preference survey:
        if (preferenceJsonStr == null) {
            datingpreferenceSurvey = new Survey(DATING_PREFERENCE_QUESTION_COUNT);
            Intent toSurveyLauncher = new Intent(this, SurveyLauncher.class);
            startActivityForResult(toSurveyLauncher, 321);
        }
        //If the preference survey file exists, initialize preferenceSurvey from the JSON and start the MainScreen activity
        else {
            datingpreferenceSurvey = gson.fromJson(preferenceJsonStr, Survey.class);


        }
    }

    /**
     * Checks to see if the preferenceSurvey JSON file (of the specified name) exists.
     * Launches the SurveyLauncher Activity if it does not exist.
     * Builds a Survey object out of the JSON file if it does exist, then launches Activity MainScreen.
     */
    private void checkForSexPreferenceSurvey() {
        Gson gson = new Gson();
        String preferenceJsonStr = checkForFile(SEX_PREFERENCE_SURVEY_FILE_NAME);

        //If the preference survey file does not exist, have the user fill out the preference survey:
        if (preferenceJsonStr == null) {
            sexpreferenceSurvey = new Survey(SEX_PREFERENCE_QUESTION_COUNT);
            Intent toSurveyLauncher = new Intent(this, SurveyLauncher.class);
            surveyResultLauncher.launch(toSurveyLauncher);
        }
        //If the preference survey file exists, initialize preferenceSurvey from the JSON and start the MainScreen activity
        else {
<<<<<<< Updated upstream
            Log.d(TAG,"preference not null");
            preferenceSurvey = gson.fromJson(preferenceJsonStr, Survey.class);
            //Intent toMainScreen = new Intent(this, MainScreen.class);
            //startActivity(toMainScreen);
=======
            datingpreferenceSurvey = gson.fromJson(preferenceJsonStr, Survey.class);


>>>>>>> Stashed changes
        }
    }

    /**
     * Getter for field preferenceSurvey
     * @return The datingpreferenceSurvey object of type Survey generated by this activity
     */
    public static Survey getDatingPreferenceSurvey() {
        return datingpreferenceSurvey;
    }

    /**
     * Getter for field preferenceSurvey
     * @return The sexpreferenceSurvey object of type Survey generated by this activity
     */
    public static Survey getSexPreferenceSurvey() {
        return sexpreferenceSurvey;
    }
}
