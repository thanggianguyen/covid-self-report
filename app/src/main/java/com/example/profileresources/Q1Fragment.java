package com.example.profileresources;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.covidselfreport.R;

public class Q1Fragment extends Fragment
{
    //Class fields:
    private int questionNum;


    /**
     * Zero argument Constructor
     */
    public Q1Fragment()
    {
        questionNum = 1;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment:
        return inflater.inflate(R.layout.fragment_q1, container, false);
    }


    public int getQuestionNum()
    {
        return questionNum;
    }
}