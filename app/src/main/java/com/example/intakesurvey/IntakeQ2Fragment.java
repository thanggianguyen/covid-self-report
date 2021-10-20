package com.example.intakesurvey;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.example.covidselfreport.R;


public class IntakeQ2Fragment extends Fragment implements View.OnClickListener {

    EditText whereTextBox, whoTextBox;
    TextView whereTextBoxInstructions, intakeQ2AText, whoTextBoxInstructions;
    RadioGroup intakeQ2Responses, intakeQ2AResponses;

    public IntakeQ2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_intake_q2, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        super.onViewCreated(view, savedInstanceState);

        //Initializing fragment widgets:
        whereTextBox = view.findViewById(R.id.intake_q2_where_edittext);
        whoTextBox = view.findViewById(R.id.intake_q2a_who_edittext);
        whereTextBoxInstructions = view.findViewById(R.id.intake_q2_whereinstructions_textview);
        intakeQ2AText = view.findViewById(R.id.intake_q2a_text_textview);
        whoTextBoxInstructions = view.findViewById(R.id.intake_q2a_whoinstructions_textview);
        intakeQ2Responses = view.findViewById(R.id.intake_q2_responses_radiogroup);
        intakeQ2AResponses = view.findViewById(R.id.intake_q2a_responses_radiogroup);


        //Setting the OnClickListeners of each radiobutton to this fragment (its onClick() method):
        view.findViewById(R.id.intake_q2_rb0_radiobutton).setOnClickListener(this);
        view.findViewById(R.id.intake_q2_rb1_radiobutton).setOnClickListener(this);
        view.findViewById(R.id.intake_q2a_rb0_radiobutton).setOnClickListener(this);
        view.findViewById(R.id.intake_q2a_rb1_radiobutton).setOnClickListener(this);

    }

    //onClick event handler for the entire fragment:
    public void onClick(View v)
    {
        //Determine the View generating the click event:
        switch(v.getId())
        {
            //Handler for inq2_RB0 ("Yes" button):
            case R.id.intake_q2_rb0_radiobutton:
                whereTextBox.setVisibility(View.VISIBLE);
                whereTextBoxInstructions.setVisibility(View.VISIBLE);
                intakeQ2AText.setVisibility(View.VISIBLE);
                intakeQ2AResponses.setVisibility(View.VISIBLE);
                intakeQ2Responses.check(R.id.intake_q2_rb0_radiobutton);
                break;

            //Handler for inq2_rb1 ("No" button):
            case R.id.intake_q2_rb1_radiobutton:
                whereTextBox.setVisibility(View.INVISIBLE);
                whereTextBoxInstructions.setVisibility(View.INVISIBLE);
                intakeQ2AText.setVisibility(View.INVISIBLE);
                intakeQ2AResponses.setVisibility(View.INVISIBLE);
                intakeQ2AResponses.clearCheck();
                whoTextBox.setVisibility(View.INVISIBLE);
                whoTextBoxInstructions.setVisibility(View.INVISIBLE);
                intakeQ2Responses.check(R.id.intake_q2_rb1_radiobutton);
                break;

            //Handler for inq2A_rb0 ("Yes" button):
            case R.id.intake_q2a_rb0_radiobutton:
                whoTextBox.setVisibility(View.VISIBLE);
                whoTextBoxInstructions.setVisibility(View.VISIBLE);
                break;

            //Handler for inq2A_rb1 ("No" button):
            case R.id.intake_q2a_rb1_radiobutton:
                whoTextBox.setVisibility(View.INVISIBLE);
                whoTextBoxInstructions.setVisibility(View.INVISIBLE);
                break;
            default :
                break;
        }
    }


    //Event handler for inq2_rb0 ("Yes" button):
    public void intakeQ2RB0Handler(View view)
    {
        whereTextBox.setVisibility(View.VISIBLE);
        whereTextBoxInstructions.setVisibility(View.VISIBLE);
        intakeQ2AText.setVisibility(View.VISIBLE);
        intakeQ2AResponses.setVisibility(View.VISIBLE);

        intakeQ2Responses.check(R.id.intake_q2_rb0_radiobutton);
    }


    //Event handler for inq2_rb1 ("No" button):
    public void intakeQ2RB1Handler(View view)
    {
        whereTextBox.setVisibility(View.INVISIBLE);
        whereTextBoxInstructions.setVisibility(View.INVISIBLE);
        intakeQ2AText.setVisibility(View.INVISIBLE);
        intakeQ2AResponses.setVisibility(View.INVISIBLE);

        intakeQ2AResponses.clearCheck();
        whoTextBox.setVisibility(View.INVISIBLE);
        whoTextBoxInstructions.setVisibility(View.INVISIBLE);

        intakeQ2Responses.check(R.id.intake_q2_rb1_radiobutton);
    }


    //Event handler for inq2A_rb0 ("Yes" button):
    public void intakeQ2ARB0Handler(View view)
    {
        whoTextBox.setVisibility(View.VISIBLE);
        whoTextBoxInstructions.setVisibility(View.VISIBLE);
    }


    //Event handler for inq2A_rb1 ("No" button):
    public void intakeQ2ARB1Handler(View view)
    {
        whoTextBox.setVisibility(View.INVISIBLE);
        whoTextBoxInstructions.setVisibility(View.INVISIBLE);
    }
}