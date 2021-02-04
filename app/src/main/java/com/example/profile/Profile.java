package com.example.profile;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import com.google.gson.Gson;


/**
 * Represents a user profile for the app.
 * Stores the user's first and last names, phone number, and COVID preferences (a survey).
 */
public class Profile
{
    /** First name of the user */
    private String firstName;
    /** Last name of the user */
    private String lastName;
    /** Phone number of the user */
    private String phoneNumber;
    /** The user's preference survey responses */
    private Survey preferences;


    /**
     * Zero argument constructor (All fields initialized to null)
     */
    public Profile() {}


    /**
     * Constructor for all public fields except preferences
     * @param firstName
     * @param lastName
     * @param phoneNumber
     */
    public Profile(String firstName, String lastName, String phoneNumber)
    {
        this.firstName = firstName;
        this.lastName = lastName;
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
     * @param preferences
     */
    public Profile(String firstName, String lastName, String phoneNumber, Survey preferences)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.preferences = preferences;
        if (phoneNumber.matches("^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]?\\d{3}[\\s.-]?\\d{4}$"))
            this.phoneNumber = phoneNumber;
        else
            this.phoneNumber = null;
    }


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
