package com.example.datingconsent.surveyresources;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.example.datingconsent.R;
import com.example.datingconsent.ui.MainActivity;


public class SurveySexFragment extends Fragment {

    private final Survey sexpreferences = MainActivity.getSexPreferenceSurvey();
    private CheckBox kiss,birthControl, tongue,
                     vaginal, anal, oral;
    private CheckBox[] bc;
    private TextView bcNote, bcSubnote;
    private RadioGroup analGroup, oralGroup;
    private Button doneButton,backButton;

    public SurveySexFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment\
        View rootView = inflater.inflate(R.layout.fragment_survey_sex, container, false);

        return rootView;
    }
    

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        /**
         * Instantiate java parameters with UI parameters
         */
        kiss = view.findViewById(R.id.sex_KissingCheck_checkbox);
        tongue = view.findViewById(R.id.sex_TongueCheck_checkbox);
        birthControl = view.findViewById(R.id.sex_BirthControlCheck_checkbox);
        bcNote = view.findViewById(R.id.sex_BirthControlNote_text);
        bcSubnote = view.findViewById(R.id.sex_BirthControlSubnote_text);
        vaginal = view.findViewById(R.id.sex_VaginalCheck_checkbox);
        anal = view.findViewById(R.id.sex_AnalCheck_checkbox);
        analGroup = view.findViewById(R.id.sex_Anal_radioGroup);
        oral = view.findViewById(R.id.sex_OralCheck_checkbox);
        oralGroup = view.findViewById(R.id.sex_Oral_radioGroup);
        doneButton = view.findViewById(R.id.sex_Done_button);
        backButton = view.findViewById(R.id.sex_Back_button);

        //Additional information prompted if kissing Checkbox is checked
        kiss.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tongue.setVisibility(View.VISIBLE);
                } else {
                    tongue.setVisibility(View.GONE);
                }
            }
        });
        int[] checkIDs = {R.id.sex_BirthControl1_checkbox, R.id.sex_BirthControl2_checkbox,
                R.id.sex_BirthControl3_checkbox, R.id.sex_BirthControl4_checkbox,
                R.id.sex_BirthControl5_checkbox, R.id.sex_BirthControl6_checkbox,
                R.id.sex_BirthControl7_checkbox, R.id.sex_BirthControl8_checkbox,
                R.id.sex_BirthControl9_checkbox, R.id.sex_BirthControl10_checkbox,
                R.id.sex_BirthControl11_checkbox, R.id.sex_BirthControl12_checkbox};
        bc = new CheckBox[checkIDs.length];
        for (int i = 0; i < checkIDs.length; i++) {
            bc[i] = view.findViewById(checkIDs[i]);}
        //Additional information prompted if Birth Control Checkbox is checked
        birthControl.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    for (int i = 0; i < checkIDs.length; i++){
                        bc[i].setVisibility(View.VISIBLE);
                    }
                    bcNote.setVisibility(View.VISIBLE);
                    bcSubnote.setVisibility(View.VISIBLE);
                }
                else{
                    for (int i = 0; i < checkIDs.length; i++){
                        bc[i].setVisibility(View.GONE);
                    }
                    bcNote.setVisibility(View.GONE);
                    bcSubnote.setVisibility(View.GONE);
                }
            }
        });
        //Additional Radio Group will appear if Anal Checkbox is checked
        anal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    analGroup.setVisibility(View.VISIBLE);
                }
                else{
                    analGroup.setVisibility(View.GONE);
                }
            }
        });
        //Additional Radio Group will appear if Oral Checkbox is checked
        oral.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    oralGroup.setVisibility(View.VISIBLE);
                }
                else{
                    oralGroup.setVisibility(View.GONE);
                }
            }
        });
        //If the host activity is SurveyModifier, call initializeComponentsForModifier().
        if (requireActivity() instanceof com.example.datingconsent.ui.SurveyModifier) {
            initializeComponentsForModifier(view);
            doneButton.setText("Update");
        }
        //If the host activity is something else (SurveyLauncher), make the back button invisible
        // (user has no choice but to fill out the survey)
        else {
            backButton.setVisibility(View.INVISIBLE);

            //Center the updateButton on the screen.
            ConstraintLayout layout = (ConstraintLayout)view.findViewById(R.id.sexsurvey_constraint_layout);
            ConstraintSet set = new ConstraintSet();
            set.clone(layout);
            set.connect(R.id.sex_Done_button, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 0);
            set.applyTo(layout);
        }
        //Set the onClick action for the update button:
        doneButton = view.findViewById(R.id.sex_Done_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!kiss.isChecked() && !birthControl.isChecked() &&
                        !vaginal.isChecked() && !anal.isChecked() && !oral.isChecked())
                {
                    AlertDialog.Builder warning = new AlertDialog.Builder(this);

                    warning.setCancelable(true);
                    warning.setTitle("WARNING!");
                    warning.setMessage("No checkboxes are checked. This will update the Sex Checkbox in the Dating Survey to Unchecked. Do you want to Continue?");

                    warning.setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    warning.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            requireActivity().finish();
                        }
                    });
                }
                    doneButtonHandler(v);
            }
        });

    }

    /**
     * Initializes the settings for each component to the user's previous survey responses.
     * Only called if the parent activity is SurveyModifier (only called if the user has already filled out a survey).
     * @param view
     */
    private void initializeComponentsForModifier(View view) {
        //Set the selected checkbox for Kissing:
        if (sexpreferences.getResponse(0).contains(kiss.getText().toString()))
            kiss.setChecked(true);
        //Set the selected checkbox for Tongue:
        if (sexpreferences.getResponse(1).contains(tongue.getText().toString()))
            tongue.setChecked(true);
        //Set the selected checkbox for Birth Control Initiate:
        if (sexpreferences.getResponse(2).contains(birthControl.getText().toString()))
            birthControl.setChecked(true);
        //Set the selected checkboxes for Birth Control:
        for (int i = 0; i < bc.length; i++) {
            if (sexpreferences.getResponse(3).contains(bc[i].getText().toString()))
                bc[i].setChecked(true);
        }
        //Set the selected checkbox for Vaginal:
        if (sexpreferences.getResponse(4).contains(vaginal.getText().toString()))
            vaginal.setChecked(true);
        //Set the selected checkbox for Anal:
        if (sexpreferences.getResponse(5).contains(anal.getText().toString()))
            anal.setChecked(true);
        //Set the selected RadioButton of RadioGroup Anal Responses:
        int selectedIndexAnal;
        try { selectedIndexAnal = Integer.parseInt(sexpreferences.getResponse(6)); }
        catch (NumberFormatException e) { selectedIndexAnal = 0; }
        switch (selectedIndexAnal) {
            case 0: analGroup.check(R.id.sex_AnalGiving_radiobtn); break;
            case 1: analGroup.check(R.id.sex_AnalReceiving_radiobtn); break;
            case 2: analGroup.check(R.id.sex_AnalBoth_radiobtn); break;
        }
        //Set the selected checkbox for Oral:
        if (sexpreferences.getResponse(7).contains(oral.getText().toString()))
            oral.setChecked(true);
        //Set the selected RadioButton of RadioGroup Oral Responses:
        int selectedIndexOral;
        try { selectedIndexOral = Integer.parseInt(sexpreferences.getResponse(8)); }
        catch (NumberFormatException e) { selectedIndexOral = 0; }
        switch (selectedIndexOral) {
            case 0: oralGroup.check(R.id.sex_OralGiving_radiobtn); break;
            case 1: oralGroup.check(R.id.sex_OralReceiving_radiobtn); break;
            case 2: oralGroup.check(R.id.sex_OralBoth_radiobtn); break;
        }
        //Set the onClick action for the back button:
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backButtonHandler(v);
            }
        });
    }
    private void doneButtonHandler(View view) {
        //Set the response for Kissing (index 0 of the preferenceSurvey questions arrays) to the selected CheckBox's text fields:
        sexpreferences.setQuestion(0, ((TextView)requireActivity().findViewById(R.id.sex_KissingCheck_checkbox)).getText().toString());
        String kissResponse = "";
            if (kiss.isChecked())
                kissResponse = kiss.getText().toString();
        sexpreferences.setResponse(0, kissResponse);

        //Set the response for Tongue (index 1 of the preferenceSurvey questions arrays) to the selected CheckBox's text fields:
        sexpreferences.setQuestion(1, ((TextView)requireActivity().findViewById(R.id.sex_TongueCheck_checkbox)).getText().toString());
        String tongueResponse = "";
        if (tongue.isChecked())
            tongueResponse = tongue.getText().toString();
        sexpreferences.setResponse(1, tongueResponse);

        //Set the response for Birth Control Initiate (index 2 of the preferenceSurvey questions arrays) to the selected CheckBox's text fields:
        sexpreferences.setQuestion(2, ((TextView)requireActivity().findViewById(R.id.sex_BirthControlCheck_checkbox)).getText().toString());
        String birthControlResponse = "";
        if (birthControl.isChecked())
            birthControlResponse = birthControl.getText().toString();
        sexpreferences.setResponse(2, birthControlResponse.substring(0, birthControlResponse.length()));

        //Set the response for Birth Control (index 3 of the preferenceSurvey questions arrays) to the selected CheckBoxes' text fields:
        sexpreferences.setQuestion(3, ((TextView)requireActivity().findViewById(R.id.dating_DateTitle_text)).getText().toString());
        String bcResponse = "";
        for (CheckBox current : bc) {
            if (current.isChecked())
                bcResponse += current.getText().toString() + ", ";
        }
        sexpreferences.setResponse(3, bcResponse.substring(0, bcResponse.length() - 2));

        //Set the response for Vaginal (index 4 of the preferenceSurvey questions arrays) to the selected CheckBox's text fields:
        sexpreferences.setQuestion(4, ((TextView)requireActivity().findViewById(R.id.sex_VaginalCheck_checkbox)).getText().toString());
        String vaginalResponse = "";
        if (vaginal.isChecked())
            vaginalResponse = vaginal.getText().toString();
        sexpreferences.setResponse(4, vaginalResponse.substring(0, vaginalResponse.length()));

        //Set the response for Anal (index 5 of the preferenceSurvey questions arrays) to the selected CheckBox's text fields:
        sexpreferences.setQuestion(5, ((TextView)requireActivity().findViewById(R.id.sex_AnalCheck_checkbox)).getText().toString());
        String analResponse = "";
        if (anal.isChecked())
            analResponse = anal.getText().toString();
        sexpreferences.setResponse(5, analResponse);

        //Set the response for Anal (index 6 of the preferenceSurvey questions arrays) to the selected radiobutton index of Physical Touch Response:
        sexpreferences.setQuestion(6, ((TextView)requireActivity().findViewById(R.id.sex_AnalCheck_checkbox)).getText().toString());
        sexpreferences.setResponse(6, Integer.toString(analGroup.indexOfChild(requireView().findViewById(
                analGroup.getCheckedRadioButtonId()))));

        //Set the response for Oral (index 7 of the preferenceSurvey questions arrays) to the selected CheckBox's text fields:
        sexpreferences.setQuestion(7, ((TextView)requireActivity().findViewById(R.id.sex_OralCheck_checkbox)).getText().toString());
        String oralResponse = "";
        if (oral.isChecked())
            oralResponse = oral.getText().toString();
        sexpreferences.setResponse(7, oralResponse);

        //Set the response for Oral (index 8 of the preferenceSurvey questions arrays) to the selected radiobutton index of Physical Touch Response:
        sexpreferences.setQuestion(8, ((TextView)requireActivity().findViewById(R.id.sex_OralCheck_checkbox)).getText().toString());
        sexpreferences.setResponse(8, Integer.toString(oralGroup.indexOfChild(requireView().findViewById(
                oralGroup.getCheckedRadioButtonId()))));

        //Save responses and quit the activity
        sexpreferences.saveToJson(requireActivity().getFilesDir().toString(), MainActivity.SEX_PREFERENCE_SURVEY_FILE_NAME);
        if (requireActivity() instanceof com.example.datingconsent.ui.SurveyLauncher) {
            Intent returnIntent = new Intent();
            requireActivity().setResult(Activity.RESULT_OK, returnIntent);
        }
        requireActivity().finish();
    }
    /**
     * Handles click events for the backButton.
     * Quits the parent activity.
     * @param view the back button
     */
    private void backButtonHandler(View view) {
        requireActivity().finish();
    }
}
