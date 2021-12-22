package com.example.datingconsent.profileresources;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.datingconsent.ui.MainActivity;
import com.example.datingconsent.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This fragment is used for user creation or modification of their profile for this app.
 * Called upon by the ProfileCreator and the ProfileModifier activities.
 * Contains text boxes for the user to enter their first and last names and their phone number
 * Each text box has a text watcher to ensure valid names and phone numbers are entered.
 * The Create Button will differ in functionality based on whether this fragment is used by...
 * ...ProfileCreator or ProfileModifier.
 * If used by ProfileModifier, three "Change" buttons will be visible and usable next to each...
 * ...text box for names and phone number.
 */
public class ProfileCreatorFragment extends Fragment {

    /**
     * The textbox the user enters their name in
     */
    private EditText nameEdt;
    /**
     * The textbox the user enters their phone number in
     */
    private EditText phoneNumberEdt;

    /**
     * The spinner to select gender
     */
    private Spinner genderSpinner;

    /**
     * The textbox the user enters if they select Other for gender
     */
    private EditText genderOtherEdt;
    /**
     * The button the user clicks to create their profile
     */
    private Button createButton;
    /**
     * The TextView that informs the user the name entered is invalid
     */
    private TextView invalidNameTV;
    /**
     * The TextView that informs the user the phone number entered is invalid
     */
    private TextView invalidPhoneNumberTV;
    /**
     * Button for changing the name attribute (not used if parent is ProfileCreator)
     */
    private Button changeNameButton;
    /**
     * Button for changing the phone number attribute (not used if parent is ProfileCreator)
     */
    private Button changePhoneNumberButton;
    /**
     * ImageView for successful name change (not used if parent is ProfileCreator)
     */
    private ImageView nameCheckMark;
    /**
     * ImageView for successful phone number change (not used if parent is ProfileCreator)
     */
    private ImageView phoneNumberCheckMark;
    /**
     * Tracks and is activated upon any text input events for phoneNumberEdt
     */
    private PhoneNumberFormattingTextWatcher phoneNumberFormatter;
    /**
     * Tracks and is activated upon any text input events for nameEdt
     */
    private TextWatcher nameFormatter;


