package com.example.datingconsent.profileresources;

import static java.lang.String.*;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import android.telephony.PhoneNumberFormattingTextWatcher;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.datingconsent.ui.MainActivity;
import com.example.datingconsent.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
     * The text box the user enters their name in
     */
    private EditText nameEdt;
    /**
     * The text box the user enters their age in
     */
    private EditText ageEdt;
    /**
     * The text box the user enters their pronouns in
     */
    private EditText pronounsEdt;
    /**
     * The text box the user enters their phone number in
     */
    private EditText phoneNumberEdt;
    /**
     * The spinner to select gender
     */
    private Spinner genderSpinner;
    /**
     * The text box the user enters if they select Other for gender
     */
    private EditText genderOtherEdt;
    /**
     * The text box the user enters their religion in
     */
    private EditText religionEdt;
    /**
     * The text box the user enters their political view in
     */
    private EditText politicalViewEdt;
    /**
     * The spinner to select sexual orientation
     */
    private Spinner sexualOrientationSpinner;
    /**
     * The text box the user enters their other sexual orientation in
     */
    private EditText sexualOrientationOtherEdt;
    /**
     * The spinner to select looking for
     */
    private Spinner lookingForSpinner;
    /**
     * The text box the user enters their custom looking for in
     */
    private EditText lookingForOtherEdt;
    /**
     * Radio Buttons for COVID-19 vaccination status
     */
    private RadioGroup vaccinatedRadioGroup;
    /**
     * First dose date text box
     */
    private EditText vaccinatedDateEdt;
    /**
     * Second shot prompt
     */
    private TextView secondShotTextView;
    /**
     * Radio Buttons for the second shot status
     */
    private RadioGroup secondShotRadioGroup;
    /**
     * Second shot date text box
     */
    private EditText secondShotDateEdt;
    /**
     * Booster shot prompt
     */
    private TextView boosterTextView;
    /**
     * Radio Buttons for the booster shot status
     */
    private RadioGroup boosterRadioGroup;
    /**
     * Booster shot date text box
     */
    private EditText boosterShotDateEdt;

    /**
     * The button the user clicks to create their profile
     */
    private Button createButton;

    /**
     * Tracks and is activated upon any text input events for phoneNumberEdt
     */
    private PhoneNumberFormattingTextWatcher phoneNumberFormatter;
    /**
     * Tracks and is activated upon any text input events for input fields
     */
    private TextWatcher nameFormatter;
    private TextWatcher ageFormatter;
    private TextWatcher pronounsFormatter;
    private TextWatcher genderFormatter;
    private TextWatcher religionFormatter;
    private TextWatcher politicalFormatter;
    private TextWatcher sexualOrientationFormatter;
    private TextWatcher lookingForFormatter;



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
        //TODO: autofill out input with saved data
        if (!(requireActivity() instanceof com.example.datingconsent.ui.ProfileCreator)) {
            nameEdt.setText(MainActivity.getProfile().getName());
        }

            //Instantiate the name text box and build its formatter (enhanced TextWatcher)
        nameEdt = view.findViewById(R.id.profile_name_edittext);
        nameFormatter= buildInputFormatter(nameEdt);
        nameEdt.addTextChangedListener(nameFormatter);

        //Instantiate the age text box and build its formatter (enhanced TextWatcher)
        ageEdt = view.findViewById(R.id.profile_age_edittext);
        ageFormatter = buildNumberFormatter(ageEdt);
        ageEdt.addTextChangedListener(ageFormatter);

        //Instantiate the pronouns text box and build its formatter (enhanced TextWatcher)
        pronounsEdt = view.findViewById(R.id.profile_pronouns_edittext);
        pronounsFormatter =  buildInputFormatter(pronounsEdt);
        pronounsEdt.addTextChangedListener(pronounsFormatter);

        //Instantiate the phone number text box and build its formatter (enhanced PhoneNumberFormattingTextWatcher)
        phoneNumberEdt = view.findViewById(R.id.profile_phone_number_edittext);
        buildPhoneNumberFormatter();
        phoneNumberEdt.addTextChangedListener(phoneNumberFormatter);

        //Instantiate the other gender text box and build its formatter (enhanced TextWatcher)
        genderOtherEdt = view.findViewById(R.id.profile_gender_other_edittext);
        genderFormatter = buildInputFormatter(genderOtherEdt);
        genderOtherEdt.addTextChangedListener(genderFormatter);

        //Instantiate the religion text box and build its formatter (enhanced TextWatcher)
        religionEdt = view.findViewById(R.id.profile_religion_edittext);
        religionFormatter = buildInputFormatter(religionEdt);
        religionEdt.addTextChangedListener(religionFormatter);

        //Instantiate the political text box and build its formatter (enhanced TextWatcher)
        politicalViewEdt = view.findViewById(R.id.profile_political_edittext);
        politicalFormatter = buildInputFormatter(politicalViewEdt);
        politicalViewEdt.addTextChangedListener(politicalFormatter);

        //Instantiate the other sexual orientation text box and build its formatter (enhanced TextWatcher)
        sexualOrientationOtherEdt = view.findViewById(R.id.profile_sexual_orientation_other_edittext);
        sexualOrientationFormatter = buildInputFormatter(sexualOrientationOtherEdt);
        sexualOrientationOtherEdt.addTextChangedListener(sexualOrientationFormatter);

        //Instantiate the other looking for text box and build its formatter (enhanced TextWatcher)
        lookingForOtherEdt = view.findViewById(R.id.profile_looking_for_other_edittext);
        lookingForFormatter = buildInputFormatter(lookingForOtherEdt);
        lookingForOtherEdt.addTextChangedListener(lookingForFormatter);

        //Vaccination prompts
        secondShotTextView = view.findViewById(R.id.second_shot_textview);
        boosterTextView = view.findViewById(R.id.booster_shot_textview);

        //Date Picker Text Box for shots
        vaccinatedDateEdt = view.findViewById(R.id.vaccinated_date_edittext);
        secondShotDateEdt = view.findViewById(R.id.second_shot_date_edittext);
        boosterShotDateEdt = view.findViewById(R.id.booster_date_edittext);
        vaccinatedDateEdt.setInputType(InputType.TYPE_NULL);
        secondShotDateEdt.setInputType(InputType.TYPE_NULL);
        boosterShotDateEdt.setInputType(InputType.TYPE_NULL);

        vaccinatedDateEdt.setOnClickListener(v -> {
            final Calendar calendar=Calendar.getInstance();
            String date = vaccinatedDateEdt.getText().toString();
            String[] dateParts = date.split("/");

            int day = date.isEmpty() ? calendar.get(Calendar.DAY_OF_MONTH): Integer.parseInt(dateParts[1]);
            int month = date.isEmpty() ? calendar.get(Calendar.MONTH):Integer.parseInt(dateParts[0]);
            int year = date.isEmpty() ? calendar.get(Calendar.YEAR):Integer.parseInt(dateParts[2]);
            DatePickerDialog picker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view1, int year, int month, int dayOfMonth) {
                    vaccinatedDateEdt.setText(format(Locale.US,"%d/%d/%d",  month + 1,dayOfMonth, year));
                }
            }, year, month, day);
            picker.getDatePicker().setMaxDate(System.currentTimeMillis());
            picker.show();
        });
        secondShotDateEdt.setOnClickListener(v -> {
            final Calendar calendar=Calendar.getInstance();
            String date = secondShotDateEdt.getText().toString();
            String[] dateParts = date.split("/");

            int day = date.isEmpty() ? calendar.get(Calendar.DAY_OF_MONTH): Integer.parseInt(dateParts[1]);
            int month = date.isEmpty() ? calendar.get(Calendar.MONTH):Integer.parseInt(dateParts[0]);
            int year = date.isEmpty() ? calendar.get(Calendar.YEAR):Integer.parseInt(dateParts[2]);
            DatePickerDialog picker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view12, int year, int month, int dayOfMonth) {
                    secondShotDateEdt.setText(format(Locale.US,"%d/%d/%d",  month + 1,dayOfMonth, year));
                }
            }, year, month, day);
            picker.getDatePicker().setMaxDate(System.currentTimeMillis());
            picker.show();
        });
        boosterShotDateEdt.setOnClickListener(v -> {
            final Calendar calendar=Calendar.getInstance();
            String date = boosterShotDateEdt.getText().toString();
            String[] dateParts = date.split("/");

            int day = date.isEmpty() ? calendar.get(Calendar.DAY_OF_MONTH): Integer.parseInt(dateParts[1]);
            int month = date.isEmpty() ? calendar.get(Calendar.MONTH):Integer.parseInt(dateParts[0]);
            int year = date.isEmpty() ? calendar.get(Calendar.YEAR):Integer.parseInt(dateParts[2]);
            DatePickerDialog picker = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view13, int year, int month, int dayOfMonth) {
                    boosterShotDateEdt.setText(format(Locale.US,"%d/%d/%d",  month + 1,dayOfMonth, year));
                }
            }, year, month, day);
            picker.getDatePicker().setMaxDate(System.currentTimeMillis());
            picker.show();
        });
        //Vaccination status choices
        vaccinatedRadioGroup = view.findViewById(R.id.vaccinated_radio_group);
        secondShotRadioGroup = view.findViewById(R.id.second_shot_radio_group);
        boosterRadioGroup = view.findViewById(R.id.booster_radio_group);

        vaccinatedRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.vaccinated_yes_radio_button) {
                    vaccinatedDateEdt.setVisibility(View.VISIBLE);
                    secondShotTextView.setVisibility(View.VISIBLE);
                    secondShotRadioGroup.setVisibility(View.VISIBLE);
                }
                else {
                    vaccinatedDateEdt.setVisibility(View.GONE);
                    secondShotTextView.setVisibility(View.GONE);
                    secondShotRadioGroup.setVisibility(View.GONE);
                    secondShotDateEdt.setVisibility(View.GONE);
                    boosterTextView.setVisibility(View.GONE);
                    boosterRadioGroup.setVisibility(View.GONE);
                    boosterShotDateEdt.setVisibility(View.GONE);
                }
            }
        });

        secondShotRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.second_yes_radio_button) {
                    secondShotDateEdt.setVisibility(View.VISIBLE);
                    boosterTextView.setVisibility(View.VISIBLE);
                    boosterRadioGroup.setVisibility(View.VISIBLE);
                }
                else {
                    secondShotDateEdt.setVisibility(View.GONE);
                    boosterTextView.setVisibility(View.GONE);
                    boosterRadioGroup.setVisibility(View.GONE);
                }
            }
        });

        boosterRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.booster_yes_radio_button)
                    boosterShotDateEdt.setVisibility(View.VISIBLE);
                else
                    boosterShotDateEdt.setVisibility(View.GONE);
            }
        });

        //region Spinners
        //Instantiate gender spinner
        String[] gender_array = getResources().getStringArray(R.array.gender_array);
        String[] sexual_orientation_array = getResources().getStringArray(R.array.sexual_orientation_array);
        String[] looking_for_array = getResources().getStringArray(R.array.looking_for_array);

        genderSpinner = (Spinner) view.findViewById(R.id.gender_spinner);
        sexualOrientationSpinner = (Spinner) view.findViewById(R.id.sexual_orientation_spinner);
        lookingForSpinner = (Spinner) view.findViewById(R.id.looking_for_spinner);

        buildSpinnerAdapter(gender_array,genderSpinner,genderOtherEdt);
        buildSpinnerAdapter(sexual_orientation_array,sexualOrientationSpinner,sexualOrientationOtherEdt);
        buildSpinnerAdapter(looking_for_array,lookingForSpinner,lookingForOtherEdt);
        //endregion


        //Instantiate the buttons:
        createButton = view.findViewById(R.id.profile_create_button);



        //***SET ONCLICK METHODS FOR THE BUTTONS***
        //If this fragment is used by ProfileCreator:
        if (!(requireActivity() instanceof com.example.datingconsent.ui.ProfileCreator)) {
            //TODO Populate fields with Profile
            ((TextView)(view.findViewById(R.id.profile_title_textview))).setText(R.string.profile_title_textview_text_alternate);
            createButton.setText(R.string.done);
        }
        createButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    createButtonHandler(v);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * Implement spinner adapter with custom spinner template
     * set the hint gray
     * and enable other EditText when "Other" is selected
     * @param array
     * @param spinner
     * @param otherEdt
     */
    private void buildSpinnerAdapter(String[] array, Spinner spinner, EditText otherEdt){
        final List<String> itemList = new ArrayList<>(Arrays.asList(array));
        final ArrayAdapter<String> adapter = new ArrayAdapter<String>(requireActivity().getBaseContext(),
                R.layout.custom_spinner_items,itemList){
            @Override
            public boolean isEnabled(int position){
                // Disable the first item from Spinner
                // First item will be use for hint
                return position != 0;
            }
            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent){
                View view = super.getDropDownView(position, convertView,parent);
                TextView tv = (TextView) view;
                if(position ==0){
                    tv.setTextColor(Color.GRAY); //set Gender hint as gray
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
        spinner.setAdapter(adapter);
        //Track spinner selection
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                otherEdt.setVisibility(View.GONE);
                if(position== 4) //Enable other EditText when Other is selected
                    otherEdt.setVisibility(View.VISIBLE);
                if(position>0){ //Color the current selection black instead of gray
                    ((TextView)parent.getChildAt(0)).setTextColor(Color.BLACK);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /**
     * Only used if the parent activity is ProfileCreator.
     * Handles any click event for the "Create Profile" button (profile_create_button).
     * If the EditText entries are valid, they get assigned to their respective MainActivity.profile
     * fields and the activity finishes.
     * If any EditText entry is not valid,
     * or any Spinner is not selected,
     * or any RadioGroup is not selected
     * an error message will appear as Toast message.
     * @param view The button being clicked.
     */
    private void createButtonHandler(View view) throws ParseException {
        boolean missing;
        String name = nameEdt.getText().toString();
        String age = ageEdt.getText().toString();
        String pronouns = pronounsEdt.getText().toString();
        String phoneNumber = phoneNumberEdt.getText().toString();
        String gender = genderSpinner.getSelectedItem().toString();
        if (gender.equals(getResources().getStringArray(R.array.gender_array)[4]))
            gender = genderOtherEdt.getText().toString();
        else if(gender.equals(getResources().getStringArray(R.array.gender_array)[0]))
            gender ="";
        String religion = religionEdt.getText().toString();
        String sexualOrientation = sexualOrientationSpinner.getSelectedItem().toString();
        if (sexualOrientation.equals(getResources().getStringArray(R.array.sexual_orientation_array)[4]))
            sexualOrientation = sexualOrientationOtherEdt.getText().toString();
        else if(sexualOrientation.equals(getResources().getStringArray(R.array.sexual_orientation_array)[0]))
            sexualOrientation = "";
        String lookingFor = lookingForSpinner.getSelectedItem().toString();
        if(lookingFor.equals(getResources().getStringArray(R.array.looking_for_array)[4]))
            lookingFor=lookingForOtherEdt.getText().toString();
        else if(lookingFor.equals(getResources().getStringArray(R.array.sexual_orientation_array)[0]))
            lookingFor="";
        String politicalView = politicalViewEdt.getText().toString();
        boolean vaccinated = vaccinatedRadioGroup.getCheckedRadioButtonId() == R.id.vaccinated_yes_radio_button;
        boolean second = secondShotRadioGroup.getCheckedRadioButtonId() == R.id.second_yes_radio_button;
        boolean booster = boosterRadioGroup.getCheckedRadioButtonId() == R.id.booster_yes_radio_button;
        Date vaccinatedDate;
        Date secondDate;
        Date boosterDate;

        //Check for missing fields or unselected fields
        if(missing = name.isEmpty())
            Toast.makeText(requireActivity(), R.string.profile_name_missing, Toast.LENGTH_LONG).show();
        else if(missing = age.isEmpty())
            Toast.makeText(requireActivity(), R.string.profile_age_missing, Toast.LENGTH_LONG).show();
        else if(missing = pronouns.isEmpty())
            Toast.makeText(requireActivity(), R.string.profile_pronouns_missing, Toast.LENGTH_LONG).show();
        else if(missing = phoneNumber.isEmpty())
            Toast.makeText(requireActivity(), R.string.profile_phone_number_missing, Toast.LENGTH_LONG).show();
        else if(missing = genderSpinner.getSelectedItemPosition()==0)
            Toast.makeText(requireActivity(), R.string.profile_gender_not_selected, Toast.LENGTH_LONG).show();
        else if(missing = gender.isEmpty())
            Toast.makeText(requireActivity(), R.string.profile_gender_missing, Toast.LENGTH_LONG).show();
        else if(vaccinated){
            if(missing = vaccinatedDateEdt.getText().toString().isEmpty()) //vaccinated date is not there
            Toast.makeText(requireActivity(), R.string.vaccinated_date_missing, Toast.LENGTH_LONG).show();
            else if (second) {
                if (missing = secondShotDateEdt.getText().toString().isEmpty())
                    Toast.makeText(requireActivity(), R.string.second_date_missing, Toast.LENGTH_LONG).show();
                else if (booster) {
                    if (missing = boosterShotDateEdt.getText().toString().isEmpty())
                        Toast.makeText(requireActivity(), R.string.booster_shot_date_missing, Toast.LENGTH_LONG).show();
                }
            }
        }

        //TODO:Prepare dates for setting
        if(!missing) {

            SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy", Locale.US);
            try {
                if(vaccinated) {
                    vaccinatedDate = format.parse(vaccinatedDateEdt.getText().toString());
                    MainActivity.getProfile().setVaccinationDate(vaccinatedDate);
                }
                if(second) {
                    secondDate = format.parse(secondShotDateEdt.getText().toString());
                    MainActivity.getProfile().setSecondShotDate(secondDate);
                }
                if(booster) {
                    boosterDate = format.parse(boosterShotDateEdt.getText().toString());
                    MainActivity.getProfile().setBoosterDate(boosterDate);
                }
            } catch (ParseException e) {
                Log.e("ProfileCreator: ", "Date Parsing Error");
            }

            Log.d("ProfileCreator: ",name);
        MainActivity.getProfile().setName(name);
        MainActivity.getProfile().setAge(Integer.parseInt(age));
        MainActivity.getProfile().setPronouns(pronouns);
        MainActivity.getProfile().setPhoneNumber(phoneNumber);
        MainActivity.getProfile().setGender(gender);
        MainActivity.getProfile().setReligion(religion);
        MainActivity.getProfile().setSexOrientation(sexualOrientation);
        MainActivity.getProfile().setLooking(lookingFor);
        MainActivity.getProfile().setPoliticalView(politicalView);
        MainActivity.getProfile().setVaccinated(vaccinated);
        MainActivity.getProfile().setSecondShot(second);
        MainActivity.getProfile().setBoosterShot(booster);


        Intent returnIntent = new Intent();
        requireActivity().setResult(Activity.RESULT_OK, returnIntent);
        requireActivity().finish();

        }
    }







    /**
     * Builds field phoneNumberFormatter, of type PhoneNumberFormattingTextWatcher.
     * Ensures that the phone number entered into the EditText is valid.
     */
    private void buildPhoneNumberFormatter() {
        phoneNumberFormatter = new PhoneNumberFormattingTextWatcher();
    }


    /**
     * Builds field numberFormatter, of type TextWatcher.
     * Ensures that the inputted value are only digits
     */
    private TextWatcher buildNumberFormatter(EditText inputEdt) {
        return new TextWatcher() {
        private boolean backspaceFlag; //Set to true if the user is erasing a character.
        private boolean editedFlag;
        private int cursorPos;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            //Assign the cursor position to the location the edit took place.
            cursorPos = s.length() - inputEdt.getSelectionStart();

            backspaceFlag = count > after;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int count, int after)
        {}

        @Override
        public void afterTextChanged(Editable s) {
            String number = s.toString();
            //Get rid of all characters that are not number:
            number = number.replaceAll("[^\\d]", "");
            if (!editedFlag) {
                if (!backspaceFlag) {
                    editedFlag = true;
                    inputEdt.setText(number);
                    inputEdt.setSelection(inputEdt.getText().length() - cursorPos);
                }
            }
            else
                editedFlag = false;
        }
    };
    }

    /**
     * Builds field buildInputFormatter, of type TextWatcher.
     * Ensures that the text entered into the EditText is valid by
     * allowing only alphabetical characters, spaces, and hyphens to be entered.
     */
    private TextWatcher buildInputFormatter(EditText inputEdt){
        //Set to true if the user is erasing a character.
        //Assign the cursor position to the location the edit took place.
        //Get rid of all characters that are not alphabetical, whitespace, or hyphens:
        return new TextWatcher() {
            private boolean backspaceFlag; //Set to true if the user is erasing a character.
            private boolean editedFlag;
            private int cursorPos;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Assign the cursor position to the location the edit took place.
                cursorPos = s.length() - inputEdt.getSelectionStart();

                backspaceFlag = count > after;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = s.toString();
                //Get rid of all characters that are not alphabetical, whitespace, hyphens, or slash:
                input = input.replaceAll("[^a-zA-z\\s-/]", "");
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