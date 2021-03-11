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
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.covidselfreport.*;
import com.example.profileresources.Profile;
import com.example.profileresources.Survey;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


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
    private Button changePreferencesButton;
    private Button shareProfileButton;


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

        //Set the OnClickListener for the change preferences button:
        changePreferencesButton = view.findViewById(R.id.profilefragment_change_preferences_button);
        changePreferencesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toPreferenceModifier = new Intent(requireActivity(), SurveyModifier.class);
                startActivity(toPreferenceModifier);
            }
        });

        //Set the OnClickListener for the Share Profile button:
        shareProfileButton = view.findViewById(R.id.profilefragment_share_profile_button);
        shareProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment message = new ShareOptionsDialog();
                message.show(requireActivity().getSupportFragmentManager(), "share");
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


    public static String getProfileText(boolean includeIntakes) {
        Survey preferences = MainActivity.getPreferenceSurvey();
        Profile profile = MainActivity.getProfile();
        Date today = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyy_MM_dd", Locale.getDefault());

        String profileText = profile.getFirstName() + " " + profile.getLastName() + "'s Health Report" +
                "\nReport generated on " + today + "\n\nPreferences:" + "\n";// + preferences.toString();

        for (int i = 0; i < MainActivity.PREFERENCE_QUESTION_COUNT; i++) {
            profileText += preferences.getQuestion(i) + "\n";
            if (i == MainActivity.PREFERENCE_QUESTION_COUNT - 1)
                profileText += preferences.getResponse(i) + "\n";
            else
                profileText += preferenceNumberToText(i, Integer.parseInt(preferences.getResponse(i))) + "\n";
            if (preferences.getTextboxResponse(i) != null && !preferences.getTextboxResponse(i).isEmpty())
                profileText += "Condition(s): " + preferences.getTextboxResponse(i) + "\n";
        }

        if (includeIntakes) {
            Survey[] intakes = MainScreen.getIntakeSurveys();
            profileText += "\n\nIntakes:" + "\n";
            for (int i = 0; i < intakes.length; i++) {
                if (i == 0)
                    profileText += "Today:\n";
                else if (i == 1)
                    profileText += "1 day ago:\n";
                else
                    profileText += i + " days ago:\n";
                if (intakes[i] == null)
                    profileText += "No intake survey was taken on this day.\n\n";
                else {
                    profileText += intakes[i].getQuestion(0) + "\n";
                    profileText += intakeNumberToText(Integer.parseInt(intakes[i].getResponse(0))) + "\n";
                    profileText += intakes[i].getQuestion(1) + "\n";
                    profileText += intakes[i].getResponse(1) + "\n";
                    if (intakes[i].getTextboxResponse(1) != null) {
                        String tbResponse = intakes[i].getTextboxResponse(1);
                        int whoIndex = tbResponse.indexOf("With whom");
                        if (whoIndex == -1)
                            profileText += tbResponse + "\n\n";
                        else
                            profileText += tbResponse.substring(0, whoIndex) + "\n" + tbResponse.substring(whoIndex) + "\n\n";
                    }
                }
            }
        }
        return profileText;
    }


    private static String preferenceNumberToText(int questionNum, int num) {
        String str = "";
        if (questionNum == 0 || questionNum == 1) {
            switch (num) {
                case 0: str = "No Risk"; break;
                case 1: str = "Nearly No Risk"; break;
                case 2: str = "Low Risk"; break;
                case 3: str = "Medium Risk"; break;
                case 4: str = "High Risk"; break;
                default: str = Integer.toString(num); break;
            }
        }
        else if (questionNum == 2) {
            switch (num) {
                case 0: str = "Not At All Comfortable"; break;
                case 1: str = "Not Comfortable"; break;
                case 2: str = "Somewhat Comfortable"; break;
                case 3: str = "Comfortable"; break;
                case 4: str = "Very Comfortable"; break;
                default: str = Integer.toString(num); break;
            }
        }
        return str;
    }


    private static String intakeNumberToText(int num) {
        String str = "";
        switch (num) {
            case 0: str = "Many Symptoms"; break;
            case 1: str = "Some Symptoms"; break;
            case 2: str = "One Symptom"; break;
            case 3: str = "Relatively Well"; break;
            case 4: str = "Peak of Health"; break;
            default: str = Integer.toString(num); break;
        }
        return str;
    }

}