package net.teamIdea.StreamSavr;

/**
 * Created by IntelliJ IDEA.
 * User: beala
 * Date: Oct 31, 2010
 * Time: 12:06:11 PM
 * To change this template use File | Settings | File Templates.
 */
public class Tweet {
    String statusText; //Text of the tweet.
    String sid; //Unique status ID.
    String date; //Date of the tweet.

    public Tweet(String sid, String date, String statusText)
    {
        this.statusText = statusText;
        this.date = date;
        this.sid = sid;
    }

    public String getStatus(){ return statusText; }    
    /* Returns the unique status ID of the tweet as a string */
    public String getSid() {return sid;}
    public String getDate() {return date;}
}
