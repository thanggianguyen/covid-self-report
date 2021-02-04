package com.example.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.covidselfreport.R;


public class Q4Fragment extends Fragment
{
    //Class Fields:
    int questionNum;


    /**********************************************************************************************
     * Empty Constructor
     **********************************************************************************************/
    public Q4Fragment()
    {
        questionNum = 4;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_q4, container, false);
    }


    public int getQuestionNum()
    {
        return questionNum;
    }
}