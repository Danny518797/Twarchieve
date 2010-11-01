package net.teamIdea.StreamSavr;
import twitter4j.Status;
import twitter4j.Tweet;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.File;
import java.io.Writer;
import java.lang.Long;
import java.util.Date;
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
    private List<Status> source;
    private String sFileName;
    private Status tweet;
    private Writer output;
    private File file;


    public String getFilename()
    {
        if (file.isFile())
            return file.getName();
        else
            return null;
    }

    public File getFile()
    {
        if (file.isFile())
            return file;
        else
            return null;
    }

    public String getLocation()
    {
        if (file.isFile())
            return file.getPath();
        else
            return null;
    }

    public boolean deleteFile()
    {
        return file.delete();
    }

    public boolean createCSV(List data)
    {
        source = data;
        sFileName = source.get(0).getUser().getScreenName();
        String intermediate;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");

        try
        {
            file = File.createTempFile((sFileName + ".csv"), ".tmp");
            output = new BufferedWriter(new FileWriter(file));
            output.write("Date");
            output.write(',');
            output.write("Tweet");
            output.write('\n');

            for(int i=0; i < source.size(); i++)
            {
                tweet = source.get(i);
                intermediate = df.format(tweet.getCreatedAt());
                output.write(addQuotes(intermediate));
                output.write(',');
                intermediate = tweet.getText();
                output.write(modifyStringForCSV(intermediate));
                output.write('\n');
            }
        }
        catch (IOException e){

        }
        return true;

    }

    private String modifyStringForCSV(String text)
    {
        StringBuffer modified = new StringBuffer(text);
        char[] quotes = new char['"'];
        String Quotes = new String(quotes, 0,1);
        int currentLocation = 0;
        int lastLocation = 0;
        if (modified.indexOf(Quotes) != -1)
        {
            while (currentLocation != -1)
            {
                currentLocation = modified.indexOf(Quotes,lastLocation);
                if (currentLocation != -1)
                {
                    modified.append(quotes, currentLocation, 1);
                }
                lastLocation = currentLocation+1;
            }
        }
        modified.append(quotes, 0, 1);
        modified.append('"');
        return modified.toString();
        
    }

    private String addQuotes(String text)
    {
        StringBuffer modified = new StringBuffer(text);
        char[] quotes = new char['"'];
        modified.append(quotes, 0, 1);
        modified.append('"');
        return modified.toString();

    }


    
}
