package com.example.datingconsent.ui.survey;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.datingconsent.R;
import com.example.datingconsent.surveyresources.Survey;
import com.example.datingconsent.ui.MainActivity;
import com.example.datingconsent.ui.SurveyModifier;


public class SurveyFragment extends Fragment {

    private Survey datingSurvey = MainActivity.getDatingPreferenceSurvey();
    private Survey sexSurvey = MainActivity.getSexPreferenceSurvey();
    private TextView SexTitle;
    private TextView[] titles;
    private TextView[] response;
    private Button EditButton;
    public ActivityResultLauncher<Intent> surveyModifierLauncher;

    public SurveyFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_survey, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        SexTitle = view.findViewById(R.id.survey_Sex);

        titles = new TextView[12];
        response = new TextView[14];

        //Initialize all the TextViews for Question and Responses in the respective array.
        titles[0] = view.findViewById(R.id.survey_Dating_PhyTou);
        titles[1] = view.findViewById(R.id.survey_Dating_Pay);
        titles[2] = view.findViewById(R.id.survey_Dating_Dates);
        titles[3] = view.findViewById(R.id.survey_Dating_Sex);

        titles[4] = view.findViewById(R.id.survey_Sex_Kiss);
        titles[5] = view.findViewById(R.id.survey_Sex_Tongue);
        titles[6] = view.findViewById(R.id.survey_Sex_BirthControl);
        titles[7] = view.findViewById(R.id.survey_Sex_BirthControl_itemsTitle);
        titles[8] = view.findViewById(R.id.survey_Sex_Vaginal);
        titles[9] = view.findViewById(R.id.survey_Sex_Anal);
        titles[10] = view.findViewById(R.id.survey_Sex_Oral);

        response[0] = view.findViewById(R.id.survey_Dating_PhyTou_response);
        response[1] = view.findViewById(R.id.survey_Dating_Pay_response);
        response[2] = view.findViewById(R.id.survey_Dating_Dates_response);
        response[3] = view.findViewById(R.id.survey_Dating_Sex_response);

        response[4] = view.findViewById(R.id.survey_Sex_Kiss_response);
        response[5] = view.findViewById(R.id.survey_Sex_Tongue_response);
        response[6] = view.findViewById(R.id.survey_Sex_BirthControl_response);
        response[7] = view.findViewById(R.id.survey_Sex_BirthControl_items);
        response[8] = view.findViewById(R.id.survey_Sex_Vaginal_YesNoresponse);
        response[9] = view.findViewById(R.id.survey_Sex_Anal_YesNoresponse);
        response[10] = view.findViewById(R.id.survey_Sex_Oral_YesNoresponse);
        response[11] = view.findViewById(R.id.survey_Sex_Anal_response);
        response[12] = view.findViewById(R.id.survey_Sex_Oral_response);

        titles[11] = view.findViewById(R.id.survey_Dating_PhyTouWhere);
        response[13] = view.findViewById(R.id.survey_Dating_PhyTouWhere_response);

        titles[11].setText("Where?:");

