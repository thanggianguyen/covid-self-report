package com.example.covidselfreport.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.covidselfreport.MainActivity;
import com.example.covidselfreport.ProfileModifier;
import com.example.covidselfreport.R;
import com.example.profileresources.Profile;


/**
 * The "Profile" section in MainScreen (the bottom navigation activity).
 * Displays profile info and allows the user to change their profile, change their
 * preferences on the preference survey, or to share their profile.
 */
public class ProfileFragment extends Fragment {

    private TextView initialsText;
    private TextView nameText;
    private TextView phoneNumberText;
    private ImageView changeProfileIcon;
    private ImageView changePreferencesIcon;
    private ImageView shareProfileIcon;
    private ImageView changeProfileOpenIcon;
    private ImageView changePreferencesOpenIcon;
    private ImageView sharePreferencesOpenIcon;
    private Button changeProfileButton;


    /**
     * Called when the fragment is created. Inflates the View.
     * @param inflater
     * @param container
     * @param savedInstanceState
     * @return
     */
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


    /**
     * Called once the fragment is created (after oncCreateView() ).
     * Initializes all View/Widget fields.
     * Displays the appropriate Views and initializes their attributes.
     * Sets the onClick() methods for the Buttons.
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        initialsText = view.findViewById(R.id.profilefragment_initials_textview);
        nameText = view.findViewById(R.id.profilefragment_name_textview);
        phoneNumberText = view.findViewById(R.id.profilefragment_phonenumber_textview);
        changeProfileIcon = view.findViewById(R.id.profilefragment_change_profile_imageview);
        changePreferencesIcon = view.findViewById(R.id.profilefragment_change_preferences_imageview);
        shareProfileIcon = view.findViewById(R.id.profilefragment_share_profile_imageview);
        changeProfileOpenIcon = view.findViewById(R.id.profilefragment_change_profile_open_imageview);
        changePreferencesOpenIcon = view.findViewById(R.id.profilefragment_change_preferences_open_imageview);
        sharePreferencesOpenIcon = view.findViewById(R.id.profilefragment_share_profile_open_imageview);

        //Display the initials, first name, last name, and phone number, taken from the user profile:
        updateProfileInfo();

        //Set the image resources:
        changeProfileIcon.setImageResource(R.drawable.ic_profilefragment_person);
        changePreferencesIcon.setImageResource(R.drawable.ic_profilefragment_settings);
        shareProfileIcon.setImageResource(R.drawable.ic_profilefragment_share);
        changeProfileOpenIcon.setImageResource(R.drawable.ic_open_in_new);
        changePreferencesOpenIcon.setImageResource(R.drawable.ic_open_in_new);
        sharePreferencesOpenIcon.setImageResource(R.drawable.ic_open_in_new);

        //Set the OnClickListener for the change profile button:
        changeProfileButton = view.findViewById(R.id.profilefragment_change_profile_button);
        changeProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toProfileModifier = new Intent(requireActivity(), ProfileModifier.class);
                startActivityForResult(toProfileModifier, 789);
            }
        });
    }


    /**
     * When this screen is resumed, profile information (name and phone number) is updated.
     * This is called just in case the user has changed their profile info, to keep it up to date.
     */
    @Override
    public void onResume() {
        super.onResume();
        updateProfileInfo();
    }


    /**
     * Sets the text of the initials, name and phone number TextViews to the appropriate values,
     * taken from the profile object in MainActivity.
     * Called upon Fragment creation and upon Fragment resuming.
     */
    private void updateProfileInfo() {
        //Find the initials and display them on fragment_profile:
        char firstInitial, lastInitial;
        String firstName = MainActivity.getProfile().getFirstName();
        String lastName = MainActivity.getProfile().getLastName();
        if (firstName != null && firstName.length() > 0)
            firstInitial = firstName.charAt(0);
        else
            firstInitial = '#';
        if (lastName != null && lastName.length() > 0)
            lastInitial = lastName.charAt(0);
        else
            lastInitial = '#';
        String initials = Character.toString(firstInitial) + Character.toString(lastInitial);
        initialsText.setText(initials);

        //Find the full name and display it on fragment_profile:
        String fullName;
        if (firstName != null && lastName != null)
            fullName = firstName + " " + lastName;
        else
            fullName = "Your name here";
        nameText.setText(fullName);

        //Find the phone number and display it on fragment_profile:
        String phoneNumber = MainActivity.getProfile().getPhoneNumber();
        if (phoneNumber == null || phoneNumber.isEmpty())
            phoneNumber = "Your phone number here";
        phoneNumberText.setText(phoneNumber);
    }
}