package com.example.datingconsent.surveyresources;

import java.io.*;

import com.google.gson.*;

//Todo: edit Survey Class
/** Represents a survey.
 */
public class Survey
{
    /** Number of questions in the survey */
    private int numQuestions;
    /** Contains the text for the question */
    private String[] questions;
    /** Contains the text for the response */
    private String[] responses;
    /** Optional responses entered in a text box by the user */
    private String[] textboxResponses;


    /**
     * Constructor
     * @param numQuestions - The number of questions in the survey.
     * @throws IllegalArgumentException
     */
    public Survey(int numQuestions)
    {
        if (numQuestions <= 0)
            throw new IllegalArgumentException("Number of questions must be greater than 0.");

        this.numQuestions = numQuestions;
        questions = new String[numQuestions];
        responses = new String[numQuestions];
        textboxResponses = new String[numQuestions];
    }


    /**
     * Sets a question (in the questions array) at the specified index to the specified text.
     * @param index The index in the questions array where the question will be set.
     * @param question The text that the question will be set to.
     * @throws IllegalArgumentException - When the index is out of bounds of the questions array.
     */
    public void setQuestion(int index, String question)
    {
        if (index < numQuestions)
            questions[index] = question;
        else
            throw new IllegalArgumentException("Index must be within allowable question range.");
    }


    /**
     * Sets a response (in the responses array) at the specified index to the specified text.
     * @param index The index in the responses array where the response will be set.
     * @param response The text that the response will be set to.
     * @throws IllegalArgumentException - When the index is out of bounds of the responses array.
     */
    public void setResponse(int index, String response)
    {
        if (index < numQuestions)
            responses[index] = response;
        else
            throw new IllegalArgumentException("Index must be within allowable question range.");
    }


    /**
     * Sets a text response (in the textResponses array) at the specified index to the specified text.
     * @param index The index in the textResponses array where the text response will be set.
     * @param textResponse The text that the text response will be set to.
     * @throws IllegalArgumentException - When the index is out of bounds of the textResponses array.
     */
    public void setTextboxResponse(int index, String textResponse)
    {
        if (index < numQuestions)
            textboxResponses[index] = textResponse;
        else
            throw new IllegalArgumentException("Index must be within allowable question range.");
    }


    /**
     * Getter method for variable numQuestions.
     * @return The number of questions in the survey.
     */
    public int getNumQuestions()
    {
        return numQuestions;
    }


    /**
     * Getter method for a specified question.
     * @return The String at the specified index of the questions array.
     */
    public String getQuestion(int index)
    {
        if (index >= numQuestions)
            return null;
        else
            return questions[index];
    }


    /**
     * Getter method for a specified response.
     * @return The String at the specified index of the responses array.
     */
    public String getResponse(int index)
    {
        if (index >= numQuestions)
            return null;
        else
            return responses[index];
    }


    /**
     * Getter method for a specified text response.
     * @return The String at the specified index of the textResponses array.
     */
    public String getTextboxResponse(int index)
    {
        if (index >= numQuestions)
            return null;
        else
            return textboxResponses[index];
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


    @Override
    public String toString() {
        String str = "";
        for (int i = 0; i < numQuestions; i++) {
            str += questions[i] + "\n" + responses[i] + "\n";
            if (textboxResponses[i] != null && !textboxResponses[i].isEmpty())
                str += textboxResponses[i] + "\n";
        }
        return str;
    }
}
