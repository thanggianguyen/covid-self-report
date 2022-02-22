package com.example.datingconsent.ui.profile;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.datingconsent.R;
import com.example.datingconsent.profileresources.Profile;
import com.example.datingconsent.surveyresources.Survey;
import com.example.datingconsent.ui.MainActivity;
import com.example.datingconsent.ui.MainScreen;
import com.example.datingconsent.ui.ProfileModifier;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

/**
 * The "Profile" section in MainScreen (the bottom navigation activity).
 * Displays profile info and allows the user to change their profile, change their
 * preferences on the preference survey, or to share their profile.
 */
public class ProfileFragment extends Fragment {

    private Context thisContext;
    private static FragmentActivity thisActivity;
    private TextView initialsText;
    private TextView nameText;
    private TextView phoneNumberText;
    private ImageView changeProfileIcon;
    private ImageView notificationsIcon;
    private ImageView shareProfileIcon;
    private ImageView changeProfileOpenIcon;
    private ImageView notificationsOpenIcon;
    private ImageView sharePreferencesOpenIcon;
    private Button changeProfileButton;
    private Button notificationsButton;
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
        thisContext = container.getContext();
        thisActivity = requireActivity();
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
        notificationsIcon = view.findViewById(R.id.profilefragment_notifications_imageview);
        shareProfileIcon = view.findViewById(R.id.profilefragment_share_profile_imageview);
        changeProfileOpenIcon = view.findViewById(R.id.profilefragment_change_profile_open_imageview);
        notificationsOpenIcon = view.findViewById(R.id.profilefragment_notifications_open_imageview);
        sharePreferencesOpenIcon = view.findViewById(R.id.profilefragment_share_profile_open_imageview);

        //Display the initials, first name, last name, and phone number, taken from the user profile:
        updateProfileInfo();

        //Set the image resources:
        changeProfileIcon.setImageResource(R.drawable.ic_profilefragment_person);
        notificationsIcon.setImageResource(R.drawable.ic_profilefragment_notification);
        shareProfileIcon.setImageResource(R.drawable.ic_profilefragment_share);
        changeProfileOpenIcon.setImageResource(R.drawable.ic_open_in_new);
        notificationsOpenIcon.setImageResource(R.drawable.ic_open_in_new);
        sharePreferencesOpenIcon.setImageResource(R.drawable.ic_open_in_new);

        //Set the OnClickListener for the change profile button:
        changeProfileButton = view.findViewById(R.id.profilefragment_change_profile_button);
        changeProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toProfileModifier = new Intent(requireActivity(), ProfileModifier.class);
                startActivity(toProfileModifier);
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
        char Initial;
        String Name = MainActivity.getProfile().getName();
        if (Name != null && Name.length() > 0)
            Initial = Name.charAt(0);
        else
            Initial = '#';
        String initial = Character.toString(Initial);
        initialsText.setText(initial);

        //Find the full name and display it on fragment_profile:
        String fullName;
        if (Name != null)
            fullName = Name;
        else
            fullName = "Your name here";
        nameText.setText(fullName);

