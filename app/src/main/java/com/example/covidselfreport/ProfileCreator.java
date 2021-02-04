package com.example.covidselfreport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;


/**
 * The activity used to create a user profile.
 * Prompts the user to enter their first and last names, and phone number.
 */
public class ProfileCreator extends AppCompatActivity {

    /** The textbox the user enters their first name in */
    private EditText firstNameET;
    /** The textbox the user enters their last name in */
    private EditText lastNameET;
    /** The textbox the user enters their phone number in */
    private EditText phoneNumberET;
    /** Tracks and is activated upon any text input evetns for phoneNumberET */
    private PhoneNumberFormattingTextWatcher phoneNumberFormatter;
    /** Tracks and is activated upon any text input evetns for firstNameET */
    private TextWatcher firstNameFormatter;
    /** Tracks and is activated upon any text input evetns for lastNameET */
    private TextWatcher lastNameFormatter;


    /**
     * Called upon Activity creation.
     * Initializes the EditText objects in the activity and assigns them their respective
     * TextWatchers.
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_creator);

        //Instantiate the first name text box and build its formatter (enhanced TextWatcher)
        firstNameET = findViewById(R.id.profile_first_name_edittext);
        buildFirstNameFormatter();
        firstNameET.addTextChangedListener(firstNameFormatter);

        //Instantiate the last name text box and build its formatter (enhanced TextWatcher)
        lastNameET = findViewById(R.id.profile_last_name_edittext);
        buildLastNameFormatter();
        lastNameET.addTextChangedListener(lastNameFormatter);

        //Instantiate the phone number text box and build its formatter (enhanced PhoneNumberFormattingTextWatcher)
        phoneNumberET = findViewById(R.id.profile_phone_number_edittext);
        buildPhoneNumberFormatter();
        phoneNumberET.addTextChangedListener(phoneNumberFormatter);
    }


    /**
     * Handles any click event for the "Create Profile" button (profile_create_button).
     * If the EditText entries are valid, they get assigned to their respective MainActivity.profile
     * fields and the activity finishes.
     * If any EditText entry is not valid, an error message will appear under that EditText.
     * @param view The button being clicked.
     */
    public void createButtonHandler(View view) {
        String firstName = firstNameET.getText().toString();
        String lastName = lastNameET.getText().toString();
        String phoneNumber = phoneNumberET.getText().toString();

        if (firstName.matches("[a-zA-Z]+") && lastName.matches("[a-zA-Z]+") &&
        phoneNumber.matches("\\(\\d{3}\\)\\s\\d{3}-\\d{4}")) {
            MainActivity.profile.setFirstName(firstName);
            MainActivity.profile.setLastName(lastName);
            MainActivity.profile.setPhoneNumber(phoneNumber);

            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_OK, returnIntent);
            finish();
        }
        else {
            if (!firstName.matches("[a-zA-z]+"))
                findViewById(R.id.profile_invalid_first_name_textview).setVisibility(View.VISIBLE);
            if (!lastName.matches("[a-zA-Z]+"))
                findViewById(R.id.profile_invalid_last_name_textview).setVisibility(View.VISIBLE);
            if (!phoneNumber.matches("\\(\\d{3}\\)\\s\\d{3}-\\d{4}"))
                findViewById(R.id.profile_invalid_phone_number_textview).setVisibility(View.VISIBLE);
        }

    }


    /**
     * Builds field phoneNumberFormatter, of type PhoneNumberFormattingTextWatcher.
     * Ensures that the phone number entered into the EditText instance phoneNumberEt is
     * valid.
     */
    private void buildPhoneNumberFormatter() {
        phoneNumberFormatter = new PhoneNumberFormattingTextWatcher() {
            private boolean backspaceFlag; //Set to true if the user is erasing a character.
            private boolean editedFlag; //Set to true if the text box is edited in the
                                        //afterTextChanged() method, because the watcher will be
                                        //triggered again when that method changes the text.
                                        //Tells us whether the edit was by the user or the method.
            private int cursorPos; //The position of the cursor in the text box.

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Assign the cursor position to the location the edit took place.
                cursorPos = s.length() - phoneNumberET.getSelectionStart();

                if (count > after)
                    backspaceFlag = true;
                else
                    backspaceFlag = false;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after)
            { }

            @Override
            public void afterTextChanged(Editable s) {
                String phoneNum = s.toString();
                //Replace all occurrences of non-digit characters with an empty string (remove them)
                phoneNum = phoneNum.replaceAll("[^\\d]", "");
                //^^^ raw phone number (digits only) ^^^

                //If the edited flag is false, that means that the text changed event was triggered
                //by the user, so the string must be formatted appropriately:
                if (!editedFlag) {
                    editedFlag = true; //phone number string will be edited here.
                    String formatted;
                    if (phoneNum.length() >= 6 && !backspaceFlag) {
                        if (phoneNum.length() > 10) //10 digits max for phone numbers.
                            formatted = "(" + phoneNum.substring(0, 3) + ") " +
                                    phoneNum.substring(3, 6) + "-" + phoneNum.substring(6, 10);
                        else
                            formatted = "(" + phoneNum.substring(0, 3) + ") " +
                                phoneNum.substring(3, 6) + "-" + phoneNum.substring(6);
                    }
                    else if (phoneNum.length() >= 3 && !backspaceFlag)
                        formatted = "(" + phoneNum.substring(0, 3) + ") " + phoneNum.substring(3);
                    else if (phoneNum.length() <= 3 && backspaceFlag)
                        formatted = phoneNum;
                    else if (phoneNum.length() <= 6 && backspaceFlag)
                        formatted = "(" + phoneNum.substring(0, 3) + ") " + phoneNum.substring(3);
                    else if (backspaceFlag)
                        formatted = "(" + phoneNum.substring(0, 3) + ") " + phoneNum.substring(3, 6) +
                                "-" + phoneNum.substring(6);
                    else
                        formatted = phoneNum;

                    phoneNumberET.setText(formatted);
                    phoneNumberET.setSelection(phoneNumberET.getText().length() - cursorPos);
                }
                //If the edited flag is true, that means that the text changed event was triggered
                //by this formatter, so the string is already formatted:
                else
                    editedFlag = false;
            }
        };
    }


    /**
     * Builds field firstNameFormatter, of type TextWatcher.
     * Ensures that the first name entered into the EditText instance firstNameET is valid by
     * allowing only alphabetical characters, spaces, and hyphens to be entered.
     */
    private void buildFirstNameFormatter() {
        firstNameFormatter = new TextWatcher() {
            private boolean backspaceFlag; //Set to true if the user is erasing a character.
            private boolean editedFlag;
            private int cursorPos;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Assign the cursor position to the location the edit took place.
                cursorPos = s.length() - firstNameET.getSelectionStart();

                if (count > after)
                    backspaceFlag = true;
                else
                    backspaceFlag = false;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after)
            {}

            @Override
            public void afterTextChanged(Editable s) {
                String firstName = s.toString();
                //Get rid of all characters that are not alphabetical, whitespace, or hyphens:
                firstName = firstName.replaceAll("[^a-zA-z\\s-]", "");
                if (!editedFlag) {
                    if (!backspaceFlag) {
                        editedFlag = true;
                        firstNameET.setText(firstName);
                        firstNameET.setSelection(firstNameET.getText().length() - cursorPos);
                    }
                }
                else
                    editedFlag = false;
            }
        };
    }


    /**
     * Builds field lastNameFormatter, of type TextWatcher.
     * Ensures that the last name entered into the EditText instance lastNameET is valid by
     * allowing only alphabetical characters, spaces, and hyphens to be entered.
     */
    private void buildLastNameFormatter() {
        lastNameFormatter = new TextWatcher() {
            private boolean backspaceFlag; //Set to true if the user is erasing a character.
            private boolean editedFlag;
            private int cursorPos;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Assign the cursor position to the location the edit took place.
                cursorPos = s.length() - lastNameET.getSelectionStart();

                if (count > after)
                    backspaceFlag = true;
                else
                    backspaceFlag = false;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int count, int after)
            {}

            @Override
            public void afterTextChanged(Editable s) {
                String lastName = s.toString();
                //Get rid of all characters that are not alphabetical, whitespace, or hyphens:
                lastName = lastName.replaceAll("[^a-zA-z\\s-]", "");
                if (!editedFlag) {
                    if (!backspaceFlag) {
                        editedFlag = true;
                        lastNameET.setText(lastName);
                        lastNameET.setSelection(lastNameET.getText().length() - cursorPos);
                    }
                }
                else
                    editedFlag = false;
            }
        };
    }
}