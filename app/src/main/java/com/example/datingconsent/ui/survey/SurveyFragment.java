package com.example.datingconsent.ui.survey;

import android.content.Intent;
import android.os.Bundle;

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
    private Survey sexSurvey = MainActivity.getDatingPreferenceSurvey();
    private TextView[] titles;
    private TextView[] response;
    private Button EditButton;

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
        titles = new TextView[11];
        response = new TextView[11];

        titles[0] = view.findViewById(R.id.survey_Dating_PhyTou);
        titles[1] = view.findViewById(R.id.survey_Dating_Pay);
        titles[2] = view.findViewById(R.id.survey_Dating_Dates);
        titles[3] = view.findViewById(R.id.survey_Dating_Sex);
        titles[4] = view.findViewById(R.id.survey_Sex_Kiss);
        titles[5] = view.findViewById(R.id.survey_Sex_Tongue);
        titles[6] = view.findViewById(R.id.survey_Sex_BirthControl);
        titles[7] = view.findViewById(R.id.survey_Sex_Vaginal);
        titles[8] = view.findViewById(R.id.survey_Sex_Anal);
        titles[9] = view.findViewById(R.id.survey_Sex_Oral);
        titles[10] = view.findViewById(R.id.survey_Dating_PhyTouWhere);

        response[0] = view.findViewById(R.id.survey_Dating_PhyTou_response);
        response[1] = view.findViewById(R.id.survey_Dating_Pay_response);
        response[2] = view.findViewById(R.id.survey_Dating_Dates_response);
        response[3] = view.findViewById(R.id.survey_Dating_Sex_response);
        response[4] = view.findViewById(R.id.survey_Sex_Kiss_response);
        response[5] = view.findViewById(R.id.survey_Sex_Tongue_response);
        response[6] = view.findViewById(R.id.survey_Sex_BirthControl_response);
        response[7] = view.findViewById(R.id.survey_Sex_Vaginal_response);
        response[8] = view.findViewById(R.id.survey_Sex_Anal_response);
        response[9] = view.findViewById(R.id.survey_Sex_Oral_response);
        response[10] = view.findViewById(R.id.survey_Dating_PhyTouWhere_response);

        titles[10].setText("Where?:");
        response[10].setText(datingSurvey.getTextboxResponse(0));

        for(int i = 0; i <= 3; i++)
        {
            titles[i].setText(datingSurvey.getQuestion(i));
            response[i].setText(datingSurvey.getResponse(i));
        }
        for(int i = 0; i <= 5; i++)
        {
            titles[i+4].setText(sexSurvey.getQuestion(i));
            response[i+4].setText(datingSurvey.getResponse(i));
        }

        /*if(datingSurvey.getResponse(3).equals("Yes")){
            titles[4].setVisibility(View.VISIBLE);
            titles[5].setVisibility(View.VISIBLE);
            titles[6].setVisibility(View.VISIBLE);
            titles[7].setVisibility(View.VISIBLE);
            titles[8].setVisibility(View.VISIBLE);
            titles[9].setVisibility(View.VISIBLE);
            response[4].setVisibility(View.VISIBLE);
            response[5].setVisibility(View.VISIBLE);
            response[6].setVisibility(View.VISIBLE);
            response[7].setVisibility(View.VISIBLE);
            response[8].setVisibility(View.VISIBLE);
            response[9].setVisibility(View.VISIBLE);
        }
        else if(datingSurvey.getResponse(3).equals("No")){
            titles[4].setVisibility(View.INVISIBLE);
            titles[5].setVisibility(View.INVISIBLE);
            titles[6].setVisibility(View.INVISIBLE);
            titles[7].setVisibility(View.INVISIBLE);
            titles[8].setVisibility(View.INVISIBLE);
            titles[9].setVisibility(View.INVISIBLE);
            response[4].setVisibility(View.INVISIBLE);
            response[5].setVisibility(View.INVISIBLE);
            response[6].setVisibility(View.INVISIBLE);
            response[7].setVisibility(View.INVISIBLE);
            response[8].setVisibility(View.INVISIBLE);
            response[9].setVisibility(View.INVISIBLE);
        }*/

        //Set the OnClickListener for the change preferences button:
        EditButton = view.findViewById(R.id.survey_Edit_button);
        EditButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toSurveyModifier = new Intent(requireActivity(), SurveyModifier.class);
                startActivity(toSurveyModifier);
            }
        });
    }
}