package net.teamIdea.StreamSavr;

import twitter4j.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static net.teamIdea.StreamSavr.TwitterUtils.getTweetsDownloaded;
import static net.teamIdea.StreamSavr.TwitterUtils.setTweetsDownloaded;

/**
 * Created by IntelliJ IDEA.
 * User: alex
 * Date: Nov 23, 2010
 * Time: 4:55:04 PM
 * To change this template use File | Settings | File Templates.
 */

/* Description; this class contains the functions needed to download all a user's tweets from twitter via the twit API.*/
public class TweetGetter {

    public static final int MAX_TRIES = 4; //Max number of times we hit twitter before giving up.

    /* Description: gets all of the users tweets and returns them as a tweet list
     * Arguments: twitter, the twitter object of the current user.
     * Return: A List<Status> containing all of the user's tweets (up to 3200).
     */
    public List<Status> getAllTweets(Twitter twitter, HttpServletRequest request) throws IllegalStateException {
        List<Status> toArchive = new ArrayList<Status>(); //Where all the tweets are copied too.
        ResponseList<Status> tweets; //Set of 0-200 tweets returned by Twitter.
        Integer downloadedTweets=0; //Start with 0 tweets downloaded
        setTweetsDownloaded(request, downloadedTweets);

        int currentPage = 0; //Start with the first page.
        //get a maximum of 17 pages. Twitter only stores 3,200 tweets so this should be more than enough.
        while(currentPage < 16) {
            //Gets a single page of tweets, and increment the currentPage
            tweets = getPage(twitter, new Paging(++currentPage, 200), 0);

            //If twitter retuns no tweets, you're done.
            if(tweets == null || tweets.isEmpty()) break;

            toArchive.addAll(tweets); //add the downloaded tweets to the archive.

            //Update the number of tweets downloaded for use in the status bar.
            downloadedTweets = (Integer) getTweetsDownloaded(request);
            downloadedTweets += tweets.size();
            setTweetsDownloaded(request, downloadedTweets);
        }

        return toArchive;
    }

    /* Description: Recursive function that gets a single page worth of tweets (usually 200).
     *              The function will stop recursing once we've gotten to MAX_TRIES.
     */
    private ResponseList<Status> getPage(Twitter twitter, Paging page, int currentTry) throws IllegalStateException {
        ResponseList<Status> tweets = null;
        if(currentTry < MAX_TRIES) {
            try {
                tweets = twitter.getUserTimeline(page);
            }
            catch(TwitterException e) {
                //If we get an error 502, try hitting twitter again (ie recurse).
                System.out.println("Exception:" + e.getStatusCode());
                if ( shouldRetry(e.getStatusCode()) && hitsRemain(twitter) ) {
                    System.out.println("Error. Trying again.");
                    tweets = getPage(twitter, page, ++currentTry); //Recurse (ie try getting the page again)
                }
                else {
                    System.out.println("Unexpected error status code: " + e.getStatusCode());
                    e.printStackTrace();
                }
            }
        }
        else {
            System.out.println("Hit Max Tries: " + MAX_TRIES);
        }

        return tweets;
    }

    /* Description: Returns true if, given the error code supplied, we should try to hit the twit API again. */
    private boolean shouldRetry(int errorCode) {

        /* 401 Unauthorized: Authentication credentials were missing or incorrect.
         * 500 Internal Server Error: Something is broken.  Please post to the group so the Twitter team can investigate.
         * 502 Bad Gateway: Twitter is down or being upgraded.
         * 503 Service Unavailable: The Twitter servers are up, but overloaded with requests. Try again later.
         */

        return (errorCode == 502 || errorCode == 503 || errorCode == 401 || errorCode==500);

    }

    /* Description: Returns true if there is 1 or more twitter API hits left */
    private boolean hitsRemain(Twitter twitter) {
        try {
            if( twitter.getRateLimitStatus().getRemainingHits() > 0)
                return true;
        } catch (TwitterException e) {
            e.printStackTrace();
        }

        return false;
    }
}
