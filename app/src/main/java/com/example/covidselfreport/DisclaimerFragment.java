package com.example.covidselfreport;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.profileresources.ProfileCreatorFragment;

/**
 * Displays a disclaimer for the user to read, before they start using the app (before they create their profile).
 * Contains a "Continue" button. Once the user clicks this, they are taken to the profile creation page (ProfileCreatorFragment)
 * This fragment is used by the ProfileCreator activity, since that is the firs activity the user interacts directly with.
 */
public class DisclaimerFragment extends Fragment {

    public DisclaimerFragment() {
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
        return inflater.inflate(R.layout.fragment_disclaimer, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Button disclaimerContinueButton = view.findViewById(R.id.disclaimer_continue_button);
        //Set the OnClick action when the user clicks the "Continue" button:
        disclaimerContinueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Switch fragments from the current one (DisclaimerFragment) to the ProfileCreatorFragment.
                requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.profilecreator_content_container, new ProfileCreatorFragment()).commit();
            }
        });
    }
}