        //Updates the Page after the user edits their survey
        updateSurveyContent();
        surveyModifierLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result ->{
                        updateSurveyContent();
                }
        );
        //Set the OnClickListener for the change preferences button:
        EditButton = view.findViewById(R.id.survey_Edit_button);
        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSurveyModifier = new Intent(requireActivity(), SurveyModifier.class);
                surveyModifierLauncher.launch(toSurveyModifier);
            }
        });
    }

    private void updateSurveyContent() {
        //Set the TextView for PhyTouWhere TextBox
        response[13].setText(datingSurvey.getTextboxResponse(0));

        //Substitute the TextView with its respective data
        //By accessing its respective Survey
        for(int i = 0; i <= 3; i++)
        {
            titles[i].setText(datingSurvey.getQuestion(i));
            if(i!=2) {
                response[i].setText(preferenceNumberToText(i, Integer.parseInt(datingSurvey.getResponse(i))));
            }
            else {
                response[i].setText(datingSurvey.getResponse(i));
            }
        }
        for(int i = 0; i <= 6; i++)
        {
            if(i!=3) {
                titles[i + 4].setText(sexSurvey.getQuestion(i));
                response[i + 4].setText(sexSurvey.getResponse(i));
            }
            else
                continue;
        }

        //Shows the Sex Survey only if the user selects "Yes" for consent
        //For sex in the Dating Survey
        if(datingSurvey.getResponse(3).equals("0")) {
            titles[4].setVisibility(View.VISIBLE);
            titles[5].setVisibility(View.VISIBLE);
            titles[6].setVisibility(View.VISIBLE);
            titles[7].setVisibility(View.VISIBLE);
            titles[8].setVisibility(View.VISIBLE);
            titles[9].setVisibility(View.VISIBLE);
            titles[10].setVisibility(View.VISIBLE);
            response[4].setVisibility(View.VISIBLE);
            response[5].setVisibility(View.VISIBLE);
            response[6].setVisibility(View.VISIBLE);
            if (sexSurvey.getResponse(2).equals("Yes")){
                titles[7].setText(sexSurvey.getQuestion(3));
                titles[7].setVisibility(View.VISIBLE);
                response[7].setText(sexSurvey.getResponse(3));
                response[7].setVisibility(View.VISIBLE);
            }
            else if(sexSurvey.getResponse(2).equals("No")) {
                titles[7].setVisibility(View.GONE);
                response[7].setVisibility(View.GONE);
            }
            response[8].setVisibility(View.VISIBLE);
            response[9].setVisibility(View.VISIBLE);
            if (sexSurvey.getResponse(5).equals("Yes")){
                response[11].setText(preferenceNumberToText(11, Integer.parseInt(sexSurvey.getResponse(7))));
                response[11].setVisibility(View.VISIBLE);
            }
            else if(sexSurvey.getResponse(5).equals("No")) {
                response[11].setVisibility(View.GONE);
            }
            response[10].setVisibility(View.VISIBLE);
            if(sexSurvey.getResponse(6).equals("Yes")) {
                response[12].setText(preferenceNumberToText(12, Integer.parseInt(sexSurvey.getResponse(8))));
                response[12].setVisibility(View.VISIBLE);
            }
            else if(sexSurvey.getResponse(6).equals("No")) {
                response[12].setVisibility(View.GONE);
            }
        }
        //Hides the Sex Survey only if the user selects "Yes" for consent
        //For sex in the Dating Survey
        else if(datingSurvey.getResponse(3).equals("1")){
            SexTitle.setVisibility(View.GONE);
            titles[4].setVisibility(View.GONE);
            titles[5].setVisibility(View.GONE);
            titles[6].setVisibility(View.GONE);
            titles[7].setVisibility(View.GONE);
            titles[8].setVisibility(View.GONE);
            titles[9].setVisibility(View.GONE);
            titles[10].setVisibility(View.GONE);
            response[4].setVisibility(View.GONE);
            response[5].setVisibility(View.GONE);
            response[6].setVisibility(View.GONE);
            response[7].setVisibility(View.GONE);
            response[8].setVisibility(View.GONE);
            response[9].setVisibility(View.GONE);
            response[10].setVisibility(View.GONE);
            response[11].setVisibility(View.GONE);
            response[12].setVisibility(View.GONE);
        }

        //Shows or Hides "PhyTouWhere" depending on
        //What the user selects for "PhyTou"
        if(datingSurvey.getResponse(0).equals("1"))
        {
            titles[11].setVisibility(View.GONE);
            response[13].setVisibility(View.GONE);
        }
        else
        {
            titles[11].setVisibility(View.VISIBLE);
            response[13].setVisibility(View.VISIBLE);
        }
    }

    private static String preferenceNumberToText(int questionNum, int num) {
        String str = "";
        if (questionNum == 0 || questionNum == 1 || questionNum == 3) {
            switch (num) {
                case 0: str = "Yes"; break;
                case 1: str = "No"; break;
                default: str = Integer.toString(num); break;
            }
        }
        else if (questionNum == 11 || questionNum == 12) {
            switch (num) {
                case 0: str = "Giving"; break;
                case 1: str = "Receiving"; break;
                case 2: str = "Both"; break;
                default: str = Integer.toString(num); break;
            }
        }
        return str;
    }
}