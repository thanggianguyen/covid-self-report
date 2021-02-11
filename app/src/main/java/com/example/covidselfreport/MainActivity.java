package com.example.covidselfreport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.example.profileresources.*;
import com.google.gson.*;


/**
 * Entry point for the app. Determines whether a profile or survey should be filled out.
 * Sends the user to the appropriate screen (profile creator, survey launcher, or main app screen).
 * Sends the user to the profile creator screen if no profile has been created
     * After this, the user is sent to the preference survey (survey launcher) screen.
     * After the preference survey, the user is sent to the MainScreen.
 * Sends the user to the preferences survey (survey launcher) screen if no preference survey was filled out.
 * Sents the user to the MainScreen if both a profile exists and a preference survey exists.
 */
public class MainActivity extends AppCompatActivity {

    /** Number of questions in the preference survey */
    public static final int PREFERENCE_QUESTION_COUNT = 4;
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
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        updateDirectory();

        Gson gson = new Gson();
        Intent toProfileCreator = new Intent(this, ProfileCreator.class);
        String profileJsonStr = checkForFile(PROFILE_FILE_NAME);

        //If there is not profile.json file, instantiate profile as a blank Profile object and
        //launch the ProfileCreator Activity.
        if (profileJsonStr == null) {
            profile = new Profile();
            //Request Code 123 is for the ProfileCreator Activity.
            startActivityForResult(toProfileCreator, 123);
        }
        //If the profile.json file exists, build it into a Profile object, then call
        //the checkForPreferenceSurvey() method.
        else {
            profile = gson.fromJson(profileJsonStr, Profile.class);
            checkForPreferenceSurvey();
        }
    }

    /**
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
            checkForPreferenceSurvey();
        }
        //Request Code 321 is for the SurveyLauncher Activity.
        //This if statement is called when SurveyLauncher finishes its execution.
        else if (requestCode == 321 && resultCode == Activity.RESULT_OK) {
            preferenceSurvey.saveToJson(getFilesDir().toString(), PREFERENCE_SURVEY_FILE_NAME);
            profile.saveToJson(getFilesDir().toString(), PROFILE_FILE_NAME);
            Intent toMainScreen = new Intent(this, MainScreen.class);
            startActivity(toMainScreen);
        }
    }


    //FOR DELETING PROFILE AND PREFERENCE SURVEY FILES:
    /*@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        File preference = new File(getFilesDir(), PREFERENCE_SURVEY_FILE_NAME);
        preference.delete();

        File profileFile = new File(getFilesDir(), PROFILE_FILE_NAME);
        profileFile.delete();

//        String intakeJsonFileName = IntakeLauncher.getIntakeFileNameToday();
//        File intakeToday = new File(getFilesDir(), intakeJsonFileName);
//        if (intakeToday.delete())
//            ((TextView)findViewById(R.id.main_textview)).setText(intakeToday.getName() + " was deleted.");
//        else
//            ((TextView)findViewById(R.id.main_textview)).setText("No files to delete.");
    }*/


    //FOR DISPLAYING JSON FILE CONTENTS ONLY
    /*@Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get current date and name the file "intake_currentDate.json":
        String intakeJsonFileName = IntakeLauncher.getIntakeFileNameToday();

        Gson gson = new Gson();
        String intakeTodayStr = checkForFile(intakeJsonFileName);
        ((TextView)findViewById(R.id.main_textview)).setText(intakeJsonFileName + ":\n" + intakeTodayStr);
    }*/

    /*protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        File f = new File(getFilesDir(), "intake_2021_01_01");
        File f1 = new File(getFilesDir(), "intake_2020_12_29");
        File f2 = new File(getFilesDir(), "intake_2021_01_22");
        File f3 = new File(getFilesDir(), "intake_2021_01_14");
        File f4 = new File(getFilesDir(), "intake_2021_01_13");
        File f5 = new File(getFilesDir(), "intake_2021_01_15");
        try {
            f.createNewFile();
            f1.createNewFile();
            f2.createNewFile();
            f3.createNewFile();
            f4.createNewFile();
            f5.createNewFile();
        } catch(IOException e) {}
    }*/


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
    private void checkForPreferenceSurvey() {
        Gson gson = new Gson();
        String preferenceJsonStr = checkForFile(PREFERENCE_SURVEY_FILE_NAME);

        //If the preference survey file does not exist, have the user fill out the preference survey:
        if (preferenceJsonStr == null) {
            preferenceSurvey = new Survey(PREFERENCE_QUESTION_COUNT);
            Intent toSurveyLauncher = new Intent(this, SurveyLauncher.class);
            startActivityForResult(toSurveyLauncher, 321);
        }
        //If the preference survey file exists, initialize preferenceSurvey from the JSON and start the MainScreen activity
        else {
            preferenceSurvey = gson.fromJson(preferenceJsonStr, Survey.class);
            Intent toMainScreen = new Intent(this, MainScreen.class);
            startActivity(toMainScreen);
        }
    }


    /**
     * Deletes all intake survey files that are older than 14 days.
     */
    private void updateDirectory() {
        File dir = new File(getFilesDir().toString());
        File[] filesInDir = dir.listFiles();
        String filePrefix = "intake_";
        SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd", Locale.getDefault());
        Date today = Calendar.getInstance().getTime();

        if (filesInDir != null) {
            //Iterate through each file in the main application directory:
            for (File file : filesInDir) {
                //If the file is an intake survey file:
                if (file.getName().startsWith(filePrefix)) {
                    //Obtain the date portion of the intake survey filename:
                    String dateStr = file.getName().substring(filePrefix.length()).split("\\.")[0];
                    Date date;

                    //Attempt to parse the date string, assign it to a Date object, date:
                    try {
                        date = df.parse(dateStr);
                    }
                    catch (ParseException e) {
                        continue;
                    }

                    //If the intake date was longer than 14 days ago, delete the file:
                    if (TimeUnit.MILLISECONDS.toDays(today.getTime() - date.getTime()) > 14) {
                        file.delete();
                    }
                }
            }
        }
    }


    public static Profile getProfile() {
        return profile;
    }


}