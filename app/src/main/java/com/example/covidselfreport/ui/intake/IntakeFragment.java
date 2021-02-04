package com.example.covidselfreport.ui.intake;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import java.io.File;

import com.example.covidselfreport.*;
import com.example.covidselfreport.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;


/**
 * The "Intake" tab for the MainScreen bottom navigation activity.
 * This fragment informs the user of whether they have taken the daily intake survey today or not.
 * If they have not, there is a button for the user to click that starts the IntakeLauncher activity,
 * where the user takes the daily intake survey.
 */
public class IntakeFragment extends Fragment {

    private IntakeViewModel intakeViewModel;
    /** The button the user can click to take them to the intake survey */
    Button intakeButton;
    /** The button the user can click to allow them to view their responses */
    Button responsesButton;
    /** The image displayed to inform the user whether they took today's intake or not */
    ImageView intakeImage;
    /** The title text for this fragment */
    TextView intakeTitleText;
    /** The text displayed to inform the user whether they took today's intake or not */
    TextView intakeInfoText;
    /** The name of today's intake survey file */
    String intakeJsonFileName;


    /**
     * Called once the Fragment is ready to be created.
     * Initializes intakeJsonFileName and returns the inflated View.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        intakeJsonFileName = IntakeLauncher.getIntakeFileNameToday();
        return inflater.inflate(R.layout.fragment_intake, container, false);
    }


    /**
     * Called once the fragment is created.
     * Initializes the intakeButton's onClick() method.
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        intakeButton = (Button)(view.findViewById(R.id.intake_take_survey_button));
        //Set the onClick method for intakebutton to take the user to the IntakeLauncher activity.
        intakeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toIntake = new Intent(getActivity(), IntakeLauncher.class);
                requireActivity().startActivity(toIntake);
            }
        });

        intakeImage = (ImageView)(view.findViewById(R.id.intake_today_image));
        intakeTitleText = (TextView)(view.findViewById(R.id.intake_today_title_textview));
        intakeInfoText = (TextView)(view.findViewById(R.id.intake_today_information_textview));

        responsesButton = (Button)(view.findViewById(R.id.intake_view_responses_button));
        responsesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomNavigationView navView = requireActivity().findViewById(R.id.nav_view);
                navView.setSelectedItemId(R.id.navigation_calendar);
            }
        });
    }


    /**
     * Called when the fragment is resumed.
     * Changes the visibility of intakeButton, based on whether the intake survey was taken today or not.
     */
    @Override
    public void onResume() {
        super.onResume();

        if (intakeTakenToday()) {
            intakeButton.setVisibility(View.INVISIBLE);
            responsesButton.setVisibility(View.VISIBLE);
            intakeImage.setImageResource(R.drawable.ic_intake_taken_check);
            intakeTitleText.setText("Survey completed today");
            intakeInfoText.setText("You already took the intake survey today.\nCome back tomorrow!");
        } else {
            intakeButton.setVisibility(View.VISIBLE);
            responsesButton.setVisibility(View.INVISIBLE);
            intakeImage.setImageResource(R.drawable.ic_intake_not_taken_info);
            intakeTitleText.setText("Survey ready to take");
            intakeInfoText.setText("Today's intake survey is available to take!\nTap the button to start.");
        }
    }


    /**
     * Tells whether the intake survey was taken today or not.
     * @return True if the intake was taken today, false if it was not taken today.
     */
    private boolean intakeTakenToday() {
        File intakeJsonFile = new File(requireActivity().getFilesDir(), intakeJsonFileName);
        return intakeJsonFile.exists();
    }

}