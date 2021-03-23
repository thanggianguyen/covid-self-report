package com.example.covidselfreport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import com.example.profileresources.Survey;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;


/**
 * The activity for the main screen of the app, which is a bottom navigation activity.
 * Within this activity, the user can navigate between the "Calendar", "Intake", and "Profile" tabs.
 * This activity also locates all intake surveys taken within the last 14 days and initializes them.
 */
public class MainScreen extends AppCompatActivity {

    /** The number of intake surveys stored */
    public static final int INTAKE_SURVEY_COUNT = 15;
    /** Stores intake surveys from the last 14 days, and today. */
    private static Survey[] intakeSurveys;

    private static MainScreen mainObj;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_screen);
        BottomNavigationView navView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_calendar, R.id.navigation_intake, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);

        if (savedInstanceState == null)
            navView.setSelectedItemId(R.id.navigation_intake);

        //Create an array containing the intake surveys taken within the last 14 days:
        intakeSurveys = generateIntakeArray();
        mainObj = this;
    }


    /**
     * Getter method for field intakeSurveys
     * @return the intakeSurveys field, of type Survey[]
     */
    public static Survey[] getIntakeSurveys() {
        return intakeSurveys;
    }


    /**
     * Scans the file directory for all intake survey JSON files taken within the last 14 days.
     * If it locates one, it adds it to the appropriate index of the intakes array.
     * For intakes array: 0 is the survey taken today, 1 is the survey taken 1 day ago, 2 is the
       survey taken 2 days ago, etc.
     * @return An array of size 15 containing the intake Survey objects (from today up to 14 days ago)
     */
    private Survey[] generateIntakeArray() {
        Survey[] intakes = new Survey[INTAKE_SURVEY_COUNT];
        File dir = new File(getFilesDir().toString());
        File[] filesInDir = dir.listFiles();
        String filePrefix = "intake_";
        SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd", Locale.getDefault());
        Date today = Calendar.getInstance().getTime();
        Gson gson = new Gson();

        for (File file : filesInDir) { //Cycle through each file in the app's directory.
            if (file.getName().startsWith(filePrefix)) {
                //Get the date from the intake file's name:
                String dateStr = file.getName().substring(filePrefix.length()).split("\\.")[0];
                Date date;

                try {
                    date = df.parse(dateStr); //Turn the date string into a Date object.
                }
                catch (ParseException e) {
                    continue;
                }

                //How many days ago the date is:
                int daysAgo = (int) TimeUnit.MILLISECONDS.toDays(today.getTime() - date.getTime());

                if (daysAgo > 14)
                    continue;

                try { //Read from the JSON file:
                    FileReader reader = new FileReader(file);
                    String jsonStr = "";
                    int charIndex;

                    while ((charIndex = reader.read()) != -1)
                        jsonStr += (char) charIndex;

                    //Build a Survey object and assign it to the appropriate index of intakes.
                    intakes[daysAgo] = gson.fromJson(jsonStr, Survey.class);
                    reader.close();
                }
                catch (FileNotFoundException e) {
                    continue;
                }
                catch (IOException e) {
                    continue;
                }
            }
        }

        return intakes;
    }


    /**
     * Adds the new intake survey file (that was presumably just filled out) to the calendar
     * section for day 0.
     * @return The survey if successfully added to the calendar, null if unsuccessfully added.
     */
    public static Survey addNewIntakeToday() {

        return addNewIntake(0);
        /*if (intakeTodayFile.exists()) {
            Gson gson = new Gson();
            try {
                FileReader reader = new FileReader(intakeTodayFile);
                String jsonStr = "";
                int charIndex;

                while ((charIndex = reader.read()) != -1)
                    jsonStr += (char) charIndex;

                intakeSurveys[0] = gson.fromJson(jsonStr, Survey.class);
                return intakeSurveys[0];
            }
            catch (FileNotFoundException e) { return null; }
            catch (IOException e) { return null; }
        }
        return null;*/

    }


    /**
     * Adds the new intake survey file (that was presumably just filled out) to the calendar
     * section for day 1.
     * @return The survey if successfully added to the calendar, null if unsuccessfully added.
     */
    public static Survey addNewIntakeYesterday() {
        return addNewIntake(1);
    }


    /**
     * Adds the new intake survey file (that was presumably just filled out) to the calendar
     * section for the specified day number (0-14, 0 being today, 14 being 14 days ago).
     * @return The survey if successfully added to the calendar, null if unsuccessfully added.
     */
    private static Survey addNewIntake(int index) {
        if (index < 0 || index > 14)
            throw new IllegalArgumentException("The index must be between 0 and 14 (within the last 14 days)");
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, (-1*index));
        Date date = cal.getTime();
        File intakeFile = new File(mainObj.getFilesDir(), IntakeLauncher.getIntakeFileNameByDate(date));

        if (intakeFile.exists()) {
            Gson gson = new Gson();
            try {
                FileReader reader = new FileReader(intakeFile);
                String jsonStr = "";
                int charIndex;

                while ((charIndex = reader.read()) != -1)
                    jsonStr += (char) charIndex;

                intakeSurveys[0] = gson.fromJson(jsonStr, Survey.class);
                return intakeSurveys[0];
            }
            catch (FileNotFoundException e) { return null; }
            catch (IOException e) { return null; }
        }
        return null;
    }

}