package com.example.datingconsent.ui;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.datingconsent.R;
import com.example.datingconsent.profileresources.ProfileCreatorFragment;

//Todo: add ProfileCreatorFragment
/**
 * The activity used to create a user profile.
 * It first displays a disclaimer (DisclaimerFragment) for the user to read.
 * After the user continues, it displays the ProfileCreatorFragment.
 * Prompts the user to enter their first and last names, and phone number.
 * Uses the ProfileCreatorFragment for the UI and user input.
 * elias is here
 */
public class ProfileCreator extends AppCompatActivity {

    /** The fragment manager for this activity */
    private FragmentManager fm;
    /** The only fragment that will be displayed by this activity */
    private Fragment firstFragment;


    /**
     * Called upon Activity creation.
     * Displays the DiclaimerFragment (where the user reads a disclaimer)
     * ProfileCreatorFragment (where the user is prompted to enter their info) is displayed once the user clicks the "Continue" button on DisclaimerFragment.
     * **The fragment transition logic mentioned above is handled by DisclaimerFragment.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_creator);

        fm = getSupportFragmentManager();
        fm.beginTransaction().replace(R.id.profilecreator_content_container, new ProfileCreatorFragment()).commit();
    }

}