package com.example.covidselfreport;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.example.profileresources.Survey;


public class SurveyModifier extends AppCompatActivity {

    /** The preferences survey, taken from MainActivity */
    Survey preferences = MainActivity.preferenceSurvey;

    RadioGroup q1Responses;

    RadioGroup q2Responses;

    RadioGroup q3Responses;

    CheckBox[] q4Responses;

    EditText q1TextResponse;

    EditText q2TextResponse;

    Button backButton;
    Button updateButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey_modifier);

        //Set the selected RadioButton of RadioGroup q1Responses:
        q1Responses = findViewById(R.id.preferencemodifier_q1_responses_radiogroup);
        int selectedIndexQ1;
        try { selectedIndexQ1 = Integer.parseInt(preferences.getResponse(0)); }
        catch (NumberFormatException e) { selectedIndexQ1 = 0; }
        switch (selectedIndexQ1) {
            case 0: q1Responses.check(R.id.preferencemodifier_q1_rb0_radiobutton); break;
            case 1: q1Responses.check(R.id.preferencemodifier_q1_rb1_radiobutton); break;
            case 2: q1Responses.check(R.id.preferencemodifier_q1_rb2_radiobutton); break;
            case 3: q1Responses.check(R.id.preferencemodifier_q1_rb3_radiobutton); break;
            case 4: q1Responses.check(R.id.preferencemodifier_q1_rb4_radiobutton); break;
        }

        //Set the text response for Q1:
        q1TextResponse = findViewById(R.id.preferencemodifier_q1_optionalresponse_edittext);
        q1TextResponse.setText(preferences.getTextboxResponse(0));

        //Set the selected RadioButton of RadioGroup q2Responses:
        q2Responses = findViewById(R.id.preferencemodifier_q2_responses_radiogroup);
        int selectedIndexQ2;
        try { selectedIndexQ2 = Integer.parseInt(preferences.getResponse(1)); }
        catch (NumberFormatException e) { selectedIndexQ2 = 0; }
        switch (selectedIndexQ2) {
            case 0: q2Responses.check(R.id.preferencemodifier_q2_rb0_radiobutton); break;
            case 1: q2Responses.check(R.id.preferencemodifier_q2_rb1_radiobutton); break;
            case 2: q2Responses.check(R.id.preferencemodifier_q2_rb2_radiobutton); break;
            case 3: q2Responses.check(R.id.preferencemodifier_q2_rb3_radiobutton); break;
            case 4: q2Responses.check(R.id.preferencemodifier_q2_rb4_radiobutton); break;
        }

        //Set the text response for Q2:
        q2TextResponse = findViewById(R.id.preferencemodifier_q2_optionalresponse_edittext);
        q2TextResponse.setText(preferences.getTextboxResponse(1));

        //Set the selected RadioButton of RadioGroup q3Responses:
        q3Responses = findViewById(R.id.preferencemodifier_q3_responses_radiogroup);
        int selectedIndexQ3;
        try { selectedIndexQ3 = Integer.parseInt(preferences.getResponse(2)); }
        catch (NumberFormatException e) { selectedIndexQ3 = 0; }
        switch(selectedIndexQ3) {
            case 0: q3Responses.check(R.id.preferencemodifier_q3_rb0_radiobutton); break;
            case 1: q3Responses.check(R.id.preferencemodifier_q3_rb1_radiobutton); break;
            case 2: q3Responses.check(R.id.preferencemodifier_q3_rb2_radiobutton); break;
            case 3: q3Responses.check(R.id.preferencemodifier_q3_rb3_radiobutton); break;
            case 4: q3Responses.check(R.id.preferencemodifier_q3_rb4_radiobutton); break;
        }

        //Initialize q4Responses by adding each CheckBox to the array:
        int[] checkIDs = {R.id.preferencemodifier_q4_cb0_checkbox, R.id.preferencemodifier_q4_cb1_checkbox,
                R.id.preferencemodifier_q4_cb2_checkbox, R.id.preferencemodifier_q4_cb3_checkbox,
                R.id.preferencemodifier_q4_cb4_checkbox, R.id.preferencemodifier_q4_cb5_checkbox,
                R.id.preferencemodifier_q4_cb6_checkbox, R.id.preferencemodifier_q4_cb7_checkbox,
                R.id.preferencemodifier_q4_cb8_checkbox};
        q4Responses = new CheckBox[checkIDs.length];
        for (int i = 0; i < checkIDs.length; i++)
            q4Responses[i] = findViewById(checkIDs[i]);
        String[] checkBoxText = new String[q4Responses.length];
        for (int i = 0; i < checkBoxText.length; i++)
            checkBoxText[i] = q4Responses[i].getText().toString();

        //Set the selected checkboxes for Q4:
        for (int i = 0; i < checkBoxText.length; i++) {
            if (preferences.getResponse(3).contains(checkBoxText[i]))
                q4Responses[i].setChecked(true);
        }

        //Set the onClick action for the back button:
        backButton = findViewById(R.id.preferencemodifier_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButtonHandler(v);
            }
        });

        //Set the onClick action for the update button:
        updateButton = findViewById(R.id.preferencemodifier_update_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateButtonHandler(v);
            }
        });
    }


    private void updateButtonHandler(View view) {
        //Set the response for q1 (index 0) to the selected radiobutton index of q1Responses:
        preferences.setResponse(0, Integer.toString(q1Responses.indexOfChild(findViewById(
                q1Responses.getCheckedRadioButtonId()))));
        preferences.setTextboxResponse(0, q1TextResponse.getText().toString());

        //Set the response for q2 (index 1) to the selected radiobutton index of q2Responses:
        preferences.setResponse(1, Integer.toString(q2Responses.indexOfChild(findViewById(
                q2Responses.getCheckedRadioButtonId()))));
        preferences.setTextboxResponse(1, q2TextResponse.getText().toString());

        //Set the response for q3 (index 2) to the selected radiobutton index of q3Responses:
        preferences.setResponse(2, Integer.toString(q3Responses.indexOfChild(findViewById(
                q3Responses.getCheckedRadioButtonId()))));

        //Set the response for q4 (index 3) to the selected CheckBoxs' text fields:
        String q4Response = "";
        for (CheckBox current : q4Responses) {
            if (current.isChecked())
                q4Response += current.getText().toString() + ", ";
        }
        preferences.setResponse(3, q4Response.substring(0, q4Response.length() - 2));

        //Save responses and quit the activity
        preferences.saveToJson(getFilesDir().toString(), MainActivity.PREFERENCE_SURVEY_FILE_NAME);
        finish();
    }


    private void backButtonHandler(View view) {
        finish();
    }
}