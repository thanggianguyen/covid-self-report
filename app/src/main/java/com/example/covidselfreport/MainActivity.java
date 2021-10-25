package com.example.covidselfreport;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.example.datingconsent.ui.SurveyLauncher;
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

        //Delete all intake survey files that are older than 14 days:
        updateDirectory();

        //Initialize local variables gson (JSON file reader), toProfileCreator (takes user to the ProfileCreator activity), and profileJsonStr (String of the user's profile, saved as a JSON file):
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

        //If there are no timed notifications set, start the timed daily notifications:
        if (PendingIntent.getBroadcast(MainActivity.this, 0, new Intent(MainActivity.this, AlarmBroadcastReceiver.class), PendingIntent.FLAG_NO_CREATE) == null)
            startAlarmBroadcastReceiver(MainActivity.this, profile.getNotificationHour(), profile.getNotificationMinute()); //Start the daily notifications to fill out intake survey
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
//        String intakeJsonFileNameYesterday = IntakeLauncher.getIntakeFileNameYesterday();
//        File intakeYesterday = new File(getFilesDir(), intakeJsonFileNameYesterday);
//        if (intakeToday.delete())
//            ((TextView)findViewById(R.id.main_textview)).setText(intakeToday.getName() + " was deleted.");
//        else
//            ((TextView)findViewById(R.id.main_textview)).setText("No files to delete.");
//        if (intakeYesterday.delete())
//            ((TextView)findViewById(R.id.main_textview)).setText(intakeYesterday.getName() + " was deleted.");
//        else
//            ((TextView)findViewById(R.id.main_textview)).setText("No files to delete.");
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


    /**
     * Getter for field profile
     * @return The Profile object, profile, generated by this activity
     */
    public static Profile getProfile() {
        return profile;
    }


    /**
     * Getter for field preferenceSurvey
     * @return The preferenceSurvey object of type Survey generated by this activity
     */
    public static Survey getPreferenceSurvey() {
        return preferenceSurvey;
    }


    /**
     * Facilitates the delivery of daily notifications that remind the user to fill out the daily intake survey.
     * Starts the daily notification alarm (class AlarmBroadcastReceiver in same package, extending BroadcastReceiver)
     * The alarm will go off at the specified time, and it will be received by the AlarmBroadcastReceiver object.
     * The AlarmBroadcastReceiver object generates and sends out a notification reminding the user to fill out the daily intake survey.
     * @param context The context of the activity
     * @param hour The hour of day that the notification will be set to be sent out at
     * @param minute The minute of the hour that the notification will be set to be sent out at
     */
    public static void startAlarmBroadcastReceiver(Context context, int hour, int minute) {
        //An intent to go to the AlarmBroadcastReceiver class:
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        //A pending intent, which activates when the alarm goes off:
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        //Cancel any possible other alarms from this app (using pendingIntent) that exist in the system.
        alarmManager.cancel(pendingIntent);

        //Generate a Calendar object of the current date and the specified time, by parameters hour and minute. This is when the alarm will go off.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        //Set the repeating alarm (daily, at the specified time):
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }


    /**
     * Cancels any AlarmBroadcastReceiver that may be activated for this application.
     * @param context The context of the activity
     */
    public static void cancelAlarmBroadcastReceiver(Context context) {
        //Use the same intent (to go to the AlarmBroadcastReceiver class) as in the startAlarmBroadcastReceiver() method:
        Intent intent = new Intent(context, AlarmBroadcastReceiver.class);
        //Use the same PendingIntent as in the startAlarmBroadcastReceiver() method:
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        AlarmManager alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        //Cancel the alarm that is already set for this application:
        alarmManager.cancel(pendingIntent);
        pendingIntent.cancel();
    }


}