package net.teamIdea.StreamSavr;

import java.util.ArrayList;
import java.util.List;
import twitter4j.Tweet;

/**
 * Created by IntelliJ IDEA.
 * User: beala
 * Date: Oct 31, 2010
 * Time: 11:31:22 AM
 * To change this template use File | Settings | File Templates.
 */
public class TweetList {

    String username; //User name of the user.
    String uid; //Unique user ID.

    List<Tweet> tweetList = new ArrayList();
    int tweetListLength = 0;
    
    /* Returns the tweet object at the nth place in the tweetList, where 'tweetToGet' is set the nth place */
    public Tweet getTweet(int tweetToGet)
    {
        return tweetList.get( tweetToGet );
    }

    /*
    * Description: Adds a tweet to the end of the tweetList
    * Return: Returns the element number of the tweet just added. ie, first element is 0, 2nd is 1, etc.
    */
    public int addTweet(Tweet tweetToAdd)
    {
        tweetList.add(tweetToAdd);

        ++tweetListLength; //increment the size of the tweetList

        return tweetList.size() - 1; //return the element number of the tweet just added. ie, first element is 0, 2nd is 1, etc.
    }

    /*Uninteresting setters and getters below...*/

    public int getSize()
    {
        return tweetListLength;
    }

    /* User name setter */
    public void setUsername(String username)
    {
        this.username = username;
    }

    /* UID setter */
    public void setUid(String uid)
    {
        this.uid = uid;
    }

    /* tweetList setter */
    public void setTweetList(List<Tweet> tweetList)
    {
        this.tweetList = tweetList;
    }

    public String getUsername(){ return username; }
    public String getUid(){ return uid; }

}
