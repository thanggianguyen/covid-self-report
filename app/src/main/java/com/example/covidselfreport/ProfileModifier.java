package com.example.covidselfreport;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.profileresources.ProfileCreatorFragment;


/**
 * This activity is used to modify a user's profile.
 * Accessed in the MainScreen Activity in the Profile fragment.
 * Uses the ProfileCreatorFragment for the UI and user input.
 */
public class ProfileModifier extends AppCompatActivity {

    /** The fragment manager for this activity */
    private FragmentManager fm;
    /** The only fragment that will be displayed by this activity */
    private Fragment mainFragment;

    /**
     * Called upon Activity creation.
     * Displays the ProfileCreatorFragment (where the user is prompted to change their info).
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_modifier);

        fm = getSupportFragmentManager();
        mainFragment = new ProfileCreatorFragment();
        fm.beginTransaction().replace(R.id.profilemodifier_content_container, mainFragment).commit();
    }


    /**
     * Automatically called when this activity finishes.
     * Save the profile and preference survey data, in case it was modified.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        MainActivity.getProfile().saveToJson(getFilesDir().toString(), MainActivity.PROFILE_FILE_NAME);
    }
}