    /**
     * Called when the fragment is being created.
     * Inflates the fragment.
     *
     * @param inflater
     * @param container
     * @param savedInstanceState
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile_creator, container, false);
    }


    /**
     * Called once the fragment has been created.
     * Instantiate all Views/Widgets from the fragment's R field
     * Give all Views/Widgets their functionality.
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Instantiate the name text box and build its formatter (enhanced TextWatcher)
        nameEdt = view.findViewById(R.id.profile_name_edittext);
        buildInputFormatter(nameEdt);
        nameEdt.addTextChangedListener(nameFormatter);
        invalidNameTV = view.findViewById(R.id.profile_invalid_name_textview);

        //Instantiate the phone number text box and build its formatter (enhanced PhoneNumberFormattingTextWatcher)
        phoneNumberEdt = view.findViewById(R.id.profile_phone_number_edittext);
        //buildPhoneNumberFormatter();
        phoneNumberEdt.addTextChangedListener(phoneNumberFormatter);
        invalidPhoneNumberTV = view.findViewById(R.id.profile_invalid_phone_number_textview);

        //Instantiate gender spinner
        String[] gender_array = getResources().getStringArray(R.array.gender_array);
        genderSpinner = (Spinner) view.findViewById(R.id.gender_spinner);
        genderOtherEdt = view.findViewById(R.id.profile_gender_other_edittext);
        final List<String> genderList = new ArrayList<String>(Arrays.asList(gender_array));
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireActivity().getBaseContext(),
                 R.layout.custom_spinner_items,genderList){
            @Override
            public boolean isEnabled(int position){
                if(position==0){
                    // Disable the first item from Spinner
                    // First item will be use for hint
                    return false;
                }
                else
                    return true;
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent){
                View view = super.getDropDownView(position, convertView,parent);
                TextView tv = (TextView) view;
                if(position ==0){
                    tv.setTextColor(Color.GRAY);
                }
                else{
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        genderSpinner.setAdapter(adapter);
        //Track spinner selection
        genderSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genderOtherEdt.setVisibility(View.GONE);
                if(position==4)
                    genderOtherEdt.setVisibility(View.VISIBLE);
                if(position>0){
                    ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
/*
        //Instantiate the buttons:
        createButton = view.findViewById(R.id.profile_create_button);
        changeNameButton = view.findViewById(R.id.profile_change_name_button);
        changePhoneNumberButton = view.findViewById(R.id.profile_change_phone_number_button);

        //Instantiate the check mark ImageViews
        nameCheckMark = view.findViewById(R.id.profile_checkmark_name_imageview);
        nameCheckMark.setImageResource(R.drawable.ic_check);
        phoneNumberCheckMark = view.findViewById(R.id.profile_checkmark_phone_number_imageview);
        phoneNumberCheckMark.setImageResource(R.drawable.ic_check);*//*


        //***SET ONCLICK METHODS FOR THE BUTTONS***
        //If this fragment is used by ProfileCreator:
        if (requireActivity() instanceof com.example.covidselfreport.ProfileCreator) {
            createButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    createButtonHandler(v);
                }
            });
        }
        else { //else, make the change buttons visible and set their OnClickListeners:
            ((TextView)(view.findViewById(R.id.profile_title_textview))).setText(R.string.profile_title_textview_text_alternate);

            createButton.setText(R.string.done);
            createButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    requireActivity().finish();
                }
            });

            changeFirstNameButton.setVisibility(View.VISIBLE);
            changeFirstNameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeFirstNameButtonHandler(v);
                }
            });

            changeLastNameButton.setVisibility(View.VISIBLE);
            changeLastNameButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeLastNameButtonHandler(v);
                }
            });

            changePhoneNumberButton.setVisibility(View.VISIBLE);
            changePhoneNumberButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changePhoneNumberButtonHandler(v);
                }
            });

            Profile profile = MainActivity.getProfile();
            if (profile != null) {
                firstNameET.setText(profile.getFirstName());
                lastNameET.setText(profile.getLastName());
                phoneNumberET.setText(profile.getPhoneNumber());
            }
        }*/
    }



    /**
     * Only used if the parent activity is ProfileCreator.
     * Handles any click event for the "Create Profile" button (profile_create_button).
     * If the EditText entries are valid, they get assigned to their respective MainActivity.profile
     * fields and the activity finishes.
     * If any EditText entry is not valid, an error message will appear under that EditText.
     * @param view The button being clicked.
     */
    /*private void createButtonHandler(View view) {
        String firstName = firstNameET.getText().toString();
        String lastName = lastNameET.getText().toString();
        String phoneNumber = phoneNumberET.getText().toString();

        if (firstName.matches("[a-zA-Z]+") && lastName.matches("[a-zA-Z]+") &&
                phoneNumber.matches("\\(\\d{3}\\)\\s\\d{3}-\\d{4}")) {
            MainActivity.getProfile().setFirstName(firstName);
            MainActivity.getProfile().setLastName(lastName);
            MainActivity.getProfile().setPhoneNumber(phoneNumber);

            Intent returnIntent = new Intent();
            requireActivity().setResult(Activity.RESULT_OK, returnIntent);
            requireActivity().finish();
        }
        else {
            if (!firstName.matches("[a-zA-z]+"))
                invalidFirstNameTV.setVisibility(View.VISIBLE);
            if (!lastName.matches("[a-zA-Z]+"))
                invalidLastNameTV.setVisibility(View.VISIBLE);
            if (!phoneNumber.matches("\\(\\d{3}\\)\\s\\d{3}-\\d{4}"))
                invalidPhoneNumberTV.setVisibility(View.VISIBLE);
        }

    }*/


    /**
     * Only used if the parent activity is NOT ProfileCreator
     * Handles any click event for the "change first name" button.
     * If the EditText entry for first name is valid, profile first name is changed and a check mark
     * is displayed.
     * If the EditText entry is invalid, an error message appears.
     * @param view The button being clicked
     */
    /*private void changeFirstNameButtonHandler(View view) {
        String firstName = firstNameET.getText().toString();

        if (firstName.matches("[a-zA-z]+")) {
            MainActivity.getProfile().setFirstName(firstName);
            invalidFirstNameTV.setVisibility(View.INVISIBLE);
            firstNameCheckMark.setVisibility(View.VISIBLE);
        }
        else {
            invalidFirstNameTV.setVisibility(View.VISIBLE);
            firstNameCheckMark.setVisibility(View.INVISIBLE);
        }
    }*/


    /**
     * Only used if the parent activity is NOT ProfileCreator
     * Handles any click event for the "change last name" button.
     * If the EditText entry for first name is valid, profile last name is changed and a check mark
     * is displayed.
     * If the EditText entry is invalid, an error message appears.
     * @param view The button being clicked
     */
    /*private void changeLastNameButtonHandler(View view) {
        String lastName = lastNameET.getText().toString();

        if (lastName.matches("[a-zA-z]+")) {
            MainActivity.getProfile().setLastName(lastName);
            invalidLastNameTV.setVisibility(View.INVISIBLE);
            lastNameCheckMark.setVisibility(View.VISIBLE);
        }
        else {
            invalidLastNameTV.setVisibility(View.VISIBLE);
            lastNameCheckMark.setVisibility(View.INVISIBLE);
        }
    }*/


    /**
     * Only used if the parent activity is NOT ProfileCreator
     * Handles any click event for the "change phone number" button.
     * If the EditText entry for first name is valid, profile phone number is changed and a check mark
     * is displayed.
     * If the EditText entry is invalid, an error message appears.
     * @param view The button being clicked
     */
    /*private void changePhoneNumberButtonHandler(View view) {
        String phoneNumber = phoneNumberET.getText().toString();

        if (phoneNumber.matches("\\(\\d{3}\\)\\s\\d{3}-\\d{4}")) {
            MainActivity.getProfile().setPhoneNumber(phoneNumber);
            invalidPhoneNumberTV.setVisibility(View.INVISIBLE);
            phoneNumberCheckMark.setVisibility(View.VISIBLE);
        }
        else {
            invalidPhoneNumberTV.setVisibility(View.VISIBLE);
            phoneNumberCheckMark.setVisibility(View.INVISIBLE);
        }
    }*/


    /**
     * Builds field phoneNumberFormatter, of type PhoneNumberFormattingTextWatcher.
     * Ensures that the phone number entered into the EditText instance phoneNumberEt is
     * valid.
     */
    /*private void buildPhoneNumberFormatter() {
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
    }*/


    /**
     * Builds field firstNameFormatter, of type TextWatcher.
     * Ensures that the first name entered into the EditText instance firstNameET is valid by
     * allowing only alphabetical characters, spaces, and hyphens to be entered.
     *//*
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


    *//**
     * Builds field lastNameFormatter, of type TextWatcher.
     * Ensures that the last name entered into the EditText instance lastNameET is valid by
     * allowing only alphabetical characters, spaces, and hyphens to be entered.
     *//*
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
    }*/

    private void buildInputFormatter(EditText inputEdt){
        //Set to true if the user is erasing a character.
        //Assign the cursor position to the location the edit took place.
        //Get rid of all characters that are not alphabetical, whitespace, or hyphens:
        TextWatcher inputFormatter = new TextWatcher() {
            private boolean backspaceFlag; //Set to true if the user is erasing a character.
            private boolean editedFlag;
            private int cursorPos;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Assign the cursor position to the location the edit took place.
                cursorPos = s.length() - inputEdt.getSelectionStart();

                if (count > after)
                    backspaceFlag = true;
                else
                    backspaceFlag = false;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                //Get rid of all characters that are not alphabetical, whitespace, or hyphens:
                input = input.replaceAll("[^a-zA-z\\s-]", "");
                if (!editedFlag) {
                    if (!backspaceFlag) {
                        editedFlag = true;
                        inputEdt.setText(input);
                        inputEdt.setSelection(inputEdt.getText().length() - cursorPos);
                    }
                } else
                    editedFlag = false;
            }
        };
    }
}