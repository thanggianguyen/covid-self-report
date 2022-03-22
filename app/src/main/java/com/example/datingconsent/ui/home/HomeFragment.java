package com.example.datingconsent.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.datingconsent.R;
import com.example.datingconsent.ui.MainActivity;


public class HomeFragment extends Fragment {

    private TextView title, subtitle;

    public HomeFragment() {
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
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        title = view.findViewById(R.id.home_Title);
        subtitle = view.findViewById(R.id.home_Subtitle);
        String Name = MainActivity.getProfile().getName();

        title.setText("Hello " + Name + "!");
        subtitle.setText("There are no other users to view currently.");

    }
}