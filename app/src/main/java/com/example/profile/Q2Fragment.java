package com.example.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.covidselfreport.R;


public class Q2Fragment extends Fragment
{
    //Class Fields:
    private int questionNum;


    /**********************************************************************************************
     * Empty Constructor
     **********************************************************************************************/
    public Q2Fragment()
    {
        questionNum = 2;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        //Inflate the layout for this fragment:
        return inflater.inflate(R.layout.fragment_q2, container, false);
    }


    public int getQuestionNum()
    {
        return questionNum;
    }
}