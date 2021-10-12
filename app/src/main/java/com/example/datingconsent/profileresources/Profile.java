package com.example.datingconsent.profileresources;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import com.google.gson.Gson;


/**
 * Represents a user profile for the app.
 * Stores the user's personal information and their preferences .
 */
public class Profile
{
    /** First name of the user */
    private String firstName;
    /** Last name of the user */
    private String lastName;
    /** Phone number of the user */
    private String phoneNumber;
    /**Gender of the user */
    private String gender;
    /**Sexual orientation of the user */
    private String sexOrientation;
    /**Pronouns of the user */
    private String pronouns;
    /**Age of the user */
    private int age;
    /**Religion of the user */
    private String religion;
    /**Political View of the user */
    private String politicalView;
    /**Looking for status of the user */
    private String looking;
    /**COVID-19 vaccination status of the user */
    private boolean vaccinated;
    private Date vaccinationDate;
    /**COVID-19 booster shot status of the user */
    private boolean booster;
    private Date boosterDate;
    /** The user's preference survey responses */
    private Survey preferences;

    //region Constructors
        /**
         * Zero argument constructor (All fields initialized to null)
         */
    public Profile() {
    }


        /**
         * Constructor for all public fields except preferences
         * @param firstName
         * @param lastName
         * @param phoneNumber
         * @param gender
         * @param sexOrientation
         * @param pronouns
         * @param age
         * @param religion
         * @param politicalView
         * @param looking
         * @param vaccinated
         * @param vaccinationDate
         * @param booster
         * @param boosterDate
         */
    public Profile(String firstName, String lastName, String phoneNumber,
            String gender, String sexOrientation, String pronouns,
        int age, String religion, String politicalView, String looking,
        boolean vaccinated, Date vaccinationDate,boolean booster, Date boosterDate) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.sexOrientation = sexOrientation;
        this.pronouns = pronouns;
        this.age = age;
        this.religion = religion;
        this.politicalView = politicalView;
        this.looking = looking;
        this.vaccinated = vaccinated;
        this.vaccinationDate = vaccinationDate;
        this.booster = booster;
        this.boosterDate = boosterDate;
        this.preferences = null;
        if (phoneNumber.matches("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$"))
            this.phoneNumber = phoneNumber;
        else
            this.phoneNumber = null;

    }


        /**
         * Constructor for all public fields.
         * @param firstName
         * @param lastName
         * @param phoneNumber
         * @param gender
         * @param sexOrientation
         * @param pronouns
         * @param age
         * @param religion
         * @param politicalView
         * @param looking
         * @param vaccinated
         * @param vaccinationDate
         * @param booster
         * @param boosterDate
         * @param preferences
         */
    public Profile(String firstName, String lastName, String phoneNumber,
            String gender, String sexOrientation, String pronouns, int age, String religion, String politicalView, String looking,
        boolean vaccinated, Date vaccinationDate,boolean booster, Date boosterDate, Survey
        preferences) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.sexOrientation = sexOrientation;
        this.pronouns = pronouns;
        this.age = age;
        this.religion = religion;
        this.politicalView = politicalView;
        this.looking = looking;
        this.vaccinated = vaccinated;
        this.vaccinationDate = vaccinationDate;
        this.booster = booster;
        this.boosterDate = boosterDate;
        this.preferences = preferences;
        if (phoneNumber.matches("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$"))
            this.phoneNumber = phoneNumber;
        else
            this.phoneNumber = null;

    }
    //endregion

    //region Setter Methods
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }


    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }


    public void setPreferences(Survey preferences)
    {
        this.preferences = preferences;
    }


    public void setPhoneNumber(String phoneNumber)
    {
        if (phoneNumber.matches("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$"))
            this.phoneNumber = phoneNumber;
    }

    public void setGender(String gender)
    {
        this.gender = gender;
    }
    public void setSexOrientation(String sexOrientation)
    {
        this.sexOrientation = sexOrientation;
    }
    public void setPronouns(String pronouns)
    {
        this.pronouns = pronouns;
    }
    public void setAge(int age)
    {
        this.age = age;
    }
    public void setReligion(String religion)
    {
        this.religion = religion;
    }
    public void setPoliticalView(String politicalView)
    {
        this.politicalView = politicalView;
    }
    public void setLooking(String looking)
    {
        this.looking = looking;
    }
    public void setVaccinated(boolean vaccinated)
    {
        this.vaccinated = vaccinated;
    }
    public void setVaccinationDate(Date vaccinationDate)
    {
        this.vaccinationDate = vaccinationDate;
    }
    public void setBooster(boolean booster)
    {
        this.booster = booster;
    }
    public void setBoosterDate(Date boosterDate )
    {
        this.boosterDate = boosterDate;
    }
    //endregion

    //region Getter Methods
    public String getFirstName()
    {
        return firstName;
    }


    public String getLastName()
    {
        return lastName;
    }


    public String getPhoneNumber()
    {
        return phoneNumber;
    }


    public Survey getPreferences()
    {
        return preferences;
    }

    public String getGender() {
        return gender;
    }

    public String getSexOrientation() {
        return sexOrientation;
    }

    public String getPronouns() {
        return pronouns;
    }

    public int getAge() {
        return age;
    }

    public String getReligion() {
        return religion;
    }

    public String getPoliticalView() {
        return politicalView;
    }

    public String getLooking() {
        return looking;
    }

    public boolean isVaccinated() {
        return vaccinated;
    }

    public Date getVaccinationDate() {
        return vaccinationDate;
    }

    public boolean isBooster() {
        return booster;
    }

    public Date getBoosterDate() {
        return boosterDate;
    }



    //endregion

    /**
     * Saves the current instance to a JSON file
     * @param path The path that the file will be located in
     * @param fileName The name the file will be saved to
     */
    public void saveToJson(String path, String fileName)
    {
        try
        {
            File jsonFile = new File(path, fileName);
            if (jsonFile.exists())
                jsonFile.delete();
            jsonFile.createNewFile();

            FileWriter writer = new FileWriter(jsonFile);
            Gson gson = new Gson();
            String jsonStr = gson.toJson(this);

            writer.write(jsonStr);
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
