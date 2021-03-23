package com.example.covidselfreport.ui.calendar;

import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import com.example.covidselfreport.*;
import com.example.profileresources.Survey;


/**
 * Displays the Intake Survey results from the last 14 days.
 * Each result is displayed in its own little module, consisting of two TextView objects:
 * One TextView displays the title (the date and how many days ago).
 * The other TextView displays the intake results from that day.
 */
public class CalendarFragment extends Fragment {

    private Survey[] intakeSurveys;
    private TextView[] titles;
    private TextView[] infoBoxes;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_calendar, container, false);
    }


    /**
     * Initializes all 15 elements of the titles and infoBoxes TextView arrays to the appropriate
     * TextView declared in the fragment_calendar.xml file.
     * Initializes the day box titles by adding the dates to each title section.
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        intakeSurveys = MainScreen.getIntakeSurveys();
        titles = new TextView[MainScreen.INTAKE_SURVEY_COUNT];
        infoBoxes = new TextView[MainScreen.INTAKE_SURVEY_COUNT];
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat df = new SimpleDateFormat("MM/dd", Locale.getDefault());

        //Initialize all of the title TextViews:
        titles[0] = view.findViewById(R.id.calendar_day0_title_textview);
        titles[1] = view.findViewById(R.id.calendar_day1_title_textview);
        titles[2] = view.findViewById(R.id.calendar_day2_title_textview);
        titles[3] = view.findViewById(R.id.calendar_day3_title_textview);
        titles[4] = view.findViewById(R.id.calendar_day4_title_textview);
        titles[5] = view.findViewById(R.id.calendar_day5_title_textview);
        titles[6] = view.findViewById(R.id.calendar_day6_title_textview);
        titles[7] = view.findViewById(R.id.calendar_day7_title_textview);
        titles[8] = view.findViewById(R.id.calendar_day8_title_textview);
        titles[9] = view.findViewById(R.id.calendar_day9_title_textview);
        titles[10] = view.findViewById(R.id.calendar_day10_title_textview);
        titles[11] = view.findViewById(R.id.calendar_day11_title_textview);
        titles[12] = view.findViewById(R.id.calendar_day12_title_textview);
        titles[13] = view.findViewById(R.id.calendar_day13_title_textview);
        titles[14] = view.findViewById(R.id.calendar_day14_title_textview);

        //Initialize all of the info TextViews
        infoBoxes[0] = view.findViewById(R.id.calendar_day0_info_textview);
        infoBoxes[1] = view.findViewById(R.id.calendar_day1_info_textview);
        infoBoxes[2] = view.findViewById(R.id.calendar_day2_info_textview);
        infoBoxes[3] = view.findViewById(R.id.calendar_day3_info_textview);
        infoBoxes[4] = view.findViewById(R.id.calendar_day4_info_textview);
        infoBoxes[5] = view.findViewById(R.id.calendar_day5_info_textview);
        infoBoxes[6] = view.findViewById(R.id.calendar_day6_info_textview);
        infoBoxes[7] = view.findViewById(R.id.calendar_day7_info_textview);
        infoBoxes[8] = view.findViewById(R.id.calendar_day8_info_textview);
        infoBoxes[9] = view.findViewById(R.id.calendar_day9_info_textview);
        infoBoxes[10] = view.findViewById(R.id.calendar_day10_info_textview);
        infoBoxes[11] = view.findViewById(R.id.calendar_day11_info_textview);
        infoBoxes[12] = view.findViewById(R.id.calendar_day12_info_textview);
        infoBoxes[13] = view.findViewById(R.id.calendar_day13_info_textview);
        infoBoxes[14] = view.findViewById(R.id.calendar_day14_info_textview);

        for (int i = 0; i < intakeSurveys.length; i++) {
            //Add the appropriate date to each intake survey title:
            cal.add(Calendar.DATE, -1);
            String title = titles[i].getText().toString() + df.format(cal.getTime());
            titles[i].setText(title);
            String infoText = generateIntakeInfoString(i);
            infoBoxes[i].setText(Html.fromHtml(infoText));
        }
    }


    /**
     * Takes a number on a 5 point scale (0 through 4) for Intake Question 1 and returns a text explanation.
     * @param num The number being converted to a text explanation
     * @return The text explanation
     */
    private String numberToExplanation(int num) {
        if (num < 0 || num > 4)
            return null;

        String explanation = null;

        switch (num) {
            case 0: explanation = getString(R.string.intake_q1_rb0_radiobutton_text); break;
            case 1: explanation = getString(R.string.intake_q1_rb1_radiobutton_text); break;
            case 2: explanation = getString(R.string.intake_q1_rb2_radiobutton_text); break;
            case 3: explanation = getString(R.string.intake_q1_rb3_radiobutton_text); break;
            case 4: explanation = getString(R.string.intake_q1_rb4_radiobutton_text); break;
        }

        return explanation;
    }


    /**
     * When the CalendarFragment is resumed, it updates today's intake survey results box, if needed.
     * Checks to see if the intake survey was taken today (the file exists) while also not being
     * in the array of Surveys, intakeSurveys. If so, today's intake TextView gets updated.
     */
    @Override
    public void onResume() {
        super.onResume();

        File intakeTodayFile = new File(requireActivity().getFilesDir(), IntakeLauncher.getIntakeFileNameToday());
        File intakeYesterdayFile = new File(requireActivity().getFilesDir(), IntakeLauncher.getIntakeFileNameYesterday());

        if (intakeSurveys[0] == null && intakeTodayFile.exists()) {
            /*intakeSurveys[0] = */MainScreen.addNewIntakeToday();
            infoBoxes[0].setText(Html.fromHtml(generateIntakeInfoString(0)));
        }
        if (intakeSurveys[1] == null && intakeYesterdayFile.exists() ) {
            MainScreen.addNewIntakeYesterday();
            infoBoxes[1].setText(Html.fromHtml(generateIntakeInfoString(1)));
        }
    }


    /**
     * Generates the appropriate string to insert into the specified info box (intake survey results TextView object).
     * The String generated is in HTML format, so to print it out, it must be decoded by using Html.fromHtml().
     * @param i The specific intake Survey element in intakeSurveys which will have a String generated for it.
     * @return A String summary of the specified intakeSurveys element, showing all applicable responses.
     */
    public String generateIntakeInfoString(int i) {
        String infoText;

        if (i >= 0 && i < intakeSurveys.length && intakeSurveys[i] != null) {
            //Put the health information (Question 1) and whether they went outside of the house or not (Q2) into infoText:
            infoText = "<b>Health:</b> " + numberToExplanation(Integer.parseInt(intakeSurveys[i].getResponse(0)));
            infoText += "<br/><b>Went outside of home?</b> " + intakeSurveys[i].getResponse(1);

            //If the response to Q2 is yes, also add to infoText where they went and who they saw:
            if (intakeSurveys[i].getResponse(1).equals("Yes")) {
                String textResponse = intakeSurveys[i].getTextboxResponse(1);
                String whereStr = "Where:", whoStr = "With whom:";

                //Find the indices in the text response where the "where" answer begins and where the "who" answer begins:
                int whereIndex = textResponse.indexOf(whereStr);
                int whoIndex = textResponse.indexOf(whoStr);

                //Extract where the user went and who they hung out with from the Q2 textbox response:
                String where = textResponse.substring(whereIndex + whereStr.length(), whoIndex).trim();
                String who = textResponse.substring(whoIndex + whoStr.length()).trim();

                //If the final character in the where or who Strings is a comma, remove it:
                if (where.endsWith(","))
                    where = where.substring(0, where.length() - 1);
                if (who.endsWith(","))
                    who = who.substring(0, who.length() - 1);

                infoText += "<br/><b>Where:</b> " + where + "<br/><b>People visited:</b> " + who;
            }
        }
        //If the survey was not taken on the specified day...
        else {
            infoText = "No intake survey was taken on this day.";
        }

        return infoText;
    }
}