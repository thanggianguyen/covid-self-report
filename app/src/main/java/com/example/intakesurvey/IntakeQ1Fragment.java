package com.example.intakesurvey;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.covidselfreport.R;


public class IntakeQ1Fragment extends Fragment
{


    public IntakeQ1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_intake_q1, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {

    }
}
