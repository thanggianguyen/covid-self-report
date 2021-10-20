package com.example.covidselfreport.ui.profile;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.*;
import android.graphics.pdf.PdfDocument.PageInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.covidselfreport.*;
import com.example.profileresources.Profile;
import com.example.profileresources.Survey;

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
    private ImageView changePreferencesIcon;
    private ImageView notificationsIcon;
    private ImageView shareProfileIcon;
    private ImageView changeProfileOpenIcon;
    private ImageView changePreferencesOpenIcon;
    private ImageView notificationsOpenIcon;
    private ImageView sharePreferencesOpenIcon;
    private Button changeProfileButton;
    private Button changePreferencesButton;
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
        changePreferencesIcon = view.findViewById(R.id.profilefragment_change_preferences_imageview);
        notificationsIcon = view.findViewById(R.id.profilefragment_notifications_imageview);
        shareProfileIcon = view.findViewById(R.id.profilefragment_share_profile_imageview);
        changeProfileOpenIcon = view.findViewById(R.id.profilefragment_change_profile_open_imageview);
        changePreferencesOpenIcon = view.findViewById(R.id.profilefragment_change_preferences_open_imageview);
        notificationsOpenIcon = view.findViewById(R.id.profilefragment_notifications_open_imageview);
        sharePreferencesOpenIcon = view.findViewById(R.id.profilefragment_share_profile_open_imageview);

        //Display the initials, first name, last name, and phone number, taken from the user profile:
        updateProfileInfo();

        //Set the image resources:
        changeProfileIcon.setImageResource(R.drawable.ic_profilefragment_person);
        changePreferencesIcon.setImageResource(R.drawable.ic_profilefragment_settings);
        notificationsIcon.setImageResource(R.drawable.ic_profilefragment_notification);
        shareProfileIcon.setImageResource(R.drawable.ic_profilefragment_share);
        changeProfileOpenIcon.setImageResource(R.drawable.ic_open_in_new);
        changePreferencesOpenIcon.setImageResource(R.drawable.ic_open_in_new);
        notificationsOpenIcon.setImageResource(R.drawable.ic_open_in_new);
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

        //Set the OnClickListener for the notifications button:
        notificationsButton = view.findViewById(R.id.profilefragment_notifications_button);
        notificationsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog timePicker = new TimePickerDialog(thisContext, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        MainActivity.getProfile().setNotificationTime(hourOfDay, minute);
                        MainActivity.getProfile().saveToJson(requireActivity().getFilesDir().toString(), MainActivity.PROFILE_FILE_NAME);
                        MainActivity.cancelAlarmBroadcastReceiver(getContext());
                        MainActivity.startAlarmBroadcastReceiver(getContext(), hourOfDay, minute);
                    }
                },
                MainActivity.getProfile().getNotificationHour(), MainActivity.getProfile().getNotificationMinute(), false);
                timePicker.show();
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


    /**
     * Generates a String-based report of the user's profile.
     * The String includes the user's name, the date the report was generated, and the user's preference survey responses.
     * The report could possibly include the user's daily intake responses (of the last 14 days) depending on the value of the boolean parameter.
     * @param includeIntakes Whether to include the user's daily intake responses in the report
     * @return The String report on the user's profile
     */
    public static String getProfileText(boolean includeIntakes) {
        Survey preferences = MainActivity.getPreferenceSurvey();
        Profile profile = MainActivity.getProfile();
        Date today = Calendar.getInstance().getTime();

        String profileText = profile.getFirstName() + " " + profile.getLastName() + "'s Health Report" +
                "\nReport generated on " + today + "\n\nPreferences:" + "\n";// + preferences.toString();

        for (int i = 0; i < MainActivity.PREFERENCE_QUESTION_COUNT; i++) {
            profileText += preferences.getQuestion(i) + "\n";
            if (i >= 3)
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


    /**
     * Generates a PDF representation of the user's profile.
     * The PDF includes the user's name, the date the report was generated, and the user's preference survey responses.
     * The report could possibly include the user's daily intake responses (of the last 14 days) depending on the value of the boolean parameter.
     * The PDF report is stored in the app's data files. It is not returned by this method.
     * @param includeIntakes Whether to include the user's daily intake responses in the report
     * @return null if the PDF is successfully generated, or a text based report (using getProfileText() ) if the PDF is not successfully generated.
     */
    public static String generatePDF(boolean includeIntakes) {
        //If the OS version is KitKat or above, attempt PDF generation:
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
            //Basic variable declarations and initializations:
            Profile profile = MainActivity.getProfile();
            Survey preferences = MainActivity.getPreferenceSurvey();
            PdfDocument pdf = new PdfDocument();
            Paint title = new Paint();
            PageInfo pageInfo = new PageInfo.Builder(1000, 2000, 1).create();
            PdfDocument.Page page = pdf.startPage(pageInfo);
            Canvas canvas = page.getCanvas();
            int yPos = 30; //Keeps track fo the next available y position to write the report's text to

            //Write the report title:
            title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
            title.setTextSize(24);
            canvas.drawText(profile.getFirstName() + " " + profile.getLastName() + "'s Health Report", 30, yPos, title);
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
            for (int i = 0; i < MainActivity.PREFERENCE_QUESTION_COUNT; i++) {
                //Write the question, in bold:
                title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                canvas.drawText(preferences.getQuestion(i) + "\n", 32, yPos, title);
                yPos += 15;

                //Write the answers:
                title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                if (i >= 3) {
                    canvas.drawText(preferences.getResponse(i) + "\n", 32, yPos, title);
                    yPos += 15;
                }
                else {
                    canvas.drawText(preferenceNumberToText(i, Integer.parseInt(preferences.getResponse(i))) + "\n", 32, yPos, title);
                    yPos += 15;
                }

                //If there is a text response, write it to the PDF:
                if (preferences.getTextboxResponse(i) != null && !preferences.getTextboxResponse(i).isEmpty()) {
                    canvas.drawText("Condition(s): " + preferences.getTextboxResponse(i) + "\n", 32, yPos, title);
                    yPos += 15;
                }
            }

            //If the user elects to include their intake responses (of the last 14 days), they are written to the PDF:
            if (includeIntakes) {
                Survey[] intakes = MainScreen.getIntakeSurveys();

                //Write the "Daily Intake" header:
                yPos += 30;
                title.setTextSize(20);
                canvas.drawText("Daily Intake Responses:", 32, yPos, title);
                yPos += 30;

                //Loop through each intake day (0-14 days ago) and print the questions and responses:
                title.setTextSize(12);
                for (int i = 0; i < intakes.length; i++) {
                    //Print the day header ("Today", "1 day ago", or "x days ago"):
                    title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.BOLD));
                    if (i == 0)
                        canvas.drawText("Today:", 32, yPos, title);
                    else if (i == 1)
                        canvas.drawText("1 day ago:", 32, yPos, title);
                    else
                        canvas.drawText(i + " days ago:", 32, yPos, title);
                    yPos += 15;

                    //If the intake of this day was not taken, write that to the PDF:
                    if (intakes[i] == null) {
                        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                        canvas.drawText("No intake survey was taken on this day.", 32, yPos, title);
                    }
                    //If the intake of this day was taken, write its question/response pairs to the PDF:
                    else {
                        //Write question 1 text:
                        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
                        canvas.drawText(intakes[i].getQuestion(0), 32, yPos, title);
                        yPos += 15;

                        //Write question 1 response:
                        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                        canvas.drawText(intakeNumberToText(Integer.parseInt(intakes[i].getResponse(0))), 32, yPos, title);
                        yPos += 15;

                        //Write question 2 text:
                        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.ITALIC));
                        canvas.drawText(intakes[i].getQuestion(1), 32, yPos, title);
                        yPos += 15;

                        //Write question 2 response:
                        title.setTypeface(Typeface.create(Typeface.DEFAULT, Typeface.NORMAL));
                        canvas.drawText(intakes[i].getResponse(1), 32, yPos, title);
                        yPos += 15;

                        //If the user put info in the who and/or where text boxes (text-based response), write them to the PDF:
                        if (intakes[i].getTextboxResponse(1) != null) {
                            String tbResponse = intakes[i].getTextboxResponse(1);
                            int whoIndex = tbResponse.indexOf("With whom");
                            if (whoIndex == -1)
                                canvas.drawText(tbResponse, 32, yPos, title);
                            else {
                                canvas.drawText(tbResponse.substring(0, whoIndex), 32, yPos, title);
                                yPos += 15;
                                canvas.drawText(tbResponse.substring(whoIndex), 32, yPos, title);
                            }
                        }
                    }
                    yPos += 30;
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
            return getProfileText(includeIntakes);
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