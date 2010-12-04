package net.teamIdea.StreamSavr;
import twitter4j.Status;
import twitter4j.Tweet;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.lang.StringBuffer;
import java.util.List;


/**
 * Created by IntelliJ IDEA.
 * User: clemendr
 * Date: Oct 31, 2010
 * Time: 11:30:55 AM
 * To change this template use File | Settings | File Templates.
 */
public class CreateCSV {
    //Define some pulic variables that we will use
    private List<Status> source;
    private Status tweet;
    byte[] csvBytes;

    //Here is a function that maybe useful
    public byte[] getCSVBytes() {
        return csvBytes;
    }

    //This creates the CSV file from a list of tweet objects
    public byte[] createCSV(List data)
    {
        //Define the variable that I will use
        String csvInMemory = new String();
        source = data;
        String intermediate;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");

            csvInMemory += "Date, Tweet, \n";

        //Run through all the tweets and add them to the CSV file
        for(int i=0; i < source.size(); i++)
        {
            //Get the current tweet object
            tweet = source.get(i);
            //Format the date of the tweet
            intermediate = df.format(tweet.getCreatedAt());
            //Add the date to the CSV but put it in quotes
            csvInMemory += addQuotes(intermediate);
            //Add a comma to denote the next column
            csvInMemory += ',';
            //Now get the text from the tweet
            intermediate = tweet.getText();
            //Add the text to the csv after it has been properly formated
            csvInMemory += modifyStringForCSV(intermediate);
            //End the current line in the csv file
            csvInMemory += '\n';
        }

        //Turn the string into a byte array
        this.csvBytes = csvInMemory.getBytes();
        //Return the byte array
        return this.csvBytes;
    }

    //This function modifies the text from the tweets so that it is CSV compatible
    private String modifyStringForCSV(String text)
    {
        String modified = new String(text);
        modified = modified.replaceAll("\"", "\"\"");
        return "\"" + modified + "\"";
    }

    //Just a function that adds quotes to text
    private String addQuotes(String text)
    {
        return "\"" + text + "\"";
    }

    
}
