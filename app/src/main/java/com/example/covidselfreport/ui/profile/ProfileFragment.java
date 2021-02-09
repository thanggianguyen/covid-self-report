package com.example.covidselfreport.ui.profile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.covidselfreport.MainActivity;
import com.example.covidselfreport.R;
import com.example.profileresources.Profile;


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


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


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

        //Set the image resources:
        changeProfileIcon.setImageResource(R.drawable.ic_profilefragment_person);
        changePreferencesIcon.setImageResource(R.drawable.ic_profilefragment_settings);
        shareProfileIcon.setImageResource(R.drawable.ic_profilefragment_share);
        changeProfileOpenIcon.setImageResource(R.drawable.ic_open_in_new);
        changePreferencesOpenIcon.setImageResource(R.drawable.ic_open_in_new);
        sharePreferencesOpenIcon.setImageResource(R.drawable.ic_open_in_new);
    }
}