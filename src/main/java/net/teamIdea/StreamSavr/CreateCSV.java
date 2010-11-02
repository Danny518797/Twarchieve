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
    private List<Status> source;
    private String sFileName;
    private Status tweet;
    private Writer output;
    private File file;
    byte[] csvBytes;


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

    public byte[] getCSVBytes() {
        return csvBytes;
    }

    public byte[] createCSV(List data)
    {
        String csvInMemory = new String();
        source = data;
        sFileName = source.get(0).getUser().getScreenName();
        String intermediate;
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");

        try
        {
            file = File.createTempFile((sFileName + ".csv"), ".tmp");
            output = new BufferedWriter(new FileWriter(file));
            output.write("Date, Tweet, \n");
            csvInMemory += "Date, Tweet, \n";
            //output.write(',');
            //output.write("Tweet");
            //output.write('\n');

            for(int i=0; i < source.size(); i++)
            {
                tweet = source.get(i);
                intermediate = df.format(tweet.getCreatedAt());

                csvInMemory += addQuotes(intermediate);
                output.write(addQuotes(intermediate));

                csvInMemory += ',';
                output.write(',');

                intermediate = tweet.getText();

                csvInMemory += modifyStringForCSV(intermediate);
                output.write(modifyStringForCSV(intermediate));

                csvInMemory += '\n';
                output.write('\n');
            }
            output.close();
        }
        catch (IOException e){

        }

        this.csvBytes = csvInMemory.getBytes();
        return this.csvBytes;

        //return true;

    }

    private String modifyStringForCSV(String text)
    {
        String modified = new String(text);
        modified = modified.replaceAll("\"", "\"\"");
        return "\"" + modified + "\"";
    }

    private String addQuotes(String text)
    {
        return "\"" + text + "\"";
    }


    
}