        //Find the phone number and display it on fragment_profile:
        String phoneNumber = MainActivity.getProfile().getPhoneNumber();
        if (phoneNumber == null || phoneNumber.isEmpty())
            phoneNumber = "Your phone number here";
        phoneNumberText.setText(phoneNumber);
    }


    /**
     * Generates a String-based report of the user's profile.
     * The String includes the user's name, the date the report was generated, and the user's profile and consent survey responses.
     * @return The String report on the user's profile
     */
    public static String getProfileText() {
        Survey datingpreferences = MainActivity.getDatingPreferenceSurvey();
        Survey sexpreferences = MainActivity.getSexPreferenceSurvey();
        Profile profile = MainActivity.getProfile();
        Date today = Calendar.getInstance().getTime();

        String profileText = profile.getName() + "'s Profile and Consent Survey" +
                "\nReport generated on " + today + "\n\nPreferences:" + "\n";// + preferences.toString();

        for (int i = 0; i < MainActivity.DATING_PREFERENCE_QUESTION_COUNT; i++) {
            profileText += datingpreferences.getQuestion(i) + "\n";
            if (i >= 3)
                profileText += datingpreferences.getResponse(i) + "\n";
            if (datingpreferences.getTextboxResponse(i) != null && !datingpreferences.getTextboxResponse(i).isEmpty())
                profileText += "Condition(s): " + datingpreferences.getTextboxResponse(i) + "\n";
        }
        for (int i = 0; i < MainActivity.SEX_PREFERENCE_QUESTION_COUNT; i++) {
            profileText += sexpreferences.getQuestion(i) + "\n";
            if (i >= 3)
                profileText += sexpreferences.getResponse(i) + "\n";
            if (sexpreferences.getTextboxResponse(i) != null && !sexpreferences.getTextboxResponse(i).isEmpty())
                profileText += "Condition(s): " + sexpreferences.getTextboxResponse(i) + "\n";
        }

        return profileText;
    }


    /**
     * Generates a PDF representation of the user's profile.
     * The PDF includes the user's name, the date the report was generated, and the user's profile and consent survey responses.
     * The PDF report is stored in the app's data files. It is not returned by this method.
     * @return null if the PDF is successfully generated, or a text based report (using getProfileText() ) if the PDF is not successfully generated.
     */
    public static String generatePDF() {
        //If the OS version is KitKat or above, attempt PDF generation:
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            //Basic variable declarations and initializations:
            Profile profile = MainActivity.getProfile();
            Survey datingpreferences = MainActivity.getDatingPreferenceSurvey();
            Survey sexpreferences = MainActivity.getSexPreferenceSurvey();
            PdfDocument pdf = new PdfDocument();
            Paint title = new Paint();
            PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(792, 1500, 1).create();
            PdfDocument.Page page = pdf.startPage(pageInfo);
            Canvas canvas = page.getCanvas();
            int yPos = 30; //Keeps track fo the next available y position to write the report's text to

            //Write the report title:
            title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            title.setTextSize(24);
            canvas.drawText(profile.getName() + "'s Health Report", 30, yPos, title);
            yPos += 20;

            //Write the date the report was generated on:
            title.setTextSize(12);
            Date today = Calendar.getInstance().getTime();
            canvas.drawText("Report generated on " + today.toString(), 32, yPos, title);
            yPos += 30;

            //Write the "Preference Survey" header:
            title.setTextSize(20);
            canvas.drawText("Preference Survey Responses:", 32, yPos, title);
            yPos += 30;

            //Go through the preferences Survey object and write each question/response pair to the PDF:
            title.setTextSize(12);
            for (int i = 0; i < MainActivity.DATING_PREFERENCE_QUESTION_COUNT; i++) {
                //Write the question, in bold:
                title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                canvas.drawText(datingpreferences.getQuestion(i) + "\n", 32, yPos, title);
                yPos += 15;

                //Write the answers:
                title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                if (i >= 3) {
                    canvas.drawText(datingpreferences.getResponse(i) + "\n", 32, yPos, title);
                    yPos += 15;
                }

                //If there is a text response, write it to the PDF:
                if (datingpreferences.getTextboxResponse(i) != null && !datingpreferences.getTextboxResponse(i).isEmpty()) {
                    canvas.drawText("Condition(s): " + datingpreferences.getTextboxResponse(i) + "\n", 32, yPos, title);
                    yPos += 15;
                }
            }

            //Go through the preferences Survey object and write each question/response pair to the PDF:
            title.setTextSize(12);
            for (int i = 0; i < MainActivity.SEX_PREFERENCE_QUESTION_COUNT; i++) {
                //Write the question, in bold:
                title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                canvas.drawText(sexpreferences.getQuestion(i) + "\n", 32, yPos, title);
                yPos += 15;

                //Write the answers:
                title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                if (i >= 3) {
                    canvas.drawText(sexpreferences.getResponse(i) + "\n", 32, yPos, title);
                    yPos += 15;
                }

                //If there is a text response, write it to the PDF:
                if (sexpreferences.getTextboxResponse(i) != null && !sexpreferences.getTextboxResponse(i).isEmpty()) {
                    canvas.drawText("Condition(s): " + sexpreferences.getTextboxResponse(i) + "\n", 32, yPos, title);
                    yPos += 15;
                }
            }


            //Finalize the PDF and write it to file (profilereport.pdf) stored in the app's data folder
            pdf.finishPage(page);
            File pdfFile = new File(thisActivity.getFilesDir().toString(), "profilereport.pdf");
            if (pdfFile.exists())
                pdfFile.delete();
            try {
                pdf.writeTo(new FileOutputStream(pdfFile));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            pdf.close();
            return null;
        } //If the OS version is below KitKat, return a text-based report:
        else
            return getProfileText();
    }


}